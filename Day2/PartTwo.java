import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            int count = 0;
            while (input.hasNext()) {
                String in = input.nextLine();
                String[] temp = in.split(": ");

                String text = temp[1];

                Map<String, Integer> map = new HashMap<>();

                for (String set : text.split("; ")) {
                    for (String item : set.split(", ")) {
                        String color = item.split(" ")[1];
                        int amt = Integer.parseInt(item.split(" ")[0]);
                        if (!map.containsKey(color)) {
                            map.put(color, amt);
                        } else {
                            int tmp = map.get(color);
                            map.put(color, Math.max(amt, tmp));
                        }
                    }
                }
                int sum = 1;
                for (String str : map.keySet()) {
                    sum *= map.get(str);
                }
                count += sum;
            }
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}