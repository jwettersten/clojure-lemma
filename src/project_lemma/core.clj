(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.lemma :as lemma])

; Define lemma n# of topic handlers
(defn topic1-handler [topic value]
  (def output (str "topic1-handler received " topic " with value: " value))
  (println output)
  output)

(defn topic2-handler [topic value]
  (def output (str "topic2-handler received " topic " with value: " value))
  (println output)
  output)

(defn topic3-handler [topic value]
  (def output (str "topic3-handler received " topic " with value: " value))
  (println output)
  output)

; Example event sending functionality
; Send test event messages for 10 seconds
(defn speak-loop
  [message-sender]
  (loop [x 10]
    (when (> x 1)
      (message-sender "topic4" "Don't panic.")
      (message-sender "topic5" "Bring a towel.")
      (message-sender "topic6" 42)
      (Thread/sleep 1000)
      (recur (- x 1)))))

(defn -main
  [& args]
 ; Start lemma passing in the following:
  ; lemma name ("guest1")
  ; noam server ("clojure-noam")
  ; "hearing" topic handlers ({"topic1" topic1-handler "topic2" topic2-handler "topic3" topic3-handler}
  ; "speaking" function (speak-loop)
  (lemma/start "guest1" "clojure-noam" {"topic1" topic1-handler "topic2" topic2-handler "topic3" topic3-handler} speak-loop))
