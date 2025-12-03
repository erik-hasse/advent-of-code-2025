(ns day3
  (:require [clojure.string :as str]))

(def input
  (->>
    (slurp "inputs/day3")
    (str/trim)
    (str/split-lines)))

(defn get-max [x]
  (str (apply max (map ^[char] Character/getNumericValue x))))

(defn get-first [x]
  (get-max (subs x 0 (dec (count x)))))

(defn get-second [x first] (get-max (last (str/split x (re-pattern first) 2))))

(defn compute-joltage [x]
  (let [first (get-first x)
        second (get-second x first)]
    (parse-long (str first second))))

(defn part1 [input]
  (reduce + (map compute-joltage input)))

(part1 input)