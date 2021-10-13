package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerCityRegister;
import edu.javacourse.studentorder.domain.CityRegisterCheckerResponse;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.exception.CityRegisterException;

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
      CityRegisterCheckerResponse hans = personChecker.checkPerson(so.getHusband());
      CityRegisterCheckerResponse wans = personChecker.checkPerson(so.getWife());
      CityRegisterCheckerResponse cans = personChecker.checkPerson(so.getChild());
    } catch (CityRegisterException e) {
      e.printStackTrace();
    }

    AnswerCityRegister answer = new AnswerCityRegister();
    return answer;
  }

}
