import java.io.*;
import java.util.*;

public class Solution{
    
    public static void getLists(String filename, List<Integer> list1, List<Integer> list2){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line; 
            while ((line = br.readLine())!=null){
                String[] parts = line.split("\\s+"); 
                list1.add(Integer.parseInt(parts[0])); 
                list2.add(Integer.parseInt(parts[1])); 
            }
        }
        catch (IOException e){
            System.err.println("An Error occurred: " + e); 
        }

    }

    public static int listDifference(List<Integer> list1, List<Integer> list2){
        int ans = 0; 
        Collections.sort(list1); 
        Collections.sort(list2); 
        for(int i=0; i<list1.size(); i++){
            ans += Math.abs(list1.get(i)-list2.get(i));
        }
        return ans; 
    }

    public static void populateList2Map(Map<Integer, Integer> map, List<Integer> list2){
        for(int num: list2){
            map.put(num, map.getOrDefault(num,0)+1); 
        }
    }

    public static int similarityScore(Map<Integer, Integer> map, List<Integer> list1){
        int ans = 0; 
        for(int num: list1){
            if(!map.containsKey(num)){
                continue; 
            }
            ans+= map.get(num)*num; 
        }
        return ans; 
    }

    public static void main(String[] args){
        List<Integer> list1 = new ArrayList<>(); 
        List<Integer> list2 = new ArrayList<>(); 

        getLists("/Users/prachiheda/Desktop/advent of code/advent-of-code/day 1/input.txt", list1, list2); 
        if (list1.isEmpty() || list2.isEmpty()) {
            System.err.println("Error: One or both lists are empty. Check the input file.");
            return;
        }
        int ans = listDifference(list1, list2); 
        System.out.println("Answer: " + ans); 

        Map<Integer, Integer> map = new HashMap<>(); 
        populateList2Map(map, list2); 
        int similarScore = similarityScore(map, list1); 
        System.out.println("Similarity Score: "+  similarScore); 

    }
}