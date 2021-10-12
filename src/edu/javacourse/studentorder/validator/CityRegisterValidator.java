package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerCityRegister;
import edu.javacourse.studentorder.domain.StudentOrder;

public class CityRegisterValidator {

  public String hostName;
  public int port;
  public String login;
  public String password;

  public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
    System.out.println("CityRegister is running: " + hostName + ", " + login + ", " +
         password);
    AnswerCityRegister answer = new AnswerCityRegister();
    answer.success = false;
    return answer;
  }

}
