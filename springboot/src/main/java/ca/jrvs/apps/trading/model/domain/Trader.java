package ca.jrvs.apps.trading.model.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Trader implements Entity<Integer>{

  private Integer id;
  private String firstName;
  private String lastName;
  private Date dob;
  private String country;
  private String email;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Trader{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dob=" + dob +
        ", country='" + country + '\'' +
        ", email='" + email + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Trader)) {
      return false;
    }
    Trader trader = (Trader) obj;
    return Objects.equals(getId(), trader.getId()) && Objects.equals(
        getFirstName(), trader.getFirstName()) && Objects.equals(getLastName(),
        trader.getLastName()) && Objects.equals(getDob(), trader.getDob())
        && Objects.equals(getCountry(), trader.getCountry()) && Objects.equals(
        getEmail(), trader.getEmail());
  }
}
