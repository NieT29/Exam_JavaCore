package service;

import entities.User;

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

            boolean foundUser = false;

            for (User user : users) {
                if (user.getUserName().equals(userName)) {
                    foundUser = true;
                    if (user.getPassWord().equals(passWord)) {
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
                                    changeUsername(scanner, users);
                                    break;
                                case "2":
                                    changeEmail(scanner, users);
                                    break;
                                case "3":
                                    changePassword(scanner, users);
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
                        System.out.println("Sai password. Mời bạn chọn");
                        System.out.println("1 - Đăng nhập lại");
                        System.out.println("2 - Quên mật khẩu");

                        String choice = scanner.nextLine();

                        switch (choice) {
                            case "1":
                                login(scanner, users);
                                return;
                            case "2":
                                forgotPassword(scanner, users);
                                break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ");
                        }
                    } break;
                }
            }
            if (!foundUser) {
                System.out.println("Tên đăng nhập không tồn tại ,kiểm tra lại username");
            }
        }

        public void changeUsername(Scanner scanner, ArrayList<User> users) {
            System.out.println("Nhập username mới");
            String newUsername = scanner.nextLine();

            for (User user : users) {
                if (user.getUserName().equals(newUsername)) {
                    System.out.println("Username đã tồn tại, mời bạn nhập lại");
                    changeUsername(scanner, users);
                    return;
                }
            }
            for (User user : users) {
                user.setUserName(newUsername);
            }
            System.out.println("Username đã được thay đổi, mời bạn tiếp tục");
        }

        public void changeEmail(Scanner scanner, ArrayList<User> users) {
            System.out.println("Nhập email mới");
            String newEmail = scanner.nextLine();

            if (!isEmailValid(newEmail)) {
                System.out.println("Email không hợp lệ, mời bạn nhập lại");
                changeEmail(scanner, users);
                return;
            }

            for (User user : users) {
                if (user.getEmail().equals(newEmail)) {
                    System.out.println("Email đã tồn tại, Mời bạn nhập lại");
                    changeEmail(scanner, users);
                    return;
                }
            }
            for (User user : users) {
                user.setEmail(newEmail);
            }
            System.out.println("Email đã được thay đổi, mời bạn tiếp tục");
        }

        public void changePassword(Scanner scanner, ArrayList<User> users) {
            System.out.println("Nhập password mới, password (dài từ 7-15 ký tự và chứa 1 ký tự hoa, 1 ký tự đặc biệt");
            String newPassword = scanner.nextLine();

            if (!isPasswordValid(newPassword)) {
                System.out.println("Password không hợp lệ, Mời bạn nhập lại");
                changePassword(scanner, users);
                return;
            }

            for (User user : users) {
                user.setPassWord(newPassword);
            }
            System.out.println("Password đã được thay đổi, mời bạn tiếp tục");
        }

        public void forgotPassword(Scanner scanner, ArrayList<User> users) {
            System.out.println("Nhập email đã đăng ký với username");
            String email = scanner.nextLine();

            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    System.out.println("Nhập password mới, password (dài từ 7-15 ký tự và chứa 1 ký tự hoa, 1 ký tự đặc biệt");
                    String newPassword = scanner.nextLine();
                    if (!isPasswordValid(newPassword)) {
                        System.out.println("Password không hợp lệ, mời bạn nhập lại");
                        forgotPassword(scanner, users);
                        return;
                    } else {
                        user.setPassWord(newPassword);
                        System.out.println("Password đã được thay đổi, mời bạn đăng nhập lại");
                        login(scanner, users);
                        return;
                    }
                } else {
                    System.out.println("Bạn đã nhập sai email, mời bạn nhập lại");
                    forgotPassword(scanner, users);
                }
            }
        }

        public void register(Scanner scanner, ArrayList<User> users) {
            System.out.println("Nhập username");
            String userName = scanner.nextLine();
            for (User user : users) {
                if (user.getUserName().equals(userName)) {
                    System.out.println("Username đã tồn tại, mời bạn đăng ký lại");
                    register(scanner, users);
                    return;
                }
            }

            System.out.println("Nhập Email");
            String email = scanner.nextLine();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    System.out.println("Email đã tồn tại, mời bạn đăng ký lại");
                    register(scanner, users);
                    return;
                }
            }

            if (!isEmailValid(email)) {
                System.out.println("Email không hợp lệ, mời bạn đăng ký lại");
                register(scanner, users);
                return;
            }

            System.out.println("Nhập password (dài từ 7-15 ký tự và chứa 1 ký tự hoa, 1 ký tự đặc biệt)");
            String passWord = scanner.nextLine();

            if (!isPasswordValid(passWord)) {
                System.out.println("Password không hợp lệ, mời bạn đăng ký lại");
                register(scanner, users);
                return;
            }

            User user = new User(userName, email, passWord);
            users.add(user);
            System.out.println("Tài khoản đã được tạo");
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
