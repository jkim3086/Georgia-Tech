public class PersonList {
    private Person[] people;
    private int count = 0;

    public PersonList(int maxSize) {
        people = new Person[maxSize];
    }

    public void listPeople() {
        int length, i;
        length = people.length;

        for (i = 0; i < length; i++) {
            if (people[i] != null) {
                System.out.println(people[i].toString());
            }
        }
    }

    public void add(Person p) {
        int i;
        if (count < people.length) {
            for (i = 0; i < people.length; i++) {
                if (people[i] == null || people[i].equals(" ")) {
                    count++;
                    break;
                }
            }
            people[i] = p;
        }
    }
}