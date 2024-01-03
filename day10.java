import java.io.*;
import java.util.*;

public class day10 {
    public static void main(String[] args) {
        List<String> map = new ArrayList<>();
        // R, U, L, D
        int[][] dir = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
        char[] fac = new char[]{'R', 'U', 'L', 'D'};
        int startV = 0, startH = 0;
        int count = 0, facing = 0;

        try {
            File myObj = new File("src/data/data10.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String temp = myReader.nextLine();
                if (temp.contains("S")) {
                    startV = count;
                    startH = temp.indexOf("S");
                }
                map.add(temp);
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int height = map.size();
        int width = map.get(0).length();
        char[][] pipes = new char[height][width];
        int[][] distance = new int[height][width];
        int currV = startV, currH = startH;

        for (int i = 0; i < height; i++) {
            pipes[i] = map.get(i).toCharArray();
        }

        // Part 1 Begin
        // Find start direction. Does not matter which one since we should traverse all connected pipes.

        if ("F|7".contains("" + pipes[currV - 1][currH])) { // check up
            if (pipes[currV - 1][currH] == '|') { // turn up
                facing = 1;
            } else if (pipes[currV - 1][currH] == '7') { // turn left
                facing = 2;
            } // F - keep right
            currV--;
        } else if ("J-7".contains("" + pipes[currV][currH + 1])) { // check right
            if (pipes[currV][currH + 1] == 'J') { // turn up
                facing = 1;
            } else if (pipes[currV][currH + 1] == '7') { // turn down
                facing = 3;
            } // - -> keep right
            currH++;
        } else if ("J|L".contains("" + pipes[currV + 1][currH])) { // check down
            if (pipes[currV + 1][currH] == '|') {
                facing = 3;
            } else if (pipes[currV + 1][currH] == 'J') { // J - turn left
                facing = 2;
            } // L - keep right
            currH++;
        } else if ("F-L".contains("" + pipes[currV][currH - 1])) { // check left
            if (pipes[currV][currH - 1] == '-') {
                facing = 2;
            } else if (pipes[currV][currH - 1] == 'L') { // L - turn up
                facing = 1;
            } else { // F - turn down
                facing = 3;
            }
            currH--;
        }
        
        count = 1;

        while (!(currV == startV && currH == startH)) {
            distance[currV][currH] = count;
            count++;

            currV += dir[facing][0];
            currH += dir[facing][1];

            // turn if needed
            if (pipes[currV][currH] == 'F') {
                facing = (facing == 2) ? 3 : 0;
            } else if (pipes[currV][currH] == '7') {
                facing = (facing == 0) ? 3 : 2;
            } else if (pipes[currV][currH] == 'J') {
                facing = (facing == 0) ? 1 : 2;
            } else if (pipes[currV][currH] == 'L') {
                facing = (facing == 2) ? 1 : 0;
            }
        }

        System.out.println("Part 1: " + (count / 2));

        // Part 1 Begin
        int tile = 0;
       
        distance[startV][startH] = 1; // Set start to be "on path". Actual value doesn't matter as long as > 0.
        pipes[startV][startH] = '|'; //cheating a little to pre-compute the start tile.

        for (int m = 0; m < height; m++) {
            boolean inside = false;
            for (int n = 0; n < width; n++) {
                if (distance[m][n] > 0) {
                    if (pipes[m][n] == '|') {
                        inside = !inside;
                    } else if (pipes[m][n] == 'L') {
                        n++;
                        while (n < width && pipes[m][n] == '-') {
                            n++;
                        }
                        if (pipes[m][n] == '7') {
                            inside = !inside;
                        }
                    } else if (pipes[m][n] == 'F') {
                        n++;
                        while (n < width && pipes[m][n] == '-') {
                            n++;
                        }
                        if (pipes[m][n] == 'J') {
                            inside = !inside;
                        }
                    } else if (inside) {
                        tile++;
                    }
                } else if (inside) {
                    tile++;
                } 
            }
        }

        System.out.println("Part 2: " + tile);
    }
}
