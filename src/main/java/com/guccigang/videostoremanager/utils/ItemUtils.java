package com.guccigang.videostoremanager.utils;

import java.util.Calendar;

import com.guccigang.videostoremanager.items.Item;

public class ItemUtils extends ObjectUtils<Item> {
    public Item parse(String str) {
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
            System.out.println("Error: Invalid item format");
            return null;
        }
    }

    public String serialize(Item item) {
        String[] tokens = {
                item.getId() != null ? item.getId() : "UNKNOWN",
                item.getTitle() != null ? item.getTitle() : "UNKNOWN",
                item.getRentalType() != null ? item.getRentalType() : "UNKNOWN",
                item.getLoanType() != null ? item.getLoanType() : "UNKNOWN",
                Integer.toString(item.getInStock()),
                Double.toString(item.getRentalFee()),
                item.getGenre() != null ? item.getGenre() : "UNKNOWN",
        };

        return String.join(", ", tokens);
    }

    public static boolean isValidId(String id) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int maxLength = 9;

        if (id.length() != maxLength)
            return false;
        String regexPattern = "I\\d{3}-\\d{4}";

        if (!id.matches(regexPattern))
            return false;

        return Integer.parseInt(id.substring(5, 9)) <= currentYear;
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
            return Item.Genre.NOT_APPLICABLE;

        return null;
    }
}
