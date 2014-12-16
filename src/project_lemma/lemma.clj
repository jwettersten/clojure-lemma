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

(defn setup-noam-connection
  "Called once discovery is made to setup TCP services to begin communication with noam"
  [polo-response]
  (println "polo response: " polo-response)
  )

(defn init
  [lemma-id topic-handlers]
  ;first setup udp discovery broadcast
  (def udp-socket (udp/create-udp-socket 0))
  (def marco (udp/receive-discovery-loop udp-socket setup-noam-connection))
  (while @marco
    (udp/send-broadcast udp-socket "[\"marco\",\"guest1\",\"clojure-noam\",\"clojure\",\"1.6.0\"]" "127.0.0.1" 1030)
    (Thread/sleep 2000)
    (println "in marco loop...")
    )
;  (def hearing (tcp-server/serve 4423 json-handler/read-msg-in topic-handlers))
;  (tcp-client/send-message "127.0.0.1" 7733 (json-handler/package-message (message/create-registration-message "guest1" 4423 (keys topic-handlers) [] "clojure" "1.6.0")))
;  {:send-event (fn [topic value] (send-event "127.0.0.1" 7733 lemma-id topic value)) :stop [hearing]}
  )
