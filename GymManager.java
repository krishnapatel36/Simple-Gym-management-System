import java.io.*;
import java.util.*;

class GymManager {
    private List<Member> members;
    private static final String MEMBER_FILE = "members.txt";
    private static final String USER_DATA_FILE = "user_data.txt";
    private static final String ADMIN_PASSWORD = "I am admin";

    public GymManager() {
        members = new ArrayList<>();
        loadMembersFromFile();
    }

    public void addMember(String name, int age, String address, String phoneNumber, String password, MembershipType membershipType, Role role) {
        Member newMember = new Member(name, age, address, phoneNumber, password, membershipType, role);
        members.add(newMember);
        saveMembersToFile();
    }

    public void listMembers() {
        for (Member member : members) {
            System.out.println(member);
            System.out.println("---------------------");
        }
    }

    public void listActiveMembers() {
        System.out.println("Active Members:");
        for (Member member : members) {
            if (member.isActive()) {
                System.out.println(member);
                System.out.println("---------------------");
            }
        }
    }

    public void listMembersByAge(int minAge, int maxAge) {
        for (Member member : members) {
            if (member.getAge() >= minAge && member.getAge() <= maxAge) {
                System.out.println(member);
                System.out.println("---------------------");
            }
        }
    }

    public void upgradeMembership(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                if (member.getMembershipType() == MembershipType.BASIC) {
                    member.setMembershipType(MembershipType.PREMIUM);
                    System.out.println("Membership upgraded to Premium for " + member.getName());
                    saveMembersToFile(); 
                    return;
                } else {
                    System.out.println(member.getName() + " already has a Premium membership.");
                    return;
                }
            }
        }
        System.out.println("Member with name '" + name + "' not found.");
    }

    public void deactivateMember(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                member.setActive(false);
                System.out.println("Member " + member.getName() + " deactivated.");
                saveMembersToFile();
                return;
            }
        }
        System.out.println("Member with name '" + name + "' not found.");
    }

    public void recordAttendance(String name) {
        Date currentDate = new Date();
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                if (member.getLastAttendanceDate() != null && isSameDay(member.getLastAttendanceDate(), currentDate)) {
                    System.out.println("Attendance already recorded for today.");
                    return;
                }
                member.incrementDaysAttended();
                member.setLastAttendanceDate(currentDate);
                saveUserData();
                System.out.println("Attendance recorded for " + member.getName());
                return;
            }
        }
        System.out.println("Member with name '" + name + "' not found.");
    }

    public Member searchMember(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private void saveMembersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBER_FILE))) {
            for (Member member : members) {
                writer.println(member);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMembersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                members.add(Member.fromString(line));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveUserData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (Member member : members) {
                writer.println(member.getName() + "," + member.getDaysAttended() + "," + (member.getLastAttendanceDate() != null ? member.getLastAttendanceDate().getTime() : "null"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateMember(String name, String password) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name) && member.verifyPassword(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean authenticateAdmin(String password) {
        return ADMIN_PASSWORD.equals(password);
    }
}
