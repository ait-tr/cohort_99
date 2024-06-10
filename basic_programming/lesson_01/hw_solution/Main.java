public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Java!"); // печать с новой строки
        System.out.print("Hello Cohort 31_1!"); // печать на той же строке
        System.out.println("Hello again!");

        int a;
        String b = "Hello";
        double c;
        char ch = 'r';

        a = 10;
        System.out.println(a);

        a = a + 1;

        int x = 6;

        int sum = a + x;
        System.out.println(sum);

        System.out.println("Hello" + " Java");
        System.out.println(1 + 2 + "1" + 2); // при сложении разных типов со строкой происходит
        // сложение по правилу конкатенации строк.
    }
}
