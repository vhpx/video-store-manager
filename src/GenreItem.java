public class GenreItem extends Item{
    enum Genre{
        ACTION,
        HORROR,
        DRAMA,
        COMEDY
    }

    private Genre genre;

    public GenreItem(String itemId, String itemTitle, RentalType rentalType, LoanType loanType, int numCopy,
                     double rentalFee, Genre genre) throws ItemException
    {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee);
        this.genre = genre;
    }

    public GenreItem(String itemId, String itemTitle, String rentalType, String loanType, int numCopy,
                     double rentalFee, String genre) throws ItemException
    {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee);
        if (!this.setGenre(genre))
            throw new ItemException("cannot set genre");
    }

    public String getGenre() {
        switch (this.genre){
            case ACTION:
                return "Action";
            case HORROR:
                return "Horror";
            case COMEDY:
                return "Comedy";
            case DRAMA:
                return "Drama";
            default:
                return "N/A";
        }
    }

    protected boolean setGenre(String genre) {
        Genre type = this.isValidGenre(genre);

        if (type == null)
            return false;

        switch (type){
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
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Genre: " + this.getGenre() + "\n";
    }


}
