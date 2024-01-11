import java.io.*;
import java.util.*;
public class day14 {
    public static void main(String[] args) {

        List<char[]> tempPlatform = new ArrayList<>();

        try {
            File myObj = new File("src/data/data14.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                tempPlatform.add(myReader.nextLine().toCharArray());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        char[][] platform = new char[tempPlatform.size()][tempPlatform.get(0).length];
        List<int[]> stones = new ArrayList<>();

        for (int i = 0; i < tempPlatform.size(); i++) {
            platform[i] = tempPlatform.get(i);
        }

        int height = platform.length, width = platform[0].length, sum = 0, rotations = 0;
        boolean partOne = true;

        while (rotations < 1000) {
            for (int j = 0; j < 4; j++) {
                stones.clear();
                stones = buildStones(platform);
                platform = shift(stones, height, width);

                if (partOne) {
                    System.out.println("Part 1: " + readPlatform(platform));
                    partOne = false;
                }
                platform = rotateRight(platform);
            }
            rotations++;
        }
        System.out.println("Part 2: " + readPlatform(platform));
    }
    
    // Builds "stones" list
    public static List<int[]> buildStones(char[][] platform) {
        int height = platform.length, width = platform[0].length;
        List<int[]> stones = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            int round = 0;
            int[] cube = new int[]{-1, c};
            for (int r = 0; r < height; r++) {
                char space = platform[r][c];
                if (space == '#') {
                    stones.add(new int[]{cube[0], cube[1], round});
                    round = 0;
                    cube = new int[]{r, c};
                } else if (space == 'O') {
                    round++;
                }
            }
            stones.add(new int[]{cube[0], cube[1], round});
        }
        return stones;
    }

    // Creates new shifted platform based on "stones" data.
    public static char[][] shift(List<int[]> map, int height, int width) {
        char[][] shifted = new char[height][width];

        for (char[] r : shifted) {
            Arrays.fill(r, '.');
        }

        for (int i = 0; i < map.size(); i++) {
            int[] stop = map.get(i);
            if (stop[0] != -1) {
                shifted[stop[0]][stop[1]] = '#';
                for (int j = 1; j <= stop[2]; j++) {
                    shifted[stop[0] + j][stop[1]] = 'O';
                }
            } else {
                for (int j = 1; j <= stop[2]; j++) {
                    shifted[stop[0] + j][stop[1]] = 'O';
                }
            }
        }
        return shifted;
    }
    // Rotates platform right. Top Edge: North - West -> South -> East
    public static char[][] rotateRight(char[][] oldPlatform) {
        int height = oldPlatform.length, width = oldPlatform[0].length;
        char[][] newPlatform = new char[width][height];

        for (char[] r : newPlatform) {
            Arrays.fill(r, '.');
        }

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                newPlatform[c][height - 1 - r] = oldPlatform[r][c];
            }
        }
        return newPlatform;
    }

    // Reads stones weights
    public static int readPlatform(char[][] platform) {
        int sum = 0, height = platform.length;

        for (int r = 0; r < platform.length; r++) {
            for (int c = 0; c < platform[0].length; c++) {
                if (platform[r][c] == 'O') {
                    sum += (height - r);
                }
            }
        }
        return sum;
    }
}
