rm -Rf build
mkdir build
javac -d build -cp src/ src/ricochet_robots/*.java -Xlint
java -cp build ricochet_robots.Main
