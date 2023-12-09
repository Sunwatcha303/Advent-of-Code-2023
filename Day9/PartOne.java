import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        ArrayList<Integer[]> a = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String[] num = input.nextLine().split(" ");
                Integer[] seq = new Integer[num.length];
                for (int i = 0; i < num.length; i++) {
                    seq[i] = Integer.parseInt(num[i]);
                }
                a.add(seq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (Integer[] i : a) {
            sum += nextSeq(i);
        }
        System.out.println(sum);
    }

    private static int nextSeq(Integer[] seq) {
        Integer[] d = new Integer[seq.length - 1];
        for (int i = 0; i < d.length; i++) {
            d[i] = seq[i + 1] - seq[i];
        }
        if (!isAllZero(d)) {
            nextSeq(d);
        }
        seq[seq.length - 1] = seq[seq.length - 1] + d[d.length - 1];
        return seq[seq.length - 1];
    }

    private static boolean isAllZero(Integer[] d) {
        for (int i = 0; i < d.length - 1; i++) {
            if (d[i + 1] - d[i] != 0) {
                return false;
            }
        }
        return true;
    }
}