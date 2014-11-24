(ns project-lemma.message-handler-json-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [project-lemma.message-handler-json :refer :all]))

(deftest test-msg-payload-count-pass
         (testing "comparing byte count with actual payload size"
                 (let [test-json-reader (str "000042[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")]
                       (assert (= true (parse-payload test-json-reader))))))


(deftest test-helloJSON
  (testing "helloJSON"
           (assert (= (helloJSON "{\"name\": \"jw\"}") "{\"greeting\":\"Hello!\",\"greeting-name\":\"jw\"}"))))
