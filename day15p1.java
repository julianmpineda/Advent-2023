import java.util.*;
import java.io.*;

public class day15 {
    public static void main(String[] args) {
        int sum = 0, commas = 0;
        String input = "";

        try {
            File myObj = new File("src/data/data15.txt");
            Scanner myReader = new Scanner(myObj);
            input = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] sequence = input.split(",");

        for (int i = 0; i < sequence.length; i++) {
            int value = hash(sequence[i]);
            sum += value;
        }

        System.out.println("Part 1: " + sum);
    }

    private static int hash(String s) {

        int value = 0;

        for (int i = 0; i < s.length(); i++) {
            int c = (int) s.charAt(i);
            value += c;
            value *= 17;
            value = value % 256;
        }

        return value;
    }
}
