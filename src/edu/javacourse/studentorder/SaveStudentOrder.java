package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.StudentOrder;

public class SaveStudentOrder {
  public static void main(String[] args) {
    StudentOrder studentOrder1 = new StudentOrder();
    studentOrder1.hFirstName = "Alex";
    studentOrder1.hLastName = "Petrov";
    studentOrder1.wFirstName = "Galina";
    studentOrder1.wLastName = "Petrova";

    StudentOrder studentOrder2 = new StudentOrder();
    studentOrder2.hFirstName = "Alex";
    studentOrder2.hLastName = "Sidorov";
    studentOrder2.wFirstName = "Galina";
    studentOrder2.wLastName = "Sidorova";

    long answer1 = saveStudentOrder(studentOrder1);
    System.out.println(answer1);

//    long answer2 = saveStudentOrder(studentOrder2);
//    System.out.println(answer2);

  }

  public static long saveStudentOrder(StudentOrder studentOrder) {
    long answer = 199;
    System.out.println("saveStudentOrder: " + studentOrder.hLastName);
    return answer;
  }



}
