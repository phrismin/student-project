package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerChildren;
import edu.javacourse.studentorder.domain.StudentOrder;

public class ChildrenValidator {

  public AnswerChildren checkChildren(StudentOrder studentOrder) {
    System.out.println("Children check is running");
    AnswerChildren answerChildren = new AnswerChildren();
    answerChildren.success = false;
    return answerChildren;
  }
}
