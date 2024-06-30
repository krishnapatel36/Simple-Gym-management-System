import java.util.Scanner;
public class GymManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GymManager manager = new GymManager();
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Gym Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Trainer Login");
            System.out.println("3. Member Login");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    if (manager.authenticateAdmin(adminPassword)) {
                        adminMenu(manager, scanner);
                    } else {
                        System.out.println("Incorrect password.");
                    }
                    break;
                case 2:
                    System.out.print("Enter trainer name: ");
                    String trainerName = getNonEmptyInput(scanner);
                    System.out.print("Enter password: ");
                    String trainerPassword = getNonEmptyInput(scanner);
                    if (manager.authenticateMember(trainerName, trainerPassword)) {
                        trainerMenu(manager, scanner, trainerName);
                    } else {
                        System.out.println("Incorrect name or password.");
                    }
                    break;
                case 3:
                    System.out.print("Enter member name: ");
                    String memberName = getNonEmptyInput(scanner);
                    System.out.print("Enter password: ");
                    String memberPassword = getNonEmptyInput(scanner);
                    if (manager.authenticateMember(memberName, memberPassword)) {
                        memberMenu(manager, scanner, memberName);
                    } else {
                        System.out.println("Incorrect name or password.");
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
        System.out.println("Exiting Gym Management System. Goodbye!");
    }

    private static void adminMenu(GymManager manager, Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Member");
            System.out.println("2. Add Trainer");
            System.out.println("3. List All Members");
            System.out.println("4. List Active Members");
            System.out.println("5. List Members by Age Range");
            System.out.println("6. Upgrade Membership");
            System.out.println("7. Deactivate Member");
            System.out.println("8. Record Attendance");
            System.out.println("9. Search Member by Name");
            System.out.println("10. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter member name: ");
                    String name = getNonEmptyInput(scanner);
                    System.out.print("Enter member age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter member address: ");
                    String address = getNonEmptyInput(scanner);
                    System.out.print("Enter member phone number: ");
                    String phoneNumber = getValidPhoneNumber(scanner);
                    System.out.print("Enter member password: ");
                    String password = getNonEmptyInput(scanner);
                    System.out.print("Enter membership type (BASIC/PREMIUM): ");
                    MembershipType membershipType = MembershipType.valueOf(getNonEmptyInput(scanner).toUpperCase());
                    manager.addMember(name, age, address, phoneNumber, password, membershipType, Role.MEMBER);
                    break;
                case 2:
                    System.out.print("Enter trainer name: ");
                    String trainerName = getNonEmptyInput(scanner);
                    System.out.print("Enter trainer age: ");
                    int trainerAge = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter trainer address: ");
                    String trainerAddress = getNonEmptyInput(scanner);
                    System.out.print("Enter trainer phone number: ");
                    String trainerPhoneNumber = getValidPhoneNumber(scanner);
                    System.out.print("Enter trainer password: ");
                    String trainerPassword = getNonEmptyInput(scanner);
                    manager.addMember(trainerName, trainerAge, trainerAddress, trainerPhoneNumber, trainerPassword, MembershipType.PREMIUM, Role.TRAINER);
                    break;
                case 3:
                    manager.listMembers();
                    break;
                case 4:
                    manager.listActiveMembers();
                    break;
                case 5:
                    System.out.print("Enter minimum age: ");
                    int minAge = scanner.nextInt();
                    System.out.print("Enter maximum age: ");
                    int maxAge = scanner.nextInt();
                    manager.listMembersByAge(minAge, maxAge);
                    break;
                case 6:
                    System.out.print("Enter member name to upgrade: ");
                    name = getNonEmptyInput(scanner);
                    manager.upgradeMembership(name);
                    break;
                case 7:
                    System.out.print("Enter member name to deactivate: ");
                    name = getNonEmptyInput(scanner);
                    manager.deactivateMember(name);
                    break;
                case 8:
                    System.out.print("Enter member name to record attendance: ");
                    name = getNonEmptyInput(scanner);
                    manager.recordAttendance(name);
                    break;
                case 9:
                    System.out.print("Enter member name to search: ");
                    name = getNonEmptyInput(scanner);
                    Member member = manager.searchMember(name);
                    if (member != null) {
                        System.out.println(member);
                    } else {
                        System.out.println("Member with name '" + name + "' not found.");
                    }
                    break;
                case 10:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void trainerMenu(GymManager manager, Scanner scanner, String trainerName) {
        boolean back = false;

        while (!back) {
            System.out.println("Trainer Menu:");
            System.out.println("1. Record Attendance");
            System.out.println("2. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.print("Enter member name to record attendance: ");
                    String name = getNonEmptyInput(scanner);
                    manager.recordAttendance(name);
                    break;
                case 2:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void memberMenu(GymManager manager, Scanner scanner, String memberName) {
        boolean back = false;

        while (!back) {
            System.out.println("Member Menu:");
            System.out.println("1. Record Attendance");
            System.out.println("2. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manager.recordAttendance(memberName);
                    break;
                case 2:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static String getNonEmptyInput(Scanner scanner) {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.print("Input cannot be empty. Please enter again: ");
            } else {
                break;
            }
        }
        return input;
    }

    private static String getValidPhoneNumber(Scanner scanner) {
        String phoneNumber;
        while (true) {
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches("\\d{10}")) {
                break;
            } else {
                System.out.print("Invalid phone number. Please enter a 10-digit phone number: ");
            }
        }
        return phoneNumber;
    }
}
