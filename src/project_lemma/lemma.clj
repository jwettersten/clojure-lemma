(ns project-lemma.lemma
  (:gen-class))

(require '[project-lemma.server :as server]
         '[project-lemma.message-handler-json :as json-handler]
         '[project-lemma.tcp-client :as tcp-client])

(defn send-event
  [lemma-id topic value]
  (json-handler/create-event-msg lemma-id topic value))

(defn init
  [lemma-id listeners]
  (server/serve-persistent 4423 json-handler/read)
  (tcp-client/send-registration "127.0.0.1" 7733)
  {:send-event (fn [topic value] (send-event lemma-id topic value)) :stop (fn [] ) }
 ;(println (send-event lemma-id topic value))
  )
