package items;

import utils.ItemUtils;

public class Item {
    private String id;
    private String title;
    private RentalType rentalType;
    private LoanType loanType;
    private int inStock;
    private double rentalFee;
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
            this.inStock = numCopy;
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

    public int getInStock() {
        return inStock;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public boolean increaseNumCopy(int n) {
        if (n < 0)
            return false;
        this.inStock += n;
        return true;
    }

    public void decreaseStock(int n) throws ItemException {
        if (isEmpty()) {
            throw new ItemException("This item is not in stock");
        }

        if (this.getInStock() < n) {
            throw new ItemException("This product does not have enough quantity");
        }
        this.inStock -= n;
    }

    public void increaseStock() {
        this.inStock++;
    }

    public void increaseStock (int n) throws ItemException {
        if (n<=0) {
            throw new ItemException("Invalid number of quantity");
        }
        this.inStock += n;
    }

    public void decreaseStock() throws ItemException {
        if (isEmpty()) {
            throw new ItemException("This item is not in stock");
        }
        this.inStock--;
    }

    // ------other feature function-------
    protected boolean isEmpty() {
        return this.getInStock() == 0;
    }

    @Override
    public String toString() {
        String genre = (this.getGenre().length() == 0) ? "" : ", " + this.getGenre();
        return this.getId() + ", " + this.getTitle() + ", " + this.getRentalType() + ", " +
                this.getLoanType() + ", " +
                this.getInStock() + ", " +
                this.getRentalFee() + genre;
    }

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

}

