package edu.javacourse.studentorder;

import edu.javacourse.studentorder.dao.DictionaryDaoIml;
import edu.javacourse.studentorder.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
  public static void main(String[] args) throws Exception {
    List<Street> nd = new DictionaryDaoIml().findStreets("d");
    for (Street i : nd) {
      System.out.println(i.getStreetName());
    }
  }

  static long saveStudentOrder(StudentOrder so) {
    long answer = 47;
    System.out.println("saveStudentOrder");
    return answer;
  }

  public static StudentOrder buildStudentOrder(long id) {
    StudentOrder so = new StudentOrder();
    so.setStudentOrderId(id);
    so.setMarriageCertificateId("" + (123456000 + id));
    so.setMarriageDate(LocalDate.of(2016, 7, 4));
    so.setMarriageOffice("Отдел ЗАГС");

    Street street = new Street(1L, "First street");

    Address address = new Address("195000", street, "12", "", "142");

    // Муж
    Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997, 8, 24));
    husband.setPassportSeries("" + (1000 + id));
    husband.setPassportNumber("" + (100000 + id));
    husband.setIssueDate(LocalDate.of(2017, 9, 15));
    husband.setIssueDepartment("Отдел милиции №" + id);
    husband.setStudentId("" + (100000 + id));
    husband.setAddress(address);
    // Жена
    Adult wife = new Adult("Петрова", "Вероника", "Алексевна", LocalDate.of(1998, 3, 12));
    wife.setPassportSeries("" + (2000 + id));
    wife.setPassportNumber("" + (200000 + id));
    wife.setIssueDate(LocalDate.of(2018, 4, 5));
    wife.setIssueDepartment("Отдел милиции №" + id);
    wife.setStudentId("" + (200000 + id));
    wife.setAddress(address);
    // Ребенок1
    Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 6, 29));
    child1.setCertificateNumber("" + (300000 + id));
    child1.setIssueDate(LocalDate.of(2018, 7, 19));
    child1.setIssueDepartment("Отдел ЗАГС №" + id);
    child1.setAddress(address);
    // Ребенок2
    Child child2 = new Child("Петрова", "Евгений", "Викторович", LocalDate.of(2018, 6, 29));
    child2.setCertificateNumber("" + (400000 + id));
    child2.setIssueDate(LocalDate.of(2018, 7, 19));
    child2.setIssueDepartment("Отдел ЗАГС №" + id);
    child2.setAddress(address);

    so.setHusband(husband);
    so.setWife(wife);
    so.addChildren(child1);
    so.addChildren(child2);

    return so;
  }
}