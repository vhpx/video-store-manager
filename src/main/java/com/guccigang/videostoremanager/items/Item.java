package com.guccigang.videostoremanager.items;

import com.guccigang.videostoremanager.core.Entity;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.utils.ItemUtils;

public class Item extends Entity {
    private String title;
    private RentalType rentalType;
    private LoanType loanType;
    private Genre genre;
    private int stock;
    private double rentalFee;

    public Item(String id, String title, RentalType rentalType, LoanType loanType, Genre genre,
                int stock, double rentalFee) {
        super(id);
        this.title = title;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.genre = genre;
        this.stock = stock;
        this.rentalFee = rentalFee;
    }

    public void setId(String id) throws Exception {
        if (!ItemUtils.isValidId(id))
            throw new ItemException("Invalid item id: " + id);

        super.setId(id);
    }

    protected boolean setTitle(String title) {
        if (!ItemUtils.isValidTitle(title))
            return false;

        this.title = title;
        return true;
    }

    protected boolean setRentalType(RentalType rentalType) {
        if (rentalType == null)
            return false;

        this.rentalType = rentalType;
        return true;
    }

    protected boolean setGenre(Genre genre) {
        if (genre == null)
            return false;

        this.genre = (rentalType == RentalType.GAME) ? Genre.NOT_APPLICABLE : genre;
        return true;
    }

    protected boolean setRentalType(String rentalType) {
        RentalType type = ItemUtils.parseRentalType(rentalType);

        if (type == null)
            return false;

        switch (type) {
            case RECORD -> {
                this.rentalType = RentalType.RECORD;
                return true;
            }
            case DVD -> {
                this.rentalType = RentalType.DVD;
                return true;
            }
            case GAME -> {
                this.rentalType = RentalType.GAME;
                return true;
            }
        }

        return false;
    }

    protected boolean setLoanType(LoanType loanType) {
        if (loanType == null)
            return false;

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

    protected boolean setGenre(String genre) {
        Genre type = ItemUtils.parseGenre(genre);

        if (type == null)
            return false;

        switch (type) {
            case ACTION -> {
                this.genre = Genre.ACTION;
                return true;
            }
            case HORROR -> {
                this.genre = Genre.HORROR;
                return true;
            }
            case DRAMA -> {
                this.genre = Genre.DRAMA;
                return true;
            }
            case COMEDY -> {
                this.genre = Genre.COMEDY;
                return true;
            }
            case NOT_APPLICABLE -> {
                this.genre = Genre.NOT_APPLICABLE;
                return true;
            }
        }

        return false;
    }

    protected boolean setStock(int stock) {
        if (stock < 0)
            return false;

        this.stock = stock;
        return true;
    }

    protected boolean setRentalFee(double rentalFee) {
        if (rentalFee < 0)
            return false;

        this.rentalFee = rentalFee;
        return true;
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
            case TWO_DAYS_LOAN -> "TWO_DAY";
            case ONE_WEEK_LOAN -> "ONE_WEEK";
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

    public int getStock() {
        return stock;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    private boolean isOutOfStock() {
        return this.stock == 0;
    }

    public void increaseStock() {
        this.stock++;
    }

    public void decreaseStock() throws ItemException {
        if (isOutOfStock())
            throw new ItemException("This item is not in stock");

        this.stock--;
    }

    public void increaseStock(int n) throws ItemException {
        if (n <= 0)
            throw new ItemException("Cannot increase stock by a negative number");

        this.stock += n;
    }

    public void decreaseStock(int n) throws ItemException {
        if (isOutOfStock())
            throw new ItemException("Item is out of stock");

        if (n > this.stock)
            throw new ItemException("Cannot decrease stock by a number greater than the current stock");

        this.stock -= n;
    }

    public void updateTitle(String newTitle) throws ItemException
    {
        if (newTitle.length() == 0)
            throw new ItemException("Title cannot be empty");

        this.title = newTitle;
    }
    public void updateGenre(String newGenre) throws ItemException
    {
        if (newGenre.length() == 0)
            throw new ItemException("Title cannot be empty");
        if (!setGenre(newGenre))
            throw new ItemException("Invalid input for Genre");

        setGenre(newGenre);
    }
    public void updateRentalType(String newRentalType) throws ItemException
    {
        if (newRentalType.length() == 0)
        throw new ItemException("Title cannot be empty");
        if (!setRentalType(newRentalType))
            throw new ItemException("Invalid input for Rental Type");

        setRentalType(newRentalType);
    }
    public void updateFee(double newFee) throws ItemException
    {
        if (setRentalFee(newFee))
            return;
        throw new ItemException("Invalid input for Rental Fee");
    }

    public void updateLoanType(String newLoanType) throws ItemException
    {
        if (newLoanType.length() == 0)
            throw new ItemException("Title cannot be empty");
        if (!setLoanType(newLoanType))
            throw new ItemException("Invalid input for Loan Type");
        setLoanType(newLoanType);
    }

    public void updateCopy(int newNoCoppy) throws ItemException
    {
        if (setStock(newNoCoppy))
            return;
        throw new ItemException("Invalid input for number of copy");
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


    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", rentalType=" + rentalType +
                ", loanType=" + loanType +
                ", genre=" + genre +
                ", stock=" + stock +
                ", rentalFee=" + rentalFee +
                '}';
    }
}
