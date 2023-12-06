import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> dist = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            String timeInput = input.nextLine().split(":")[1];
            String distInput = input.nextLine().split(":")[1];
            for (String str : timeInput.split(" ")) {
                if (!str.isBlank()) {
                    times.add(Integer.parseInt(str));
                }
            }
            for (String str : distInput.split(" ")) {
                if (!str.isBlank()) {
                    dist.add(Integer.parseInt(str));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        int sum = 1;
        for (int i = 0; i < times.size(); i++) {
            sum *= getDiff(times.get(i), dist.get(i));
        }
        System.out.println(sum);
    }

    private static int getDiff(int time, int dist) {
        int sum = 0;
        for (int i = 0; i < time; i++) {
            if (i * (time - i) > dist) {
                sum++;
            }
        }
        return sum;
    }
}