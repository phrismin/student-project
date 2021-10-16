package edu.javacourse.studentorder.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrder {
  private long orderId;
  private LocalDateTime orderDate;
  private StudentOrderStatus orderStatus;
  private Adult husband;
  private Adult wife;
  private List<Child> children;
  private String marriageCertificateId;
  private RegisterOffice marriageOffice;
  private LocalDate marriageDate;

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public StudentOrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(StudentOrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Adult getHusband() {
    return husband;
  }

  public void setHusband(Adult husband) {
    this.husband = husband;
  }

  public Adult getWife() {
    return wife;
  }

  public void setWife(Adult wife) {
    this.wife = wife;
  }

  public void addChildren(Child child) {
    if (children == null) {
      children = new ArrayList<>(5);
    }
    children.add(child);
  }

  public List<Child> getChildren() {
    return children;
  }

  public String getMarriageCertificateId() {
    return marriageCertificateId;
  }

  public void setMarriageCertificateId(String marriageCertificateId) {
    this.marriageCertificateId = marriageCertificateId;
  }

  public RegisterOffice getMarriageOffice() {
    return marriageOffice;
  }

  public void setMarriageOffice(RegisterOffice marriageOffice) {
    this.marriageOffice = marriageOffice;
  }

  public LocalDate getMarriageDate() {
    return marriageDate;
  }

  public void setMarriageDate(LocalDate marriageDate) {
    this.marriageDate = marriageDate;
  }
}
