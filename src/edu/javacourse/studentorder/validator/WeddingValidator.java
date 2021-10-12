package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerWedding;
import edu.javacourse.studentorder.domain.StudentOrder;

public class WeddingValidator {

  public AnswerWedding checkWedding(StudentOrder studentOrder) {
    System.out.println("Wedding check is running");
    AnswerWedding answerWedding = new AnswerWedding();
    answerWedding.success = true;
    return answerWedding;
  }
}
