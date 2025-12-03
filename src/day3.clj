(ns day3
  (:require [clojure.string :as str]))

(def input
  (->>
    (slurp "inputs/day3")
    (str/trim)
    (str/split-lines)))

(defn get-max [x]
  (str (apply max (map ^[char] Character/getNumericValue x))))

(defn get-rest [x next-char]
  (apply str (drop (inc (str/index-of x next-char)) x)))

(defn get-next [x n]
  (if (= 1 n)
    [(get-max x)]
    (let
      [next-char (get-max (subs x 0 (- (count x) n -1)))]
      (concat [next-char] (get-next (get-rest x next-char) (dec n))))))

(defn get-joltage [n x]
  (parse-long (apply str (get-next x n))))

(defn solve [input n]
  (reduce + (map (partial get-joltage n) input)))


(println "Part 1: " (solve input 2))
(println "Part 2: " (solve input 12))
