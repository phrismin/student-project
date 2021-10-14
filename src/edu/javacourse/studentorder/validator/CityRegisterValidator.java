package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.domain.register.AnswerCityRegister;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.register.AnswerCityRegisterItem;
import edu.javacourse.studentorder.domain.register.CityRegisterResponse;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.exception.TransportException;
import edu.javacourse.studentorder.validator.register.CityRegisterCheckable;
import edu.javacourse.studentorder.validator.register.FakeCityRegisterChecker;

public class CityRegisterValidator {
  public static final String IN_CODE = "NO_GRM";
  private CityRegisterCheckable personChecker;

  public CityRegisterValidator() {
    personChecker = new FakeCityRegisterChecker();
  }

  public AnswerCityRegister checkCityRegister(StudentOrder so) {
    AnswerCityRegister answerCityRegister = new AnswerCityRegister();

    answerCityRegister.addItem(checkPerson(so.getHusband()));
    answerCityRegister.addItem(checkPerson(so.getWife()));
    for (Child child : so.getChildren()) {
      answerCityRegister.addItem(checkPerson(child));
    }

    return answerCityRegister;
  }

  private AnswerCityRegisterItem checkPerson(Person person) {
    AnswerCityRegisterItem.RegisterStatus status = null;
    AnswerCityRegisterItem.RegisterError error = null;

    try {
      CityRegisterResponse response = personChecker.checkPerson(person);
      status = response.isExist() ?
          AnswerCityRegisterItem.RegisterStatus.YES :
          AnswerCityRegisterItem.RegisterStatus.NO;
    } catch (CityRegisterException e) {
      e.printStackTrace(System.out);
      status = AnswerCityRegisterItem.RegisterStatus.ERROR;
      error = new AnswerCityRegisterItem.RegisterError(e.getCode(), e.getMessage());
    } catch (TransportException e) {
      e.printStackTrace(System.out);
      status = AnswerCityRegisterItem.RegisterStatus.ERROR;
      error = new AnswerCityRegisterItem.RegisterError(IN_CODE, e.getMessage());
    }

    return new AnswerCityRegisterItem(status, person, error);
  }

}
