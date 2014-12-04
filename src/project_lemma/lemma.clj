(ns project-lemma.lemma
  (:gen-class))

(require '[project-lemma.server :as server]
         '[project-lemma.message-handler-json :as json-handler]
         '[project-lemma.tcp-client :as tcp-client])

(defn init
  [& args]
  (server/serve-persistent 4423 json-handler/read)
  (tcp-client/send-registration "127.0.0.1" 7733))
