(ns project-lemma.message-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [project-lemma.message :refer :all]))

(deftest test-message-event-type-mapping
  (testing "Event message type mapping"
           (let [json-msg '["event","guest1","topic1", [1,2,"potato"]]]
             (is (= (message json-msg) {:type (get json-msg 0) :guest (get json-msg 1) :topic (get json-msg 2) :value (get json-msg 3)})))))

(deftest test-message-heartbeat-type-mapping
  (testing "Heartbeat message type mapping"
           (let [json-msg '["heartbeat","guest1"]]
             (is (= (message json-msg) {:type (get json-msg 0) :guest (get json-msg 1)})))))

(deftest test-message-event-type-mapping-failure
  (testing "Ensure malformed event message type mapping fails gracefully"
           (let [json-msg (json/read-str "[]")]
             (is (= (message json-msg) nil)))))

