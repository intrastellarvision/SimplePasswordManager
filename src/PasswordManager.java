import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PasswordManager {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the password manager, select an action.");

        try (Scanner scan = new Scanner(System.in)) {
            ArrayList<String> passwords = new ArrayList<String>();

            boolean running = true;
            do {
                System.out.println("Menu:");
                System.out.println("1. Write down the password.");
                System.out.println("2. Delete Password.");
                System.out.println("3. Show password.");
                System.out.println("4. Encrypt password.");
                System.out.println("5. Write the password to a file.");
                System.out.println("6. Exit.");
                System.out.print("Your choice: ");

                int choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        writePassword(passwords, scan);
                        break;

                    case 2:
                        deletePassword(passwords, scan);
                        break;

                    case 3:
                        showPasswords(passwords);
                        break;

                    case 4:
                        encryptPasswords(passwords);
                        break;

                    case 5:
                        writePasswordsToFile(passwords);
                        break;

                    case 6:
                        running = false;
                        break;
                }

            } while (running);
        }
    }

    public static void writePassword(ArrayList<String> passwords, Scanner scan) {
        System.out.print("Enter the password: ");
        String newPassword = scan.nextLine();
        passwords.add(newPassword);
    }

    public static void deletePassword(ArrayList<String> passwords, Scanner scan) {
        if (passwords.isEmpty()) {
            System.out.println("No passwords to delete.");
            return;
        }

        System.out.println("Enter password to delete: ");
        String passwordToDelete = scan.nextLine();

        if (passwords.remove(passwordToDelete)) {
            System.out.println("The password was deleted successfully");
        } else {
            System.out.println("Password not found.");
        }
    }

    public static void showPasswords(ArrayList<String> passwords) {
        System.out.println("Passwords: ");
        for (String password : passwords) {
            System.out.println(password);
        }
    }

    public static void encryptPasswords(ArrayList<String> passwords) {
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

    public static void writePasswordsToFile(ArrayList<String> passwords) {
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
