; see LICENSE file

(ns quil-cljs.core
  (:require [quil.core :as quil :include-macros true]
            [quil.middleware]))

(def initial-state
  {:hue 140
   :angle 0})

(defn setup []
  (quil/frame-rate 30)
  (quil/color-mode :hsb) ; :rgb is default
  initial-state)

#_(def update-state identity)
(defn update-state [state]
  #_state
  {:hue (-> (:hue state)
            (+ 0.7)
            (mod 255))
   :angle (-> (:angle state)
              (+ 0.05))})

(defn draw-state [state]
  (quil/background 40)
  ; top-left is 0,0
  ; bottom-right is w,h
  (let [w (quil/width)
        h (quil/height)
        center-x (/ w 2)
        center-y (/ h 2)
        angle (:angle state)
        x (* 150 (quil/cos angle))
        y (* 80 (quil/sin angle))]
    (quil/ellipse center-x center-y 20 20)
    (quil/with-translation [center-x center-y]
      (quil/with-fill [(:hue state) 140 120]
        (quil/ellipse x y 100 100)))))

(quil/defsketch the-sketch
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [quil.middleware/fun-mode])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start"))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
