import java.util.Scanner;

/**
 * Created by Mihail on 08.10.15.
 */
public class BadTask2 {
    public static void main(String [] args) {

        Scanner in = new Scanner(System.in);
        String pattern = in.nextLine();
        long timeout= System.currentTimeMillis();
        String s = "1";
        int count = 2;
        while(true) {
            if (pattern.length() > s.length()) {
                s += count++;
                continue;
            }

            if (s.contains(pattern)) {
                System.out.println(count);
                System.out.println(s);
                break;
            }
            else {
                s += count++;
            }
        }
        System.out.print(System.currentTimeMillis() - timeout);
    }

}
