# Introduction
(50-100 words)
Discuss the design of each app. What does the app do? What technologies have you used? (e.g. core java, libraries, lambda, IDE, docker, etc..)

The Java Grep app is meant to mimic the Linux command line `grep` function, to
recursively search through the provided directory to find files containing a
user-defined keyword. The application reads through the files and outputs lines
containing the search term to the `out.txt` file. The project was completed using 
the IntelliJ Ultimate IDE, various methods were written using the Java 8 Lambda 
Stream API. The project was packaged using Maven and the Maven Shade plugin, and
uploaded to DockerHub as a Docker Image.
# Quick Start
The application has three arguments:
1. `<regex>` - The Java regular expression to describe the search pattern
2. `rootPath` - The root directory in which to conduct the search
3. `outFile` - The output file to store the lines matching the regex search pattern

To run the application:

    #Clean,compile,and package using Maven:
    mvn clean package

    #Run application locally: 
    java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepLambdaImp <regex> <rootPath> <outFile>
    
    #Run in Docker container: 
    docker run --rm docker_user/grep <regex> <rootPath> <outFile>

#Implemenation
## Pseudocode
At the core of the application is the `void process()` method, which calls various helper 
functions as outlined in the pseudocode below:
    
    void process() {
    fileList = listFiles(rootPath)
    List matchedLines;

    for file in fileList {
        for line in readLines(file) {
            if containsPattern(line) {
                matchedLines.add(line)
    }   }   }

    writeToFile(matchedLines);

## Helper Functions
Methods used as part of the `void process()` main method include:
- `listFiles(String rootDirectory)` - recursively lists all files within provided directory
- `readLines(File inputFile)` - reads each line within provided file
- `containsPattern(String line)` - determines whether the provided string contains the regex pattern
- `writeToFile(List<String> lines)` - writes matched lines to outFile.txt
## Performance Issue
When launched, the JVM is allocated a limited amount of memory. If this heap size
is smaller than the size of the `rootDirectory`, the application will run out of memory, 
and will throw `OutOfMemoryError`. This can be avoided by specifying the maximum
heap size by using the `-Xmx` option, followed by the desired heap size in MB.

# Test
Where applicable, methods were unit tested using junit in IntelliJ. Other methods
were tested on local directories and files to ensure all files in a directory were
found using the `listFiles()` method. The application as a whole was tested using
a sample directory, regex pattern, and output file, and the output was verified by
comparison with the results using the built in Linux `grep` command.

# Deployment
The finished project was packaged into a `.jar` file using the Maven build manager, 
and a Docker image was built using the `docker build` command. The Docker image was
uploaded to dockerHub, under `mitchellacton/grep`.

# Improvement
1. Format the output file with numbered lines and origin file names for easier readability
2. Provide an option for displaying the output to the terminal instead of writing to a file
3. Include additional options available with the built in `grep` command, such as `-n` to display the line number, and `-C` to display the lines before and after the matched line to provide some context