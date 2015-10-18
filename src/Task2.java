import java.math.BigInteger;
import java.util.*;

/**
 * Created by Mihail on 08.10.15.
 */

public class Task2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PositionSearcher searcher = new PositionSearcher();

        while (true) {
            System.out.println("Введите числовую строку для поиска или введите exit:");

            String input = in.nextLine();
            if (input.equals("exit")) {
                System.out.print("Работа программы завершена.\n");
                break;
            }

            try {
                Integer.parseInt(input);
            }
            catch (Exception ex) {
                System.out.println("Входная строка имеет недопустимый формат! Попробуйте еще раз.\n");
                continue;
            }

            Integer[] ar = new Integer[input.length()];

            for (int i = 0; i < ar.length; i++) {
                ar[i] = Character.getNumericValue(input.charAt(i));
            }

            long timeout = System.currentTimeMillis();

            System.out.println("Искомая позиция: " + searcher.search(ar));

            System.out.print("Время работы алгоритма: " + (System.currentTimeMillis() - timeout) + " милисекунд.\n\n");
        }
    }
}

class PositionSearcher {

    BigInteger currentNumber = BigInteger.ONE; // текущее число, которое добавляем к ленте
    String numberString = "1";                 // строковое представление этого числа
    int index = 0;                             // индекс добавляемой цифры в строке текущего числа

    BigInteger position = BigInteger.ONE;      // Позиция в строке бесконечной ленты

    Integer[] substringArray;                  // Искомый массив чисел
    Queue<Integer> queue = new LinkedList<>(); // Очередь-каретка, через которую проходят все цифры

    public BigInteger search(Integer[] substringArray) {
        this.substringArray = substringArray;

        while (queue.size() != substringArray.length) {
            addNextDigitToQueue();
        }

        while(true) {
            queue.remove();
            addNextDigitToQueue();
            position = position.add(BigInteger.ONE);

            if (isQueueContainsSubstring()) break;
        }

        return position;
    }


    /**
     * Добавляет следующую цифру текущего числа в очередь
     */
    private void addNextDigitToQueue() {

        int numberLength = currentNumber.toString().length();

        if (index == numberLength) {
            currentNumber = currentNumber.add(BigInteger.ONE);
            numberString = currentNumber.toString();
            index = 0;
        }

        int currentIndex = index;
        int newDigit = Character.getNumericValue(numberString.charAt(currentIndex));
        queue.add(newDigit);
        index++;
    }

    /**
     * @return содержит ли текущая очередь искомую строку
     */
    private boolean isQueueContainsSubstring() {
        int i = 0;
        for (Integer el : queue) {
            if (el != substringArray[i++]) {
                return false;
            }
        }

        return true;
    }
}