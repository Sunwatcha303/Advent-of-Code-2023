import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        String dir = "";
        int n = 0;
        Map<String, String[]> map = new HashMap<>();
        ArrayList<String> source = new ArrayList<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            dir = input.nextLine();
            n = dir.length();
            input.nextLine();
            while (input.hasNext()) {
                String[] str = input.nextLine().split(" = ");
                String key = str[0];
                if (key.endsWith("A")) {
                    source.add(key);
                }
                String[] value = str[1].split(", ");
                String v1 = value[0].substring(1);
                String v2 = value[1].substring(0, value[1].length() - 1);
                map.put(key, new String[] { v1, v2 });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] posZ = new int[source.size()];
        for (int i = 0; i < source.size(); i++) {
            posZ[i] = goToZ(source.get(i), map, dir, n);
        }
        long sum = calculateLCM(posZ);
        System.out.println(sum);
    }

    private static int goToZ(String str, Map<String, String[]> map, String dir, int n) {
        int iter = 0;
        while (!str.endsWith("Z")) {
            int idx = iter++ % n;
            if (dir.charAt(idx) == 'L') {
                str = map.get(str)[0];
            } else {
                str = map.get(str)[1];
            }
        }
        return iter;
    }

    public static long calculateLCM(int[] numbers) {
        Map<Integer, Integer> primeFactors = new HashMap<>();

        // Iterate through each number and find its prime factors
        for (int num : numbers) {
            Map<Integer, Integer> factors = primeFactorization(num);

            // Update primeFactors with the maximum occurrences of each prime factor
            factors.forEach(
                    (factor, count) -> primeFactors.put(factor, Math.max(primeFactors.getOrDefault(factor, 0), count)));
        }

        // Calculate LCM using the prime factors
        long lcm = 1;
        for (Map.Entry<Integer, Integer> entry : primeFactors.entrySet()) {
            lcm *= Math.pow(entry.getKey(), entry.getValue());
        }

        return lcm;
    }

    public static Map<Integer, Integer> primeFactorization(int num) {
        Map<Integer, Integer> factors = new HashMap<>();
        int divisor = 2;

        while (num > 1) {
            while (num % divisor == 0) {
                factors.put(divisor, factors.getOrDefault(divisor, 0) + 1);
                num /= divisor;
            }
            divisor++;
        }

        return factors;
    }
}