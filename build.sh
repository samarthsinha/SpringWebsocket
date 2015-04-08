#!/usr/bin/env sh
string=`ant test`
if [[ $string == *"BUILD SUCCESSFUL"* ]]
then
  echo "0";
fi
