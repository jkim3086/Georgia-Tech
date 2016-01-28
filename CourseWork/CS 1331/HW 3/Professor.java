public class Professor extends Person {
    private int rateMyProfessorRating;
    private double averageGPA;

    public Professor(String firstName, String lastName,
                    int rateMyProfessor, double averageGPA) {
        super(firstName, lastName);
        this.rateMyProfessorRating = rateMyProfessor;
        this.averageGPA = averageGPA;
    }

    public String toString() {
        return super.toString() + "My Rate My Professor rating is "
            + this.rateMyProfessorRating
            + "/5 and my average GPA is " + this.averageGPA + "/4.00."
            + " I really wish students whould stop emailing me so much";
    }
}