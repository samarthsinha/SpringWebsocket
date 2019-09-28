#!/usr/bin/env sh
ls -l
string=`./gradlew clean build`
if [[ $string == *"BUILD SUCCESSFUL"* ]]
then
  echo "0";
fi
