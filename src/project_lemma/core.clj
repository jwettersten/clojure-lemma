(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.lemma :as lemma])

(defn topic-1-handler [topic value]
 (println topic value))

(defn set-interval [callback ms]
  (future
    (while true
      (do
        (Thread/sleep ms)
       (println "sending event" (callback))))))

(comment
(defn -main
  "Lemma standalone app entry point"
  [& args]
  (let [[{:send-event :stop}] (lemma/init "guest1" {:topic1 topic-1-handler})]
    (println ":send-event: " )
    ;loop through this event call, pausing n seconds in between
    (let [send-event-thread (set-interval #(send-event "topic1" "Don't Panic") 1000)]
    ;(send-event "topic1" "Don't Panic.")
    (.addShutdownHook (Runtime/getRuntime) (Thread. (fn []
                                                      (println "Shutting down...")
       (future-cancel send-event-thread)))))))
  )

(defn -main
  [& args]
  (let [sending (lemma/init "guest1" {:topic1 "topic1" :value "don't panic"})]
    (let [send-function (:send-event sending)]
      (let [send-event-thread (set-interval #(send-function "topic1" "Don't Panic") 1000)]
      ;(println "sending: " (send-function "topic1" "don't panic"))
        ))))

