import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService implements interfaces.UserManagement, interfaces.PasswordManagement {
    private ArrayList<User> users;

    public UserService(ArrayList<User> users) {
        this.users = users;
    }

    ////////////////////////////////////////////////UserManagement\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void showMenu(User user, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Menu:");
            System.out.println("1. Write down a password.");
            System.out.println("2. Delete a password.");
            System.out.println("3. Show passwords.");
            System.out.println("4. Encrypt passwords.");
            System.out.println("5. Write passwords to a file.");
            System.out.println("6. Admin menu.");
            System.out.println("7. Exit.");
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
                    if (user instanceof Admin) {
                        ((Admin) user).adminTask();
                    } else {
                        System.out.println("You are not an admin.");
                    }
                    break;
                case 7:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1-7.");
            }
        }
    }

    public void logIn(Scanner scanner) {
        System.out.println("You chose to LogIn.");

        System.out.print("Enter your login: ");
        String loginInput = scanner.nextLine();

        System.out.print("Enter your password: ");
        String passwordInput = scanner.nextLine();

        User foundUser = findUser(loginInput);
        if (foundUser == null || !foundUser.getPassword().equals(passwordInput)) {
            System.out.println("Login failed. Please try again.");
            return;
        }

        System.out.println("Login successful. Welcome back, " + foundUser.getLogin() + "!");
        showMenu(foundUser, scanner);
    }

    public User findUser(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public void registration(Scanner scanner) {
        System.out.println("You chose to Registration.");
        System.out.print("Enter your desired login: ");
        String loginInput = scanner.nextLine();

        if (findUser(loginInput) != null) {
            System.out.println("Login already exists. Please choose another login.");
            return;
        }

        System.out.print("Enter your password: ");
        String passwordInput = scanner.nextLine();

        System.out.print("Are you registering as an admin? (yes/no): ");
        String isAdmin = scanner.nextLine();

        User newUser;
        if (isAdmin.equalsIgnoreCase("yes")) {
            newUser = new Admin(loginInput, passwordInput, this);
        } else {
            newUser = new RegularUser(loginInput, passwordInput, this);
        }

        users.add(newUser);

        System.out.println("Registration successful. Welcome, " + newUser.getLogin() + "!");
        showMenu(newUser, scanner);
    }

////////////////////////////////////////////////PasswordManagement\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void showPasswords(User user) {
        System.out.println("Passwords:");
        for (String password : user.getPasswords()) {
            System.out.println(password);
        }
    }

    public void encryptPasswords(User user) {
        ArrayList<String> passwords = user.getPasswords();
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

    public void writePasswordsToFile(User user) {
        String filePath = "passwords.txt";
        ArrayList<String> passwords = user.getPasswords();

        try (FileWriter passwordWriter = new FileWriter(filePath)) {
            for (String password : passwords) {
                passwordWriter.write(password + "\n");
            }

            System.out.println("Passwords have been successfully written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }


    public void writePassword(User user, Scanner scanner) {
        System.out.print("Enter the password: ");
        String newPassword = scanner.nextLine();
        user.addPassword(newPassword);
    }

    public void deletePassword(User user, Scanner scanner) {
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
