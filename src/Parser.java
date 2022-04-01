import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Parser {

    private final HashMap<Integer, Double> sortedExpressions = new HashMap<> ();
    private int index = 0;

    public void calculate (String expression) {
        LinkedList<String> symbols = new LinkedList<> ();
        String[] symbol = expression.trim ().split (" ");
        Collections.addAll (symbols, symbol);
        staplesPriorityCheck (symbols);
        for (Double value : sortedExpressions.values ()) {
                System.out.println (value);
        }
    }

    private void staplesPriorityCheck (LinkedList<String> symbols) {
        LinkedList<String> expressions = new LinkedList<> ();
        LinkedList<String> countedItems = new LinkedList<> ();
        int count = 0;
        int leftStapleIndex = 0;
        int rightStapleIndex = 0;
        int nextLeftStapleIndex = 0;
        int nextRightStapleIndex = 0;
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
                for (int i = leftStapleIndex + 1; i < rightStapleIndex; i++) {
                    expressions.add (symbols.get (i));
                }


                int restedOperations = 0;

                for (int i = 0; i < expressions.size (); i++) {
                    if (expressions.get(i).equals ("*") || expressions.get(i).equals ("/")){
                        restedOperations++;
                    }
                }
                while (restedOperations>0){
                    restedOperations--;
                    for (int i = 0; i < expressions.size (); i++) {
                        if (expressions.get (i).equals ("*") || expressions.get (i).equals ("/")){
                            index++;
                            sortedExpressions.put (index, calc (Double.parseDouble (expressions.get (i-1)),
                                  Double.parseDouble (expressions.get (i+1)),
                                  expressions.get (i).charAt (0)));
                        }
                    }
                }
            }
          /*  while (count > 1) {
                LinkedList<String> expressionsLeft = new LinkedList<> ();
                LinkedList<String> expressionsRight = new LinkedList<> ();
                LinkedList<String> exceptionsLeft = new LinkedList<> ();
                LinkedList<String> exceptionsRight = new LinkedList<> ();

                count--;

                if (count == 1) {
                    count = 0;
                }

                // наполнение expressions, удаление уже отсортированых выражений
                fillingExpressions (symbols, expressions, countedItems, leftStapleIndex, rightStapleIndex);

                // переборка левой части следующей скобки
                nextLeftStapleIndex = getNextLeftStapleIndex (symbols, leftStapleIndex, nextLeftStapleIndex, expressionsLeft);
                sortOperatorsPriority (expressionsLeft, exceptionsLeft);
                leftStapleIndex = nextLeftStapleIndex;
                nextLeftStapleIndex = 0;

                // переборка правой части следующей скобки
                nextRightStapleIndex = getNextRightStapleIndex (symbols, rightStapleIndex, nextRightStapleIndex, expressionsRight);

                // наполнение мапы согласно приоритетов операций
                sortOperatorsPriority (expressionsRight, exceptionsRight);
                rightStapleIndex = nextRightStapleIndex;
                nextRightStapleIndex = 0;

                // наполнение мапы не приоритетными операциями
                expressionsSideAdd (expressionsLeft);
                expressionsSideAdd (expressionsRight);

                //наполнение листа с уже отсортированными операциями
                countedItems.addAll (expressions);
                countedItems.addAll (exceptionsLeft);
                countedItems.addAll (exceptionsRight);
                countedItems.addAll (expressionsLeft);
                countedItems.addAll (expressionsRight);
                if (index < 3) {
                    expressions.clear ();
                    expressions.addAll (expressionsLeft);
                    expressions.addAll (expressionsRight);
                    index++;
                    sortedExpressions.put (index, new LinkedList<> (expressions));
                }
            }
            if (count == 0) {
                for (String countedItem : countedItems) {
                    System.out.println (countedItem);
                }
            }

           */

        }
    }

    private double calc (double firstNumber, double secondNumber, char sign){
        double result = 0;
        switch (sign){
            case '+':{
                result = firstNumber + secondNumber;
                break;
            }case '-':{
                result = firstNumber - secondNumber;
                break;
            }case '*':{
                result = firstNumber * secondNumber;
                break;
            }case '/':{
                result = firstNumber / secondNumber;
                break;
            }default :{
                System.out.println( "Не верная операция" );
            }
        }
        return result;
    }

   /* private void expressionsSideAdd (LinkedList<String> expressions) {

        if (expressions.size () > 0) {
            index++;
            sortedExpressions.put (index, new LinkedList<> (expressions));
        }
    }

    private void fillingExpressions (LinkedList<String> symbols, LinkedList<String> expressions,
                                     LinkedList<String> countedItems, int leftStapleIndex, int rightStapleIndex) {

        for (int i = leftStapleIndex + 1; i < rightStapleIndex; i++) {
            expressions.add (symbols.get (i));
        }
        expressions.removeAll (countedItems);
        if (countedItems.size () == 0) {
            index++;
            sortedExpressions.put (index, new LinkedList<> (expressions));
        }
    }

    private int getNextRightStapleIndex (LinkedList<String> symbols, int rightStapleIndex, int nextRightStapleIndex,
                                         LinkedList<String> expressionsRight) {

        for (int j = rightStapleIndex + 1; j < symbols.size (); j++) {
            if (symbols.get (j).equals (")")) {
                nextRightStapleIndex = j;
                break;
            }
        }
        if (nextRightStapleIndex > 0) {
            for (int j = rightStapleIndex + 1; j < nextRightStapleIndex; j++) {
                expressionsRight.add (symbols.get (j));
            }
        }
        return nextRightStapleIndex;
    }

    private int getNextLeftStapleIndex (LinkedList<String> symbols, int leftStapleIndex, int nextLeftStapleIndex,
                                        LinkedList<String> expressionsLeft) {

        for (int i = leftStapleIndex - 1; i > 0; i--) {
            if (symbols.get (i).equals ("(")) {
                nextLeftStapleIndex = i;
                break;
            }
        }
        for (int i = leftStapleIndex - 1; i > nextLeftStapleIndex; i--) {
            expressionsLeft.add (symbols.get (i));
        }
        return nextLeftStapleIndex;
    }

    private void sortOperatorsPriority (LinkedList<String> expressions, LinkedList<String> exceptions) {

        if ((expressions.contains ("*") || expressions.contains ("/")) &&
                !(expressions.contains ("-") || expressions.contains ("+"))) {
            index++;
            sortedExpressions.put (index, new LinkedList<> (expressions));
            expressions.clear ();
        } else if ((expressions.contains ("*") || expressions.contains ("/")) &&
                (expressions.contains ("-") || expressions.contains ("+"))) {
            if (expressions.get (0).equals ("*") || expressions.get (0).equals ("/")) {
                for (int i = 0; i < expressions.size (); i++) {
                    if (expressions.get (i).equals ("*") || expressions.get (i).equals ("/")) {
                        exceptions.add (expressions.get (i));
                        exceptions.add (expressions.get (i + 1));
                    }
                }
            }else{
                for (int i = expressions.size ()-1; i > 0 ; i--) {
                    if (expressions.get (i).equals ("*") || expressions.get (i).equals ("/")) {
                        exceptions.add (expressions.get (i-1));
                        exceptions.add (expressions.get (i));
                        exceptions.add (expressions.get (i+1));
                    }
                }
            }
            expressions.removeAll (exceptions);
         //   for (String exception : exceptions) {
         //       expressions.remove (exception);
          //  }
            index++;
            sortedExpressions.put (index, new LinkedList<> (exceptions));
        }
    }

  /*      expression = openBrackets( expression );
        expression = calcMultDivSequences( expression );
        String result = calcSequence( expression ); // here expression should contain only numbers, '+', and '~'
        //operations so we can just calc it as sequence of operation with equal priority
        return result;
    }

    // Example: ( 1 + 1 ) * 3 ~ ( 4 / 2 ) should return 2 * 3 ~ 2
    private String openBrackets( String expression ){
        int index = expression.length() - 1;
        int lastRightBracketIndex = 0;
        int openedBracketsAmnt = 0;
        char currentChar;
        while( -1 < index  ){
            currentChar = expression.charAt(index);
            if( currentChar == ')'  ){
                if( ++openedBracketsAmnt == 1 ){
                    lastRightBracketIndex = index;
                }
            }else if( currentChar == '('  ){
                if( --openedBracketsAmnt == 0 ){
                    expression = expression.substring( 0 , index ) +
                            calculate( expression.substring( index + 1, lastRightBracketIndex ) ) +
                            expression.substring( lastRightBracketIndex + 1 );
                }
            }
            index--;
        }
        return expression;
    }




    // Example: 1*8/4 ~ 3*16*11/24 + 4 * 6 should return 2 ~ 22 + 24
    public String calcMultDivSequences( String expression ) {
        int index = expression.length() - 1;
        int lastPlusOrMinusIndex = expression.length();
        char currentChar;
        while( -1 < index  ){
            currentChar = expression.charAt(index);
            if( currentChar == '+' || currentChar == '-'  ){
                expression = expression.substring( 0 , index + 1 ) +
                        calcSequence( expression.substring( index + 1, lastPlusOrMinusIndex ) ) +
                        expression.substring( lastPlusOrMinusIndex );
                lastPlusOrMinusIndex = index;
            }
            index--;
        }
        return expression;
    }

    // Important !1!  input string  should contain operations with equal priority
    private String calcSequence( String expression ){
        int index = 0;
        double result = 1.0;
        char prevSign = '*';
        int prevSignIndex = -1;
        char currentChar;
        while( true ){
            currentChar = expression.charAt( index );
            if( currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/' ){
                result = calc( result,
                        Double.parseDouble(expression.substring( prevSignIndex + 1, index )),
                        prevSign
                );
                prevSign = currentChar;
                prevSignIndex = index;
            }else if( index == expression.length() - 1 ){
                result = calc( result,
                        Double.parseDouble(expression.substring( prevSignIndex + 1, index + 1 )),
                        prevSign
                );
                break;
            }
            index++;
        }
        return Double.toString( result );
    }
*/
    // Just calc

}
