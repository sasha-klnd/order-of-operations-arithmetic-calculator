/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Driver {

    public static void main(String[] args) {
        
        // Stack for holding operators
        ArrayStack<String> operatorStack = new ArrayStack<>(10);

        // Queue for holding the results of operations
        LLQueue<String> resultQueue = new LLQueue<>();

        Scanner reader = null;
        try {
            reader = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        

        /* int res = evaluateExpression(reader, opStack, valStack);

        System.out.println(res); */

        /* valStack.push(5);
        opStack.push("+");
        valStack.push(5);
        opStack.push("*");
        valStack.push(2); */
    }

    public static boolean isNumeric(String s) {
        // Simple method to check if a string is numeric, without risking any unwanted exceptions
        
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(String s) {
        // Returns whether the string parameter is an operator
        String operators = "+-*/^<>";
        String le = "<=";
        String ge = ">=";
        return (operators.contains(s) || s.equals(le) || s.equals(ge));
    }
    
    public static int precedence(String operator) {
        // Returns an integer representing the "precedence" of an operator, based on the order of operations
        // A higher value indicates higher precedence
        // Incldes a $ operator that indicates the lowest possible precedence

        if (operator.equals("$")) {
            return 5;
        } else if (operator.equals("<")  || operator.equals(">") || operator.equals("<=") || operator.equals(">=")) {
            return 4;
        } else if (operator.equals("+") || operator.equals("-")) {
            return 3;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else if (operator.equals("^")) {
            return 1;
        } else {
            return 10;
        }
    }



    public static void performOperation(ArrayStack opStack, ArrayStack valStack) {
        int x = (int) valStack.pop();
        int y = (int) valStack.pop();
        String operator = (String) opStack.pop();

        if (operator.equals("+")) {
            valStack.push(x + y);
        } else if (operator.equals("-")) {
            valStack.push(x - y);
        } else if (operator.equals("*")) {
            valStack.push(x * y);
        } else if (operator.equals("/")) {
            valStack.push(x / y);
        } else if (operator.equals("^")) {
            valStack.push(Math.pow(x, y));
        }        
    }

    public static void repeatOperations(ArrayStack opStack, ArrayStack valStack, String operator) {
        while (valStack.size() > 1 && (prec(operator) <= prec((String) opStack.top()))) {
            performOperation(opStack, valStack);
        }
    }

    public static int evaluateExpression(Scanner reader, ArrayStack opStack, ArrayStack valStack) {
        while (reader.hasNext()) {
            String token = reader.next();
            if (isNumeric(token)) {
                valStack.push(Integer.parseInt(token));
            } else {
                repeatOperations(opStack, valStack, token);
                opStack.push(token);
            }
        }

        repeatOperations(opStack, valStack, "$");

        return (int) valStack.top();
    }

    public static int shuntingSort(Scanner reader, ArrayStack operatorStack, LLQueue resultQueue) {
        // This method will read an expression from the input file, written in infix notation, and 
        // push the equivalent expression in Reverse Polish format onto the resultQueue.

        String token;
        while (reader.hasNext()) {
            token = reader.next();
            if (isNumeric(token)) {
                resultQueue.enqueue(token);
            } else if (isOperator(token)) {
                while (precedence((String) operatorStack.top()) < precedence(token)) {
                    resultQueue.enqueue(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {

            } else if (token.equals(")")) {

            }


        }
        
        return 0;
    }

}