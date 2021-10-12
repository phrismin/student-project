package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerStudent;
import edu.javacourse.studentorder.domain.StudentOrder;

public class StudentValidator {

  public AnswerStudent checkStudent(StudentOrder studentOrder) {
    System.out.println("Student check id running");
    AnswerStudent answerStudent = new AnswerStudent();
    answerStudent.success = false;
    return answerStudent;
  }
}
