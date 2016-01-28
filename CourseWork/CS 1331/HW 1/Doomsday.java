import java.util.Scanner;

public class Doomsday {

    public static void main(String[] args) {
        int doom, dd, fin;
        System.out.println("Welcome to the Doomsday Calculator!");
        System.out.print("What year are you looking for?");
        Scanner scYear = new Scanner(System.in);
        int year = scYear.nextInt();
        System.out.print("What month (1-12)?");
        Scanner scMon = new Scanner(System.in);
        int mon = scMon.nextInt();
        System.out.print("what day?");
        Scanner scDay = new Scanner(System.in);
        int day = scDay.nextInt();
        System.out.println("");
        doom = math(year);
        dd = leap(year, mon);
        fin = calc(dd, day, doom);
        print(year, mon, day, fin);
    }

    public static int math(int a) {
        int value, value1, value2, value3;
        value = a - 1900;
        value1 = value / 12;
        value2 = value % 12;
        value3 = value2 / 4;
        value = (value1 + value2 + value3 + 3) % 7;
        return value;
    }

    public static int leap(int y, int m) {
        int var = 0;
        int[] year = {3, 28, 7, 4, 9, 6, 11, 8, 5, 10, 7, 12};

        if ((y & 4) == 0) {
            if (((y % 100) == 0) && ((y % 400) == 0)) {
                var = 1;
            }
        }
        if (var == 1) {
            if (m == 1 || m == 2) {
                return (year[m - 1] + 1);
            }
        }
        return year[m - 1];
    }

    public static int calc(int dDay, int gDay, int day) {
        int sub = 0;
        sub = dDay - gDay;
        day = day - sub;
        day = day % 7;
        return day;
    }

    public static void print(int year, int month, int day, int fin) {
        String days = "Sunday, Monday, Tuesday, Wednesday,"
            + "Thursday, Friday, Saturday, Sunday";
        String[] week = days.split(",");
        System.out.println(month + "/" + day + "/"
            + year + " was on a " + week[fin]);
    }
}
