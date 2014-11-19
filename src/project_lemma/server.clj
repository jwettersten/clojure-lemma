(ns project-lemma.server
  (:gen-class))

(require '[clojure.java.io :as io])
(import '[java.net ServerSocket])

(defn hello [name]
  (str "Hello, " name))

(defn receive
  "read a line of text data from the socket"
  [socket]
  (.readLine (io/reader socket)))

(defn send
  "echo the string back to the socket"
  [socket msg]
  (let [writer (io/writer socket)]
    (.write writer msg)
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
