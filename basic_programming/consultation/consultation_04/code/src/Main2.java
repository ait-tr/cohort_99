/**
 * 1/24/2024
 * consultation_sidikov_01
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main2 {
    public static void main(String[] args) {
      Bus b1 = new Bus(22);
      Bus b2 = new Bus(35);

      Passenger p1 = new Passenger("Marsel");
      Passenger p2 = new Passenger("Kirill");
      Passenger p3 = new Passenger("Andrej");
      Passenger p4 = new Passenger("Arseni");
      Passenger p5 = new Passenger("Dmitriy");

      b1.addPassenger(p1);
      b1.addPassenger(p2);
      b1.addPassenger(p3);
      b1.addPassenger(p4);
      b1.addPassenger(p5);
      b1.addPassenger(p1);

      b2.addPassenger(p4);
      b2.addPassenger(p5);

      b1.go();
      b2.go();


    }
}
