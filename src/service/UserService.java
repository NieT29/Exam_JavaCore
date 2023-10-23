package service;

import entities.User;
import view.Menu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    public void login(Scanner scanner, ArrayList<User> users) {
        System.out.println("Nhập username: ");
        String userName = scanner.nextLine();
        System.out.println("Nhập password: ");
        String passWord = scanner.nextLine();

        User foundUser = findUserByUsername(userName, users);

        if (foundUser != null) {
            if (foundUser.getPassWord().equals(passWord)){
                System.out.println("Chào mừng "+(userName) +", bạn có thể thực hiện các chức năng sau");
                while (true) {
                    System.out.println("1 - Thay đổi username");
                    System.out.println("2 - Thay đổi email");
                    System.out.println("3 - Thay đổi password");
                    System.out.println("4 - Đăng xuất");
                    System.out.println( "0 - Thoát chương trình");

                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            changeUsername(scanner, users, foundUser);
                            break;
                        case "2":
                            changeEmail(scanner, users, foundUser);
                            break;
                        case "3":
                            changePassword(scanner, foundUser);
                            break;
                        case "4":
                            return;
                        case "0":
                            System.out.println("Chương trình đã thoát");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ, mời bạn chọn lại");
                    }
                }
            } else {
                handleIncorrectPassword(scanner, users);
            }
        } else {
            System.out.println("Tài khoản không tồn tài, kiểm tra lại username hoặc đăng ký");
        }
    }

    private User findUserByUsername(String userName, ArrayList<User> users) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    private void handleIncorrectPassword(Scanner scanner, ArrayList<User> users) {
        System.out.println("Sai password. Mời bạn chọn");
        System.out.println("1 - Đăng nhập lại");
        System.out.println("2 - Quên mật khẩu");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                login(scanner, users);
                break;
            case "2":
                forgotPassword(scanner, users);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ");
        }
    }

    private boolean isUsernameAvailable(String newUsername, ArrayList<User> users) {
        for (User existingUser : users) {
            if (existingUser.getUserName().equals(newUsername)) {
                return false;
            }
        }
        return true;
    }

    public void changeUsername(Scanner scanner, ArrayList<User> users, User user) {
        System.out.println("Nhập username mới");
        String newUsername = scanner.nextLine();

        if (isUsernameAvailable(newUsername, users)) {
            user.setUserName(newUsername);
            System.out.println("Username đã được thay đổi, mời bạn tiếp tục");
        } else {
            System.out.println("Username đã tồn tại, mời bạn nhập lại");
            changeUsername(scanner, users, user);
        }
    }

    private boolean isEmailAvailable(String newEmail, ArrayList<User> users, User user) {
        for (User existingUser : users) {
            if (existingUser != user && existingUser.getEmail().equals(newEmail)) {
                return false;
            }
        }
        return true;
    }

    private void changeEmail(Scanner scanner, ArrayList<User> users, User user) {
        System.out.println("Nhập email mới");
        String newEmail = scanner.nextLine();

        if (isEmailValid(newEmail)) {
            if (isEmailAvailable(newEmail, users, user)) {
                user.setEmail(newEmail);
                System.out.println("Email đã được thay đổi, mời bạn tiếp tục");
            } else {
                System.out.println("Email đã tồn tại, mời bạn nhập lại");
                changeEmail(scanner, users, user);
            }
        } else {
            System.out.println("Email không hợp lệ, mời bạn nhập lại");
            changeEmail(scanner, users, user);
        }
    }

    public void changePassword(Scanner scanner, User user) {
        System.out.println("Nhập password mới, password (dài từ 7-15 ký tự và chứa 1 ký tự hoa, 1 ký tự đặc biệt");
        String newPassword = scanner.nextLine();

        if (isPasswordValid(newPassword)) {
            user.setPassWord(newPassword);
            System.out.println("Password đã được thay đổi, mời bạn tiếp tục");
        } else {
            System.out.println("Password không hợp lệ, mời bạn nhập lại");
            changePassword(scanner, user);
        }
    }

    private User findUserByEmail(String email, ArrayList<User> users) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void forgotPassword(Scanner scanner, ArrayList<User> users) {
        System.out.println("Nhập email đã đăng ký với username");
        String email = scanner.nextLine();

        User user = findUserByEmail(email, users);

        if (user != null) {
            System.out.println("Nhập password mới, password (dài từ 7-15 ký tự và chứa 1 ký tự hoa, 1 ký tự đặc biệt)");
            String newPassword = scanner.nextLine();

            if (isPasswordValid(newPassword)) {
                user.setPassWord(newPassword);
                System.out.println("Password đã được thay đổi, mời bạn đăng nhập lại");
                login(scanner, users);
            } else {
                System.out.println("Password không hợp lệ, mời bạn nhập lại");
                forgotPassword(scanner, users);
            }
        } else {
            System.out.println("Email không tồn tại, mời bạn nhập lại");
            forgotPassword(scanner, users);
        }
    }

    public void register(Scanner scanner, ArrayList<User> users) {
        System.out.println("Nhập username");
        String userName = scanner.nextLine();

        if (isUsernameAvailable(userName, users)) {
            System.out.println("Nhập Email");
            String email = scanner.nextLine();

            if (isEmailAvailable(email, users, null) && isEmailValid(email)) {
                System.out.println("Nhập password (dài từ 7-15 ký tự và chứa 1 ký tự hoa, 1 ký tự đặc biệt)");
                String passWord = scanner.nextLine();

                if (isPasswordValid(passWord)) {
                    User user = new User(userName, email, passWord);
                    users.add(user);
                    System.out.println("Tài khoản đã được tạo");
                } else {
                    System.out.println("Password không hợp lệ, mời bạn đăng ký lại");
                }
            } else {
                if (!isEmailValid(email)) {
                    System.out.println("Email không hợp lệ, mời bạn đăng ký lại");
                } else {
                    System.out.println("Email đã tồn tại, mời bạn đăng ký lại");
                }
            }
        } else {
            System.out.println("Username đã tồn tại, mời bạn đăng ký lại");
        }
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[\\W_]).{7,15}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
