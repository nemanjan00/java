# Compile main

javac -cp src -d ./bin ./src/top/nemanja/project/Main.java

# Run main

java -cp ./bin top.nemanja.project.Main

# Delete old logs


if ls hs* 1> /dev/null 2>&1; then
	rm hs*
fi


