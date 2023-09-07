import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestRandom {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an number:");
            if (scanner.hasNextInt()) {
                Random random = new Random();
                int num = scanner.nextInt();
                if (num <= 5)
                    break;
                List<Integer> numbers = new ArrayList<>();
                if (num < 10)
                    numbers = random.ints(6, 1, num + 1)
                            .boxed()
                            .toList();
                else {
                    numbers = random.ints(1, num + 1)
                            .distinct()
                            .limit(6)
                            .sorted()
                            .boxed()
                            .toList();
                }
                System.out.println("=======================");
                System.out.println(numbers);
                System.out.println("=======================");
            } else {
                break;
            }
        }
        scanner.close();
    }
}
