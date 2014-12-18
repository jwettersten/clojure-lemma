(ns project-lemma.udp
  (:gen-class))

(require '[clojure.data.json :as json])
(import '[java.net DatagramSocket
          DatagramPacket
          InetSocketAddress])

;This module was informed by and implemented from "Clojure Cookbook by Luke VanderHart and Ryan Neufeld (O’Reilly). Copyright 2014 Cognitect, Inc., 978-1-449-36617-9.”

(defn create-udp-socket
  "Initialize DatagramSocket"
  [port]
  (DatagramSocket. port))

(defn send-broadcast
    "Broadcast marco discovery message via DatagramSocket to the specified
    host and port."
    [^DatagramSocket socket msg host port]
    (let [payload (.getBytes msg)
                  length (alength payload)
                  address (InetSocketAddress. host port)
                  packet (DatagramPacket. payload length address)]
          (.send socket packet)))

(defn receive
  "Block until a UDP message is received and
  test for noam polo to return"
  [^DatagramSocket socket]
  (let [buffer (byte-array 512)
        packet (DatagramPacket. buffer 512)]
    (.receive socket packet)
    (def json-polo-msg (json/read-str (String. (.getData packet) 0 (.getLength packet))))
    ;noam response will be: "[\"polo\", \"clojure-noam\",7733]"
    (when (= (get json-polo-msg 0) "polo")
      (conj json-polo-msg (.getAddress packet))
      )))

(defn receive-discovery-loop
 "Continually monitor socket for incoming message via UDP
 and forward callback function with handlers to this and marco locating loops
 to be reset once registration process is complete"
  [socket callback locating]
  (let [receiving (atom true)]
    (future
      (while @receiving
        (callback (receive socket) locating receiving)))
    ))
