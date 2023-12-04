import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        int sum = 0;
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String[] line = input.nextLine().split(": ");
                String card = line[1];
                String[] tmp = card.split(" \\| ");
                ArrayList<String> win = new ArrayList<>(Arrays.asList(tmp[0].split(" ")));
                ArrayList<String> onHand = new ArrayList<>(Arrays.asList(tmp[1].split(" ")));

                int score = 0;
                for (int i = 0; i < onHand.size(); i++) {
                    if (match(onHand.get(i), win)) {
                        score = (score == 0) ? 1 : score * 2;
                    }
                }
                sum += score;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }

    private static boolean match(String str, ArrayList<String> win) {
        if (isNumber(str)) {
            if (win.contains(str))
                return true;
        }
        return false;
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}