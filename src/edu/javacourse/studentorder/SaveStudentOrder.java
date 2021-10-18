package edu.javacourse.studentorder;

import edu.javacourse.studentorder.dao.StudentOrderDao;
import edu.javacourse.studentorder.dao.StudentOrderDaoImpl;
import edu.javacourse.studentorder.domain.*;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
  public static void main(String[] args) throws Exception {
//    List<Street> streets = new DictionaryDaoIml().findStreets("про");
//    for (Street s : streets) {
//      System.out.println(s.getStreetName());
//    }
//
//    List<PassportOffice> po = new DictionaryDaoIml().findPassportOffices("0102000");
//    for (PassportOffice p : po) {
//      System.out.println(p.getOfficeName());
//    }
//
//    List<RegisterOffice> ro = new DictionaryDaoIml().findRegisterOffices("0101000");
//    for (RegisterOffice r : ro) {
//      System.out.println(r.getOfficeName());
//    }

//    List<CountryArea> ca1 = new DictionaryDaoIml().findAreas("");
//    for (CountryArea ca : ca1) {
//      System.out.println(ca.getAreaId() + ":" +ca.getAreaName());
//    }
//    System.out.println("-----");
//    List<CountryArea> ca2 = new DictionaryDaoIml().findAreas("0200000");
//    for (CountryArea ca : ca2) {
//      System.out.println(ca.getAreaId() + ":" +ca.getAreaName());
//    }
//    System.out.println("-----");
//    List<CountryArea> ca3 = new DictionaryDaoIml().findAreas("0202000");
//    for (CountryArea ca : ca3) {
//      System.out.println(ca.getAreaId() + ":" +ca.getAreaName());
//    }

//    StudentOrder studentOrder = SaveStudentOrder.buildStudentOrder(10);
    StudentOrderDao dao = new StudentOrderDaoImpl();
//    Long id = dao.saveStudentOrder(studentOrder);
//    System.out.println(id);

    List<StudentOrder> studentOrders = dao.getStudentOrders();
    for (StudentOrder so : studentOrders) {
      System.out.println(so.getOrderId());
    }
  }

  static long saveStudentOrder(StudentOrder so) {
    long answer = 47;
    System.out.println("saveStudentOrder");
    return answer;
  }

  public static StudentOrder buildStudentOrder(long id) {
    StudentOrder so = new StudentOrder();
    so.setOrderId(id);
    so.setMarriageCertificateId("" + (123456000 + id));
    so.setMarriageDate(LocalDate.of(2016, 7, 4));

    RegisterOffice registerOffice = new RegisterOffice(1L, "", "");
    so.setMarriageOffice(registerOffice);

    Street street = new Street(1L, "First street");

    Address address = new Address("195000", street, "12", "", "142");

    // Husband
    Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997, 8, 24));
    husband.setPassportSeries("" + (1000 + id));
    husband.setPassportNumber("" + (100000 + id));
    husband.setIssueDate(LocalDate.of(2017, 9, 15));
    PassportOffice passportOffice1 = new PassportOffice(1L, "", "");
    husband.setIssueDepartment(passportOffice1);
    husband.setStudentId("" + (100000 + id));
    husband.setAddress(address);
    husband.setUniversity(new University(2L, "BSUIR"));
    husband.setStudentId("BSIUR12345");

    // Wife
    Adult wife = new Adult("Петрова", "Вероника", "Алексевна", LocalDate.of(1998, 3, 12));
    wife.setPassportSeries("" + (2000 + id));
    wife.setPassportNumber("" + (200000 + id));
    wife.setIssueDate(LocalDate.of(2018, 4, 5));
    PassportOffice passportOffice2 = new PassportOffice(2L, "", "");
    wife.setIssueDepartment(passportOffice2);
    wife.setStudentId("" + (200000 + id));
    wife.setAddress(address);
    wife.setUniversity(new University(2L, "BSTU"));
    wife.setStudentId("BSTU67890");

    // Child1
    Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 6, 29));
    child1.setCertificateNumber("" + (300000 + id));
    child1.setIssueDate(LocalDate.of(2017, 12, 30));
    RegisterOffice registerOffice2 = new RegisterOffice(2L, "", "");
    child1.setIssueDepartment(registerOffice2);
    child1.setAddress(address);

    // Child2
    Child child2 = new Child("Петрова", "Евгений", "Викторович", LocalDate.of(2018, 6, 29));
    child2.setCertificateNumber("" + (400000 + id));
    child2.setIssueDate(LocalDate.of(2018, 10, 19));
    RegisterOffice registerOffice3 = new RegisterOffice(3L, "", "");
    child2.setIssueDepartment(registerOffice3);
    child2.setAddress(address);

    so.setHusband(husband);
    so.setWife(wife);
    so.addChildren(child1);
    so.addChildren(child2);

    return so;
  }
}