package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.mail.MailSender;
import edu.javacourse.studentorder.validator.ChildrenValidator;
import edu.javacourse.studentorder.validator.CityRegisterValidator;
import edu.javacourse.studentorder.validator.StudentValidator;
import edu.javacourse.studentorder.validator.WeddingValidator;

public class SaveOrderValidator {
  public static void main(String[] args) {
    checkAll();
  }

  static void checkAll() {
    while (true) {
      StudentOrder studentOrder = readStudentOrder();

      if (studentOrder == null) {
        break;
      }

      AnswerCityRegister cityRegister = checkCityRegister(studentOrder);
      if (!cityRegister.success) {
//        continue;
        break;
      }

      AnswerWedding weddingAnswer = checkWedding(studentOrder);
      AnswerChildren answerChildren = checkChildren(studentOrder);
      AnswerStudent answerStudent = checkStudent(studentOrder);

      sendMail(studentOrder);
    }
  }


  static StudentOrder readStudentOrder() {
    return new StudentOrder();
  }

  static AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
    CityRegisterValidator validator1 = new CityRegisterValidator();
    validator1.hostName = "Host1";
    validator1.login = "Login1";
    validator1.password = "Password1";
    CityRegisterValidator validator2 = new CityRegisterValidator();
    validator2.hostName = "Host2";
    validator2.login = "Login2";
    validator2.password = "Password2";
    AnswerCityRegister answer1 = validator1.checkCityRegister(studentOrder);
    AnswerCityRegister answer2 = validator2.checkCityRegister(studentOrder);
    return answer1;
  }

  static AnswerWedding checkWedding(StudentOrder studentOrder) {
    WeddingValidator validator = new WeddingValidator();
    return validator.checkWedding(studentOrder);
  }

  static AnswerChildren checkChildren(StudentOrder studentOrder) {
    ChildrenValidator validator = new ChildrenValidator();
    return validator.checkChildren(studentOrder);
  }

  static AnswerStudent checkStudent(StudentOrder studentOrder) {
    StudentValidator validator = new StudentValidator();
    return validator.checkStudent(studentOrder);
  }


  static void sendMail(StudentOrder studentOrder) {
    new MailSender().sendMail(studentOrder);
  }
}
