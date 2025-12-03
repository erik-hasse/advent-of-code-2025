(ns day3
  (:require [clojure.string :as str]))

(def input
  (->>
    "inputs/day3"
    slurp
    str/trim
    str/split-lines))

(defn get-max [s]
  (char (apply max (map int s))))

(defn remove-up-to [s c]
  (let [idx (str/index-of s c)]
    (subs s (inc idx))))

(defn pick-digits [x n]
  (if (= 1 n)
    [(get-max x)]
    (let
      [next-char (get-max (subs x 0 (- (count x) n -1)))]
      (cons next-char (pick-digits (remove-up-to x next-char) (dec n))))))

(defn get-joltage [n x]
  (parse-long (str/join (pick-digits x n))))

(defn solve [input n]
  (reduce + (map (partial get-joltage n) input)))

(println "Part 1: " (solve input 2))
(println "Part 2: " (solve input 12))
