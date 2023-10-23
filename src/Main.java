import entities.User;
import service.UserService;
import view.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();
        UserService  userService = new UserService();
        Menu menu = new Menu();
        menu.menuSelect(scanner, users, userService);
    }
}
