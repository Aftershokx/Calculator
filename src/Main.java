import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        Scanner scanner = new Scanner (System.in);
        Parser parser = new Parser ();
        while (true) {
            System.out.println ("Тестовое задание #1. 1 + 2");
            System.out.println ("Результат вычисления: ");
            parser.finalCalculation ("1 + 2");
            System.out.println ("Тестовое задание #2. 5 - 4 * 2");
            System.out.println ("Результат вычисления: ");
            parser.finalCalculation ("5 - 4 * 2");
            System.out.println ("Тестовое задание #3. 4 + 6 / ( 12 - 3 * 2 )");
            System.out.println ("Результат вычисления: ");
            parser.finalCalculation ("4 + 6 / ( 12 - 3 * 2 )");
            System.out.println ("Введите математическое выражение");
            String expression = scanner.nextLine ();
            if (!(expression == null)) {
                System.out.println ("Результат вычисления");
                parser.finalCalculation (expression);
                break;
            }else {
                err ();
            }
        }
    }

    public static void err () {
        System.out.println ("Ошибка ввода");
    }

}
