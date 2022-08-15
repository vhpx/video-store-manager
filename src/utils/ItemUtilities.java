package utils;

import items.Genre;
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
        switch (rentalType) {
            case "Game":
                return Item.RentalType.GAME;
            case "Record":
                return Item.RentalType.RECORD;
            case "DVD":
                return Item.RentalType.DVD;
            default:
                return null;
        }
    }

    public Item.LoanType isValidLoanType(String loanType) {
        switch (loanType) {
            case "1-week":
                return Item.LoanType.ONE_WEEK_LOAN;
            case "2-day":
                return Item.LoanType.TWO_DAYS_LOAN;
            default:
                return null;
        }
    }

    public Genre.GenreType isValidGenre(String genre) {
        switch (genre) {
            case "Action":
                return Genre.GenreType.ACTION;
            case "Horror":
                return Genre.GenreType.HORROR;
            case "Drama":
                return Genre.GenreType.DRAMA;
            case "Comedy":
                return Genre.GenreType.COMEDY;
            default:
                return null;
        }
    }

}
