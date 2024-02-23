import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryDataAccessObject {
    private Connection connection;

    // Establishing connection from DB to Java with JDBC
    public LibraryDataAccessObject() {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "1234";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Adding books to the database
    public void addBook(String title, String author, String genre, int publicationYear) {
        try {
            String query = "INSERT INTO library (title, author, genre, publication_year) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, genre);
            preparedStatement.setInt(4, publicationYear);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getting list of books
    public List<Book> getBooks() {
        // Creating list for all books
        List<Book> books = new ArrayList<>();

        try {
            String query = "SELECT * FROM library";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int publicationYear = resultSet.getInt("publication_year");
                books.add(new Book(id, title, author, genre, publicationYear));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Returning books list
        return books;
    }

    // Updating a book
    public void updateBook(int id, String title, String author, String genre, int publicationYear) {
        try {
            String query = "UPDATE library SET title=?, author=?, genre=?, publication_year=? WHERE book_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, genre);
            preparedStatement.setInt(4, publicationYear);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deleting a book
    public void deleteBook(int id) {
        try {
            String query = "DELETE FROM library WHERE book_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Closing connection
    public void closeConnection() {
        try {
            // Truncate the table and reset sequence for id (primary key)
            String query = "TRUNCATE library RESTART IDENTITY";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("CONNECTION WITH DATABASE CLOSED.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

