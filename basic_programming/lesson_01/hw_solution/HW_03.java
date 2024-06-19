public class HW_03 {
    public static void main(String[] args) {
        System.out.println("======= Task 1 =======");

        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        int e = 4;
        int f = 5;
        int g = 6;
        int h = 7;
        int j = 8;
        int k = 9;

        int avg = (a + b + c + d + e + f + g + h + j + k) / 10;

        double avg1 = ((double) a + (double) b + (double) c + (double) d + (double) e + (double) f + (double) g + (double) h + (double) j + (double) k) / 10;

        System.out.println(avg); // программа отбросила 0.5 в дробной части

        System.out.println(avg1);

        System.out.println("======= Task 2 =======");

        int pr1 = 1000;
        int pr2 = 500;
        int disct = 100;

        int sumPrice = (pr1 + pr2) - disct;

        System.out.println("The final price for this purchase is " + sumPrice + " r.");
        System.out.println("The discount for this purchase is " + disct + " r.");

        System.out.println("======= Task 3 =======");

        int day1 = 32;
        int day2 = 37;
        int day3 = 39;
        int day4 = 32;
        int day5 = 32;
        int day6 = 33;
        int day7 = 33;

        int avgTempWeek = (day1 + day2 + day3 + day4 + day5 + day6 + day7) / 7;

        System.out.println("Last week the average temperature was " + avgTempWeek + " Celsius degrees.");

        System.out.println("======= Task 4 =======");

        int m;
        int y;
        int z;
        y = 2;
        z = 3;

        m = 1;
        int rest2 = m % y;
        int rest3 = m % z;

        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        m = 2;
        rest2 = m % y;
        rest3 = m % z;
        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        m = 3;
        rest2 = m % y;
        rest3 = m % z;
        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        m = 4;
        rest2 = m % y;
        rest3 = m % z;
        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        m = 5;
        rest2 = m % y;
        rest3 = m % z;
        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        m = 6;
        rest2 = m % y;
        rest3 = m % z;
        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        m = 7;
        rest2 = m % y;
        rest3 = m % z;
        System.out.println("деление с остатком " + m + " на " + 2 + " = " + rest2);
        System.out.println("деление с остатком " + m + " на " + 3 + " = " + rest3);

        // Таким образом, остаток от деления целого числа на 2 или на 3, дает числа: 0, 1 или 2.

        System.out.println("======= Task 5 =======");

        // х будет равен 6-ти (а не 7-ми, поскольку инкремент постфиксный).

        int x = 3;
        x += x++;

        System.out.println(x);
    }
}
