(ns project-lemma.tcp-client
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io])
(import '[java.io StringWriter]
        '[java.net Socket])

(defn send-message
  "Sends passed in message from lemma to noam host server"
  [host port msg]
 (let [running (atom true)]
   (future
     (with-open [sock (Socket. host port)
                 writer (io/writer sock)
                 reader (io/reader sock)
                 response (StringWriter.)]
       (.append writer msg)
       (.flush writer)
       (io/copy reader response)
       (str response)
       ))))

