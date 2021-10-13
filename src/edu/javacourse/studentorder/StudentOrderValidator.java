package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.mail.MailSender;
import edu.javacourse.studentorder.validator.ChildrenValidator;
import edu.javacourse.studentorder.validator.CityRegisterValidator;
import edu.javacourse.studentorder.validator.StudentValidator;
import edu.javacourse.studentorder.validator.WeddingValidator;

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
    StudentOrder[] soArray = readStudentOrders();
    for (StudentOrder studentOrder : soArray) {
      System.out.println();
      checkOneOrder(studentOrder);
    }

  }

  public StudentOrder[] readStudentOrders() {
    StudentOrder[] soArray = new StudentOrder[3];
    for (int i = 0; i < soArray.length; i++) {
      soArray[i] = SaveStudentOrder.buildStudentOrder(i);

    }
    return soArray;
  }

  public void checkOneOrder(StudentOrder so) {
    AnswerCityRegister cityRegister = checkCityRegister(so);

    AnswerWedding weddingAnswer = checkWedding(so);
    AnswerChildren answerChildren = checkChildren(so);
    AnswerStudent answerStudent = checkStudent(so);

    sendMail(so);
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
