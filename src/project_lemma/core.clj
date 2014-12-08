(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.lemma :as lemma])

; Define lemma n# of topic handlers
(defn topic-1-handler [topic value]
 (println "received " topic " with value: " value))

(defn topic-2-handler [topic value]
 (println "received " topic " with value: " value))

(defn topic-3-handler [topic value]
 (println "received " topic " with value: " value))

(defn -main
  [& args]
  (let [sending (lemma/init "guest1" {:topic1 topic-1-handler :topic2 topic-2-handler :topic3 topic-3-handler})]
    (let [send-function (:send-event sending)]
      ;Trap ctrl-c to handle shutting down the lemma
      (.addShutdownHook (Runtime/getRuntime)
                        (Thread. (fn [] (println "Shutting down..."))))
      (loop [x 10]
          (when (> x 1)
            (send-function "topic4" "Don't panic.")
            (send-function "topic5" "A towel.")
            (send-function "topic6" "42.")
            (Thread/sleep 1000)
            (recur (- x 1)))))))

