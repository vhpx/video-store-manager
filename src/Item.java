public class Item {
    private String id;
    private String title;
    private int loanType;
    private int copies;
    private String rentalType;
    private double rentalFee;
    private boolean isAvailable;

    public Item() {}

    public Item(String id, String title, int loanType, int copies, String rentalType, double rentalFee, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.loanType = loanType;
        this.copies = copies;
        this.rentalType = rentalType;
        this.rentalFee = rentalFee;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
        this.loanType = loanType;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
