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

public class JavaGrepLambdaImp extends JavaGrepImp {

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
}