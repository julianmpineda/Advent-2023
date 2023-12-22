import java.util.*;
import java.io.*;

public class day07 {

    public static void main(String[] args) {

        boolean part2 = false;
        int nextRank = 1, totalWinnings = 0;
        //[Hand, Bid, Rank]
        Map<String, Integer> handRanks = new HashMap<>();
        Map<String, Integer> handValues = new HashMap<>();
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        int max = 0;

        // copy loop
        try {
            File myObj = new File("src/data/data07.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                keys.add(line.substring(0, 5));
                values.add(Integer.parseInt(line.substring(6)));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // end copy loop

        // parse loop

        for (int i = 0; i < keys.size(); i++) {
            //[2,3,4,5,6,7,8,9,T,J,Q,K,A];
            int[] cards = new int[13];
            char[] cardFace = new char[]{'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            String hand = keys.get(i), newHand = "";
            int type = 0;

            for (int j = 0; j < 5; j++) {
                char card = hand.charAt(j);

                if (card == 'A') {
                    cards[12]++;
                    newHand += "n";
                } else if (card == 'K') {
                    cards[11]++;
                    newHand += "m";
                } else if (card == 'Q') {
                    cards[10]++;
                    newHand += "l";
                } else if (card == 'J') {
                    cards[9]++;
                    newHand += part2 ? "a" : "k";
                } else if (card == 'T') {
                    cards[8]++;
                    newHand += "j";
                } else {
                    cards[(card - '0') - 2]++;
                    newHand += cardFace[(card - '0') - 2];
                }
            }

            for (int k = 0; k < 14; k++) {
                if (part2) { // automatically set to 5 of a kind
                    if (cards[9] == 4 || cards[9] == 5) {
                        type = 6;
                        break;
                    }
                }

                if (k == 9 && part2) {
                    //skip jokers in calculation
                    continue;
                }

                if (k == 13 && part2 && cards[9] > 0) {
                    if (cards[9] == 1) {
                        if (type == 0) {
                            type = 1;
                        } else if (type == 1) {
                            type = 3;
                        } else if (type == 2) {
                            type = 4;
                        } else if (type == 3) {
                            type = 5;
                        } else if (type == 5) {
                            type = 6;
                        }
                    } else if (cards[9] == 2) {
                        if (type == 0) {
                            type = 3;
                        } else if (type == 1) {
                            type = 5;
                        } else if (type == 3) {
                            type = 6;
                        }
                    } else if (cards[9] == 3) {
                        if (type == 0) {
                            type = 5;
                        } else if (type == 1) {
                            type = 6;
                        }
                    }
                    break;
                } else if (k == 13) {
                    break; // prevent "out of bounds" for part 1 or if no jacks.
                }

                if (cards[k] == 2) {
                    type += 1;
                } else if (cards[k] == 3) {
                    type += 3;
                } else if (cards[k] == 4) {
                    type += 5;
                } else if (cards[k] == 5) {
                    type += 6;
                }
            }
            handValues.put(newHand, values.get(i));
            handRanks.put(newHand, (type + 10000));
            max = Math.max((type + 10000), max);
        }

        // end parse loop
        int group = 7;
        while (group <= max) {
            List<String> tier = new ArrayList<>();

            for (String r : handRanks.keySet()) {
                int rank = handRanks.get(r);
                if (rank == group) {
                    tier.add(r);
                }
            }

            Collections.sort(tier);

            for (int s = 0; s < tier.size(); s++) {
                handRanks.put(tier.get(s), nextRank);
                nextRank++;
            }

            group++;
            tier.clear();
        }

        for (String s : handRanks.keySet()) {
            totalWinnings += handRanks.get(s) * handValues.get(s);
        }

        if (!part2) {
            System.out.println("Part 1: " + totalWinnings);
        } else {
            System.out.println("Part 2: " + totalWinnings);
        }

    }
}
