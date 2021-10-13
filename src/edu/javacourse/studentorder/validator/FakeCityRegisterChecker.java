package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.CityRegisterCheckerResponse;
import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.exception.CityRegisterException;

public class FakeCityRegisterChecker implements CityRegisterCheckable {
  private static final String GOOD_1 = "1000";
  private static final String GOOD_2 = "2000";
  private static final String BAD_1 = "1001";
  private static final String BAD_2 = "2001";
  private static final String ERROR_1 = "1002";
  private static final String ERROR_2 = "2002";

  public CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException {
    CityRegisterCheckerResponse resp = new CityRegisterCheckerResponse();

    if (person instanceof Adult) {
      Adult adult = (Adult) person;
      String passportSeries = adult.getPassportSeries();
      if (passportSeries.equals(GOOD_1) || passportSeries.equals(GOOD_2)) {
        resp.setExist(true);
        resp.setTemporal(false);
      }
      if (passportSeries.equals(BAD_1) || passportSeries.equals(BAD_2)) {
        resp.setExist(false);
      }
      if (passportSeries.equals(ERROR_1) || passportSeries.equals(ERROR_2)) {
        throw new CityRegisterException("Fake ERROR " + passportSeries);
      }
    }

    if (person instanceof Child) {
      resp.setExist(true);
      resp.setTemporal(true);
    }

    System.out.println(resp);
    return null;
  }
}
