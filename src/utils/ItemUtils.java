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
}
