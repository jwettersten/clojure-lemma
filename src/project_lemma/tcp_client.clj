(ns project-lemma.tcp-client
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io])
(import '[java.io StringWriter]
        '[java.net Socket])

(defn send-registration
  "Sends registration message from lemma to noam host server"
  [host port]
 (let [running (atom true)]
   (future
     (with-open [sock (Socket. host port)
                 writer (io/writer sock)
                 reader (io/reader sock)
                 response (StringWriter.)]
       (.append writer "000076[\"register\",\"guest1\",4423,[\"topic1\",\"topic2\",\"topic3\"],[],\"clojure\",\"1.6.0\"]")
       (.flush writer)
       (io/copy reader response)
       (str response)
    ))))

