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
        continue;
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
    return CityRegisterValidator.checkCityRegister(studentOrder);
  }

  static AnswerWedding checkWedding(StudentOrder studentOrder) {
    return WeddingValidator.checkWedding(studentOrder);
  }

  static AnswerChildren checkChildren(StudentOrder studentOrder) {
    return ChildrenValidator.checkChildren(studentOrder);
  }

  static AnswerStudent checkStudent(StudentOrder studentOrder) {
    return StudentValidator.checkStudent(studentOrder);
  }

  private static void sendMail(StudentOrder studentOrder) {
  }
}
