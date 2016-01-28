public class Student extends Person {
    protected int intelligence;
    protected int motivation;

    public Student(String firstName, String lastName,
                    int intelligence, int motivation) {
        super(firstName, lastName);
        this.intelligence = intelligence;
        this.motivation = motivation;
    }

    public String toString() {
        return "Hi, my name is " + firstName + " "
                    + lastName + ". " + "My intelligence is " + intelligence
                    + "/10 and my motivation is " + motivation + "/10. "
                    + "I'm stressed out.";
    }
}