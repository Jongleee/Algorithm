import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class TestRandom {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            System.out.println("Choose an number:");
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                if (num <= 5)
                    break;
                List<Integer> numbers = new ArrayList<>();
                numbers = generateUniqueSortedNumbers(random, num);
                if (num < 10)
                    numbers = generateRandomNumbers(random, num);
                System.out.println("=======================");
                System.out.println(numbers);
                System.out.println("=======================");
            } else {
                break;
            }
        }
        scanner.close();
    }

    private static List<Integer> generateUniqueSortedNumbers(Random random, int max) {
        Set<Integer> numberSet = new HashSet<>();

        while (numberSet.size() < 6) {
            numberSet.add(random.nextInt(max + 1));
        }

        List<Integer> sortedNumbers = new ArrayList<>(numberSet);
        Collections.sort(sortedNumbers);

        return sortedNumbers;
    }

    private static List<Integer> generateRandomNumbers(Random random, int max) {
        List<Integer> numberList = new ArrayList<>();

        while (numberList.size() < 6) {
            numberList.add(random.nextInt(max + 1));
        }

        return numberList;
    }
}
