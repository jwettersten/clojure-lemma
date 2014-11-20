(ns project-lemma.message-handler-json-test
  (:require [clojure.test :refer :all]
            [project-lemma.message-handler-json :refer :all]))

(deftest test-helloJSON
  (testing "helloJSON"
           (assert (= (helloJSON "{\"name\": \"jw\"}") "{\"greeting\":\"Hello!\",\"greeting-name\":\"jw\"}"))))
