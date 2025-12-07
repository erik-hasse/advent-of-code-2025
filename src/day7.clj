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

(defn step [{splits :splits beams :beams} next]
  (let [splitters (get-indices next \^)
        split-locs (set/intersection splitters beams)
        continuing-beams (set/difference beams split-locs)
        new-beams (set (mapcat (fn [x] #{(inc x) (dec x)}) split-locs))
        all-beams (set/union continuing-beams new-beams)]
    {:splits (+ splits (count split-locs)) :beams all-beams}))

(defn part1 [input]
  ((reduce step {:splits 0 :beams (set (get-indices (first input) \S))} (rest input)) :splits))

(println "Part 1:" (time (part1 input)))


