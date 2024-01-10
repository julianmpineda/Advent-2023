import java.io.*;
import java.util.*;

public class day14 {
    public static void main(String[] args){

        List<String> platform = new ArrayList<>();

        try {
            File myObj = new File("src/data/data14.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                platform.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<int[]> stones = new ArrayList<>();
        int height = platform.size(), width = platform.get(0).length(), sum = 0;


        for (int i = 0; i < width; i++) {
            int round = 0, cube = -1;
            for (int j = 0; j < height; j++) {
                char space = platform.get(j).charAt(i);
                if (space == '#') {
                    if (round > 0) {
                        stones.add(new int[]{(height - cube), round});
                        round = 0;
                    }
                    cube = j;
                } else if (space == 'O') {
                    round++;
                }
            }
            if (round > 0) {
                stones.add(new int[]{(height - cube), round});
            }
        }

        for (int m = 0; m < stones.size(); m++) {
            int[] section = stones.get(m);
            for (int n = 1; n <= section[1]; n++) {
                sum += (section[0] - n);
            }
        }

        System.out.println("Part 1: " + sum);
    }
}
