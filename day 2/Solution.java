import java.io.*;
import java.util.*;

public class Solution {
    public static void getReports(String filename, List<List<Integer>> report){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line; 
            while ((line = br.readLine())!=null){
                String[] parts = line.split("\\s+"); 
                List<Integer> subReport = new ArrayList<>(); 
                for(String part: parts){
                    subReport.add(Integer.parseInt(part)); 
                }
                report.add(subReport); 
            }
        }
        catch (IOException e){
            System.err.println("An Error occurred: " + e); 
        }

    }

    public static int safeReports(List<List<Integer>> report){
        int ans = 0;  
        for (List<Integer> levels : report){
            if (isSafe(levels)) {
                ans++; // Increment if the report is safe
            }
        }
        return ans; 
    }

    public static int safeReportsDampener(List<List<Integer>> report){
        int ans = 0;  
        for (List<Integer> levels : report){
            if (isSafe(levels)) {
                ans++; // Increment if the report is safe
            }
            else {
                // Check if removing one level makes it safe
                for (int i = 0; i < levels.size(); i++) {
                    // Create a new list without the current level
                    List<Integer> modifiedLevels = new java.util.ArrayList<>(levels);
                    modifiedLevels.remove(i);

                    if (isSafe(modifiedLevels)) {
                        ans++; // Increment if the modified report is safe
                        break; // No need to check further removals
                    }
                }
            }
        }
        return ans; 
    }

    private static boolean isSafe(List<Integer> levels) {
        boolean increasing = true;
        boolean decreasing = true;
        
        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i) - levels.get(i - 1);
            
            // Check if the difference is within the acceptable range
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }
            
            // Update monotonicity checks
            if (diff > 0) decreasing = false; // Not decreasing
            if (diff < 0) increasing = false; // Not increasing
        }
        
        // Return true if levels are either all increasing or all decreasing
        return increasing || decreasing;
    }



    public static void main(String[] args){
        List<List<Integer>> report = new ArrayList<>();
        getReports("/Users/prachiheda/Desktop/advent of code/advent-of-code/day 2/input.txt", report);

        int numSafe = safeReports(report); 
        int numSafeDamp = safeReportsDampener(report); 
        System.out.println("Answer: " + numSafe);
        System.out.println("Answer Part 2: " + numSafeDamp); 
    }
    
}
