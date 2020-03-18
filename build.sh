#!/bin/bash

if [ ! -d ./build/build_files ]; then
	mkdir ./build/build_files
fi
javac -cp ./packages/*.jar ./src/* -d ./build/build_files/
ls -al ./src
cd packages
if [ ! -d output ]; then
        mkdir output
fi
cp ./*.jar ./output
cd output
for lib in ./*.jar; do
	jar xf "${lib}"
done
rm ./*.jar
for path in ./*; do
	if [[ -d "${path}" && "${path}" !=  "./META-INF" ]]; then
		cp -r "${path}" ../../build/build_files
	fi
done
cd ../../build/build_files
ls -al
jar -cvf ../ZSerial-1.0.0.0.jar ./*
