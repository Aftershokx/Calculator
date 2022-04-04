import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Parser {
    private double result;

    //Перевод строки в массив(для работы), вычисление и печать результата
    public void finalCalculation (String expression) {
        LinkedList<String> symbols = new LinkedList<> ();
        String[] symbol = expression.trim ().split (" ");
        Collections.addAll (symbols, symbol);
        priorityCheck (symbols);
        System.out.println (result);
    }

    //Расставление приоритетов
    private void priorityCheck (LinkedList<String> symbols) {
        LinkedList<String> expressions = new LinkedList<> ();
        result = 0;
        int count = 0;
        int leftStapleIndex = 0;
        int rightStapleIndex = 0;
        if (symbols.contains ("(")) {
            for (int i = 0; i < symbols.size (); i++) {
                if (symbols.get (i).equals ("(")) {
                    count = count + 1;
                    leftStapleIndex = i;
                }
            }
            for (int j = symbols.size () - 1; j > 0; j--) {
                if (symbols.get (j).equals (")")) {
                    rightStapleIndex = j;
                }
            }
            if (count == 1) {
                removeStaples (symbols, expressions, leftStapleIndex, rightStapleIndex);
                result = calculationWithOperatorsPriority (symbols, result);
            }
        } else {
            result = calculationWithOperatorsPriority (symbols, result);
        }
        while (count > 1) {
            openStaples (symbols, expressions, leftStapleIndex, rightStapleIndex);
            if (count == 2) {
                result = calculationWithOperatorsPriority (symbols, result);
            }
            if (count > 2) {
                openStaples (symbols, expressions, leftStapleIndex, rightStapleIndex);
            }
            count--;
            if (count == 1) {
                count = 0;
            }
        }
    }

    //Удаление скобок, вычисления внутри скобок
    private void removeStaples (LinkedList<String> symbols, LinkedList<String> expressions, int leftStapleIndex, int rightStapleIndex) {
        for (int i = leftStapleIndex + 1; i < rightStapleIndex; i++) {
            expressions.add (symbols.get (i));
        }
        result = calculationWithOperatorsPriority (expressions, result);
        expressions.clear ();
        symbols.add (leftStapleIndex, String.valueOf (result));
        for (int i = rightStapleIndex + 1; i > leftStapleIndex; i--) {
            symbols.remove (i);
        }
    }

    //Определение новых скобок, вычисление внутри новых скобок
    private void openStaples (LinkedList<String> symbols, LinkedList<String> expressions, int leftStapleIndex, int rightStapleIndex) {
        for (int i = 0; i < symbols.size (); i++) {
            if (symbols.get (i).equals ("(")) {
                leftStapleIndex = i;
            }
        }
        for (int j = symbols.size () - 1; j > 0; j--) {
            if (symbols.get (j).equals (")")) {
                rightStapleIndex = j;
            }
        }
        removeStaples (symbols, expressions, leftStapleIndex, rightStapleIndex);
    }

    //Расставление приоритетов операций
    private double calculationWithOperatorsPriority (LinkedList<String> expressions, double result) {
        LinkedList<String> doneList = new LinkedList<> ();
        HashMap<Integer, Double> countedExpressions = new HashMap<> ();
        String prevSign = "-";
        String sign = "";
        int restedOperations = -1;
        for (int i = 0; i < expressions.size (); i++) {
            if (expressions.get (i).equals ("*") || expressions.get (i).equals ("/") ||
                    expressions.get (i).equals ("+") || expressions.get (i).equals ("-")) {
                sign = expressions.get (i);
                if (sign.equals ("+") || sign.equals ("-")) {
                    prevSign = sign;
                }
                if ((sign.equals ("*") || sign.equals ("/")) && (prevSign.equals ("*") || prevSign.equals ("/"))) {
                    result = calculation (result, Double.parseDouble (expressions.get (i + 1)),
                            expressions.get (i).charAt (0));
                    countedExpressions.put (i, result);
                    countedExpressions.remove (restedOperations);
                    doneList.add (expressions.get (i));
                    doneList.add (expressions.get (i + 1));
                    prevSign = sign;
                }
                if ((sign.equals ("*") || sign.equals ("/")) && (prevSign.equals ("+") || prevSign.equals ("-"))) {
                    result = calculation (Double.parseDouble (expressions.get (i - 1)),
                            Double.parseDouble (expressions.get (i + 1)),
                            expressions.get (i).charAt (0));
                    countedExpressions.put (i, result);
                    restedOperations = i;
                    prevSign = sign;
                    doneList.add (expressions.get (i - 1));
                    doneList.add (expressions.get (i));
                    doneList.add (expressions.get (i + 1));
                }
            }
        }
        for (Integer integer : countedExpressions.keySet ()) {
            expressions.add (integer, countedExpressions.get (integer).toString ());
        }
        for (String countedExpression : doneList) {
            expressions.remove (countedExpression);
        }
        restedOperations = -1;
        for (int i = 0; i < expressions.size (); i++) {
            if ((expressions.get (i).equals ("+") || expressions.get (i).equals ("-")) && restedOperations < 0) {
                result = calculation (Double.parseDouble (expressions.get (i - 1)),
                        Double.parseDouble (expressions.get (i + 1)),
                        expressions.get (i).charAt (0));
                restedOperations++;
            } else if ((expressions.get (i).equals ("+") || expressions.get (i).equals ("-")) && restedOperations >= 0) {
                result = (calculation (result, Double.parseDouble (expressions.get (i + 1)), expressions.get (i).charAt (0)));
            }
        }
        return result;
    }

    //Вычисление выражений
    private double calculation (double firstNumber, double secondNumber, char sign) {
        double result = 0;
        switch (sign) {
            case '+': {
                result = firstNumber + secondNumber;
                break;
            }
            case '-': {
                result = firstNumber - secondNumber;
                break;
            }
            case '*': {
                result = firstNumber * secondNumber;
                break;
            }
            case '/': {
                result = firstNumber / secondNumber;
                break;
            }
            default: {
                System.out.println ("Не верная операция");
            }
        }
        return result;
    }
}
