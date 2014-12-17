(ns project-lemma.lemma
  (:gen-class))

(require '[project-lemma.server :as tcp-server]
         '[project-lemma.message :as message]
         '[project-lemma.message-handler-json :as json-handler]
         '[project-lemma.udp :as udp]
         '[project-lemma.tcp-client :as tcp-client])

(defn send-event
  [host port lemma-id topic value]
  (tcp-client/send-message host port (json-handler/package-message (message/create-event-message lemma-id topic value))))

(defn shutdown
  [[server-ref]]
  (reset! server-ref false)
  (println "stopping lemma from hearing...")
  (println "speak soon!"))

(defn locate-noam
 [broadcast-ip broadcast-port lemma-name noam-room-name callback]
  (def udp-socket (udp/create-udp-socket 0))
  (let [locating (atom true)]
    (udp/receive-discovery-loop udp-socket callback locating)
     (while @locating
       (udp/send-broadcast udp-socket (str "[\"marco\",\"" lemma-name "\",\"" noam-room-name "\",\"clojure\",\"0.1\"]") broadcast-ip broadcast-port)
       (Thread/sleep 2000))
   ))

(defn init
  [lemma-id noam-ip noam-port topic-handlers]
  ;setup "hearing" via tcp
  (def hearing (tcp-server/serve 4423 json-handler/read-msg-in topic-handlers))
  ;register lemma with noam via tcp
  (tcp-client/send-message noam-ip noam-port (json-handler/package-message (message/create-registration-message lemma-id 4423 (keys topic-handlers) [] "clojure" "0.1")))
  ;return event sending functions and shutdown "stop" handlers
  {:send-event (fn [topic value] (send-event noam-ip noam-port lemma-id topic value)) :stop [hearing]}
  )
