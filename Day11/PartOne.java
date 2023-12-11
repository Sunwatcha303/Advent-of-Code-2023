import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        ArrayList<String[]> map = new ArrayList<>();
        ArrayList<Integer> rowEmpty = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            int r = 0;
            while (input.hasNext()) {
                String[] in = input.nextLine().split("");
                map.add(in);
                int rowCnt = 0;
                for (int i = 0; i < in.length; i++) {
                    if (in[i].equals(".")) {
                        rowCnt++;
                    }
                }
                if (rowCnt == in.length) {
                    rowEmpty.add(r + rowEmpty.size());
                }
                r++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String[]> newMap = expansionMap(map, rowEmpty);
        ArrayList<Integer[]> source = getSource(newMap);

        int sum = 0;
        for (int i = 0; i < source.size(); i++) {
            sum += shortestPath(source, i);
        }
        System.out.println(sum);
    }

    private static ArrayList<Integer[]> getSource(ArrayList<String[]> map) {
        ArrayList<Integer[]> src = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).length; j++) {
                if (!map.get(i)[j].equals(".")) {
                    src.add(new Integer[] { i, j });
                }
            }
        }
        return src;
    }

    private static ArrayList<String[]> expansionMap(ArrayList<String[]> map, ArrayList<Integer> rowEmpty) {
        ArrayList<Integer> colEmpty = new ArrayList<>();
        for (int i = 0; i < map.get(0).length; i++) {
            int colCnt = 0;
            for (int j = 0; j < map.size(); j++) {
                if (map.get(j)[i].equals(".")) {
                    colCnt++;
                }
            }
            if (colCnt == map.size()) {
                colEmpty.add(i + colEmpty.size());
            }
        }
        ArrayList<String[]> newMap = new ArrayList<>();
        int row = 0;
        for (int i = 0; i < map.size() + rowEmpty.size() && row < map.size(); i++) {
            String[] str = new String[map.get(0).length + colEmpty.size()];
            int col = 0;
            for (int j = 0; j < str.length && col < map.get(0).length; j++) {
                if (rowEmpty.contains(i) || colEmpty.contains(j)) {
                    str[j] = ".";
                } else {
                    str[j] = map.get(row)[col];
                    col++;
                }
            }
            col = 0;
            if (!rowEmpty.contains(i)) {
                row++;
            }
            newMap.add(str);
        }

        return newMap;
    }

    private static int shortestPath(ArrayList<Integer[]> source, int i) {
        Integer[] src = source.get(i);

        int sum = 0;
        for (int j = i + 1; j < source.size(); j++) {
            Integer[] dest = source.get(j);
            sum += Math.abs(dest[0] - src[0]) + Math.abs(dest[1] - src[1]);
        }
        return sum;
    }
}