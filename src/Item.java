public abstract class Item extends ItemUtility {
    enum RentalType {
        RECORD,
        DVD,
        GAME
    }

    enum LoanType {
        TWO_DAYS_LOAN,
        ONE_WEEK_LOAN
    }

    enum RentalStatus {
        AVAILABLE,
        BORROWED
    }

    private String itemId;
    private String itemTitle;
    private RentalType rentalType;
    private LoanType loanType;
    private int numCopy;
    private double rentalFee;
    private RentalStatus rentalStatus;

    private Item(String itemId, String itemTitle, int numCopy, double rentalFee) throws ItemException {
        if (!setItemId(itemId))
            throw new ItemException("cannot set item id " + itemId);
        if (!setItemTitle(itemTitle))
            throw new ItemException("cannot set item title");
        if (!setNumCopy(numCopy))
            throw new ItemException("cannot set item number of copy");
        if (!setRentalFee(rentalFee))
            throw new ItemException("cannot set item rental fee");
        if (this.isEmpty())
            this.setRentalStatus(RentalStatus.BORROWED);
        else
            this.setRentalStatus(RentalStatus.AVAILABLE);

    }

    public Item(String itemId, String itemTitle, RentalType rentalType, LoanType loanType,
            int numCopy, double rentalFee) throws ItemException {
        this(itemId, itemTitle, numCopy, rentalFee);
        this.rentalType = rentalType;
        this.loanType = loanType;

    }

    public Item(String itemId, String itemTitle, String rentalType, String loanType,
            int numCopy, double rentalFee) throws ItemException {
        this(itemId, itemTitle, numCopy, rentalFee);
        if (!this.setRentalType(rentalType))
            throw new ItemException("cannot set the rental type of the item");
        if (!this.setLoanType(loanType))
            throw new ItemException("cannot set the loan type of the item");

    }

    // create item

    // ---------------- Setter _____________________________

    protected boolean setItemId(String itemId) {
        if (this.isValidId(itemId)) {
            this.itemId = itemId;
            return true;
        }
        return false;

    }

    protected boolean setItemTitle(String itemTitle) {
        if (itemTitle.length() == 0)
            return false;
        this.itemTitle = itemTitle;
        return true;
    }

    protected boolean setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
        return true;
    }

    protected boolean setRentalType(String rentalType) {
        RentalType type = this.isValidRentalType(rentalType);
        if (type == null)
            return false;

        switch (type) {
            case RECORD:
                this.rentalType = RentalType.RECORD;
                return true;
            case DVD:
                this.rentalType = RentalType.DVD;
                return true;
            case GAME:
                this.rentalType = RentalType.GAME;
                return true;
            default:
                return false;
        }
    }

    protected boolean setLoanType(LoanType loanType) {
        this.loanType = loanType;
        return true;
    }

    protected boolean setLoanType(String loanType) {
        LoanType type = this.isValidLoanType(loanType);
        if (type == null)
            return false;
        switch (type) {
            case ONE_WEEK_LOAN:
                this.loanType = LoanType.ONE_WEEK_LOAN;
                return true;
            case TWO_DAYS_LOAN:
                this.loanType = LoanType.TWO_DAYS_LOAN;
                return true;
            default:
                return false;
        }
    }

    protected boolean setNumCopy(int numCopy) {
        if (numCopy >= 0) {
            this.numCopy = numCopy;
            return true;
        }
        return false;

    }

    protected boolean setRentalFee(double rentalFee) {
        if (rentalFee >= 0) {
            this.rentalFee = rentalFee;
            return true;
        }
        return false;
    }

    protected boolean setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
        return true;
    }

    protected boolean setRentalStatus(String rentalStatus) {

        switch (rentalStatus) {
            case "available":
                this.rentalStatus = RentalStatus.AVAILABLE;
                return true;
            case "borrowed":
                this.rentalStatus = RentalStatus.BORROWED;
            default:
                return false;
        }
    }

    // ------------Gettter-----------

    protected String getItemId() {
        return itemId;
    }

    protected String getItemTitle() {
        return itemTitle;
    }

    protected String getRentalType() {
        switch (this.rentalType) {
            case RECORD:
                return "Record";
            case DVD:
                return "DVD";
            case GAME:
                return "Game";
            default:
                return "N/A";
        }
    }

    protected String getLoanType() {
        switch (this.loanType) {
            case ONE_WEEK_LOAN:
                return "1-week";
            case TWO_DAYS_LOAN:
                return "2-day";
            default:
                return "N/A";
        }
    }

    protected int getNumCopy() {
        return numCopy;
    }

    protected double getRentalFee() {
        return rentalFee;
    }

    protected String getRentalStatus() {

        switch (rentalStatus) {
            case AVAILABLE:
                return "available";
            case BORROWED:
                return "borrowed";
            default:
                return "N/A";

        }
    }

    public boolean incrementNumCopy(int n) {
        if (n < 0)
            return false;
        this.numCopy += n;
        this.setRentalStatus("available");
        return true;
    }

    // ------other feature function-------
    protected boolean isEmpty() {
        return this.getNumCopy() == 0 ? true : false;
    }

    @Override
    public String toString() {
        return "ID: " + this.getItemId() + "\n" +
                "Title: " + this.getItemTitle() + "\n" +
                "Rental Type: " + this.getRentalType() + "\n" +
                "Loan Type: " + this.getLoanType() + "\n" +
                "Number of copy: " + this.getNumCopy() + "\n" +
                "Rental Fee: " + this.getRentalFee() + "\n" +
                "Rental Status: " + this.getRentalStatus() + "\n";
    }

}

class ItemException extends Exception {
    public ItemException(String message) {
        super(message);
    }
}