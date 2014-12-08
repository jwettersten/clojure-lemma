(ns project-lemma.server
  (:gen-class))

(require '[clojure.java.io :as io]
         '[clojure.data.json :as json]
         '[project-lemma.message-handler-json :as json-handler])
(import '[java.net ServerSocket])

;Several components of this module were implemented from "Clojure Cookbook by Luke VanderHart and Ryan Neufeld (O’Reilly). Copyright 2014 Cognitect, Inc., 978-1-449-36617-9.”

(defn receive
  "read a line of text data from the socket"
  [socket msg-handler]
  (let [reader (io/reader socket)]
    (msg-handler reader)))

(defn send
  "echo the string back to the socket"
  [socket msg]
  (let [writer (io/writer socket)]
    (json/write msg writer)
    (.flush writer)))

(defn serve [port msg-handler topics]
  (let [running (atom true)]
    (future
      (with-open [server-sock (ServerSocket. port)]
                  (while @running
                    (with-open [sock (.accept server-sock)]
                      (let [msg-in (receive sock msg-handler)
                            msg-out msg-in]
                        (send sock msg-out))))))
    running))
