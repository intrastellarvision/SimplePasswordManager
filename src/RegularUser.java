//DATABASE
public class RegularUser extends User {
    public RegularUser(String login, String password, UserService userService) {
        super(login, password, userService);
    }
}