import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PartTwo {
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
                    rowEmpty.add(r);
                }
                r++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Long[]> source = getSource(map, rowEmpty);
        long sum = 0;
        for (int i = 0; i < source.size(); i++) {
            sum += shortestPath(source, i);
        }
        System.out.println(sum);
    }

    private static ArrayList<Long[]> getSource(ArrayList<String[]> map, ArrayList<Integer> rowEmpty) {
        ArrayList<Integer> colEmpty = new ArrayList<>();
        for (int i = 0; i < map.get(0).length; i++) {
            int colCnt = 0;
            for (int j = 0; j < map.size(); j++) {
                if (map.get(j)[i].equals(".")) {
                    colCnt++;
                }
            }
            if (colCnt == map.size()) {
                colEmpty.add(i);
            }
        }
        long curRow = 0;
        long curCol = 0;

        ArrayList<Long[]> src = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).length; j++) {
                if (!map.get(i)[j].equals(".")) {
                    curRow = i + moreThan(rowEmpty, i) * (1000000 - 1);
                    curCol = j + moreThan(colEmpty, j) * (1000000 - 1);
                    src.add(new Long[] { curRow, curCol });
                }
            }
        }
        return src;
    }

    private static long moreThan(ArrayList<Integer> empty, int cur) {
        for (int i = 0; i < empty.size(); i++) {
            if (cur < empty.get(i)) {
                return i;
            }
        }
        return empty.size();
    }

    private static long shortestPath(ArrayList<Long[]> source, int i) {
        Long[] src = source.get(i);

        long sum = 0;
        for (int j = i + 1; j < source.size(); j++) {
            Long[] dest = source.get(j);
            sum += Math.abs(dest[0] - src[0]) + Math.abs(dest[1] - src[1]);
        }
        return sum;
    }
}