import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        int count = 0;
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String in = input.nextLine();
                String first = firstInputToDigit(in);
                String last = lastInputToDigit(in);
                System.out.println(first + last);
                count += Integer.parseInt(first + last);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    private static String lastInputToDigit(String in) {
        Map<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        map.put("six", "6");
        map.put("seven", "7");
        map.put("eight", "8");
        map.put("nine", "9");
        String txt = "";

        for (int i = in.length(); i > 0; i--) {
            if (in.charAt(i - 1) >= 48 && in.charAt(i - 1) <= 57) {
                return "" + in.charAt(i - 1);
            }
            String digits = "";
            for (int j = i - 1; j >= 0; j--) {
                digits = in.substring(j, i);
                // System.out.println(digits);
                if (map.containsKey(digits)) {
                    txt += map.get(digits);
                    return txt;
                }
            }
        }
        return txt;
    }

    private static String firstInputToDigit(String in) {
        Map<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        map.put("six", "6");
        map.put("seven", "7");
        map.put("eight", "8");
        map.put("nine", "9");
        String txt = "";

        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) >= 48 && in.charAt(i) <= 57) {
                return "" + in.charAt(i);
            }
            String digits = "";
            for (int j = i + 1; j < in.length() + 1; j++) {
                digits = in.substring(i, j);
                // System.out.println(digits + " " + in.length());
                if (map.containsKey(digits)) {
                    txt += map.get(digits);
                    return txt;
                }
            }
        }
        return txt;
    }

}