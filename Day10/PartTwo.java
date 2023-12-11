import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class PartTwo {
    public static void main(String[] args) {
        ArrayList<String[]> map = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            while (input.hasNext()) {
                String in = input.nextLine();
                String[] s = in.split("");
                map.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int r = -1, c = -1;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                if (map.get(i)[j].equals("S")) {
                    r = i;
                    c = j;
                    break;
                }
                if (r != -1 && c != -1)
                    break;
            }
        }
        Tiles loopPoint = getStep(map, r, c);
        int cntInside = shoelaceFormula_pickTheorem(loopPoint);
        System.out.println(cntInside);
    }

    private static int shoelaceFormula_pickTheorem(Tiles loopPoint) {
        int a = 0;
        ArrayList<Tiles> list = new ArrayList<>();
        Tiles cur = loopPoint;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        int n = list.size();

        // shoelace algorithm
        for (int i = 0; i < n; i++) {
            a += (list.get(i).r * list.get((i + 1) % (n)).c) - (list.get((i + 1) % (n)).r * list.get(i).c);
        }
        a = Math.abs(a) / 2;

        // pick's theorem
        int i = a + 1 - (list.size() / 2);

        return i;
    }

    private static Tiles getStep(ArrayList<String[]> map, int r, int c) {
        Map<String, ArrayList<String>> setDir = new HashMap<>();
        setDir.put("N", new ArrayList<>(Arrays.asList("|", "L", "J")));
        setDir.put("S", new ArrayList<>(Arrays.asList("|", "7", "F")));
        setDir.put("E", new ArrayList<>(Arrays.asList("-", "F", "L")));
        setDir.put("W", new ArrayList<>(Arrays.asList("-", "7", "J")));

        Map<String, Map<String, String>> dir = new HashMap<>();

        Map<String, String> tmp = new HashMap<>();
        tmp.put("N", "S");
        tmp.put("S", "N");
        dir.put("|", tmp);
        tmp = new HashMap<>();
        tmp.put("W", "E");
        tmp.put("E", "W");
        dir.put("-", tmp);
        tmp = new HashMap<>();
        tmp.put("N", "E");
        tmp.put("E", "N");
        dir.put("L", tmp);
        tmp = new HashMap<>();
        tmp.put("N", "W");
        tmp.put("W", "N");
        dir.put("J", tmp);
        tmp = new HashMap<>();
        tmp.put("S", "W");
        tmp.put("W", "S");
        dir.put("7", tmp);
        tmp = new HashMap<>();
        tmp.put("S", "E");
        tmp.put("E", "S");
        dir.put("F", tmp);

        boolean[][] visited = new boolean[map.size()][map.get(0).length];
        Stack<Tiles> path1 = new Stack<>();
        Tiles head = new Tiles(map.get(r)[c], r, c);
        if (path1.isEmpty() && r - 1 >= 0 && !visited[r - 1][c]
                && canGo(setDir, dir, map, r - 1, c, map.get(r)[c], "S")) { // N
            Tiles tile = new Tiles(map.get(r - 1)[c], r - 1, c);
            head.next = tile;
            path1.push(tile);
            visited[r - 1][c] = true;
        }
        if (path1.isEmpty() && r + 1 < map.size() && !visited[r + 1][c]
                && canGo(setDir, dir, map, r + 1, c, map.get(r)[c], "N")) { // S
            Tiles tile = new Tiles(map.get(r + 1)[c], r + 1, c);
            head.next = tile;
            path1.push(tile);
            visited[r + 1][c] = true;
        }
        if (path1.isEmpty() && c - 1 >= 0 && !visited[r][c - 1]
                && canGo(setDir, dir, map, r, c - 1, map.get(r)[c], "E")) { // W
            Tiles tile = new Tiles(map.get(r)[c - 1], r, c - 1);
            head.next = tile;
            path1.push(tile);
            visited[r][c - 1] = true;
        }
        if (path1.isEmpty() && c + 1 < map.get(0).length && !visited[r][c + 1]
                && canGo(setDir, dir, map, r, c + 1, map.get(r)[c], "W")) { // E
            Tiles tile = new Tiles(map.get(r)[c + 1], r, c + 1);
            head.next = tile;
            path1.push(tile);
            visited[r][c + 1] = true;
        }
        while (!path1.isEmpty()) {
            Tiles p = path1.pop();
            visited[p.r][p.c] = true;
            nextTile(setDir, dir, path1, map, p, visited);
        }
        return head;

    }

    private static void nextTile(Map<String, ArrayList<String>> setDir, Map<String, Map<String, String>> dir,
            Stack<Tiles> path1, ArrayList<String[]> map, Tiles p, boolean[][] visited) {
        if (p.r - 1 >= 0 && !visited[p.r - 1][p.c] && canGo(setDir, dir, map, p.r - 1, p.c, map.get(p.r)[p.c], "S")) { // N
            Tiles tile = new Tiles(map.get(p.r - 1)[p.c], p.r - 1, p.c);
            p.next = tile;
            path1.push(tile);
            visited[p.r - 1][p.c] = true;
        }
        if (p.r + 1 < map.size() && !visited[p.r + 1][p.c]
                && canGo(setDir, dir, map, p.r + 1, p.c, map.get(p.r)[p.c], "N")) { // S
            Tiles tile = new Tiles(map.get(p.r + 1)[p.c], p.r + 1, p.c);
            p.next = tile;
            path1.push(tile);
            visited[p.r + 1][p.c] = true;
        }
        if (p.c - 1 >= 0 && !visited[p.r][p.c - 1] && canGo(setDir, dir, map, p.r, p.c - 1, map.get(p.r)[p.c], "E")) { // W
            Tiles tile = new Tiles(map.get(p.r)[p.c - 1], p.r, p.c - 1);
            p.next = tile;
            path1.push(tile);
            visited[p.r][p.c - 1] = true;
        }
        if (p.c + 1 < map.get(0).length && !visited[p.r][p.c + 1]
                && canGo(setDir, dir, map, p.r, p.c + 1, map.get(p.r)[p.c], "W")) { // E
            Tiles tile = new Tiles(map.get(p.r)[p.c + 1], p.r, p.c + 1);
            p.next = tile;
            path1.push(tile);
            visited[p.r][p.c + 1] = true;
        }
    }

    private static boolean canGo(Map<String, ArrayList<String>> setDir, Map<String, Map<String, String>> dirMap,
            ArrayList<String[]> map, int r, int c, String cur, String next) {
        if (map.get(r)[c].equals("."))
            return false;
        if (cur.equals("S")) {
            if (dirMap.get(map.get(r)[c]).containsKey(next)) {
                return true;
            }
            return false;
        }
        Map<String, String> curDir = dirMap.get(cur);
        ArrayList<String> dirSet = setDir.get(next);

        if (next.equals("E")) {
            if (dirSet.contains(map.get(r)[c]) && curDir.containsKey("W")) {
                return true;
            }
            return false;
        }
        if (next.equals("W")) {
            if (dirSet.contains(map.get(r)[c]) && curDir.containsKey("E")) {
                return true;
            }
            return false;
        }
        if (next.equals("N")) {
            if (dirSet.contains(map.get(r)[c]) && curDir.containsKey("S")) {
                return true;
            }
            return false;
        }
        if (next.equals("S")) {
            if (dirSet.contains(map.get(r)[c]) && curDir.containsKey("N")) {
                return true;
            }
            return false;
        }

        return false;
    }
}

class Tiles {
    String str;
    int r;
    int c;
    Tiles next;

    Tiles(String str, int r, int c) {
        this.str = str;
        this.r = r;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Tiles [str =" + str + ", r=" + r + ", c=" + c + " ] --> " + next;
    }

}