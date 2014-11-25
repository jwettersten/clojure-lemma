(ns project-lemma.message-handler-json-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [project-lemma.message-handler-json :refer :all]))

(deftest test-msg-parse-payload-mismatch
         (testing "ensure nil response to non-matching payload count and data"
                 (let [test-json-reader (str "000000[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")]
                       (is (= nil (parse-payload test-json-reader))))))

(deftest test-msg-parse-payload-exception
         (testing "ensure parse payload fails gracefully"
                 (let [test-json-reader (str "0")]
                       (is (= nil (parse-payload test-json-reader))))))

(deftest test-msg-payload-match-pass
         (testing "comparing byte count with actual payload size"
                 (let [test-json-reader (str "000042[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")]
                       (is (= true (payload-match? test-json-reader))))))

(deftest test-msg-payload-mismatch
         (testing "ensure a failing byte count comparison"
                 (let [test-json-reader (str "000000[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")]
                       (is (= false (payload-match? test-json-reader))))))

(deftest test-msg-payload-match-parse-fail
         (testing "ensure parse exception fails gracefully"
                 (let [test-json-reader (str "000042event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")]
                       (is (= false (payload-match? test-json-reader))))))

(deftest test-extract-payload-values
  (testing "JSON msg data extraction"
           (is (= (extract-payload-message (str "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")) (json/read-str "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")))))

(deftest test-extract-payload-values-fail
  (testing "JSON msg data extraction"
           (is (= (extract-payload-message (str "event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")) nil ))))
