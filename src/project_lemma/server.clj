(ns project-lemma.server
  (:gen-class))

(require '[clojure.java.io :as io]
         '[project-lemma.message-handler-json :as json-handler])
(import '[java.net ServerSocket])

;Components of this module were informed by "Clojure Cookbook by Luke VanderHart and Ryan Neufeld (O’Reilly). Copyright 2014 Cognitect, Inc., 978-1-449-36617-9.”

(defn receive
  "retrieve incoming data from the socket with applied msg handler"
  [socket msg-handler]
  (let [reader (io/reader socket)]
       (msg-handler reader)))

(defn serve
  "persistence tcp server"
  [port msg-handler topic-handlers]
  (let [hearing (atom true)]
    (future
      (with-open [server-sock (ServerSocket. port)]
        (while @hearing
          (with-open [socket (.accept server-sock)]
            (let [msg-in (receive socket msg-handler)]
              (json-handler/apply-topic-handler msg-in topic-handlers))))))
    hearing))
