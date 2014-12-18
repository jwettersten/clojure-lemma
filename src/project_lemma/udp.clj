(ns project-lemma.udp
  (:gen-class))

(require '[project-lemma.message :as message])
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
  [^DatagramSocket socket udp-msg-handler]
  (let [buffer (byte-array 512)
        packet (DatagramPacket. buffer 512)]
    (.receive socket packet)
    (message/map-message-type (conj (udp-msg-handler (String. (.getData packet) 0 (.getLength packet))) (.getAddress packet)))
    )
  )

(defn receive-discovery-loop
 "Monitor socket for incoming message via UDP
 forward polo messages to callback function
 and stop (via reset) marco and polo message loops"
  [socket callback locating udp-msg-handler]
  (let [receiving (atom true)]
      (while @receiving
        (def polo-msg (receive socket udp-msg-handler))
        (when-not (nil? polo-msg)
          (callback polo-msg)
          (reset! locating false)
          (reset! receiving false)
          (.close socket))
        )))
