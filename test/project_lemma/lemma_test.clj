(ns project-lemma.lemma-test
  (:require [clojure.test :refer :all]
            [project-lemma.lemma :refer :all]))

(deftest test-send-event-function
  (testing "send event function returns"
           (let [register (init "guest1" "127.0.0.1" 7733 {"topic1" (fn [topic value] (str "topic1 handler topic: " topic " and value: " value))})]
             (is (function? (:send-event register)))
             (shutdown (:stop register))
           )))

