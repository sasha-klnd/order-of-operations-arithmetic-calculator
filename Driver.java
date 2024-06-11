/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */


import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class Driver {

    public static void main(String[] args) {
        
        // Stack for holding operators
        ArrayStack<String> operatorStack = new ArrayStack<>(10);

        // Queue for holding the results of operations
        LLQueue<String> rpnQueue = new LLQueue<>();
        
        // Stack for computing the final result
        ArrayStack<String> resultStack = new ArrayStack<>(10);

        // Initialize and try to assign scanner
        Scanner reader = null;
        try {
            reader = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // Initialize and try to assign PrintWriter
        PrintWriter printer = null;
        try {
            printer = new PrintWriter(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        while (reader.hasNextLine()) {
            // Sort the expression into rpnQueue
            shuntingSort(reader, operatorStack, rpnQueue);
    
            // Evaluate the expression
            String result = evaluateExpression(rpnQueue, resultStack);
    
            printer.println(result);
        }

        reader.close();
        printer.close();
    }

    public static boolean isNumeric(String s) {
        // Simple method to check if a string is numeric, without risking any unwanted exceptions
        if (s == null) {
            return false;
        }

        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isOperator(String s) {
        // Returns whether the string parameter is an operator
        String operators = "+-*/^<>";
        String le = "<=";
        String ge = ">=";
        String et = "==";
        String ne = "!=";
        return (operators.contains(s) || s.equals(le) || s.equals(ge) || s.equals(et) || s.equals(ne));
    }
    
    public static int precedence(String operator) {
        // Returns an integer representing the "precedence" of an operator, based on the order of operations
        // A higher value indicates higher precedence
        // Incldes a $ operator that indicates the lowest possible precedence

        if (operator.equals("==")  || operator.equals("!=")) {
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

    public static void shuntingSort(Scanner reader, ArrayStack<String> operatorStack, LLQueue<String> rpnQueue) {
        // This method will read an expression from the input file, written in infix notation, and 
        // push the equivalent expression in postfix notation, or Reverse Polish Notation, onto the result queue.

        String token;
        while (reader.hasNext()) {
            token = Character.toString(reader.next().charAt(0));

            if (token.equals("\n")) {
                // Reader reaching a new line is also considered a stopping condition
                break;
            }
            if (isNumeric(token)) {
                // Numeric values are instantly placed in the queue
                rpnQueue.enqueue(token);
            } else if (isOperator(token)) {
                if (operatorStack.isEmpty()) {
                    // The operator gets pushed the to stack if it is empty
                    operatorStack.push(token);
                } else {
                    while (precedence((String) operatorStack.top()) < precedence(token)) {
                        // Pop all higher-precedence operators into the queue first to ensure order of operations is satisfied
                        rpnQueue.enqueue(operatorStack.pop());
                    }
                    // Once the new token is not of lower precedence, place it in the stack
                    operatorStack.push(token);
                }
            } 
            // Special cases for parentheses
            else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while(!operatorStack.top().equals("(")) {
                    // As long as the right parenthesis cannot find its left counterpart
                    rpnQueue.enqueue(operatorStack.pop());
                }
                operatorStack.pop(); // Pops and discards the left parenthesis once found
            }
        }

        // Once reader is finished, pop all remaining stack elements into the queue
        while (!operatorStack.isEmpty()) {
            rpnQueue.enqueue(operatorStack.pop());
        }

        // resultsQueue should now have the expression in Reverse Polish format
        return;
    }

    public static String evaluateExpression(LLQueue<String> rpnQueue, ArrayStack<String> resultStack) {
        // Evaluate an entire expression from one line of the text file

        String result = "";

        while (!rpnQueue.isEmpty()) {
            if (isNumeric((String) rpnQueue.front())) {
                // Push any numbers onto the result stack
                resultStack.push(rpnQueue.dequeue());
            } else {
                // Push the operator onto the stack
                resultStack.push(rpnQueue.dequeue());

                // Perform this operation on the two previous values and push result to stack
                resultStack.push(performOperation(rpnQueue, resultStack));
            }
        }

        return (String) resultStack.pop();
    }

    public static String performOperation(LLQueue<String> rpnQueue, ArrayStack<String> resultStack) {
        // Perform a single binary operation using the top three elements of the 

        // At this point the top element is the operator, and the subsequent two are operands
        String operator = (String) resultStack.pop();
        double operand2 = Double.parseDouble((String) resultStack.pop());
        double operand1 = Double.parseDouble((String) resultStack.pop());

        // For arithmetic operators, the operation is performed and the result is truncated to five significant figures
        // For comparison operators, the operation is performed and the result is a boolean value
        // In either case the result is converted to a string before being returned

        if (operator.equals("+")) {
            double result = operand1 + operand2;
            return Double.toString(Math.floor(result * 100000) / 100000);
        } else if (operator.equals("-")) {
            double result = operand1 - operand2;
            return Double.toString(Math.floor(result * 100000) / 100000);
        } else if (operator.equals("*")) {
            double result = operand1 * operand2;
            return Double.toString(Math.floor(result * 100000) / 100000);
        } else if (operator.equals("/")) {
            double result = operand1 / operand2;
            return Double.toString(Math.floor(result * 100000) / 100000);
        } else if (operator.equals("^")) {
            double result = Math.pow(operand1, operand2);
            return Double.toString(Math.floor(result * 100000) / 100000);
        } 
        
        else if (operator.equals("<")) {
            boolean result = operand1 < operand2;
            return Boolean.toString(result);
        } else if (operator.equals(">")) {
            boolean result = operand1 > operand2;
            return Boolean.toString(result);
        } else if (operator.equals("<=")) {
            boolean result = operand1 <= operand2;
            return Boolean.toString(result);
        } else if (operator.equals(">=")) {
            boolean result = operand1 >= operand2;
            return Boolean.toString(result);
        } else if (operator.equals("==")) {
            boolean result = operand1 == operand2;
            return Boolean.toString(result);
        } else if (operator.equals("!=")) {
            boolean result = operand1 != operand2;
            return Boolean.toString(result);
        } else {
            return "";
        }
    }

}