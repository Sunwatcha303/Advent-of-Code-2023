import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        Map<Long, Long> seed = new HashMap<>();
        Map<Long, Long> seed_to_soil = new HashMap<>();
        Map<Long, Long> soil_to_fertilizer = new HashMap<>();
        Map<Long, Long> fertilizer_to_water = new HashMap<>();
        Map<Long, Long> water_to_light = new HashMap<>();
        Map<Long, Long> light_to_temperature = new HashMap<>();
        Map<Long, Long> temperature_to_humidity = new HashMap<>();
        Map<Long, Long> humidity_to_location = new HashMap<>();
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            String seeds = input.nextLine().split(": ")[1];
            String[] seedsNew = seeds.split(" ");
            for (int i = 0; i < seedsNew.length - 1; i += 2) {
                long start = Long.parseLong(seedsNew[i]);
                long range = Long.parseLong(seedsNew[i + 1]);
                seed.put(start, start + range - 1);
            }
            String in = input.nextLine();
            String state = "";
            while (input.hasNext()) {
                in = input.nextLine();
                if (!in.isBlank()) {
                    String[] num = in.split(" ");
                    state = (!isNumber(num[0])) ? num[0].split("-")[2] : state;
                    ArrayList<String> txt = new ArrayList<>();
                    ArrayList<Long> afterMap = new ArrayList<>();
                    while (input.hasNext()) {
                        in = input.nextLine();
                        if (in.isBlank())
                            break;
                        txt.add(in);
                    }
                    process(state, txt, seed, seed_to_soil, soil_to_fertilizer, fertilizer_to_water, water_to_light,
                            light_to_temperature, temperature_to_humidity, humidity_to_location, afterMap);
                }
            }
            long min = Long.MAX_VALUE;
            for (long i : humidity_to_location.keySet()) {
                min = Math.min(min, i);
            }
            System.out.println(min);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(String state, ArrayList<String> txt, Map<Long, Long> seed, Map<Long, Long> seed_to_soil,
            Map<Long, Long> soil_to_fertilizer, Map<Long, Long> fertilizer_to_water, Map<Long, Long> water_to_light,
            Map<Long, Long> light_to_temperature, Map<Long, Long> temperature_to_humidity,
            Map<Long, Long> humidity_to_location, ArrayList<Long> afterMap) {
        for (int i = 0; i < txt.size(); i++) {
            String[] input = txt.get(i).split(" ");
            long dest_start = Long.parseLong(input[0]);
            long source_start = Long.parseLong(input[1]);
            long range = Long.parseLong(input[2]);
            if (state.equals("soil")) {
                mapMap(dest_start, source_start, range, seed_to_soil, seed, i == (txt.size() - 1), afterMap);
            } else if (state.equals("fertilizer")) {
                mapMap(dest_start, source_start, range, soil_to_fertilizer, seed_to_soil, i == (txt.size() - 1),
                        afterMap);
            } else if (state.equals("water")) {
                mapMap(dest_start, source_start, range, fertilizer_to_water, soil_to_fertilizer, i == (txt.size() - 1),
                        afterMap);
            } else if (state.equals("light")) {
                mapMap(dest_start, source_start, range, water_to_light, fertilizer_to_water, i == (txt.size() - 1),
                        afterMap);
            } else if (state.equals("temperature")) {
                mapMap(dest_start, source_start, range, light_to_temperature, water_to_light, i == (txt.size() - 1),
                        afterMap);
            } else if (state.equals("humidity")) {
                mapMap(dest_start, source_start, range, temperature_to_humidity,
                        light_to_temperature, i == (txt.size() - 1), afterMap);
            } else if (state.equals("location")) {
                mapMap(dest_start, source_start, range, humidity_to_location,
                        temperature_to_humidity, i == (txt.size() - 1), afterMap);
            }
        }
    }

    private static void mapMap(long dest_start, long source_start, long range, Map<Long, Long> map,
            Map<Long, Long> mapIns, boolean b, ArrayList<Long> afterMap) {
        Map<Long, Long> split = new HashMap<>();
        for (long str : mapIns.keySet()) {
            long dest = mapIns.get(str);

            if (str >= source_start && dest < source_start + range && !afterMap.contains(str)) {
                map.put(dest_start + (str - source_start), dest_start + (dest - source_start));
                afterMap.add(str);
            } else if (str >= source_start && str < source_start + range
                    && !afterMap.contains(str)) {
                map.put(dest_start + (str - source_start), dest_start + range - 1);
                split.put(source_start + range, dest);
                afterMap.add(str);
            } else if (source_start >= str && source_start <= dest && !afterMap.contains(source_start)) {
                map.put(dest_start, dest_start + (dest - source_start));
                split.put(str, source_start - 1);
                afterMap.add(source_start);
            } else if (b && !map.containsValue(dest) && !afterMap.contains(str)) {
                map.put(str, dest);
                afterMap.add(str);
            }
        }
        if (!split.isEmpty()) {
            for (long l : split.keySet()) {
                mapIns.put(l, split.get(l));
            }
        }
    }

    private static boolean isNumber(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}