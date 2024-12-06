import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution3 {
    public static String getString(String filename) throws IOException {
        return Files.readString(Path.of(filename)); 
    }

    public static int parseString(String input){
        int sum = 0;  
        int i = 0; 
        boolean doCalculate = true; 
        while (i < input.length()) {
            // find do()
            if (i + 4 <= input.length() && input.substring(i, i + 4).equals("do()")) {
                doCalculate = true; 
            }
            //find don't()
            if (i + 7 <= input.length() && input.substring(i, i + 7).equals("don't()")) {
                doCalculate = false; 
            }

            // Find "mul("
            if (i + 4 <= input.length() && input.substring(i, i + 4).equals("mul(")) {
                int startIndex = i + 4;
                // Parse the first number
                int[] firstNumResult = parseNumber(input, startIndex);
                if (firstNumResult[0] == -1) {
                    // Not a valid number, move on
                    i++;
                    continue;
                }
                int x = firstNumResult[0];
                int posAfterX = firstNumResult[1];

                // Next character should be a comma
                if (posAfterX >= input.length() || input.charAt(posAfterX) != ',') {
                    i++;
                    continue; // no valid comma after first number
                }
                int posAfterComma = posAfterX + 1;

                // Parse the second number
                int[] secondNumResult = parseNumber(input, posAfterComma);
                if (secondNumResult[0] == -1) {
                    i++;
                    continue; // second number invalid
                }
                int y = secondNumResult[0];
                int posAfterY = secondNumResult[1];

                // Next character should be a closing parenthesis ')'
                if (posAfterY >= input.length() || input.charAt(posAfterY) != ')') {
                    i++;
                    continue; // no closing parenthesis
                }

                // If we reach this point, we have a valid mul(X,Y)
                if(doCalculate){
                    sum += x * y;
                }
                i = posAfterY + 1; // Move past this instruction
            } else {
                i++; // move on if no 'mul(' found
            }
        }
        return sum;
    }

    private static int[] parseNumber(String s, int start) {
        int i = start;
        int num = 0;
        int digitCount = 0;

        // We allow 1 to 3 digits
        while (i < s.length() && Character.isDigit(s.charAt(i)) && digitCount < 3) {
            num = num * 10 + (s.charAt(i) - '0');
            i++;
            digitCount++;
        }

        // If we got no digits, or more than 3 digits, it's invalid
        if (digitCount == 0 || (i < s.length() && Character.isDigit(s.charAt(i)))) {
            return new int[]{-1, start};
        }

        return new int[]{num, i};
    }

   


    public static void main(String[] args){
        String str=""; 
        try{
            str = getString("/Users/prachiheda/Desktop/advent of code/advent-of-code/day 3/input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int ans = parseString(str); 
        System.out.println(ans); 
      
            // try {
            //     // Adjust the path to your input file
            //     String input = Files.readString(Path.of("/Users/prachiheda/Desktop/advent of code/advent-of-code/day 3/input.txt"));
    
            //     // Regex to match mul(X,Y) where X and Y are 1-3 digit numbers
            //     Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
            //     Matcher matcher = pattern.matcher(input);
    
            //     int sum = 0;
            //     while (matcher.find()) {
            //         int x = Integer.parseInt(matcher.group(1));
            //         int y = Integer.parseInt(matcher.group(2));
            //         sum += (x * y);
            //     }
    
            //     System.out.println(sum);
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }


    }
}
