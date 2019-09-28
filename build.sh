#!/usr/bin/env sh
ls -l
chmod u+x gradlew
string=`./gradlew clean build`
if [[ $string == *"BUILD SUCCESSFUL"* ]]
then
  echo "0";
fi
