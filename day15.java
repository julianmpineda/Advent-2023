import java.util.*;
import java.io.*;

public class day15 {
    public static void main(String[] args) {
        int sumOne = 0, sumTwo = 0;
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
        ArrayList[] boxes = new ArrayList[256];
        Map<String, Integer> lenses = new HashMap<>();

        for (int i = 0; i < sequence.length; i++) {
            // Part 1
            int value = hash(sequence[i]);
            sumOne += value;
            // end Part 1

            // Part 2
            String[] lens = sequence[i].split("[-=]");
            int boxNum = hash(lens[0]);
            System.out.println(lens[0] + " -> " + boxNum);

            if (boxes[boxNum] == null) {
                boxes[boxNum] = new ArrayList<String>();
            }

            if (sequence[i].contains("=")){
                if (!boxes[boxNum].contains(lens[0])) {
                  boxes[boxNum].add(lens[0]);
                }
                lenses.put(lens[0], Integer.parseInt(lens[1]));
            } else if (sequence[i].contains("-")) {
                if (boxes[boxNum].size() > 0 && boxes[boxNum].contains(lens[0])) {
                    boxes[boxNum].remove(lens[0]);
                }
            }
        }

        for (int j = 0; j < 256; j++) {
            if (boxes[j] != null) {
                for (int k = 0; k < boxes[j].size(); k++) {
                    sumTwo += (j + 1) * (k + 1) * lenses.get(boxes[j].get(k));
                }
            }
        }

        System.out.println("Part 1: " + sumOne);
        System.out.println("Part 2: " + sumTwo);
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
