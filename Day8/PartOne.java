import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        String dir = "";
        int n = 0;
        Map<String, String[]> map = new HashMap<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            dir = input.nextLine();
            n = dir.length();
            input.nextLine();
            while (input.hasNext()) {
                String[] str = input.nextLine().split(" = ");
                String key = str[0];
                String[] value = str[1].split(", ");
                String v1 = value[0].substring(1);
                String v2 = value[1].substring(0, value[1].length() - 1);
                map.put(key, new String[] { v1, v2 });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String curNode = "AAA";
        int iter = 0;
        while (!curNode.equals("ZZZ")) {
            int idx = iter++ % n;
            if (dir.charAt(idx) == 'L') {
                curNode = map.get(curNode)[0];
            } else {
                curNode = map.get(curNode)[1];
            }
        }
        System.out.println(iter);
    }
}