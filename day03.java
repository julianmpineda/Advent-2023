import java.sql.Array;
import java.util.*;
import java.io.*;
public class day03 {

    public static List<int[]> symbols = new ArrayList<int[]>();
    public static List<int[]> gears = new ArrayList<int[]>();
    public static List<ArrayList<int[]>> numbers = new ArrayList<ArrayList<int[]>>();

    public static void main(String[] args) {

        List<String> mapStr = new ArrayList<String>();
        int sum = 0;
        Long gearRatio = 0L;

        try {
            File myObj = new File("src/data/data03.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                mapStr.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Pad extra lines around the actual map to make it easier to search around numbers
        int mapLen = mapStr.size() + 2;
        int lineLen = mapStr.get(0).length() + 2;

        char[][] map = new char[mapLen][lineLen];

        // Initialize padded lines to '.'
        for (int i = 0; i < lineLen; i++) {
            map[0][i] = '.';
            map[mapLen - 1][i] = '.';
            // Assumes map is a square
            map[i][0] = '.';
            map[i][lineLen - 1] = '.';
        }

        // Create map from data      
        for (int j = 0; j < mapStr.size(); j++) {
            char[] temp = mapStr.get(j).toCharArray();
            for (int k = 0; k < temp.length; k++) {
                map[j + 1][k + 1] = temp[k];
            }
        }

        // Build coordinate list of symbols
        for (int r = 1; r < mapLen; r++) {
            for (int s = 1; s < lineLen; s++) {
                if (map[r][s] == '*') {
                    symbols.add(new int[]{r, s});
                    gears.add(new int[]{r, s});
                } else if (map[r][s] != '.' && !Character.isDigit(map[r][s])) {
                    symbols.add(new int[]{r, s});
                }
            }
        }

        // Part 1 and also creates number indexes for Part 2
        for (int t = 1; t < mapLen; t++) {
            ArrayList<int[]> numCoord = new ArrayList<int[]>();
            String num = "";
            boolean next = false;

            for (int u = 1; u < lineLen; u++) {
                if (Character.isDigit(map[t][u])) {
                    num += "" + map[t][u];
                    numCoord.add(new int[]{t, u});
                    if (!next) {
                        next = checkCircle(t, u, map);
                    }
                } else {
                    if (next) {
                        sum += Integer.parseInt(num);
                        numbers.add(new ArrayList(numCoord));
                    }
                    numCoord.clear();
                    num = "";
                    next = false;
                }
            }
        }

        System.out.println("Part 1: " + sum);

        // Begin Part 2 checking
        // for each gear
        for (int a = 0; a < gears.size(); a++) {

            int[] gear = gears.get(a);
            System.out.println("Checking gear [" + gear[0]+", "+gear[1]+"]");
            int ratio = 1, count = 0;

            // for each number
            for (int b = 0; b < numbers.size(); b++) {
                // for each digit
                for (int c = 0; c < numbers.get(b).size(); c++) {
                    int[] point = numbers.get(b).get(c);

                    if ((point[0] == gear[0] && point[1] == gear[1] - 1) ||
                            (point[0] == gear[0] - 1 && point[1] == gear[1] - 1) ||
                            (point[0] == gear[0] - 1 && point[1] == gear[1]) ||
                            (point[0] == gear[0] - 1 && point[1] == gear[1] + 1) ||
                            (point[0] == gear[0] && point[1] == gear[1] + 1) ||
                            (point[0] == gear[0] + 1 && point[1] == gear[1] + 1) ||
                            (point[0] == gear[0] + 1 && point[1] == gear[1]) ||
                            (point[0] == gear[0] + 1 && point[1] == gear[1] - 1)) {

                        System.out.print(numBuild(numbers.get(b), map));
                        ratio *= numBuild(numbers.get(b), map);
                        count++;

                        if (count == 1){ System.out.print(" * "); }
                        if (count == 2){ System.out.println(); }
                        break;
                    }
                }
                if (count == 2) { break; }

            }
            if (count == 2) {
                gearRatio += ratio;
            }
        }
        System.out.println("Part 2: " + gearRatio);
    }
    
    public static boolean checkCircle(int x, int y, char[][] map) {
        boolean state = false;

        for (int i = 0; i < symbols.size(); i++) {
            int[] temp = symbols.get(i);
            state = ((temp[0] == x && temp[1] == y - 1) || (temp[0] == x - 1 && temp[1] == y - 1) ||
                    (temp[0] == x - 1 && temp[1] == y) || (temp[0] == x - 1 && temp[1] == y + 1) ||
                    (temp[0] == x && temp[1] == y + 1) || (temp[0] == x + 1 && temp[1] == y + 1) ||
                    (temp[0] == x + 1 && temp[1] == y) || (temp[0] == x + 1 && temp[1] == y - 1));

            if (state) {
                break;
            }
        }
        return state;
    }

    public static int numBuild(List<int[]> coor, char[][] map) {
        String num = "";

        for (int i = 0; i < coor.size(); i++) {
            num += map[coor.get(i)[0]][coor.get(i)[1]];
        }

        return Integer.parseInt(num);
    }
}
