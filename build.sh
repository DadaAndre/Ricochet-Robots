rm -Rf build
mkdir build
javac -d build -cp src/ src/ricochet_robots/*.java -Xlint
cp src/images -r build/
java -cp build ricochet_robots.Main $@
