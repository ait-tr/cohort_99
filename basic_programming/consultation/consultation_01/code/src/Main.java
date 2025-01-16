/**
 * 1/24/2024
 * consultation_sidikov_01
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main {
    public static void main(String[] args) {
        Bus b1 = new Bus(); // создал конкретный автобус
//        b1.number = 22;

        b1.setNumber(22);

        Bus b2 = new Bus(); // создал второй конкретный автобус
//        b2.number = 35;

        b2.setNumber(35);

        b1.setNumber(-10);

        System.out.println(b1.getNumber());

        Bus b3 = new Bus(-10);

    }
}
