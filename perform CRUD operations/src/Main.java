import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryDataAccessObject libraryDAO = new LibraryDataAccessObject();

        // Input info for DB by Scanner
        Scanner scan = new Scanner(System.in);
        System.out.println("Input TITLE, AUTHOR, GENRE, PUBLICATION_YEAR by spaces:");
        String input1 = scan.nextLine();
        // Splitting elements
        String[] dbArguments = input1.split(" ");

        // adding entered element to db and Parsing PUBLICATION_YEAR to int
        libraryDAO.addBook(dbArguments[0], dbArguments[1], dbArguments[2], Integer.parseInt(dbArguments[3]));

        // adding books by addBook function from LibraryDataAccessObject class
        libraryDAO.addBook("To Kill a Mockingbird", "Harper Lee", "Fiction", 1960);
        libraryDAO.addBook("1984", "George Orwell", "Science Fiction", 1949);
        libraryDAO.addBook("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 1925);
        libraryDAO.addBook("The Catcher in the Rye", "J.D. Salinger", "Fiction", 1951);

        // Getting all books as a List
        List<Book> booksList = libraryDAO.getBooks();
        System.out.println("All books: " + booksList);

        // Updating one book (To Kill a Mockingbird was published in 1962)
        libraryDAO.updateBook(1, "To Kill a Mockingbird", "Harper Lee", "Fiction", 1962);

        // Printing updated info
        booksList = libraryDAO.getBooks();
        System.out.println("Updated books: " + booksList);

        System.out.println("BOOK PUBLICATION YEAR for 0th element: " + booksList.get(0).getPublicationYear() + " TITLE: " + booksList.get(0).getTitle());

        // Deleting books published before 1950
        for (int i = 0; i < booksList.size(); i++) {
                if (booksList.get(i).getPublicationYear() < 1950) {
                System.out.println("Deleted book: " + booksList.get(i) + ". YES THIS BOOK WAS PUBLISHED BEFORE 1950!!! :) ");
                libraryDAO.deleteBook(booksList.get(i).getId());
            }
        }



        // Final list with remaining books
        booksList = libraryDAO.getBooks();
        System.out.println("Final books: " + booksList);

        // Closing connection with DB for security, preventing connection leaks, and freeing up resources
        //libraryDAO.closeConnection();
    }
}

