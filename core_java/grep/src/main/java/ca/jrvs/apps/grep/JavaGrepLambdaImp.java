package ca.jrvs.apps.grep;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.regex.Pattern;

import java.util.List;
import java.io.File;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepLambdaImp implements JavaGrep{

final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

private String regex;
private String rootPath;
private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
    } catch (Exception ex) {
      javaGrepLambdaImp.logger.error("Error: unable to process", ex);
    }
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = listFiles(getRootPath()).stream()
        .map(this::readLines)
        .flatMap(Collection::stream)
        .filter(this::containsPattern)
        .collect(Collectors.toList());

    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    File rootDirectory = new File(rootDir);

    Arrays.stream(rootDirectory.listFiles()).forEach(file -> {
      if (file.isDirectory()) {
        fileList.addAll(listFiles(file.getAbsolutePath()));
      } else {
        fileList.add(file);
      }
    });
    return fileList;
  }

  @Override
  public List<String> readLines(File inputFile){
    List<String> lineList = new ArrayList<>();
    try {
      FileReader fileReader = new FileReader(inputFile);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String lines = bufferedReader.readLine();

      if (lines != null) {
        lineList.add(lines);
      } else {
        bufferedReader.close();
      }
    } catch (IOException ex){
      throw new RuntimeException("In readLines(" + inputFile + "), an IOException was thrown", ex);
    }
    return lineList;

  }

  @Override
  public void writeToFile(List<String> lines) throws IOException{
    FileWriter wr = new FileWriter(this.getOutFile());
    BufferedWriter bwr = new BufferedWriter(wr);

    for (String matchedLine : lines) {
      bwr.write(String.valueOf(matchedLine));
    }
    bwr.flush();
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(getRegex(), line);
  }

  // Getters and Setters
  public String getRootPath(){
    return rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public String getRegex(){
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getOutFile(){
    return outFile;
  }

  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}