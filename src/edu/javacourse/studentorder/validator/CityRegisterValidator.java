package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerCityRegister;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.CityRegisterCheckerResponse;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.exception.CityRegisterException;

import java.util.List;

public class CityRegisterValidator {

  public String hostName;
  public int port;
  public String login;
  public String password;
  private CityRegisterCheckable personChecker;

  public CityRegisterValidator() {
    personChecker = new FakeCityRegisterChecker();
  }

  public AnswerCityRegister checkCityRegister(StudentOrder so) {
    try {
      CityRegisterCheckerResponse hansw = personChecker.checkPerson(so.getHusband());
      CityRegisterCheckerResponse wansw = personChecker.checkPerson(so.getWife());
      List<Child> children = so.getChildren();
      for (Child child : children) {
        CityRegisterCheckerResponse cansw = personChecker.checkPerson(child);
      }
    } catch (CityRegisterException e) {
      e.printStackTrace(System.out);
    }

    AnswerCityRegister answer = new AnswerCityRegister();
    return answer;
  }

}
