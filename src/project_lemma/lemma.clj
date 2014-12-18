(ns project-lemma.lemma
  (:gen-class))

(require '[project-lemma.server :as tcp-server]
         '[project-lemma.message :as message]
         '[project-lemma.message-handler-json :as json-handler]
         '[project-lemma.udp :as udp]
         '[project-lemma.tcp-client :as tcp-client])

; constants definitions
(def discovery-broadcast-ip "255.255.255.255")
(def discovery-broadcast-port 1030)
(def tcp-port 4423)
(def lemma-dialect "clojure")
(def lemma-version "0.2.1") ; version is determined by the noam-io build version used

(defn send-event
  [host port lemma-id topic value]
  (tcp-client/send-message host port (json-handler/package-message (message/create-event-message lemma-id topic value))))

(defn shutdown
  [[server-ref]]
  (reset! server-ref false)
  (println "stopping lemma from hearing...")
  (println "speak soon!"))

(defn locate-noam
  "Discovery process locating the noam server"
 [lemma-name noam-room callback]
  (def udp-socket (udp/create-udp-socket 0))
  (let [locating (atom true)]
    (future
    (while @locating
       (udp/send-broadcast udp-socket (json-handler/write-json (message/create-discovery-message lemma-name noam-room lemma-dialect lemma-version)) discovery-broadcast-ip discovery-broadcast-port)
       (Thread/sleep 2000)))
    (udp/receive-discovery-loop udp-socket callback locating json-handler/parse-payload)
    ))

(defn setup-communication
  [lemma-id noam-ip noam-port topic-handlers]
  ;setup "hearing" via tcp
  (def hearing (tcp-server/serve tcp-port json-handler/read-msg-in topic-handlers))
  ;register lemma with noam via tcp
  (tcp-client/send-message noam-ip noam-port (json-handler/package-message (message/create-registration-message lemma-id tcp-port (keys topic-handlers) [] lemma-dialect lemma-version)))
  ;return event sending functions and shutdown "stop" handlers
  {:send-event (fn [topic value] (send-event noam-ip noam-port lemma-id topic value)) :stop [hearing]}
  )

(defn start
  [lemma-name noam-room topic-handlers speaking-function]
  (locate-noam lemma-name noam-room
               (fn [{polo :type, noam-name :room-name, noam-port :port, noam-ip :ip}]
                 (let [register (setup-communication "guest1" noam-ip noam-port topic-handlers)]
                         (let [send-event (:send-event register)]
                           ;Trap ctrl-c to handle shutting down the lemma
                           (.addShutdownHook (Runtime/getRuntime)
                                             (Thread. (fn []
                                                        (println "shutting down...")
                                                        (shutdown (register :stop)))))
                           (speaking-function send-event))))))
