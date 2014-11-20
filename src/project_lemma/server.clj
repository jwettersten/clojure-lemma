(ns project-lemma.server
  (:gen-class))

(require '[clojure.java.io :as io]
         '[clojure.data.json :as json])
(import '[java.net ServerSocket])

(defn hello [name]
  (str "Hello, " name))

(defn helloJSON [name]
  (let [json-reader (json/read-str name :key-fn keyword)]
    (let [greeting-name (get json-reader :name)]
      (json/write-str {:greeting "Hello!" :greeting-name greeting-name}))))

(defn receive
  "read a line of text data from the socket"
  [socket]
  (let [reader (io/reader socket)]
    (json/read reader)))

(defn send
  "echo the string back to the socket"
  [socket msg]
  (let [writer (io/writer socket)]
    (json/write msg writer)
    (.flush writer)))

(defn serve-persistent [port handler]
  (let [running (atom true)]
    (future
      (with-open [server-sock (ServerSocket. port)]
                  (while @running
                    (with-open [sock (.accept server-sock)]
                      (let [msg-in (receive sock)
                            msg-out (handler msg-in)]
                        (send sock msg-out))))))
    running))
