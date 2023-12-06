import java.nio.file.Paths;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        String times = "";
        String dist = "";
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            String timeInput = input.nextLine().split(":")[1];
            String distInput = input.nextLine().split(":")[1];
            for (String str : timeInput.split(" ")) {
                if (!str.isBlank()) {
                    times += str;
                }
            }
            for (String str : distInput.split(" ")) {
                if (!str.isBlank()) {
                    dist += str;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        long sum = getDiff(Long.parseLong(times), Long.parseLong(dist));
        System.out.println(sum);
    }

    private static long getDiff(long time, long dist) {
        long sum = 0;
        for (long i = 0; i < time; i++) {
            if (i * (time - i) > dist) {
                sum++;
            }
        }
        return sum;
    }
}