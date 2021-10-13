package edu.javacourse.studentorder.domain;

public class CityRegisterCheckerResponse {
  private boolean isExist;
  private Boolean temporal;

  public boolean isExist() {
    return isExist;
  }

  public void setExist(boolean exist) {
    isExist = exist;
  }

  public Boolean getTemporal() {
    return temporal;
  }

  public void setTemporal(Boolean temporal) {
    this.temporal = temporal;
  }
}
