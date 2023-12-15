import java.io.*;
import java.util.*;

public class day04 {
    public static void main(String[] args) {

        List<Integer> scratchOffs = new ArrayList<Integer>();
        List<String> pile = new ArrayList<String>();
        List<String[]> cards = new ArrayList<String[]>();
        int scoreSum = 0, cardSum = 0;

        try {
            File myObj = new File("src/data/data04.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                pile.add(myReader.nextLine());
                scratchOffs.add(1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Creates list of array of cards from stack
        for (int i = 0; i < pile.size(); i++) {

            Set<String> winners = new HashSet<String>();
            //very ugly
            String[] card = pile.get(i).substring(pile.get(i).indexOf(':') + 2).split("\\|");
            String[] numbers = new String[25];

            //Code to parse winning numbers and play numbers
            int wIndex = 0, pIndex = 1, score = 0, count = 0;

            while (wIndex < card[0].length()) {
                winners.add(card[0].substring(wIndex, (wIndex + 2)));
                wIndex += 3;
            }

            for (int j = 0; j < 25; j++) {
                numbers[j] = card[1].substring(pIndex, (pIndex + 2));
                pIndex += 3;
            }

            // Part 1

            for (int k = 0; k < numbers.length; k++) {
                if (winners.contains(numbers[k])) {
                    if (score == 0) {
                        score++;
                    } else {
                        score *= 2;
                    }
                }
            }

            scoreSum += score;

            // end Part 1

            // Part 2

            for (int k = 0; k < numbers.length; k++) {
                if (winners.contains(numbers[k])) {
                    count++;
                }
            }

            //Skipped 'l' because it's very similar to '1' (one)
            for (int m = 1; m <= count; m++) {
                scratchOffs.set(i+m, (scratchOffs.get(i+m) + scratchOffs.get(i)));

            }

        }

        for (int n : scratchOffs) {
            cardSum += n;
        }

        System.out.println("Part 1: " + scoreSum);
        System.out.println("Part 2: " + cardSum);
    }
}
