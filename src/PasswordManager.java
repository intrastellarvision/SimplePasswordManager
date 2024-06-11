import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PasswordManager {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the password manager. Please select an action:");
        System.out.println("1. LogIn");
        System.out.println("2. Registration");

        boolean running = true;
        ArrayList<User> users = new ArrayList<>();
        UserService userService = new UserService(users);

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        userService.logIn(scanner);
                        break;
                    case 2:
                        userService.registration(scanner);
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose 1 or 2.");
                }

                System.out.println("Do you want to continue? (yes/no)");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    running = false;
                }
            } while (running);
        }
    }
}
