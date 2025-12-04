(ns day4
  (:require [clojure.string :as str]))

(def input (slurp "inputs/day4"))
(def length (count (str/split-lines input)))
(def width (count (first (str/split-lines input))))
(def positions (for [j (range length) i (range width) ] [i j]))
(def data (str/replace input "\n" ""))

(defn rebuild-grid [x]
  (str/join "\n" (map str/join (partition length x))))

(defn build-pos-idx [x y]
  (if (or (>= x width) (>= y length) (< x 0) (< y 0))
    nil
    (+ (* width y) x)))

(defn get-item-at [data x y]
  (let [idx (build-pos-idx x y)]
    (if (nil? idx) \. (nth data idx))))

(defn gen-neighbors [x y]
  (for [i (range -1 2) j
        (range -1 2)
        :when (or (not= 0 i) (not= 0 j))]
    [(+ x i) (+ y j)]))

(defn get-neighbor-count [data x y]
  (count (filter (partial = \@) (map (fn [[a b]] (get-item-at data a b)) (gen-neighbors x y)))))

(defn is-removable [data x y]
  (and (= \@ (get-item-at data x y)) (< (get-neighbor-count data x y) 4)))

(defn part1 [data]
  (->>
    positions
    (map (fn [[x y]] (is-removable data x y)))
    (filter identity)
    count))

(println "Part 1:" (time (part1 data)))


(defn build-next [curr]
  (str/join (map (fn [[x y]] (if (is-removable curr x y) \. (get-item-at curr x y))) positions)))

(defn do-step [[data removed]]
  [(build-next data) (part1 data)])

(defn part2 [data]
  (reduce + (take-while (complement zero?) (drop 1 (map #(nth % 1) (iterate do-step [data 0]))))))

(println "Part 2:" (time (part2 data)))
