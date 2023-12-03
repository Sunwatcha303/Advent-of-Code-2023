import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        ArrayList<String> table = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String txt = input.nextLine();
                table.add(txt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).length(); j++) {
                if (table.get(i).charAt(j) == '*') {
                    String num1 = "";
                    String num2 = "";
                    for (int row = i - 1; row < i + 2; row++) {
                        if (row >= 0 && row < table.size()) {
                            boolean[] visited = new boolean[table.get(row).length()];
                            for (int col = j - 1; col < j + 2; col++) {
                                if (col >= 0 && col < table.get(i).length()) {
                                    if (!visited[col - 1] && !visited[col + 1] &&
                                            num1.equals("") && isNumber(table.get(row).charAt(col))) {
                                        num1 = getAround(row, col, table, visited);
                                    } else if (!visited[col - 1] && !visited[col + 1] &&
                                            num2.equals("") && isNumber(table.get(row).charAt(col))) {
                                        num2 = getAround(row, col, table, visited);
                                    }
                                }
                            }
                        }
                    }
                    if (!num1.equals("") && !num2.equals(""))
                        sum += Integer.parseInt(num1) * Integer.parseInt(num2);
                }
            }
        }
        System.out.println(sum);
    }

    private static String getAround(int row, int j, ArrayList<String> table, boolean[] visited) {
        int idxStart = j;
        int idxEnd = j;
        for (int col = idxEnd; col >= 0; col--) {
            if (!isNumber(table.get(row).charAt(col)) || (col == 0 && isNumber(table.get(row).charAt(col)))) {
                idxStart = (col == 0) ? (isNumber(table.get(row).charAt(col))) ? 0 : col + 1 : col + 1;
                break;
            }
            visited[col] = true;
        }
        for (int col = idxEnd; col < table.get(0).length(); col++) {
            if (!isNumber(table.get(row).charAt(col))
                    || (col == table.get(0).length() - 1 && isNumber(table.get(row).charAt(col)))) {
                idxEnd = (col == table.get(0).length() - 1) ? (isNumber(table.get(row).charAt(col))) ? col + 1 : col
                        : col;
                break;
            }
            visited[col] = true;
        }
        return table.get(row).substring(idxStart, idxEnd);
    }

    private static boolean isNumber(char c) {
        try {
            Integer.parseInt("" + c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}