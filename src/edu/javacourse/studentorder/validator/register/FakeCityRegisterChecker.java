package edu.javacourse.studentorder.validator.register;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.register.CityRegisterResponse;
import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterCheckable {
  private static final String GOOD_1 = "1000";
  private static final String GOOD_2 = "2000";
  private static final String BAD_1 = "1001";
  private static final String BAD_2 = "2001";
  private static final String ERROR_1 = "1002";
  private static final String ERROR_2 = "2002";
  private static final String ERROR_T_1 = "1003";
  private static final String ERROR_T_2 = "2003";

  public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
    CityRegisterResponse response = new CityRegisterResponse();

    if (person instanceof Adult) {
      Adult adult = (Adult) person;
      String passportSeries = adult.getPassportSeries();
      if (passportSeries.equals(GOOD_1) || passportSeries.equals(GOOD_2)) {
        response.setExist(true);
        response.setTemporal(false);
      }
      if (passportSeries.equals(BAD_1) || passportSeries.equals(BAD_2)) {
        response.setExist(false);
      }
      if (passportSeries.equals(ERROR_1) || passportSeries.equals(ERROR_2)) {
        throw new CityRegisterException("1", "GRN ERROR " + passportSeries);
      }
      if (passportSeries.equals(ERROR_T_1) || passportSeries.equals(ERROR_T_2)) {
        throw new TransportException("Transport ERROR " + passportSeries);
      }
    }

    if (person instanceof Child) {
      response.setExist(true);
      response.setTemporal(true);
    }

    System.out.println(response);
    return response;
  }
}
