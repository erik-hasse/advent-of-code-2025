(ns day4b
  (:require [clojure.string :as str]))

(def input
  (->>
    "inputs/day4"
    slurp
    str/split-lines
    (mapv vec)))

(defn rebuild-grid [x]
  (str/join "\n" (map str/join x)))

(defn positions [grid]
  (for [y (range (count grid))
        x (range (count (first grid)))]
    [x y]))

(def deltas
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= [dx dy] [0 0])]
    [dx dy]))



(defn get-neighbors [grid x y]
  (map (fn [[dx dy]] (get-in grid [(+ y dy) (+ x dx)])) deltas))

(defn num-roll-neighbors [grid x y]
  (count (filter (partial = \@) (get-neighbors grid x y))))

(defn is-removable [data x y]
  (and (= \@ (get-in data [y x])) (< (num-roll-neighbors data x y) 4)))

(defn part1 [data]
  (->>
    (positions data)
    (map (fn [[x y]] (is-removable data x y)))
    (filter identity)
    count))

(println "Part 1:" (time (part1 input)))


(defn build-next [curr]
  (str/join (map (fn [[x y]] (if (is-removable curr x y) \. (get-in curr [y x]))) (positions curr))))

(defn do-step [[data removed]]
  [(build-next data) (part1 data)])

(defn part2 [data]
  (reduce + (take-while (complement zero?) (drop 1 (map #(nth % 1) (iterate do-step [data 0]))))))

(println "Part 2:" (time (part2 input)))
