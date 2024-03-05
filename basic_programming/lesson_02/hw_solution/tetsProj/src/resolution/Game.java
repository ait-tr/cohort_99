package resolution;

import java.util.Arrays;

//VaF
public class Game {

    private final static char allowedCharsX = 'X';
    private final static char allowedChars0 = '0';

    public static void main(String[] args) {
        char[] arrRow1 = new char[3];
        char[] arrRow2 = new char[3];
        char[] arrRow3 = new char[3];
        //Поиграйтесь с данными, используйте только X или 0 (uppercase)
        //Сторока 1
        arrRow1[0] = 'X';
        arrRow1[1] = '0';
        arrRow1[2] = 'X';
        //Сторока 2
        arrRow2[0] = 'X';
        arrRow2[1] = 'X';
        arrRow2[2] = '0';
        //Сторока 3
        arrRow3[0] = '0';
        arrRow3[1] = 'X';
        arrRow3[2] = '0';

//        gameResult(arrRow1, arrRow2, arrRow3);
//        printResult(arrRow1, arrRow2, arrRow3);

        //То же самое только с многомерным массивом)
        buildMultiArr();
    }

    private static void buildMultiArr() {
        //Это двумерный массив 3 строки по три столбца
        char[][] arr = new char[3][3];
        arr[0][0] = '0';
        arr[0][1] = 'X';
        arr[0][2] = 'X';

        arr[1][0] = 'X';
        arr[1][1] = '0';
        arr[1][2] = '0';

        /*
         отлично!
         второй вариант заполнения, так для инфы:
            так как мы ведь уже знаем что массив можно сразуже заполнить при объявлении: int[] someArr = {1,2,3,4,5};
            можно так же проделать и в нашем коде
            arr[2] = new char[]{'0', '0', '0'};
            Но я думаю мы еще поговрим про многомерные массивы
         P.S.: чтоб если где встретишь, тебя это не пугало ;)
         */
        arr[2][0] = '0';
        arr[2][1] = '0';
        arr[2][2] = '0';

        //TODO заполнить третью строку

        System.out.println("\nТоже самое, только с двумерным массивом!\n");
        //TODO допишите расчет и вывод результатов, подсказка arr[0] - это первая строка
        /*
         1. вполне рабочее решение, но можно так же можно в некоторых местах немного упростить.
         2. воспользуйся подсказкой и посмотри код в методе public static void main. Там есть кое-что, что можно тут использовать(переиспользовать)
         3. так же с помощью того самого "кое-чего" можно и твой метод упростить.
         !!! Для этой задачи нет единого решения, пробуй разные подходы.
         */
        for (int j = 0; j < arr.length; j++) {
            System.out.print("[ ");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(String.valueOf(arr[i][j]) + " ");
            }
            System.out.print("]\n");
            System.out.println();  // те кже можно было использовать в строчке выше \n т.е. System.out.print("]\n"); но это мелочи)
        }


        //Проверка На выигрыш
        String winner="No one winner";
        for (int j = 0; j < arr.length; j++) {
            String countX = "";
            String countY = "";
            String countD = "";
            String countD2 = "";
            for (int i = 0; i < arr.length; i++) {
                //Проверь индексы!
                //попробуй переименовать (shift+f6) к примеру:
                //                                              i в indexArr2;
                //                                              j в indexArr1;
                countX += arr[i][j];
                countY += arr[j][i];
                countD += arr[i][i];
                countD2 += arr[i][arr.length-1-i]; //02,11,20


            }

            //Очень хорошо что ты используешь equals. Это правильно!
            if (countX.equals("XXX") || countY.equals("XXX") || countD.equals("XXX") || countD2.equals("XXX")) {
                winner ="Winner is X";
                //break тут не нужен
                break;

            }
            if (countX.equals("000") || countY.equals("000") || countD.equals("000") || countD2.equals("000")) {
                winner="Winner is 0";
                //break тут не нужен
                break;
            }
        }

        System.out.println(winner);
    }

    /**
     * Проверка результатов. По осям X, Y и диагоналей
     *
     * @param row1 строка 1
     * @param row2 строка 2
     * @param row3 строка 3
     */
    private static void gameResult(char[] row1, char[] row2, char[] row3) {
        //Запись результатов для Х и 0
        int counterX = 0;
        int counter0 = 0;
        //Пров рка по оси X
        for (int i = 0; i < 3; i++) {
            char car = row1[i];
            if (allowedCharsX == car) {
                counterX++;
            }
            if (allowedChars0 == car) {
                counter0++;
            }
            if (counterX == 3) {
                System.out.println("X won");
            }
            if (counter0 == 3) {
                System.out.println("0 is won");
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
        //TODO дописать проварку диагоналей для Х и 0

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
