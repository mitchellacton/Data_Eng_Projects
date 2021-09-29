package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class LambdaStreamExcImpTest {
  LambdaStreamExcImp testInstance = new LambdaStreamExcImp();
  String[] arr = {"pls", "work"};
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
  }

  @Test
  void toList() {
  }

  @Test
  void testToList() {
  }

  @Test
  void testCreateIntStream() {
  }

  @Test
  void squareRootIntStream() {
  }

  @Test
  void getOdd() {
  }

  @Test
  void getLambdaPrinter() {
  }

  @Test
  void printMessages() {
  }

  @Test
  void printOdd() {
  }

  @Test
  void flatNestedInt() {
  }
}