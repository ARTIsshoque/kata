import java.util.Scanner;

public class MyCalculator {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Enter operation or 'quit' for exit.\n>> ");
            String operation = in.nextLine();
            if (operation.equals("quit"))
                break;
            String result = processOperation(operation);
            System.out.println(result);
        }
    }

    public static String processOperation(String operationLine) throws Exception {
        operationLine = operationLine.replace(" ", "");
        String operator = getOperator(operationLine);

        String firstOperandLine = getOperandLine(operationLine, 0, operator);
        int firstOperand = getOperand(firstOperandLine);
        boolean firstOpType = isNumeric(firstOperandLine);

        String secondOperandLine = getOperandLine(operationLine, 1, operator);
        int secondOperand = getOperand(secondOperandLine);
        boolean secondOpType = isNumeric(secondOperandLine);

        checkOperands(firstOperand, secondOperand, firstOpType, secondOpType);

        int res = switch (operator) {
            case "+" -> firstOperand + secondOperand;
            case "-" -> firstOperand - secondOperand;
            case "*" -> firstOperand * secondOperand;
            default -> firstOperand / secondOperand;
        };

        if (!firstOpType) {
            if (res < 1)
                throw new Exception("Unsupported operation");
            return intToRoman(res);
        }
        else
            return Integer.toString(res);
    }

    public static String getOperator(String opLine) throws Exception {
        int operatorsCount = opLine.length() - opLine.replaceAll("[-+*/]", "").length();
        if (operatorsCount != 1)
            throw new Exception("Invalid operation"); // Число операторов не равно 1
        String [] availableOperators = {"+", "-", "*", "/"};
        for (String op : availableOperators)
            if (opLine.contains(op))
                return op;
        return " "; // Такого не будет, но IDE не понимает
    }

    public static String getOperandLine(String opLine, int opNumber, String operator) {
        opLine = opLine.replace(operator, " ");
        return opLine.split(" ")[opNumber];
    }

    public static int getOperand(String opLine) throws Exception {
        if (isNumeric(opLine))
            return Integer.parseInt(opLine);
        else if (isRoman(opLine))
            return romanToInt(opLine);
        else
            throw new Exception("Not a supported value");
    }

    public static boolean isNumeric(String line) {
        int wrongChars =  line.length() - line.replaceAll("[^0-9]", "").length();
        return wrongChars == 0;
    }

    public static boolean isRoman(String line) {
        int wrongChars = line.length() - line.replaceAll("[^ivxIVX]", "").length();
        return wrongChars == 0;
    }

    public static void checkOperands(int first, int second, boolean firstType, boolean secondType) throws Exception {
        if (first > 10 || second > 10 || firstType != secondType)
            throw new Exception("Unsupported values");
    }

    public static int romanDigit(char digit) {
        return switch (digit) {
            case 'I', 'i' -> 1;
            case 'V', 'v' -> 5;
            case 'X', 'x' -> 10;
            default -> 0;
        };
    }

    public static int romanToInt(String number) {
        int total = 0;
        for (int i=0; i<number.length();i++) {
            int char1 = romanDigit(number.charAt(i));
            if (i+1 < number.length()) {
                int char2 = romanDigit(number.charAt(i+1));
                if (char1 >= char2)
                    total += char1;
                else
                    total -= char1;
            }
            else
                total += char1;
        }
        return total;
    }

    public static String intToRoman(int number) {
        StringBuilder roman = new StringBuilder();
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanDigits = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i=0; i<values.length;i++)
            while (number >= values[i]) {
                number = number - values[i];
                roman.append(romanDigits[i]);
            }
        return roman.toString();
    }
}
