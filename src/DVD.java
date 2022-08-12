public class DVD extends GenreItem{

    public DVD(String itemId, String itemTitle, RentalType rentalType, LoanType loanType, int numCopy,
               double rentalFee, Genre genre) throws ItemException
    {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee, genre);
    }

    public DVD(String itemId, String itemTitle, String rentalType, String loanType, int numCopy,
               double rentalFee, String genre) throws ItemException
    {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee, genre);
    }


}
