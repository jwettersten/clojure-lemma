# cloure lemma

This repository is a work-in-progress to create a lemma library to connect clojure applications to [noam-io](https://github.com/noam-io).

## Installation

This lemma assumes an instance of the noam host server is installed and running. Follow the instructions at [noam-io](https://github.com/noam-io) to get started.

This project requires the installation of java (currently only tested on 1.7 and higher) and clojure.

Check your current version and update as necessary:

    $ java -version

The Leinengen clojure dependency management tool is being used to develop this lemma. You can learn more and install it from [Leinengen](http://leiningen.org/).

## Usage

Once Leiningen has been installed, clone this repo:

    $ git clone git@github.com:jwettersten/clojure-lemma.git

cd into the root project-lemma directory and run:

    $ lein trampoline run

'trampoline' causes 'lein' to exit it's java process once the clojure lemma process is up and running. 'trampoline' is recommended to ensure that ctrl-c triggers the lemma shutdown sequence on our lemma process vs. being caught by the running lein process.  

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
