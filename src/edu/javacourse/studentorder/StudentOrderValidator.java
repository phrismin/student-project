package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.mail.MailSender;
import edu.javacourse.studentorder.validator.ChildrenValidator;
import edu.javacourse.studentorder.validator.CityRegisterValidator;
import edu.javacourse.studentorder.validator.StudentValidator;
import edu.javacourse.studentorder.validator.WeddingValidator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {
  private CityRegisterValidator cityRegValidator;
  private WeddingValidator weddingValidator;
  private ChildrenValidator childrenValidator;
  private StudentValidator studentValidator;
  private MailSender mailSender;

  public StudentOrderValidator() {
    cityRegValidator = new CityRegisterValidator();
    weddingValidator = new WeddingValidator();
    childrenValidator = new ChildrenValidator();
    studentValidator = new StudentValidator();
    mailSender = new MailSender();
  }

  public static void main(String[] args) {
    StudentOrderValidator sov = new StudentOrderValidator();
    sov.checkAll();
  }

  public void checkAll() {
    List<StudentOrder> studentOrderList = readStudentOrders();
    for (StudentOrder studentOrder : studentOrderList) {
      System.out.println();
      checkOneOrder(studentOrder);
    }
  }

  public List<StudentOrder> readStudentOrders() {
    List<StudentOrder> studentOrderList = new LinkedList<>();
    for (int i = 0; i < studentOrderList.size(); i++) {
      StudentOrder studentOrder = SaveStudentOrder.buildStudentOrder(i);
      studentOrderList.add(studentOrder);
    }
    return studentOrderList;
  }

  public void checkOneOrder(StudentOrder so) {
    AnswerCityRegister cityAnswer = checkCityRegister(so);
//    AnswerWedding wedAnswer = checkWedding(so);
//    AnswerChildren childAnswer = checkChildren(so);
//    AnswerStudent studAnswer = checkStudent(so);

//    sendMail(so);
  }

  public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
    return cityRegValidator.checkCityRegister(studentOrder);
  }

  public AnswerWedding checkWedding(StudentOrder studentOrder) {
    return weddingValidator.checkWedding(studentOrder);
  }

  public AnswerChildren checkChildren(StudentOrder studentOrder) {
    return childrenValidator.checkChildren(studentOrder);
  }

  public AnswerStudent checkStudent(StudentOrder studentOrder) {
    return studentValidator.checkStudent(studentOrder);
  }

  public void sendMail(StudentOrder studentOrder) {
    mailSender.sendMail(studentOrder);
  }
}
