import java.util.*;
import java.io.*;

public class day2 {
    public static void main(String[] args) {

        List<String> bag = new ArrayList<String>();

        try {
            File myObj = new File("src/data/data2.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                bag.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String[]> games = new ArrayList<String[]>();
        int sum = 0, powSum = 0;

        //Creates list of array of games from bag
        for (int i = 0; i < bag.size(); i++) {
            //very ugly
            games.add(bag.get(i).substring(bag.get(i).indexOf(':') + 2).split(";"));
        }

        // Parse each game
        // Get game
        for (int j = 0; j < bag.size(); j++) {
            int redMax = 0, blueMax = 0, greenMax = 0;
            String[] temp = games.get(j);
            Boolean possible = true;

            //break game into rounds
            for (int k = 0; k < temp.length; k++) {
                String[] cubes = temp[k].split("[,]");

                //break rounds into pulls
                for (int l = 0; l < cubes.length; l++) {
                    String[] pull = cubes[l].trim().split(" ");
                    int num = Integer.parseInt(pull[0]);
                  
                    if (pull[1].equals("red")) {
                        redMax = Math.max(num, redMax);
                        if (num > 12) {
                            possible = false;
                        }
                    } else if (pull[1].equals("green")) {
                        greenMax = Math.max(num, greenMax);
                        if (num > 13) {
                            possible = false;
                        }
                    } else if (pull[1].equals("blue")) {
                        blueMax = Math.max(num, blueMax);
                        if (num > 14) {
                            possible = false;
                        }
                    }
                }

            }

            powSum += (redMax * blueMax * greenMax);
            if (possible) {
                sum += j + 1;
            }
        }

        System.out.println("Part 1: " + sum);
        System.out.println("Part 2: " + powSum);
    }
}

