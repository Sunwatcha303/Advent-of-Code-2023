import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        Map<String, Integer> hands = new HashMap<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String[] in = input.nextLine().split(" ");
                hands.put(in[0], Integer.parseInt(in[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> ranks = getRanks(hands);
        long sum = 0;
        int rank = 1;
        for (String str : ranks) {
            sum += hands.get(str) * rank;
            rank++;
        }
        System.out.println(sum);
    }

    private static ArrayList<String> getRanks(Map<String, Integer> hands) {
        ArrayList<String> ranks = new ArrayList<>();
        for (String hand : hands.keySet()) {
            addToList(hand, ranks);
        }
        return ranks;
    }

    private static void addToList(String hand, ArrayList<String> ranks) {
        int curIdx = 0;
        boolean b = false;
        for (int i = 0; i < ranks.size() && curIdx < ranks.size(); i++) {
            String curHand = ranks.get(i);
            curIdx = i;
            if (getRank(hand) > getRank(curHand)) {
                break;
            } else if (getRank(hand) == getRank(curHand)) {
                for (int j = 0; j < hand.length(); j++) {
                    if (rank(hand.charAt(j)) < rank(curHand.charAt(j))) {
                        b = true;
                        break;
                    } else if (rank(hand.charAt(j)) > rank(curHand.charAt(j))) {
                        curIdx += 1;
                        break;
                    }
                }
            } else {
                curIdx += 1;
            }
            if (b)
                break;
        }
        ranks.add(curIdx, hand);
    }

    private static int rank(char c) {
        Map<Character, Integer> ranks = new HashMap<>();
        ranks.put('A', 14);
        ranks.put('K', 13);
        ranks.put('Q', 12);
        ranks.put('J', 11);
        ranks.put('T', 10);
        if (ranks.containsKey(c)) {
            return ranks.get(c);
        }
        return Integer.parseInt("" + c);
    }

    private static int getRank(String hand) {
        Map<Character, Integer> count = getCount(hand);
        if (count.size() == 1) {
            return 1;
        } else if (count.size() == 2) {
            for (char c : count.keySet()) {
                int cnt = count.get(c);
                if (cnt == 4 || cnt == 1) {
                    return 2;
                } else {
                    return 3;
                }
            }
        } else if (count.size() == 3) {
            for (char c : count.keySet()) {
                int cnt = count.get(c);
                if (cnt == 3) {
                    return 4;
                }
            }
            return 5;
        } else if (count.size() == 4) {
            return 6;
        }
        return 7;
    }

    private static Map<Character, Integer> getCount(String hand) {
        Map<Character, Integer> count = new HashMap<>();
        String curStr = hand;
        for (char str : curStr.toCharArray()) {
            if (!count.containsKey(str)) {
                count.put(str, 1);
            } else {
                int tmp = count.get(str);
                count.put(str, tmp + 1);
            }
        }
        return count;
    }

}