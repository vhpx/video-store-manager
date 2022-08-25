package items;

import utils.ItemUtils;

public class Item {
    public enum Genre {
        ACTION,
        HORROR,
        DRAMA,
        COMEDY,
        NOT_APPLICABLE
    }

    public enum RentalType {
        RECORD,
        DVD,
        GAME
    }

    public enum LoanType {
        TWO_DAYS_LOAN,
        ONE_WEEK_LOAN
    }

    enum RentalStatus {
        AVAILABLE,
        BORROWED
    }

    private String id;
    private String title;
    private RentalType rentalType;
    private LoanType loanType;
    private int numCopy;
    private double rentalFee;
    private RentalStatus rentalStatus;
    private Genre genre;

    public Item() {
    }

    private Item(String id, String title, int numCopy, double rentalFee) throws ItemException {
        if (!setId(id))
            throw new ItemException("cannot set item id " + id);
        if (!setTitle(title))
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

    public Item(String id, String title, RentalType rentalType, LoanType loanType, Genre genre,
            int numCopy, double rentalFee) throws ItemException {
        this(id, title, numCopy, rentalFee);
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.genre = (rentalType == RentalType.GAME) ? Genre.NOT_APPLICABLE : genre;
    }

    public Item(String id, String title, String rentalType, String loanType, String genre,
            int numCopy, double rentalFee) throws ItemException {
        this(id, title, numCopy, rentalFee);
        if (!this.setRentalType(rentalType))
            throw new ItemException("cannot set the rental type of the item");
        if (!this.setLoanType(loanType))
            throw new ItemException("cannot set the loan type of the item");
        if (!this.setGenre(genre))
            throw new ItemException("cannot set the genre of the item");
    }

    // create item

    // ---------------- Setter -------------

    protected boolean setId(String id) {

        if (ItemUtils.isValidId(id)) {
            this.id = id;
            return true;
        }

        return false;
    }

    protected boolean setTitle(String title) {
        if (title.length() == 0)
            return false;
        this.title = title;
        return true;
    }

    protected boolean setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
        return true;
    }

    protected boolean setRentalType(String rentalType) {
        RentalType type = ItemUtils.parseRentalType(rentalType);
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
        LoanType type = ItemUtils.parseLoanType(loanType);
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

    protected boolean setGenre(Genre genre) {
        this.genre = genre;
        return true;
    }

    protected boolean setGenre(String genre) {
        Genre type = ItemUtils.parseGenre(genre);
        if (type == null)
            return false;
        switch (type) {
            case ACTION:
                this.genre = Genre.ACTION;
                return true;
            case HORROR:
                this.genre = Genre.HORROR;
                return true;
            case DRAMA:
                this.genre = Genre.DRAMA;
                return true;
            case COMEDY:
                this.genre = Genre.COMEDY;
                return true;
            case NOT_APPLICABLE:
                this.genre = Genre.NOT_APPLICABLE;
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
        if (rentalStatus.equalsIgnoreCase("available")) {
            this.rentalStatus = RentalStatus.AVAILABLE;
            return true;
        } else if (rentalStatus.equalsIgnoreCase("borrowed")) {
            this.rentalStatus = RentalStatus.BORROWED;
            return true;
        }
        return false;
    }

    // ------------Getter-----------

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRentalType() {
        return switch (this.rentalType) {
            case RECORD -> "RECORD";
            case DVD -> "DVD";
            case GAME -> "GAME";
        };
    }

    public String getLoanType() {
        return switch (this.loanType) {
            case ONE_WEEK_LOAN -> "ONE_WEEK";
            case TWO_DAYS_LOAN -> "TWO_DAY";
        };
    }

    public String getGenre() {
        return switch (this.genre) {
            case ACTION -> "ACTION";
            case HORROR -> "HORROR";
            case DRAMA -> "DRAMA";
            case COMEDY -> "COMEDY";
            case NOT_APPLICABLE -> "N/A";
        };
    }

    public int getNumCopy() {
        return numCopy;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    protected String getRentalStatus() {

        return switch (rentalStatus) {
            case AVAILABLE -> "AVAILABLE";
            case BORROWED -> "BORROWED";
        };
    }

    public boolean incrementNumCopy(int n) {
        if (n < 0)
            return false;
        this.numCopy += n;
        this.setRentalStatus("BORROWED");
        return true;
    }

    // ------other feature function-------
    protected boolean isEmpty() {
        return this.getNumCopy() == 0;
    }

    @Override
    public String toString() {
        String genre = (this.getGenre().length() == 0) ? "" : ", " + this.getGenre();
        return this.getId() + ", " + this.getTitle() + ", " + this.getRentalType() + ", " +
                this.getLoanType() + ", " +
                this.getNumCopy() + ", " +
                this.getRentalFee() + genre;
    }

}

class ItemException extends Exception {
    public ItemException(String message) {
        super(message);
    }
}