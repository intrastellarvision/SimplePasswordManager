import java.util.Scanner;

public class Admin extends User implements interfaces.AdminManagement {
    public Admin(String login, String password, UserService userService) {
        super(login, password, userService);
    }

    Scanner admin = new Scanner(System.in);

    public void adminTask() {
        System.out.println("Admin menu");
        System.out.println("1. Delete user password");
    }
}

