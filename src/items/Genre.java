package items;

public class Genre extends Item {
    public enum GenreType {
        ACTION,
        HORROR,
        DRAMA,
        COMEDY
    }

    private GenreType genre;

    public Genre(String itemId, String itemTitle, RentalType rentalType, LoanType loanType, int numCopy,
            double rentalFee, GenreType genre) throws ItemException {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee);
        this.genre = genre;
    }

    public Genre(String itemId, String itemTitle, String rentalType, String loanType, int numCopy,
            double rentalFee, String genre) throws ItemException {
        super(itemId, itemTitle, rentalType, loanType, numCopy, rentalFee);
        if (!this.setGenre(genre))
            throw new ItemException("cannot set genre");
    }

    public String getGenre() {
        switch (this.genre) {
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

    protected boolean setGenre(String input) {
        GenreType type = this.isValidGenre(input);

        if (type == null)
            return false;

        switch (type) {
            case ACTION:
                this.genre = GenreType.ACTION;
                return true;
            case HORROR:
                this.genre = GenreType.HORROR;
                return true;
            case DRAMA:
                this.genre = GenreType.DRAMA;
                return true;
            case COMEDY:
                this.genre = GenreType.COMEDY;
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
