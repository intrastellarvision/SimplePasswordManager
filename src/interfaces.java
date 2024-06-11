import java.util.Scanner;

public class interfaces {

    public interface UserManagement {
        void logIn(Scanner scanner);
        void registration(Scanner scanner);
        User findUser(String login);
        void showMenu(User user, Scanner scanner);
    }

    public interface PasswordManagement {
        void writePassword(User user, Scanner scanner);
        void deletePassword(User user, Scanner scanner);
        void showPasswords(User user);
        void encryptPasswords(User user);
        void writePasswordsToFile(User user);
    }

    public interface AdminManagement {
        void adminTask();
    }
}
