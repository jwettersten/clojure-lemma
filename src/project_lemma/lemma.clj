(ns project-lemma.lemma
  (:gen-class))

(require '[project-lemma.server :as tcp-server]
         '[project-lemma.message :as message]
         '[project-lemma.message-handler-json :as json-handler]
         '[project-lemma.tcp-client :as tcp-client])

(defn send-event
  [host port lemma-id topic value]
  (tcp-client/send-message host port (json-handler/package-message (message/create-event-message lemma-id topic value))))

(defn init
  [lemma-id topic-listeners]
  (tcp-server/serve 4423 json-handler/read topic-listeners)
  (tcp-client/send-message "127.0.0.1" 7733 (json-handler/package-message (message/create-registration-message "guest1" 4423 ["topic1" "topic2" "topic3"] [] "clojure" "1.6.0")))
  {:send-event (fn [topic value] (send-event "127.0.0.1" 7733 lemma-id topic value)) :stop (fn [] ) }
  )
