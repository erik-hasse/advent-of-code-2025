(ns day7
  (:require [clojure.string :as str])
  (:require [clojure.set :as set]))

(def input
  (->>
    "inputs/day7"
    slurp
    str/split-lines))

(defn get-indices [s t]
  (set (keep-indexed (fn [i ch] (when (= ch t) i)) s)))


(defn accum-pairs [pairs]
  (reduce (fn [acc [k v]] (update acc v (fnil conj #{}) k))
          {} pairs))

(defn get-new-beam-map [split-beams]
  (accum-pairs (mapcat (fn [x] [[x (inc x)] [x (dec x)]]) split-beams)))

(defn get-new-timelines [old-timelines new-beams]
  (apply hash-map (mapcat
                    (fn [[k v]]
                      [k (reduce + (map #(get old-timelines % 0) v))])
                    (get-new-beam-map new-beams))))

(defn step [{splits :splits timelines :timelines} next]
  (let [splitters (get-indices next \^)
        existing-beams (set (keys timelines))
        split-locs (set/intersection splitters existing-beams)
        continuing-beams (set/difference existing-beams split-locs)
        existing-timelines (select-keys timelines continuing-beams)
        new-timelines (get-new-timelines timelines split-locs)
        all-timelines (merge-with + existing-timelines new-timelines)]
    {:splits (+ splits (count split-locs)) :timelines all-timelines}))

(defn run [input]
  (reduce step {:splits 0 :timelines (zipmap (get-indices (first input) \S) (repeat 1))} (rest input)))

(defn part1 [input]
  ((run input) :splits))

(println "Part 1:" (time (part1 input)))

(defn part2 [input]
  (reduce + (vals ((run input) :timelines))))

(println "Part 2:" (time (part2 input)))
