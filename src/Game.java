public class Game extends Item{
    public Game(String itemId, String itemTitle, RentalType rentalType, LoanType loanType, int numCopy,
                double rentalFee) throws ItemException {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee);
    }

    public Game(String itemId, String itemTitle, String rentalType, String loanType, int numCopy,
                double rentalFee) throws ItemException {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee);
    }
}
