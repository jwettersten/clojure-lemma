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

(defn -main
  [& args]
  ; Locate noam
  ; Setup and register lemma
  ; Send test event messages for 10 seconds
  ;
  ; remove passing in broadcast ip 255.255.255.255 and 1030 - shame on me
  ; locating, registering all needs to be encapsulated in lemma.
  (lemma/locate-noam "255.255.255.255" 1030 "guest1" "clojure-noam"
                     (fn [{polo :type, noam-name :room-name, noam-port :port, noam-ip :ip}]
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

