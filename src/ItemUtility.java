
public class ItemUtility {

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

    public GenreItem.Genre isValidGenre(String genre) {

        switch (genre) {
            case "Action":
                return GenreItem.Genre.ACTION;
            case "Horror":
                return GenreItem.Genre.HORROR;
            case "Drama":
                return GenreItem.Genre.DRAMA;
            case "Comedy":
                return GenreItem.Genre.COMEDY;
            default:
                return null;
        }
    }

}
