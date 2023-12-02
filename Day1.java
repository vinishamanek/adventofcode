import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;


public class Day1 {

    public static void main(String[] args) {
        String file = "inputfile.txt";

        try {
            int sum = processFile(file);
            System.out.println("SUM: " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int processFile(String file) throws IOException {
        int sum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = replaceLetteredNumbers(line);
                String intValue = line.replaceAll("[^0-9]", "");
                char[] charArray = intValue.toCharArray();
                String firstAndLast = "" + charArray[0] + charArray[charArray.length - 1];
                sum += Integer.parseInt(firstAndLast);
            }
        }

        return sum;
    }

    private static String replaceLetteredNumbers(String line) {
        Map<String, String> replacementMap = new HashMap<>();
        replacementMap.put("oneight", "18");
        replacementMap.put("twone", "21");
        replacementMap.put("eightwo", "82");
        replacementMap.put("one", "1");
        replacementMap.put("two", "2");
        replacementMap.put("three", "3");
        replacementMap.put("four", "4");
        replacementMap.put("five", "5");
        replacementMap.put("six", "6");
        replacementMap.put("seven", "7");
        replacementMap.put("eight", "8");
        replacementMap.put("nine", "9");

        List<Map.Entry<String, String>> entries = new ArrayList<>(replacementMap.entrySet());
        entries.sort(Comparator.comparingInt(entry -> -entry.getKey().length()));

        for (Map.Entry<String, String> entry : entries) {
            line = line.replace(entry.getKey(), entry.getValue());
        }

        return line;
    }
}
