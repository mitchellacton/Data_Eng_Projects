package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return (T) m.readValue(json, clazz);
  }

  public static String toJson(Tweet tweet, boolean prettyJson, boolean includeNull) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    if (!includeNull) {
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    if (prettyJson) {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return mapper.writeValueAsString(tweet);
  }

}
