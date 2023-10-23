package view;

import entities.User;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public void menuSelect(Scanner scanner, ArrayList<User> users, UserService userService) {
        while (true) {
            System.out.println("====== Mời Bạn Chọn ======");
            System.out.println("1 - Đăng nhập");
            System.out.println("2 - Đăng ký");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userService.login(scanner, users);
                    break;
                case "2":
                    userService.register(scanner, users);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, mời bạn chọn lại");
            }
        }
    }
}
