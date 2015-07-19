#!/usr/bin/env sh
ls -l
string=`ant test`
if [[ $string == *"BUILD SUCCESSFUL"* ]]
then
  echo "0";
fi
