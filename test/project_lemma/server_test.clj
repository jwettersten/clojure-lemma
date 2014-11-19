(ns project-lemma.server-test
  (:require [clojure.test :refer :all]
            [project-lemma.server :refer :all]))

(deftest test-hello
  (testing "server exists"
           (assert (= (hello "jw") "Hello, jw"))))
