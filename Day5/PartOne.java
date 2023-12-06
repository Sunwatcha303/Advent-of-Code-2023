import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        Map<Long, Long> seed_to_soil = new HashMap<>();
        Map<Long, Long> soil_to_fertilizer = new HashMap<>();
        Map<Long, Long> fertilizer_to_water = new HashMap<>();
        Map<Long, Long> water_to_light = new HashMap<>();
        Map<Long, Long> light_to_temperature = new HashMap<>();
        Map<Long, Long> temperature_to_humidity = new HashMap<>();
        Map<Long, Long> humidity_to_location = new HashMap<>();
        String seeds = "";
        try (Scanner input = new Scanner(Paths.get("input.txt"))) {
            seeds = input.nextLine().split(": ")[1];
            for (String str : seeds.split(" ")) {
                seed_to_soil.put(Long.parseLong(str), Long.parseLong(str));
            }
            String in = input.nextLine();
            String state = "";
            while (input.hasNext()) {
                in = input.nextLine();
                if (!in.isBlank()) {
                    String[] num = in.split(" ");
                    state = (!isNumber(num[0])) ? num[0].split("-")[2] : state;
                    if (isNumber(num[0])) {
                        long dest_start = Long.parseLong(num[0]);
                        long source_start = Long.parseLong(num[1]);
                        long range = Long.parseLong(num[2]);
                        if (state.equals("soil")) {
                            mapMap(dest_start, source_start, range, seed_to_soil, new HashMap<>(seed_to_soil));
                        } else if (state.equals("fertilizer")) {
                            mapMap(dest_start, source_start, range, soil_to_fertilizer, seed_to_soil);
                        } else if (state.equals("water")) {
                            mapMap(dest_start, source_start, range, fertilizer_to_water, soil_to_fertilizer);
                        } else if (state.equals("light")) {
                            mapMap(dest_start, source_start, range, water_to_light, fertilizer_to_water);
                        } else if (state.equals("temperature")) {
                            mapMap(dest_start, source_start, range, light_to_temperature, water_to_light);
                        } else if (state.equals("humidity")) {
                            mapMap(dest_start, source_start, range, temperature_to_humidity,
                                    light_to_temperature);
                        } else if (state.equals("location")) {
                            mapMap(dest_start, source_start, range, humidity_to_location,
                                    temperature_to_humidity);
                        }
                    }
                }
            }
            long min = Long.MAX_VALUE;
            for (long i : humidity_to_location.keySet()) {
                min = Math.min(min, humidity_to_location.get(i));
            }
            System.out.println(min);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mapMap(long dest_start, long source_start, long range, Map<Long, Long> map,
            Map<Long, Long> mapIns) {
        for (long l : mapIns.keySet()) {
            long tmp = mapIns.get(l);
            if (tmp >= source_start && tmp < source_start + range) {
                map.put(tmp, dest_start + (tmp - source_start));
            } else if (!map.containsKey(tmp)) {
                map.put(tmp, tmp);
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