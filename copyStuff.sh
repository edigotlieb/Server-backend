#!/bin/sh

# run with sudo for pulling+ compiling 
# if password is needed: Pedig1506

git remote -v
git fetch https://github.com/edigotlieb/Server-backend.git +refs/heads/master:refs/remotes/GitHub/master
git merge GitHub/master
ant jar
cp -a preparedSQL/ dist/
