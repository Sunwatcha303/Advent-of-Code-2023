import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        int sum = 0;
        Map<Integer, Integer> times = new HashMap<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String[] line = input.nextLine().split(": ");
                String card = line[1];
                int numCard = Integer.parseInt(line[0].replace(" ", "").split("d")[1]);
                if (!times.containsKey(numCard)) {
                    times.put(numCard, 0);
                }
                int tmpNum = times.get(numCard);
                times.put(numCard, tmpNum + 1);
                String[] tmp = card.split(" \\| ");
                ArrayList<String> win = new ArrayList<>(Arrays.asList(tmp[0].split(" ")));
                ArrayList<String> onHand = new ArrayList<>(Arrays.asList(tmp[1].split(" ")));
                sum++;
                int cardCnt = numCard;
                int cntRound = 0;
                for (int i = 0; i < onHand.size(); i++) {
                    if (match(onHand.get(i), win)) {
                        if (!times.containsKey(cardCnt + 1)) {
                            times.put(cardCnt + 1, times.get(numCard));
                        } else {
                            int cnt = times.get(cardCnt + 1);
                            times.put(cardCnt + 1, cnt + times.get(numCard));
                        }
                        cntRound++;
                        cardCnt++;
                    }
                }
                sum += cntRound * times.get(numCard);
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