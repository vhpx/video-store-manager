package utils;

import items.Item;

public class ItemUtilities {

    public boolean isValidId(String itemId) {
        int currentYear = 2022;
        int maxLength = 9;

        if (itemId.length() != maxLength)
            return false;
        String regexPattern = "I\\d{3}-\\d{4}";

        if (!itemId.matches(regexPattern))
            return false;
        if (Integer.valueOf(itemId.substring(5, 9)) > currentYear)
            return false;
        return true;
    }

    public Item.RentalType isValidRentalType(String rentalType) {
        if (rentalType.equalsIgnoreCase("record"))
            return Item.RentalType.RECORD;
        if (rentalType.equalsIgnoreCase("dvd"))
            return Item.RentalType.DVD;
        if (rentalType.equalsIgnoreCase("game"))
            return Item.RentalType.GAME;
        return null;
    }

    public Item.LoanType isValidLoanType(String loanType) {
        if (loanType.equalsIgnoreCase("one_week"))
            return Item.LoanType.ONE_WEEK_LOAN;
        if (loanType.equalsIgnoreCase("two_day"))
            return Item.LoanType.TWO_DAYS_LOAN;
        return null;
    }

    public Item.Genre isValidGenre(String genre) {
        if (genre.equalsIgnoreCase("action"))
            return Item.Genre.ACTION;
        if (genre.equalsIgnoreCase("comedy"))
            return Item.Genre.COMEDY;
        if (genre.equalsIgnoreCase("drama"))
            return Item.Genre.DRAMA;
        if (genre.equalsIgnoreCase("horror"))
            return Item.Genre.HORROR;
        if (genre.equalsIgnoreCase("") || genre.equalsIgnoreCase("non-genre"))
            return Item.Genre.NON_GENRE;
        return null;

    }

}
