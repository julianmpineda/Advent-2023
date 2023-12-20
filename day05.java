import java.io.*;
import java.util.*;

public class day05 {
    public static void main(String[] args) {
        List<String> map = new ArrayList<String>();
        List<long[]> se2so = new ArrayList<long[]>();
        List<long[]> so2f = new ArrayList<long[]>();
        List<long[]> f2w = new ArrayList<long[]>();
        List<long[]> w2li = new ArrayList<long[]>();
        List<long[]> li2t = new ArrayList<long[]>();
        List<long[]> t2h = new ArrayList<long[]>();
        List<long[]> h2lo = new ArrayList<long[]>();

        try {
            File myObj = new File("src/data/data05.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                map.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Build seeds and "transform" lists

        // Part 1 Seeds
        long[] seeds = Arrays.stream(map.get(0).substring(7).split(" ")).mapToLong(Long::parseLong).toArray();

        // Skip seeds line and first empty line
        int count = 3;

        while (!map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            se2so.add(temp);
            count++;
        }

        count += 2;

        while (!map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            so2f.add(temp);
            count++;
        }
        count += 2;

        while (!map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            f2w.add(temp);
            count++;
        }
        count += 2;

        while (!map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            w2li.add(temp);
            count++;
        }
        count += 2;

        while (!map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            li2t.add(temp);
            count++;
        }
        count += 2;

        while (!map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            t2h.add(temp);
            count++;
        }

        count += 2;

        while (count < map.size() && !map.get(count).equals("") && Character.isDigit(map.get(count).charAt(0))) {
            long[] temp = Arrays.stream(map.get(count).split(" ")).mapToLong(Long::parseLong).toArray();
            h2lo.add(temp);
            count++;
        }

        // Part 1

        long[] soil = convert(seeds, se2so);
        long[] fert = convert(soil, so2f);
        long[] water = convert(fert, f2w);
        long[] light = convert(water, w2li);
        long[] tempe = convert(light, li2t);
        long[] hum = convert(tempe, t2h);
        long[] loc = convert(hum, h2lo);

        long low = Long.MAX_VALUE;
      
        for (long x : loc) {
            low = Math.min(x, low);
        }
      
        System.out.println("Part 1: " + low);
        
        // Part 2
        boolean found = false;
        long low2 = 0L;

        while (!found) {

            long hum2 = trevnoc(low2, h2lo);
            long tempe2 = trevnoc(hum2, t2h);
            long light2 = trevnoc(tempe2, li2t);
            long water2 = trevnoc(light2, w2li);
            long fert2 = trevnoc(water2, f2w);
            long soil2 = trevnoc(fert2, so2f);
            long trevSeed = trevnoc(soil2, se2so);

            for (int s = 0; s < seeds.length; s += 2) {
                if (trevSeed >= seeds[s] && trevSeed <= (seeds[s] + seeds[s + 1])) {
                    found = true;
                }
            }
            low2++;
        }

        System.out.println("Part 2: " + (low2-1));
    }

    public static long[] convert(long[] seeds, List<long[]> conv) {
        long[] temp = new long[seeds.length];

        for (int i = 0; i < seeds.length; i++) {
            boolean found = false;
            Long seed = seeds[i];
            for (int j = 0; j < conv.size(); j++) {
                Long dest = conv.get(j)[0];
                Long source = conv.get(j)[1];
                Long range = conv.get(j)[2];
                Long offset = 0L;

                if (seed >= source && seed < source + range ) {
                    offset = seed - source;
                    temp[i] = dest + offset;
                    found = true;
                    break;
                }
            }
            if (!found) {
                temp[i] = seeds[i];
            }
        }
        return temp;
    }

    public static long trevnoc(long seed, List<long[]> conv) {

        //flipped the source and destination, also only runs on a single number now not an array
        long temp = seed;

        for (int j = 0; j < conv.size(); j++) {
            Long source = conv.get(j)[0];
            Long dest = conv.get(j)[1];
            Long range = conv.get(j)[2];
            Long offset = 0L;

            if (seed >= source && seed < source + range ) {
                offset = seed - source;
                temp = dest + offset;
                break;
            }

        }
        return temp;
    }
}
