import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        Scanner scanner = new Scanner (System.in);
        Parser parser = new Parser ();
        while (true) {
            System.out.println ("Введите математическое выражение");
            String expression = scanner.nextLine ();
            if (!(expression == null)) {
                parser.calculate (expression);
                System.out.println ("Результат вычисления" );
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
