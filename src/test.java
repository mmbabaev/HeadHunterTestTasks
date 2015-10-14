import java.math.*;
import java.util.*;
import java.util.Scanner;

/**
 * @author Rengo [Гутников Дмитрий]
 *
 * 2. Бесконечная последовательность
 *  Возьмём бесконечную цифровую последовательность, образованную склеиванием.
 *  последовательных положительных чисел: S = 123456789101112131415...
 *  Определите первое вхождение заданной последовательности A в бесконечной
 *  последовательности S (нумерация начинается с 1).
 *
 *  Пример входных данных:
 *  6789
 *  111
 *
 *  Пример выходных данных:
 *  6
 *  12
 */

class Searcher
{
    private final int defMainStringLenght = 100;
    private final int curMainStringLenght;
    private final String subString;
    private String tempString;
    private BigInteger item;

    public int getFirstEntry(String source, String subString) {
        HashMap<Character, Integer> offsetTable = new HashMap<>();
        int sourceLen = source.length();
        int templateLen = subString.length();
        if (templateLen > sourceLen) { return -1; }


        for (int i = 0; i <= 255; i++)
        { offsetTable.put((char) i, templateLen); }
        for (int i = 0; i < templateLen - 1; i++)
        { offsetTable.put(subString.charAt(i), templateLen - i - 1); }

        int i = templateLen - 1;
        int tempLen = i;
        int charPos = i;
        while (tempLen >= 0 && i <= sourceLen - 1)
        {
            tempLen = templateLen - 1;
            charPos = i;
            while (tempLen >= 0 && source.charAt(charPos) == subString.charAt(tempLen))
            {
                charPos--;
                tempLen--;
            }
            i += offsetTable.get(source.charAt(i));
        }
        if (charPos >= sourceLen - templateLen)
        { return -1; }
        else
        { return charPos + 1; }
    }

    private void prepareString(int deleteSymbNum, int lastCountLenght)
    {
        if(deleteSymbNum > 0)
        { this.tempString = this.tempString.substring(deleteSymbNum); }
        if(lastCountLenght > 0)
        {
            this.tempString += String.valueOf(this.item).substring(lastCountLenght);
            item = item.add(BigInteger.ONE);
        }
    }
    private int fillString()
    {
        while(this.tempString.length() + String.valueOf(item).length() <= this.curMainStringLenght)
        {
            this.tempString += String.valueOf(item);

            if(this.item.equals(new BigInteger(this.subString)))
            { return 0; }

            item = item.add(BigInteger.ONE);
        }

        if(this.tempString.length() < this.curMainStringLenght)
        { return this.curMainStringLenght - this.tempString.length(); }
        else { return 0; }
    }
    private void endString(int notFilledLenght)
    {
        if(notFilledLenght > 0)
        {
            this.tempString += String.valueOf(this.item).substring(0, notFilledLenght);
        }
    }
    public BigInteger find()
    {
        BigInteger position = BigInteger.ONE;
        int deleteSymbNum = 0;
        int notFilledLenght = 0;
        //int count = 0;
        /******************/
        System.out.println("---------");
        System.out.println("Для завершения поиска используйте следующие комбинации клавиш:");
        System.out.println("NetBeans(default): Ctrl + Shift + Del; ");
        System.out.println("Console: Ctrl + C");
        System.out.println("---------");
        /******************/
        while(getFirstEntry(this.tempString,this.subString) == -1)
        {
            //count++;
            deleteSymbNum = Math.max(0, this.tempString.length() - this.subString.length());
            position = position.add(BigInteger.valueOf(deleteSymbNum));
            prepareString(deleteSymbNum, notFilledLenght);
            notFilledLenght = fillString();
            endString(notFilledLenght);
            //System.out.println(this.tempString + " " + this.item + " " + count);
        }
        System.out.println("Часть бесконечного ряда с найденным числом: [искомое число в конце]");
        System.out.println(this.tempString.substring(0, this.tempString.indexOf(this.subString)+this.subString.length()));
        position = position.add(BigInteger.valueOf(this.tempString.indexOf(this.subString)));

        return position;
    }
    public Searcher(String subString)
    {
        this.tempString = "";
        this.item = BigInteger.ONE;
        if(subString.isEmpty())// || subString.length() > String.valueOf(Long.MAX_VALUE).length())
        { this.subString = "0"; }
        else { this.subString = subString; }

        if(this.subString.length() > this.defMainStringLenght/2)
        { this.curMainStringLenght = this.subString.length() * 2;}
        else
        { this.curMainStringLenght = this.defMainStringLenght; }
    }
    public final String getSubString() { return this.subString; }
}
public class test {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        System.out.print("Введите искомое число: ");

        Searcher mySearcher = new Searcher(myScan.nextLine().replaceAll("[^0-9]", ""));
        long timeout = System.currentTimeMillis();
        System.out.format("Подстрока: %1$s, позиция %2$d\n", mySearcher.getSubString(),mySearcher.find());
        System.out.print(System.currentTimeMillis() - timeout);

    }
}