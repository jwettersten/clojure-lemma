(ns project-lemma.server-test
  (:require [clojure.test :refer :all]
            [project-lemma.server :refer :all]))

(deftest empty-test
  (testing ""
           (is (= true true))))
