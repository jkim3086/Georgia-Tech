/**
 * Created by Benjamin_Wilson on 9/6/14.
 */

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        PersonList list = new PersonList(10);
        list.add(new Professor("Sample1", "Data1", 5, 5));
        list.add(new Student("Sample2", "Data2", 10, 10));
        list.add(new UndergraduateStudent("Sample3", "Data3", 10, 10));
        list.add(new GraduateStudent("Sample4", "Data4", 5, 5));

        personCatalog(list);
    }

    private static void personCatalog(PersonList list) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Person Catalog!\n");
            System.out.println("Which option would you like?");
            System.out.println("0. List the people");
            System.out.println("1. Add a person");
            System.out.println("2. Exit\n");

            int userInput = input.nextInt();
            System.out.println();

            if (userInput == 0) {
                list.listPeople();
            } else if (userInput == 1) {
                System.out.println("Which type of person would"
                        + " you like to add?");
                System.out.println("0. Professor");
                System.out.println("1. Student");
                System.out.println("2. Graduate Student");
                System.out.println("3. Undergraduate Student");
                System.out.println();
                userInput = input.nextInt();
                System.out.println();
                if (userInput == 0) {
                    System.out.println("Please enter their first name.");
                    input.nextLine();
                    String firstName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their last name.");
                    String lastName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their "
                            + "Rate My Professor rating.");
                    int rateMyProfessorRating = input.nextInt();
                    System.out.println();
                    System.out.println("Please enter their average GPA.");
                    double averageGPA = input.nextDouble();
                    System.out.println();
                    list.add(new Professor(firstName, lastName,
                            rateMyProfessorRating, averageGPA));
                } else if (userInput == 1) {
                    System.out.println("Please enter their first name.");
                    input.nextLine();
                    String firstName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their last name.");
                    String lastName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their intelligence.");
                    int intelligence = input.nextInt();
                    System.out.println();
                    System.out.println("Please enter their motivation.");
                    int motivation = input.nextInt();
                    System.out.println();
                    list.add(new Student(firstName, lastName,
                            intelligence, motivation));
                } else if (userInput == 2) {
                    System.out.println("Please enter their first name.");
                    input.nextLine();
                    String firstName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their last name.");
                    String lastName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their intelligence.");
                    int intelligence = input.nextInt();
                    System.out.println();
                    System.out.println("Please enter their motivation.");
                    int motivation = input.nextInt();
                    System.out.println();
                    list.add(new GraduateStudent(firstName, lastName,
                            intelligence, motivation));
                } else if (userInput == 3) {
                    System.out.println("Please enter their first name.");
                    input.nextLine();
                    String firstName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their last name.");
                    String lastName = input.nextLine();
                    System.out.println();
                    System.out.println("Please enter their intelligence.");
                    int intelligence = input.nextInt();
                    System.out.println();
                    System.out.println("Please enter their motivation.");
                    int motivation = input.nextInt();
                    System.out.println();
                    list.add(new UndergraduateStudent(firstName, lastName,
                            intelligence, motivation));
                }
            } else if (userInput == 2) {
                System.exit(0);
            }
        }
    }
}
