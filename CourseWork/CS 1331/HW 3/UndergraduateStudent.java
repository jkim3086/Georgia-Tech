public class UndergraduateStudent extends Student {

    public UndergraduateStudent(String firstName, String lastName,
                                int intelligence, int motivation) {
        super(firstName, lastName, intelligence, motivation);
    }

    public String toString() {
        return "Hi, my name is " + firstName + " "
                    + lastName + ". " + "My intelligence is " + intelligence
                    + "/10 and my motivation is " + motivation + "/10. "
                    + "I'am stressed out AND broke";
    }
}