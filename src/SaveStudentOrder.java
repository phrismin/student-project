public class SaveStudentOrder {
  public static void main(String[] args) {
    StudentOrder studentOrder1 = new StudentOrder();
    studentOrder1.hFirstName = "Alex";
    studentOrder1.hLastName = "Petrov";
    studentOrder1.wFirstName = "Galina";
    studentOrder1.wLastName = "Petrova";

    long answer1 = saveStudentOrder(studentOrder1);
    System.out.println(answer1);
  }

  public static long saveStudentOrder(StudentOrder studentOrder) {
    long answer = 199;
    System.out.println("saveStudentOrder: " + studentOrder.hLastName);
    return answer;
  }



}
