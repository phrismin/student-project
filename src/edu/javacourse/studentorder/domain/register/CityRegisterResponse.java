package edu.javacourse.studentorder.domain.register;

public class CityRegisterResponse {
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

  @Override
  public String toString() {
    return "CityRegisterResponse{" +
        "isExist=" + isExist +
        ", temporal=" + temporal +
        '}';
  }
}
