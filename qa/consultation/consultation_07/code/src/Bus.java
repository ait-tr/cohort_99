/**
 * 1/24/2024
 * consultation_sidikov_01
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Bus {

    // глобальная (общая для всех объектов) константа
    private static final int MAX_PASSENGERS_COUNT = 5;
    private static final int DEFAULT_NUMBER = 1;
    private static final int INITIAL_COUNT = 0;

    // поле, field, отвечает за состояние (state) объекта
    private int number;

    private Passenger[] passengers; // ссылка на массив, который будет хранить пассажиров
    private int count; // число пассажиров, которое есть в автобусе на текущий момент

    public Bus() {
        this.number = DEFAULT_NUMBER; // теперь все созданные автобусы будут иметь по умолчанию номер 1
        this.count = INITIAL_COUNT;
        this.passengers = new Passenger[MAX_PASSENGERS_COUNT];
    }

    public Bus(int number) {
        this();
        setNumber(number);
    }

    public void addPassenger(Passenger passenger) {
        if (this.count == passengers.length) {
            System.err.println("Автобус переполнен");
            return; // останавливаем работу этого метода
        }
        // если место есть - добавляем
        this.passengers[this.count] = passenger;
        this.count++; // this.count = this.count + 1
    }

    public void setNumber(int number) {
        if (number >= 1) { //проверяем, вносят ли положительное число больше или равно 1
            this.number = number; // если это число больше или равно 1, то запоминаем его в поле
        } else {
            // в противном случае - выдаем сообщение об ошибке и присваиваем 1
            System.err.println("Некорректный номер автобуса. По умолчанию - 1");
            this.number = 1;
        }
    }

    public void go() {
        System.out.println("Автобус под номером " + number + " поехал");
        System.out.println("С нами едут пассажиры: ");
        for (int i = 0; i < count; i++) {
            System.out.println(passengers[i].getName());
        }
    }

    public int getNumber() {
        return number;
    }
}
