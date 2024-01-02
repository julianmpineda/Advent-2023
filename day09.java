import java.io.*;
import java.util.*;

public class day09 {
    public static void main(String[] args) {
        List<String> history = new ArrayList<>();
        int sumOne = 0, sumTwo = 0;

        try {
            File myObj = new File("src/data/data09.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                history.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < history.size(); i++) {
            String[] sequenceStr = history.get(i).split(" ");
            int[] seqOne = new int[sequenceStr.length];
            int[] seqTwo = new int[sequenceStr.length];
            int end = seqTwo.length -1;

            for(int j = 0; j < seqOne.length; j++) {
                seqOne[j] = Integer.parseInt(sequenceStr[j]);
                seqTwo[end - j] = Integer.parseInt(sequenceStr[j]);
            }

            sumOne += next(seqOne);
            sumTwo += next(seqTwo);
        }

        System.out.println("Part 1: " + sumOne);
        System.out.println("Part 2: " + sumTwo);
    }

    public static int next(int[] sequence){
        int sum = 0;
        int[] subSeq = new int[sequence.length-1];
        boolean end = true;

        for (int i = 0; i < subSeq.length; i++) {
            subSeq[i] = sequence[i+1] - sequence[i];
            end = end && (subSeq[i] == 0);
        }

        if (end) {
            return sequence[sequence.length-1];
        } else {
            return sequence[sequence.length-1] + next(subSeq);
        }
    }
}
