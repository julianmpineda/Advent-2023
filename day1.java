import java.util.*;
import java.io.*;

public class day1 {
    public static void main(String[] args) {

        List<String> calibration = new ArrayList<String>();

        try {
            File myObj = new File("src/data/data1.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                calibration.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int sum = 0;

        for (int i = 0; i < calibration.size(); i++) {
            String temp = calibration.get(i);

            //Part 2 code, can disable to only do Part 1.
            temp = temp.replaceAll("one", "o1e");
            temp = temp.replaceAll("two", "t2o");
            temp = temp.replaceAll("three", "t3e");
            temp = temp.replaceAll("four", "f4r");
            temp = temp.replaceAll("five", "f5e");
            temp = temp.replaceAll("six", "s6x");
            temp = temp.replaceAll("seven", "s7n");
            temp = temp.replaceAll("eight", "e8t");
            temp = temp.replaceAll("nine", "n9e");
            //End Part 2 code

            int start = 0, end = temp.length()-1;

            while (!Character.isDigit(temp.charAt(start)) && start < temp.length()) {
                start++;
            }
            while (!Character.isDigit(temp.charAt(end)) && end >= 0) {
                end--;
            }

            sum += Integer.parseInt("" + temp.charAt(start) + temp.charAt(end));
        }
        System.out.println(sum);
    }
}
