(ns project-lemma.message-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [project-lemma.message :refer :all]))

(deftest test-message-event-type-mapping
  (testing "Event message type mapping"
           (let [json-msg (json/read-str "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")]
             (is (= (message {:type (first json-msg)} json-msg) {:type (get json-msg 0) :guest (get json-msg 1) :topic (get json-msg 2) :value (get json-msg 3)})))))

(deftest test-message-event-type-mapping-failure
  (testing "Ensure malformed event message type mapping fails gracefully"
           (let [json-msg (json/read-str "[]")]
             (is (= (message {:type (first json-msg)} json-msg) nil)))))
