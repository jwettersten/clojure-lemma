(ns project-lemma.lemma-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [project-lemma.lemma :refer :all]))

(deftest test-send-message-event
  (testing "sending an event message"
           (let [[{:send-event :stop}] (lemma/init "guest1" {:topic1 (fn [topic value] (println topic value))})]
             (send-event  "topic4" 7)
             (stop)
             )
           (is ((lemma/send-event {:type "event" :guest "guest1" :topic "topic1" :value "dont' panic"} ))))))


