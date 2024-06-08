public class Support {
    private int supportId;
    private String contactEmail;
    private String contactPhone;

    public Support(int supportId, String contactEmail, String contactPhone) {
        this.supportId = supportId;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public void provideSupport() {
        System.out.println("Support provided through email: " + contactEmail + " or phone: " + contactPhone);
    }

    // Getters and Setters
}
