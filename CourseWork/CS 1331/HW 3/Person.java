public class Person {
    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        return "Hi, my name is " + this.firstName + " " + this.lastName + ". ";
    }
}