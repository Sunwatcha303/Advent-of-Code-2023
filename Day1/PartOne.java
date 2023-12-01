import java.nio.file.Paths;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        int count = 0;
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String in = input.nextLine();
                String digit = "";
                for (char c : in.toCharArray()) {
                    if (c >= 48 && c <= 57) {
                        digit += c;
                        break;
                    }
                }
                for (int i = in.length() - 1; i >= 0; i--) {
                    char c = in.charAt(i);
                    if (c >= 48 && c <= 57) {
                        digit += c;
                        break;
                    }
                }
                count += Integer.parseInt(digit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}