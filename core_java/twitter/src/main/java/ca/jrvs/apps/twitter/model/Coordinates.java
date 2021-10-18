package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinates {
  @JsonProperty("coordinates")
  private float[] coordinates;
  @JsonProperty("type")
  private String type;

  public String getCoordinates(int lat_long){
    return String.valueOf(coordinates[lat_long]);
  }
  public void setCoordinates(float[] coordinates) {
    this.coordinates = coordinates;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public int size() {
    return coordinates.length;
  }

}
