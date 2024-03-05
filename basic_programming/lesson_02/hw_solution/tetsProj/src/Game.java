import java.util.Arrays;

public class Game {

    private final static char allowedCharsX = 'X';
    private final static char allowedChars0 = '0';

    public static void main(String[] args) {
        char[] arrRow1 = new char[3];
        char[] arrRow2 = new char[3];
        char[] arrRow3 = new char[3];

        arrRow1[0] = 'X';
        arrRow1[1] = '0';
        arrRow1[2] = 'X';

        arrRow2[0] = 'X';
        arrRow2[1] = 'X';
        arrRow2[2] = '0';

        arrRow3[0] = 'X';
        arrRow3[1] = 'X';
        arrRow3[2] = '0';

//        gameResult(arrRow1, arrRow2, arrRow3);
//        printResult(arrRow1, arrRow2, arrRow3);
        buildMultiArr();
    }

    private static void buildMultiArr() {
        char[][] arr = new char[3][3];
        arr[0][0] = '0';
        arr[0][1] = 'X';
        arr[0][2] = 'X';

        arr[1][0] = 'X';
        arr[1][1] = 'X';
        arr[1][2] = 'X';

        arr[2][0] = '0';
        arr[2][1] = 'X';
        arr[2][2] = 'X';

        System.out.println("\nТоже самое, только с двумерным массивом!\n");
        gameResult(arr[0], arr[1], arr[2]);
        printResult(arr[0], arr[1], arr[2]);
    }

    /**
     * Проверка результатов. По осям X, Y и диагоналей
     *
     * @param row1 строка 1
     * @param row2 строка 2
     * @param row3 строка 3
     */
    private static void gameResult(char[] row1, char[] row2, char[] row3) {
        //TODO но все еще нет проверки если ничья)
        //Запись результатов для Х и 0
        int counterX = 0;
        int counter0 = 0;
        //Пров рка по оси X строка 1
        for (int i = 0; i < 3; i++) {
            char car = row1[i];
            if (allowedCharsX == car) {
                counterX++;
            }
            if (allowedChars0 == car) {
                counter0++;
            }
            if (counterX == 3) {
                System.out.println("X won (row 1)");
                return;
            }
            if (counter0 == 3) {
                System.out.println("0 is won (row 1)");
                return;
            }
        }
        //Пров рка по оси X строка 2
        counterX = 0;
        counter0 = 0;
        for (int i = 0; i < 3; i++) {
            char car = row2[i];
            if (allowedCharsX == car) {
                counterX++;
            }
            if (allowedChars0 == car) {
                counter0++;
            }
            if (counterX == 3) {
                System.out.println("X won (row 2)");
                return;
            }
            if (counter0 == 3) {
                System.out.println("0 is won (row 2)");
                return;
            }
        }
        //Пров рка по оси X строка 3
        counterX = 0;
        counter0 = 0;
        for (int i = 0; i < 3; i++) {
            char car = row3[i];
            if (allowedCharsX == car) {
                counterX++;
            }
            if (allowedChars0 == car) {
                counter0++;
            }
            if (counterX == 3) {
                System.out.println("X won (row 3)");
                return;
            }
            if (counter0 == 3) {
                System.out.println("0 is won (row 3)");
                return;
            }
        }
        //Проверка по оси Y
        //для Х
        if (row1[0] == allowedCharsX && row2[0] == allowedCharsX && row3[0] == allowedCharsX) {
            System.out.println("X won");
            return;
        }
        if (row1[1] == allowedCharsX && row2[1] == allowedCharsX && row3[1] == allowedCharsX) {
            System.out.println("X won");
            return;
        }
        if (row1[2] == allowedCharsX && row2[2] == allowedCharsX && row3[2] == allowedCharsX) {
            System.out.println("X won");
            return;
        }
        //для 0
        if (row1[0] == allowedChars0 && row2[0] == allowedChars0 && row3[0] == allowedChars0) {
            System.out.println("X won");
            return;
        }
        if (row1[1] == allowedChars0 && row2[1] == allowedChars0 && row3[1] == allowedChars0) {
            System.out.println("X won");
            return;
        }
        if (row1[2] == allowedChars0 && row2[2] == allowedChars0 && row3[2] == allowedChars0) {
            System.out.println("X won");
            return;
        }
        //проверка диагоналей
        //проверка диагонали для Х (слева верх - до права низ)
        if (row1[0] == allowedCharsX && row2[1] == allowedCharsX && row3[2] == allowedCharsX) {
            System.out.println("X won");
            return;
        }
        //проверка диагонали для 0 (слева верх - до права низ)
        if (row1[0] == allowedChars0 && row2[1] == allowedChars0 && row3[2] == allowedChars0) {
            System.out.println("X won");
            return;
        }
        //проверка диагонали Х (право верх - до лево низ)
        if (row1[2] == allowedCharsX && row2[1] == allowedCharsX && row3[0] == allowedCharsX) {
            System.out.println("0 won");
            return;
        }
        //проверка диагонали 0 (право верх - до лево низ)
        if (row1[2] == allowedChars0 && row2[1] == allowedChars0 && row3[0] == allowedChars0) {
            System.out.println("0 won");
            return;
        }
        //Бывают случаи когда побеждает дружба;)
        System.out.println("Nobody won ;)");
    }

    /**
     * Распечатывает результат
     *
     * @param row1 строка 1
     * @param row2 строка 2
     * @param row3 строка 3
     */
    private static void printResult(char[] row1, char[] row2, char[] row3) {
        System.out.println(Arrays.toString(row1) + "\n" + Arrays.toString(row2) + "\n" + Arrays.toString(row3));
    }
}
