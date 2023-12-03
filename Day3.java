import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Number {
	
    int rowStart;
    int rowEnd;
    int value;

    public Number(int rowStart, int rowEnd, int value) {
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.value = value;
    }
    
}

public class Day3 {
	
    public static void main(String[] args) {
    	
        String filePath = "input.txt";
        
        List<List<Number>> numbers = new ArrayList<>();
        List<List<Integer>> symbols = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Number> lineNumbers = new ArrayList<>();
                Matcher numberMatcher = Pattern.compile("\\d+").matcher(line);
                while (numberMatcher.find()) {
                    lineNumbers.add(new Number(numberMatcher.start() - 1, numberMatcher.end(),
                            Integer.parseInt(numberMatcher.group())));
                }
                numbers.add(lineNumbers);

                List<Integer> lineSymbols = new ArrayList<>();
                Matcher symbolMatcher = Pattern.compile("[*]").matcher(line);
                while (symbolMatcher.find()) {
                    lineSymbols.add(symbolMatcher.start());
                }
                symbols.add(lineSymbols);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lineCount = numbers.size();
        int sum1 = 0;
        int sum2 = 0;

        for (List<Number> lineNumbers : numbers) {
            for (Number number : lineNumbers) {
                sum1 += number.value;
            }
        }

        for (int i = 0; i < lineCount; i++) {
            List<Number> prevLineNumbers = (i == 0) ? new ArrayList<>() : numbers.get(i - 1);
            List<Number> curLineNumbers = numbers.get(i);
            List<Number> nextLineNumbers = (i + 1 >= lineCount) ? new ArrayList<>() : numbers.get(i + 1);

            for (int symbolIndex : symbols.get(i)) {
                List<Number> prevLineAdjacentNumbers = getAdjacentNumbers(prevLineNumbers, symbolIndex);
                List<Number> curLineAdjacentNumbers = getAdjacentNumbers(curLineNumbers, symbolIndex);
                List<Number> nextLineAdjacentNumbers = getAdjacentNumbers(nextLineNumbers, symbolIndex);

                List<Number> adjacentNumbers = new ArrayList<>();
                adjacentNumbers.addAll(prevLineAdjacentNumbers);
                adjacentNumbers.addAll(curLineAdjacentNumbers);
                adjacentNumbers.addAll(nextLineAdjacentNumbers);

                if (adjacentNumbers.size() == 2) {
                    sum2 += adjacentNumbers.get(0).value * adjacentNumbers.get(1).value;
                }
            }
        }

        System.out.println("part 1: " + sum1);
        System.out.println("part 2: " + sum2);
    }

    private static List<Number> getAdjacentNumbers(List<Number> numbers, int symbolIndex) {
        return numbers.stream()
                .filter(number -> symbolIndex >= number.rowStart && symbolIndex <= number.rowEnd).toList();
    }
}
