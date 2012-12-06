# Clear the screen.
clear

# Remove all class files.
ls  -l ./*.class > /dev/null 2>&1
if [ "$?" = "0" ]; then
    rm *.class
fi

# Compile the java files.
javac *.java

# Run the driver.
if [ "$1" != "" ]; then
    if [ "$2" != "" ]; then
        if [ -e "$1" -a "$2" ]; then
            java $1 $2
        elif [ -e "$1" ]; then
            java $1
        elif [ -e "$2" ]; then
            java NeuralNetDriver $2
        fi
    else
        if [ -e "$1" ]; then
            java $1
        else
            java NeuralNetDriver
        fi
    fi
else
    java NeuralNetDriver
fi
