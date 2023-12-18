public class day07 {
    public static void main(String[] args) {

        System.out.println("Part 1: " + part1() + " ways");
        System.out.println("Part 2: " + part2() + " ways");
    }

    public static int part1() {
        int[] raceLength = new int[]{49, 78, 79, 80};
        int[] recordDistance = new int[]{298, 1185, 1066, 1181};
        int ways = 1;

        for (int i = 0; i < raceLength.length; i++) {
            int time = raceLength[i];
            int maxDis = recordDistance[i];
            int count = 0;

            for (int j = 1; j <= time; j++) {
                if ((j*(time - j)) > maxDis) {
                    count++;
                }
            }

            ways *= count;
        }
        return ways;
    }

    public static Long part2(){

        Long time = 49787980L;
        Long maxDis = 298118510661181L;
        Long count = 0L;

        for (Long j = 1L; j <= time; j++) {
            if (((j*(time - j))) > maxDis) {
                count++;
            }
        }

        return count;
    }
}
