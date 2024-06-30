import java.util.Date;

class Member {
    private String name;
    private int age;
    private String address;
    private String phoneNumber;
    private String passwordHash;
    private MembershipType membershipType;
    private boolean isActive;
    private Date registrationDate;
    private int daysAttended;
    private Date lastAttendanceDate;
    private Role role;

    public Member(String name, int age, String address, String phoneNumber, String password, MembershipType membershipType, Role role) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.passwordHash = hashPassword(password);
        this.membershipType = membershipType;
        this.isActive = true;
        this.registrationDate = new Date();
        this.daysAttended = 0;
        this.lastAttendanceDate = null;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public int getDaysAttended() {
        return daysAttended;
    }

    public void incrementDaysAttended() {
        this.daysAttended++;
    }

    public Date getLastAttendanceDate() {
        return lastAttendanceDate;
    }

    public void setLastAttendanceDate(Date lastAttendanceDate) {
        this.lastAttendanceDate = lastAttendanceDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean verifyPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    @Override
    public String toString() {
        return name + "," + age + "," + address + "," + phoneNumber + "," + passwordHash + "," + membershipType + "," + isActive + "," + registrationDate.getTime() + "," + daysAttended + "," + (lastAttendanceDate != null ? lastAttendanceDate.getTime() : "null") + "," + role;
    }

    public static Member fromString(String memberData) {
        String[] data = memberData.split(",");
        String name = data[0];
        int age = Integer.parseInt(data[1]);
        String address = data[2];
        String phoneNumber = data[3];
        String passwordHash = data[4];
        MembershipType membershipType = MembershipType.valueOf(data[5]);
        boolean isActive = Boolean.parseBoolean(data[6]);
        Date registrationDate = new Date(Long.parseLong(data[7]));
        int daysAttended = Integer.parseInt(data[8]);
        Date lastAttendanceDate = data[9].equals("null") ? null : new Date(Long.parseLong(data[9]));
        Role role = Role.valueOf(data[10]);

        Member member = new Member(name, age, address, phoneNumber, passwordHash, membershipType, role);
        member.passwordHash = passwordHash;
        member.isActive = isActive;
        member.registrationDate = registrationDate;
        member.daysAttended = daysAttended;
        member.lastAttendanceDate = lastAttendanceDate;

        return member;
    }
}
