import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Java Launcher");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        while (!userInput.equals("quit")) {
            if (userInput.equals("fibo")) {
                System.out.println("Enter a number:");
                userInput = scanner.nextLine();
                int number = Integer.parseInt(userInput);
                int result = fibonacci(number);
                System.out.println("Fibonacci: " + result);
            } else if (userInput.equals("freq")) {
                System.out.println("Enter the path to a file:");
                userInput = scanner.nextLine();
                try {
                    String text = Files.readString(Path.of(userInput));
                    text = text.toLowerCase().replaceAll("[^a-zA-Z]", " ");
                    String[] words = text.split("\\s+");
                    List<String> wordList = Arrays.stream(words)
                            .filter(word -> !word.isEmpty())
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

                    System.out.println("Most frequent words: " + String.join(" ", wordList.subList(0, Math.min(3, wordList.size()))));
                } catch (IOException e) {
                    System.out.println("Error reading the file.");
                }
            } else {
                System.out.println("Unknown command");
            }

            System.out.println("Enter a command (fibo, freq, or quit):");
            userInput = scanner.nextLine();
        }

        System.out.println("See you soon !");
    }

    static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        int first = 0, second = 1, result = 0;

        for (int i = 2; i <= n; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }
}