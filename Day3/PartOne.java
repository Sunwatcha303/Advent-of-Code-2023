import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        ArrayList<String[]> table = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String txt = input.nextLine();
                table.add(txt.split(""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (int i = 0; i < table.size(); i++) {
            String num = "";
            int idxStart = -1;
            int idxEnd = 0;
            for (int j = 0; j < table.get(i).length; j++) {
                if (isNumber(table.get(i)[j])) {
                    num += table.get(i)[j];
                    if (idxStart == -1)
                        idxStart = j - 1;
                }
                if ((j == table.get(i).length - 1 && isNumber(table.get(i)[j])) || !isNumber(table.get(i)[j])) {
                    idxEnd = j + 1;
                    int tmp = sum;
                    if (!num.equals("")) {
                        // System.out.println(num);
                        // System.out.println(idxStart + " " + idxEnd);
                        for (int row = i - 1; row < i + 2; row++) {
                            for (int col = idxStart; col < idxEnd; col++) {
                                if (row >= 0 && row < table.size() && col >= 0 && col < table.get(row).length) {
                                    String str = table.get(row)[col];
                                    // System.out.println(row + " " + col + " " + str);
                                    if (!str.equals(".") && !isNumber(str)) {
                                        int number = Integer.parseInt(num);
                                        sum += number;
                                        break;
                                    }
                                }
                                if (sum != tmp)
                                    break;
                            }
                            if (sum != tmp) {
                                break;
                            }
                        }
                    }
                    num = "";
                    idxStart = -1;
                }
            }
        }
        System.out.println(sum);
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