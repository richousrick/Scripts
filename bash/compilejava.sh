#/bin/sh
#Author: Richousrick
#Description: takes a given *.java file and will produce a compiled .jar with run permissions
mkdir /tmp/compile &
cp $1 /tmp/compile/$1 &&
path=$(pwd) &&
cd /tmp/compile &&
javac $1 &&
name=${1%.*} &&
echo Main-Class: $name > manifest.txt &&
jar -cvfm $name.jar manifest.txt $name.class &&
chmod +x $name.jar
mv $name.jar $path/$name.jar &&
rm -r /tmp/compile
cd $path
rm $1
