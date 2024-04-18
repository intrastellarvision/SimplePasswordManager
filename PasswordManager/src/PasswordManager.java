import java.io.FileWriter;
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

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        logIn(scanner, users);
                        break;
                    case 2:
                        registration(scanner, users);
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

    public static void logIn(Scanner scanner, ArrayList<User> users) {
        System.out.println("You chose to LogIn.");

        System.out.print("Enter your login: ");
        String loginInput = scanner.nextLine();

        System.out.print("Enter your password: ");
        String passwordInput = scanner.nextLine();

        User foundUser = findUser(loginInput, users);
        if (foundUser == null || !foundUser.getPassword().equals(passwordInput)) {
            System.out.println("Login failed. Please try again.");
            return;
        }

        System.out.println("Login successful. Welcome back, " + foundUser.getLogin() + "!");
        showMenu(foundUser, scanner);
    }

    public static User findUser(String login, ArrayList<User> users) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public static void showMenu(User user, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Menu:");
            System.out.println("1. Write down a password.");
            System.out.println("2. Delete a password.");
            System.out.println("3. Show passwords.");
            System.out.println("4. Encrypt passwords.");
            System.out.println("5. Write passwords to a file.");
            System.out.println("6. Exit.");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    writePassword(user, scanner);
                    break;
                case 2:
                    deletePassword(user, scanner);
                    break;
                case 3:
                    user.showPasswords();
                    break;
                case 4:
                    user.encryptPasswords();
                    break;
                case 5:
                    user.writePasswordsToFile();
                    break;
                case 6:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1-6.");
            }
        }
    }

    public static void registration(Scanner scanner, ArrayList<User> users) {
        System.out.println("You chose to Registration.");
        System.out.print("Enter your desired login: ");
        String loginInput = scanner.nextLine();

        if (findUser(loginInput, users) != null) {
            System.out.println("Login already exists. Please choose another login.");
            return;
        }

        System.out.print("Enter your password: ");
        String passwordInput = scanner.nextLine();

        User newUser = new User(loginInput, passwordInput);
        users.add(newUser);

        System.out.println("Registration successful. Welcome, " + newUser.getLogin() + "!");
        showMenu(newUser, scanner);
    }

    public static void writePassword(User user, Scanner scanner) {
        System.out.print("Enter the password: ");
        String newPassword = scanner.nextLine();
        user.addPassword(newPassword);
    }

    public static void deletePassword(User user, Scanner scanner) {
        if (user.getPasswords().isEmpty()) {
            System.out.println("No passwords to delete.");
            return;
        }

        System.out.println("Enter password to delete: ");
        String passwordToDelete = scanner.nextLine();

        if (user.deletePassword(passwordToDelete)) {
            System.out.println("Password deleted successfully.");
        } else {
            System.out.println("Password not found.");
        }
    }
}

class User {
    private String login;
    private String password;
    private ArrayList<String> passwords;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.passwords = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getPasswords() {
        return passwords;
    }

    public void addPassword(String password) {
        passwords.add(password);
    }

    public boolean deletePassword(String password) {
        return passwords.remove(password);
    }

    public void showPasswords() {
        System.out.println("Passwords:");
        for (String password : passwords) {
            System.out.println(password);
        }
    }

    public void encryptPasswords() {
        if (passwords.isEmpty()) {
            System.out.println("No passwords to encrypt.");
            return;
        }

        int key = 5;
        System.out.println("Encrypted passwords:");
        for (String password : passwords) {
            char[] chars = password.toCharArray();
            for (char c : chars) {
                c += key;
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public void writePasswordsToFile() {
        String filePath = "passwords.txt";

        try (FileWriter passwordWriter = new FileWriter(filePath)) {
            for (String password : passwords) {
                passwordWriter.write(password + "\n");
            }

            System.out.println("Passwords have been successfully written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
