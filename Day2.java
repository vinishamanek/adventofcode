import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Round {
    int red;
    int green;
    int blue;

    Round(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}

class Game {
    int gameId;
    List<Round> rounds;

    Game(int gameId, List<Round> rounds) {
        this.gameId = gameId;
        this.rounds = rounds;
    }
}

public class Day2 {

    public static Game parse(String line) {
        String[] parts = line.split(":");
        int gameId = Integer.parseInt(parts[0].split(" ")[1].trim());
        String[] roundStrings = parts[1].split(";");

        List<Round> rounds = new ArrayList<>();

        for (String roundStr : roundStrings) {
            int red = 0;
            int green = 0;
            int blue = 0;

            String[] pullStrings = roundStr.split(",");
            for (String pullStr : pullStrings) {
                String[] scoreColor = pullStr.trim().split(" ");
                int score = Integer.parseInt(scoreColor[0]);
                String color = scoreColor[1];

                switch (color) {
                    case "red":
                        red = score;
                        break;
                    case "green":
                        green = score;
                        break;
                    case "blue":
                        blue = score;
                        break;
                }
            }

            rounds.add(new Round(red, green, blue));
        }

        return new Game(gameId, rounds);
    }

    public static void main(String[] args) {
        List<Game> games = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                games.add(parse(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int redMax = 12;
        int greenMax = 13;
        int blueMax = 14;

        List<Game> possibleGames = new ArrayList<>();

        for (Game game : games) {
            boolean possible = true;

            for (Round round : game.rounds) {
                if (round.red > redMax || round.green > greenMax || round.blue > blueMax) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                possibleGames.add(game);
            }
        }

        int sumOfIds = possibleGames.stream().mapToInt(game -> game.gameId).sum();
        System.out.print("part 1: " + sumOfIds);

        List<Integer> powers = new ArrayList<>();

        for (Game game : games) {
            int minRed = 0;
            int minGreen = 0;
            int minBlue = 0;

            for (Round round : game.rounds) {
                minRed = Math.max(round.red, minRed);
                minGreen = Math.max(round.green, minGreen);
                minBlue = Math.max(round.blue, minBlue);
            }

            int power = (minRed * minGreen * minBlue);
            powers.add(power);
        }

        int sumOfPowers = powers.stream().mapToInt(Integer::intValue).sum();
        System.out.print("\npart 2: " + sumOfPowers);
    }
}
