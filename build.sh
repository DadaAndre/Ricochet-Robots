# !/bin/bash

# Version 0.5.1
# Copyright 2020 Justine MARTIN, Lorada ANDRÉ
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
# The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

javaMainClass="ricochet_robots.Main"
jarName="Ricochet_Robots"
zipName="Ricochet_Robots.zip"

buildDir="build"
deployDir="dist"
javadocDir="javadoc"
libsDir=""
srcDir="src"
testDir="test"
resDir="res"

projectBuilded=false

# TODO : Additionnal folder to zip

zipProject() {
	cleanProject
	createJavadoc
	deployProject
	echo '>zip'

	tmpZipDir=.tmp_$(date +"%Y%m%d%H%M%S")/
	mkdir "$tmpZipDir"

	if [ -d "$srcDir" ]; then
		cp "$srcDir" "$tmpZipDir" -r
	fi

	if [ -d "$javadocDir" ]; then
		cp "$javadocDir" "$tmpZipDir" -r
	fi

	if [ -d "$testDir" ]; then
		cp "$testDir""$tmpZipDir" -r
	fi

	if [ -d "$deployDir" ]; then
		cp "$deployDir" "$tmpZipDir" -r
	fi

	if [ -d "$libsDir" ]; then
		cp "$libsDir" "$tmpZipDir" -r
	fi

	if [ -d "$resDir" ]; then
		cp "$resDir" "$tmpZipDir" -r
	fi

	cp "rapport" "$tmpZipDir" -r
	cp "$0" "$tmpZipDir" -r

	cd "$tmpZipDir"
	zip "$zipName" * -r
	mv "$zipName" ..
	cd ..

	rm -rf "$tmpZipDir"
}

showCommand() {
	echo "Usage : $0 <build|clean|deploy|javadoc|run|test|zip>" 1>&2
	exit 1
}

buildProject() {
	if [ "$projectBuilded" = true ] ; then
		return
	fi

	echo '>build'

	if [ ! -d "$buildDir" ]; then
		mkdir "$buildDir"
	fi

	updateLibs
	javac -cp "$srcDir$libs" -d "$buildDir" "$srcDir"/$(echo "$javaMainClass" | sed -E "s/\./\//g").java -Xlint

	if [ ! $? -eq 0 ]; then
		exit 2
	fi

	projectBuilded=true
}

cleanProject() {
	echo '>clean'

	if [ -d "$buildDir" ]; then
		rm -rf "$buildDir"
	fi

	if [ -d "$deployDir" ]; then
		rm -rf "$deployDir"
	fi

	if [ -d "$javadocDir" ]; then
		rm -rf "$javadocDir"
	fi

	if [ -f "$zipName" ]; then
		rm "$zipName"
	fi

	projectBuilded=false
}

createJavadoc() {
	echo '>javadoc'

	if [ ! -d "$javadocDir" ]; then
		mkdir "$javadocDir"
	fi

	updateLibs
	javadoc -subpackages $(echo "$javaMainClass" | sed -E "s/([^.]*)\..*/\1/") -d "$javadocDir" -cp "$srcDir""$libs"
}


deployProject() {
	buildProject
	echo '>deploy'

	if [ -d "$deployDir" ]; then
		rm -rf "$deployDir"
	fi

	mkdir "$deployDir"

	tmpManifest="/tmp/MANIFEST.MF"
	if [ -f "$tmpManifest" ]; then
		rm "$tmpManifest"
	fi

	echo "Main-Class:" "$javaMainClass" >> "$tmpManifest"
	if [ -d "$libsDir" ]; then
		echo "Class-Path: $(find "$libsDir" -name '*.jar' -printf '%p ')" >> "$tmpManifest"
	fi

	cd "$buildDir"
	jar cfm ../"$deployDir"/"$jarName".jar "$tmpManifest" $(find . -name '*.class')
	cd ..

	if [ -d "$resDir" ]; then
		cp "$resDir" "$deployDir" -r
	fi

	if [ -d "$libsDir" ]; then
		cp "$libsDir" "$deployDir" -r
	fi
}

runProject() {
	buildProject
	echo '>run'

	updateLibs
	java -cp "$buildDir$libs" "$javaMainClass" $*
}

testProject() {
	buildProject
	echo '>test'

	updateLibs

	tmpZipDir=.tmp_$(date +"%Y%m%d%H%M%S")/
	mkdir "$tmpZipDir"
	cd "$tmpZipDir"

	wget -q -O hamcrest-core-1.3.jar "https://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"
	wget -q -O junit-4.13.jar "https://search.maven.org/remotecontent?filepath=junit/junit/4.13/junit-4.13.jar"

	cd ..

	testLibs="$tmpZipDir"junit-4.13.jar:"$tmpZipDir"hamcrest-core-1.3.jar
	for classe in $(find "$testDir" -name '*.java');
	do
		javac -cp "$testDir""$libs":"$testLibs":"$srcDir" -d "$buildDir" "$classe" -Xlint
		java -cp "$buildDir":"$libs":"$testLibs" org.junit.runner.JUnitCore $(echo "$classe" | sed -E "s/\//./g; s/""$testDir""\.(.*)\.java/\1/")

		rm -f $(echo $classe | sed -E "s/""$testDir""\/(.*).java/""$buildDir""\/\1.class/")
	done

	rm -rf "$tmpZipDir"
}

updateLibs() {
	libs=""
	if [ -d "$libsDir" ]; then
		libs=$(find "$libsDir" -name '*.jar' -printf ':%p')
	fi
}

checkConfig() {
	if [ -z "$javaMainClass" ] || [ -z "$jarName" ] || [ -z "$buildDir" ] || [ -z "$deployDir" ] || [ -z "$javadocDir" ] || [ -z "$srcDir" ]; then
		echo "Merci de configurer les variables suivantes :"
		echo ""
		echo "- javaMainClass"
		echo "- jarName"
		echo "- buildDir"
		echo "- deployDir"
		echo "- javadocDir"
		echo "- srcDir"
		exit 3
	fi

	if [ ! -d "$srcDir" ]; then
		echo "Le dossier des sources n'existe pas"
		exit 3
	fi

	if [ ! -f "$srcDir"/$(echo "$javaMainClass" | sed -E "s/\./\//g").java ]; then
		echo "La classe principale n'existe pas"
		exit 3
	fi
}

checkConfig

if [ $# -lt 1 ]; then
	showCommand
fi

readonly BASE_DIR=$(pwd)

while [ ! -z $1 ]
do
	if [ $1 = "build" ]; then
		buildProject
	elif [ $1 = "clean" ]; then
		cleanProject
	elif [ $1 = "deploy" ]; then
		deployProject
	elif [ $1 = "javadoc" ]; then
		createJavadoc
	elif [ $1 = "run" ]; then
		args=""
		if [ ! -z $2 ] && [ "$2" = "--args" ]; then
			args=$3
			shift
			shift
		fi

		runProject $args
	elif [ $1 = "test" ]; then
		testProject
	elif [ $1 = "zip" ]; then
		zipProject
	else
		showCommand
	fi

	cd "$BASE_DIR"

	shift
done
