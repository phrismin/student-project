package edu.javacourse.studentorder.domain;

import java.time.LocalDate;

public class Adult extends Person {
  private String passportSeries;
  private String passportNumber;
  private LocalDate issueDate;
  private String issueDepartment;
  private String university;
  private String id;

  public String getPassportSeries() {
    return passportSeries;
  }

  public void setPassportSeries(String passportSeries) {
    this.passportSeries = passportSeries;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public String getIssueDepartment() {
    return issueDepartment;
  }

  public void setIssueDepartment(String issueDepartment) {
    this.issueDepartment = issueDepartment;
  }

  public String getUniversity() {
    return university;
  }

  public void setUniversity(String university) {
    this.university = university;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
