import java.nio.file.Paths;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            int count = 0;
            while (input.hasNext()) {
                String in = input.nextLine();
                String[] temp = in.split(": ");

                int game = Integer.parseInt(temp[0].split(" ")[1]);
                String text = temp[1];

                boolean flag = true;
                for (String set : text.split("; ")) {
                    for (String item : set.split(", ")) {
                        String color = item.split(" ")[1];
                        int amt = Integer.parseInt(item.split(" ")[0]);
                        if (color.equals("red") && amt > 12) {
                            flag = false;
                            break;
                        }
                        if (color.equals("green") && amt > 13) {
                            flag = false;
                            break;
                        }
                        if (color.equals("blue") && amt > 14) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag)
                        break;
                }
                if (flag) {
                    count += game;
                }
            }
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}