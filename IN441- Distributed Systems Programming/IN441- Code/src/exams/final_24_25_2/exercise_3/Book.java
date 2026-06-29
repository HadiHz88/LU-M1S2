package exams.final_24_25_2.exercise_3;

class Book {
    private final String title;
    private final String author;
    private final int year;
    private String borrowedBy;

    Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    String title() {
        return title;
    }

    String borrowedBy() {
        return borrowedBy;
    }

    void borrow(String username) {
        borrowedBy = username;
    }

    void returnBook() {
        borrowedBy = null;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + year + ")";
    }
}
