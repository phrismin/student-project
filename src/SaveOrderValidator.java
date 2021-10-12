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
    System.out.println("CityRegister is running");
    AnswerCityRegister answer = new AnswerCityRegister();
    answer.success = false;
    return answer;
  }

  static AnswerWedding checkWedding(StudentOrder studentOrder) {
    System.out.println("Wedding check is running");
    return new AnswerWedding();
  }

  static AnswerChildren checkChildren(StudentOrder studentOrder) {
    System.out.println("Children check is running");
    return new AnswerChildren();
  }

  static AnswerStudent checkStudent(StudentOrder studentOrder) {
    System.out.println("Student check id running");
    return new AnswerStudent();
  }

  private static void sendMail(StudentOrder studentOrder) {
  }
}
