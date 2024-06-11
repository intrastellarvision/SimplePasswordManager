import java.util.ArrayList;

public abstract class User {
    private String login;
    private String password;
    private ArrayList<String> passwords;
    private UserService userService;

    public User(String login, String password, UserService userService) {
        this.login = login;
        this.password = password;
        this.passwords = new ArrayList<>();
        this.userService = userService;
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
        userService.showPasswords(this);
    }

    public void encryptPasswords() {
        userService.encryptPasswords(this);
    }

    public void writePasswordsToFile() {
        userService.writePasswordsToFile(this);
    }
}
