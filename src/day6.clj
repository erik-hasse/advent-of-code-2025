(ns day6
  (:require [clojure.string :as str]))

(def part1-input
  (->>
    "inputs/day6"
    slurp
    str/split-lines
    (mapv #(str/split (str/trim %) #"\s+"))))
(def operations
  (peek part1-input))
(def numbers
  (mapv (partial mapv parse-long) (pop part1-input)))

(defn compute [op items]
  (reduce (ns-resolve *ns* (symbol op)) items))

(defn part1 [ops nums]
  (reduce + (map compute ops (apply map vector nums))))

(println "Part 1:" (time (part1 operations numbers)))

(def part2-input
  (->>
    "inputs/day6"
    slurp
    str/split-lines
    (mapv vec)
    (apply map vector)))

(defn all-space [x]
  ((complement every?) (partial = \space) x))

(defn parse-col [s]
  (parse-long (str/trim (str/join (pop s)))))

(defn build-chunks [data]
  (remove #((complement all-space) (first %)) (partition-by all-space data)))

(defn process-chunk [c]
  (mapv parse-col c))

(def part2-numbers (mapv process-chunk (build-chunks part2-input)))

(defn part2 [ops nums]
  (reduce + (map compute ops nums)))
part2-numbers
(println "Part 1:" (time (part2 operations part2-numbers)))
