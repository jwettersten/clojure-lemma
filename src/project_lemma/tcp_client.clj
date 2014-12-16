(ns project-lemma.tcp-client
  (:gen-class))

(require '[clojure.java.io :as io])
(import '[java.io StringWriter]
        '[java.net Socket])

;This module was informed by "Clojure Cookbook by Luke VanderHart and Ryan Neufeld (O’Reilly). Copyright 2014 Cognitect, Inc., 978-1-449-36617-9.”

(defn send-message
  "Sends passed in message from lemma to noam host server"
  [host port msg]
   (future
      (with-open [socket (Socket. host port)
                  writer (io/writer socket)
                  reader (io/reader socket)
                  response (StringWriter.)]
       (doto writer
         (.write msg)
         .flush
        )
       ;Need to remove this with proper solution
        (io/copy reader response)
        (str response))
    ))
