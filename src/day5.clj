(ns day5
  (:require [clojure.string :as str]))

(def parts
  (->
    "inputs/day5"
    slurp
    (str/split #"\n\n")))

(defn parse-range [x]
  (->>
    (str/split x #"-")
    (mapv parse-long)))

(def ranges
  (->>
    (first parts)
    str/split-lines
    (mapv parse-range)))

(def ids
  (->>
    (second parts)
    str/split-lines
    (mapv parse-long)))

(defn in-range [id [start stop]]
  (and (>= id start) (<= id stop)))

(defn in-any [id]
  (some true? (map (partial in-range id) ranges)))

(def part1 (count (keep  in-any ids)))
(println "Part 1:" (time part1))

(defn collapse-ranges [[[last-start last-stop] & rest :as full] [start stop]]
  (if (<= start (inc last-stop))
    (cons [last-start (max stop last-stop)] rest)
    (cons [start stop] full)))


(def sorted-ranges (sort ranges))
(def part2
  (reduce collapse-ranges [(first sorted-ranges)] (rest sorted-ranges)))

(defn get-size [[start stop]]
  (inc (- stop start)))
(println (reduce + (map get-size part2)))