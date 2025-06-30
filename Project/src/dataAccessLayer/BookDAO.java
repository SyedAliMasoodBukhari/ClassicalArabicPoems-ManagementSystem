
package dataAccessLayer;

import java.sql.Connection;
import org.apache.logging.log4j.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import transferObject.BookTO;

public class BookDAO implements IBookDAO {

    private Connection connection = null;
    private UtilDAO utilDao = null;
    private static final Logger logger = LogManager.getLogger(BookDAO.class);

    public BookDAO() {
        connection = DbConnection.getConnection();
        utilDao = new UtilDAO();
    }

    @Override
    public boolean insertBookDAO(BookTO bookTO) {
        try {
            String sql = "INSERT INTO books (Book_Name,Book_Author) VALUES ( ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookTO.getBookName());
            preparedStatement.setString(2, bookTO.getBookAuthor());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Book: " + bookTO.getBookName() + " inserted!");
                return true;
            }
        } catch (SQLException e2) {
            logger.error(e2.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateBookDAO(String currBookName, String newBookName, String newBookAuthor) {
        try {
            String updateQuery = "UPDATE Books SET Book_Name = ?, Book_Author = ? WHERE Book_Name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                preparedStatement.setString(1, newBookName);
                preparedStatement.setString(2, newBookAuthor);
                preparedStatement.setString(3, currBookName);
                int rowsupdated = preparedStatement.executeUpdate();
                if (rowsupdated > 0) {
                    logger.info("Book: " + currBookName + " updated!");
                    return true;
                }
            }
        } catch (SQLException e1) {
            logger.error(e1.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteBookDAO(String bookName) {
        try {
            connection.setAutoCommit(false);
            if (deleteBookAndMetadata(bookName)) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                logger.error(e.getMessage());
            }
            logger.error(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return false;
    }

    private boolean deleteBookAndMetadata(String bookName) {
        try {
            String query = "SELECT Poem_id FROM `poems` WHERE Book_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, utilDao.getBookId(bookName));
            ResultSet resultSet = preparedStatement.executeQuery();
            int poemId;
            while (resultSet.next()) {
                poemId = resultSet.getInt("Poem_id");
                query = "SELECT * FROM `verses` WHERE Poem_id = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, poemId);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                if (resultSet1.next()) {
                    query = "DELETE FROM verses WHERE Poem_id = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, poemId);
                    preparedStatement.executeUpdate();
                }
            }
            int rowsupdated;
            query = "SELECT * FROM poems WHERE Book_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, utilDao.getBookId(bookName));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            if (resultSet1.next()) {
                query = "DELETE FROM poems WHERE Book_id = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, utilDao.getBookId(bookName));
                preparedStatement.executeUpdate();
            }
            query = "DELETE FROM books WHERE Book_Name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookName);
            rowsupdated = preparedStatement.executeUpdate();
            if (rowsupdated > 0) {
                logger.info("Book: " + bookName + " deleted along its metadeta.");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private int getCountOfPoemsInABook(String bookName) {
        try {
            String selectQuery = "SELECT COUNT(Poem_Name) AS PoemCount FROM poems WHERE Book_id = (SELECT Book_id FROM books WHERE Book_Name = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, bookName);
            ResultSet resultSet = preparedStatement.executeQuery();
            int poemCount = 0;
            while (resultSet.next()) {
                poemCount = resultSet.getInt("PoemCount");
            }
            logger.info("Book poem count returned !");
            return poemCount;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return -1;
    }

    @Override
    public ArrayList<BookTO> getAllBooksDAO() {
        ArrayList<BookTO> books = new ArrayList<>();
        try {
            String selectQuery = "SELECT Book_Name,Book_Author FROM books";
            BookTO objBookTO;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String bookName = resultSet.getString("Book_Name");
                String bookAuthor = resultSet.getString("Book_Author");
                objBookTO = new BookTO(bookName, bookAuthor, getCountOfPoemsInABook(bookName));
                books.add(objBookTO);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("All Books returned.");
        return books;
    }
}
