package edu.javacourse.studentorder.domain;

public class Address {

  private String postIndex;
  private Street street;
  private String building;
  private String extension;
  private String apartment;

  public Address(String postIndex, Street street, String building,
                 String extension, String apartment) {
    this.postIndex = postIndex;
    this.street = street;
    this.building = building;
    this.extension = extension;
    this.apartment = apartment;
  }

  public String getPostIndex() {
    return postIndex;
  }

  public void setPostIndex(String postCode) {
    this.postIndex = postIndex;
  }

  public Street getStreet() {
    return street;
  }

  public void setStreet(Street street) {
    this.street = street;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getApartment() {
    return apartment;
  }

  public void setApartment(String apartment) {
    this.apartment = apartment;
  }
}
