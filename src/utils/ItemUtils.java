package utils;

import items.Item;

public class ItemUtils {
    public static Item parse(String str) {
        try {
            String[] tokens = str.split(", ");

            String id = tokens[0];
            String title = tokens[1];
            String rentalType = tokens[2];
            String loanType = tokens[3];

            int numCopy = Integer.parseInt(tokens[4]);
            double rentalFee = Double.parseDouble(tokens[5]);

            String genre = (rentalType.equalsIgnoreCase("GAME")) ? "N/A" : tokens[6];

            return new Item(id, title, rentalType, loanType, genre, numCopy, rentalFee);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new Item();
        }
    }

    public static String serialize(Item item) {
        String[] tokens = {
                item.getId() != null ? item.getId() : "UNKNOWN",
                item.getTitle() != null ? item.getTitle() : "UNKNOWN",
                item.getRentalType() != null ? item.getRentalType() : "UNKNOWN",
                item.getLoanType() != null ? item.getLoanType() : "UNKNOWN",
                Integer.toString(item.getNumCopy()),
                Double.toString(item.getRentalFee()),
                item.getGenre() != null ? item.getGenre() : "UNKNOWN",
        };

        return String.join(", ", tokens);
    }

    public static boolean parseId(String itemId) {
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

    public static Item.RentalType parseRentalType(String rentalType) {
        if (rentalType.equalsIgnoreCase("record"))
            return Item.RentalType.RECORD;
        if (rentalType.equalsIgnoreCase("dvd"))
            return Item.RentalType.DVD;
        if (rentalType.equalsIgnoreCase("game"))
            return Item.RentalType.GAME;
        return null;
    }

    public static Item.LoanType parseLoanType(String loanType) {
        if (loanType.equalsIgnoreCase("one_week"))
            return Item.LoanType.ONE_WEEK_LOAN;
        if (loanType.equalsIgnoreCase("two_day"))
            return Item.LoanType.TWO_DAYS_LOAN;
        return null;
    }

    public static Item.Genre parseGenre(String genre) {
        if (genre.equalsIgnoreCase("ACTION"))
            return Item.Genre.ACTION;
        if (genre.equalsIgnoreCase("COMEDY"))
            return Item.Genre.COMEDY;
        if (genre.equalsIgnoreCase("DRAMA"))
            return Item.Genre.DRAMA;
        if (genre.equalsIgnoreCase("HORROR"))
            return Item.Genre.HORROR;
        if (genre.equalsIgnoreCase("") || genre.equalsIgnoreCase("N/A"))
            return Item.Genre.NOT_APPPLICABLE;

        return null;
    }
}
