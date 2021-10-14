package edu.javacourse.studentorder.domain.register;

import edu.javacourse.studentorder.domain.Person;

public class AnswerCityRegisterItem {
  public enum RegisterStatus {
    YES, NO, ERROR
  }

  public static class RegisterError {
    private String code;
    private String message;

    public RegisterError(String code, String message) {
      this.code = code;
      this.message = message;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }
  }

  private RegisterStatus registerStatus;
  private Person person;
  private RegisterError registerError;

  public AnswerCityRegisterItem(RegisterStatus registerStatus, Person person) {
    this.registerStatus = registerStatus;
    this.person = person;
  }

  public AnswerCityRegisterItem(RegisterStatus registerStatus, Person person, RegisterError registerError) {
    this.registerStatus = registerStatus;
    this.person = person;
    this.registerError = registerError;
  }

  public RegisterStatus getRegisterStatus() {
    return registerStatus;
  }

  public Person getPerson() {
    return person;
  }

  public RegisterError getCityError() {
    return registerError;
  }
}
