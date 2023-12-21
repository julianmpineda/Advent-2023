
import java.util.*;
import java.io.*;

public class day08 {

    public static void main(String args[]) {

        Map<String, String[]> network = new HashMap<String, String[]>();
        List<String> pos = new ArrayList<String>();
        String instr = "", currPos = "";
        int steps = 0, index = 0;

        try {
            File myObj = new File("src/data/data08.txt");
            Scanner myReader = new Scanner(myObj);
            instr = myReader.nextLine();
            myReader.nextLine(); // throw away blank line
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String home = line.substring(0, 3);
                if (home.charAt(2) == 'A') {
                    pos.add(home);
                }
                String left = line.substring(7, 10), right = line.substring(12, 15);
                network.put(home, new String[]{left, right});
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        // Part 1
        currPos = "AAA";

        while (!currPos.equals("ZZZ")) {
            steps++;
            int dir = (instr.charAt(index) == 'L') ? 0 : 1;
            currPos = network.get(currPos)[dir];
            index = ((index + 1) % instr.length());
        }

        System.out.println("Part 1: " + steps);
        
        // Part 2

        steps = 0;
        index = 0;
        int pSize = pos.size(), min = Integer.MAX_VALUE;
        int[] loops = new int[pSize];

        for (int i = 0; i < pSize; i++) {
            currPos = pos.get(i);
            while (currPos.charAt(2) != 'Z') {
                steps++;
                int dir = (instr.charAt(index) == 'L') ? 0 : 1;
                currPos = network.get(currPos)[dir];
                index = ((index + 1) % instr.length());
            }

            loops[i] = steps;
            min = Math.min(min, steps);
            steps = 0;
        }
        
        int count = 1;
        long lcm = 0L, mult = 2L;

        while (count < pSize) {
            count = 0;
            lcm = min * mult;
            for (int j = 0; j < pSize; j++) {
                if ((loops[j] <= lcm) && (lcm % loops[j] == 0)) {
                        count++;
                }
            }
            mult++;
        }

        System.out.println("Part 2: " + lcm);
    }

}
