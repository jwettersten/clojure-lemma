(ns project-lemma.tcp-client-test
  (:require [clojure.test :refer :all]
            [project-lemma.tcp-client :refer :all]))

(deftest empty-test
  (testing ""
           (is (= true true))))
