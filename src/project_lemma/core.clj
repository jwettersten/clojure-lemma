(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.lemma :as lemma])

; Define lemma n# of topic handlers
(defn topic1-handler [topic value]
 (println "topic1-handler received " topic " with value: " value))

(defn topic2-handler [topic value]
 (println "topic2-handler received " topic " with value: " value))

(defn topic3-handler [topic value]
 (println "topic3-handler received " topic " with value: " value))

(defn -main
  [& args]
  ; Locate noam
  ; Stop sending marco messages
  ; Setup and register lemma
  ; Send test event messages for 10 seconds
  (lemma/locate-noam "10.0.1.255" 1030 "guest1" "clojure-noam"
                     (fn [[polo noam-name noam-port noam-ip] locating]
                       (reset! locating false)
                       (let [register (lemma/init "guest1" noam-ip noam-port {"topic1" topic1-handler "topic2" topic2-handler "topic3" topic3-handler})]
                         (let [send-event (:send-event register)]
                           ;Trap ctrl-c to handle shutting down the lemma
                           (.addShutdownHook (Runtime/getRuntime)
                                             (Thread. (fn []
                                                        (println "shutting down...")
                                                        (lemma/shutdown (register :stop)))))
                           (loop [x 10]
                             (when (> x 1)
                               (send-event "topic4" "Don't panic.")
                               (send-event "topic5" "Bring a towel.")
                               (send-event "topic6" 42)
                               (Thread/sleep 1000)
                               (recur (- x 1))))))
                       )))

