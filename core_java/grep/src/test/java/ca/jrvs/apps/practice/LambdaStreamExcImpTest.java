package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;

class LambdaStreamExcImpTest {
  LambdaStreamExcImp testInstance = new LambdaStreamExcImp();
  String[] arr = {"pls", "work"};
  int[] intArr = {1,2,3,4,5};
  @Test
  void createStrStream() {
    List<String> expected = Arrays.stream(arr).collect(Collectors.toList());

    assertEquals(expected, testInstance.createStrStream(arr).collect(Collectors.toList()));
  }

  @Test
  void toUpperCase() {
    //Stream<String> strStream = testInstance.createStrStream(arr);
    String[] arr2 = {"PLS", "WORK"};
    List<String> expected = Arrays.stream(arr2).collect(Collectors.toList());
    assertEquals(expected, testInstance.toUpperCase(arr).collect(Collectors.toList()));
  }

  @Test
  void filter() {
    String[] arr2 = {"pls"};
    List<String> expected = Arrays.stream(arr2).collect(Collectors.toList());
    Stream<String> arrStream = testInstance.createStrStream(arr);
    assertEquals(expected, testInstance.filter(arrStream, "or").collect(Collectors.toList()));
  }

  @Test
  void createIntStream() {
    IntStream expected = Arrays.stream(intArr);
    assertEquals(expected.boxed().collect(Collectors.toList()), testInstance.createIntStream(intArr).boxed().collect(Collectors.toList()));
  }

  @Test
  void toList() {
    Stream<String> strStream = Arrays.stream(arr);
    Stream<String> strStream2 = Arrays.stream(arr);
    List<String> expected = strStream2.collect(Collectors.toList());
    assertEquals(expected, testInstance.toList(strStream));
  }

  @Test
  void intStreamToList() {
    IntStream intStream = Arrays.stream(intArr);
    IntStream intStream2 = Arrays.stream(intArr);
    List<Integer> expected = intStream2.boxed().collect(Collectors.toList());
    assertEquals(expected, testInstance.toList(intStream));
  }

  @Test
  void rangeCreateIntStream() {
    IntStream intStream = Arrays.stream(intArr);
    List<Integer> expected = intStream.boxed().collect(Collectors.toList());
    assertEquals(expected, testInstance.createIntStream(1,5).boxed().collect(Collectors.toList()));
  }

  @Test
  void squareRootIntStream() {
    int[] intArr1 = {4,9,16};
    double[] intArr2 = {2.0,3.0,4.0};
    List<Double> expected = Arrays.stream(intArr2).boxed().collect(Collectors.toList());
    assertEquals(expected, testInstance.squareRootIntStream(Arrays.stream(intArr1)).boxed().collect(Collectors.toList()));
  }

  @Test
  void getOdd() {
    int[] intArr1 = {1,3,5};
    List<Integer> expected = Arrays.stream(intArr1).boxed().collect(Collectors.toList());
    assertEquals(expected, testInstance.getOdd(Arrays.stream(intArr)).boxed().collect(Collectors.toList()));
  }

  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  @Test
  void getLambdaPrinter() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(out));
    String[] messages = {"work"};
    Consumer<String> printer = testInstance.getLambdaPrinter("pls ", "!");
    testInstance.printMessages(messages, printer);
    assertEquals("pls work!", out.toString().trim());
    System.setOut(originalOut);
  }

  @Test
  void printOdd() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(out));
    IntStream intStream = testInstance.createIntStream(1,2);
    IntStream oddStream = testInstance.createIntStream(1,1);
    Consumer<String> printer = testInstance.getLambdaPrinter("odd: ", "!");
    testInstance.printOdd(intStream, printer);
    assertEquals("odd: 1!",out.toString().trim());
    System.setOut(originalOut);
  }

  @Test
  void flatNestedInt() {
    List<List<Integer>> intsNested = Arrays.asList(
        Arrays.asList(1),
        Arrays.asList(2),
        Arrays.asList(3));
    List<Integer> expList = Arrays.asList(1,4,9);
    Stream<List<Integer>> lsStream = intsNested.stream();
    List<Integer> expected = expList.stream().collect(Collectors.toList());
    assertEquals(expected, testInstance.flatNestedInt(lsStream).collect(Collectors.toList()));
  }
}