import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> a = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String[] num = input.nextLine().split(" ");
                ArrayList<Integer> seq = new ArrayList<>();
                for (int i = 0; i < num.length; i++) {
                    seq.add(Integer.parseInt(num[i]));
                }
                a.add(seq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (ArrayList<Integer> i : a) {
            sum += nextSeq(i);
        }
        System.out.println(sum);
    }

    private static int nextSeq(ArrayList<Integer> listSeq) {
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 0; i < listSeq.size() - 1; i++) {
            d.add(listSeq.get(i + 1) - listSeq.get(i));
        }
        if (!isAllZero(d)) {
            nextSeq(d);
        }
        listSeq.add(0, listSeq.get(0) - d.get(0));
        return listSeq.get(0);
    }

    private static boolean isAllZero(ArrayList<Integer> d) {
        for (int i = 0; i < d.size() - 1; i++) {
            if (d.get(i + 1) - d.get(i) != 0) {
                return false;
            }
        }
        return true;
    }
}