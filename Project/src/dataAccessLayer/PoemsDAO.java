package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.logging.log4j.*;

import transferObject.PoemsTO;

public class PoemsDAO implements IPoemsDAO {

    private Connection connection = null;
    private UtilDAO utilDao = null;
    private static final Logger logger = LogManager.getLogger(PoemsDAO.class);

    public PoemsDAO() {
        connection = DbConnection.getConnection();
        utilDao = new UtilDAO();
    }

    // to just add new poem name in DB
    // made by Malaika Tariq 21f-9408
    @Override
    public boolean addPoemDAO(String bookTitle, String poemTitle) {
        try {
            // book id against book name to make it foreign key in Poems table
            String insertQuery = "INSERT INTO poems (Book_id,Poem_Name) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, utilDao.getBookId(bookTitle));
            preparedStatement.setString(2, poemTitle);
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                logger.info("Poem: " + poemTitle + " added!");
                return true;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    // to add a single verse
    @Override
    public boolean insertVerseDAO(PoemsTO transferObj) {
        try {
            String query = "SELECT Poem_id FROM poems WHERE Book_id = ? AND Poem_Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, utilDao.getBookId(transferObj.getBookTitle()));
            preparedStatement.setString(2, transferObj.getPoemTitle());
            ResultSet resultSet = preparedStatement.executeQuery();
            int poemId = 0;
            while (resultSet.next()) {
                poemId = resultSet.getInt("Poem_id");
            }
            query = "INSERT INTO verses (Poem_id,Misra_1,Misra1WOAraab,Misra_2,Misra2WOAraab)"
                    + " VALUES (?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, poemId);
            preparedStatement.setString(2, transferObj.getMisra1());
            preparedStatement.setString(3, transferObj.getMisra1WithoutAraab());
            preparedStatement.setString(4, transferObj.getMisra2());
            preparedStatement.setString(5, transferObj.getMisra2WithoutAraab());
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                logger.info("Verse inserted!");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<String> getAllPoemsDAO() {
        ArrayList<String> result = new ArrayList<>();
        try {
            String query = "SELECT Poem_Name FROM poems";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("Poem_Name"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("All Poems name returned");
        return result;
    }

    @Override
    public ArrayList<String> getAllPoemsByBookDAO(String bookName) {
        ArrayList<String> result = new ArrayList<>();
        try {
            String query = "SELECT Poem_Name FROM poems WHERE Book_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, utilDao.getBookId(bookName));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("Poem_Name"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("All Poems of a book returned successfully!");
        return result;
    }

    @Override
    public ArrayList<PoemsTO> getAPoemByBookDAO(String bookName, String poemName, int poemNum) {
        ArrayList<PoemsTO> poem = new ArrayList<>();
        try {
            String query = "SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ? ORDER BY Poem_Name LIMIT ?, 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, poemName);
            preparedStatement.setInt(2, utilDao.getBookId(bookName));
            preparedStatement.setInt(3, (poemNum - 1));
            ResultSet resultSet = preparedStatement.executeQuery();
            int poemId = 0;
            while (resultSet.next()) {
                poemId = resultSet.getInt("Poem_id");
            }
            query = "SELECT Misra_1, Misra_2 FROM `verses` WHERE Poem_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, poemId);
            resultSet = preparedStatement.executeQuery();

            String misra1 = null;
            String misra2 = null;
            while (resultSet.next()) {
                misra1 = resultSet.getString("Misra_1");
                misra2 = resultSet.getString("Misra_2");

                poem.add(new PoemsTO(null, poemName, misra1, misra2, null, null));
            }
            if ((misra1 == null) && (misra2 == null)) {
                poem.add(new PoemsTO(null, poemName, null, null, null, null));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Verses against poem:" + poemName + " returned successfully ");
        return poem;
    }

    @Override
    public ArrayList<PoemsTO> getAPoemByVerseDAO(PoemsTO poemTo) {
        ArrayList<PoemsTO> poem = new ArrayList<>();
        try {
            String query = "SELECT Poem_id FROM `verses` WHERE Misra_1 = ? AND Misra_2 = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, poemTo.getMisra1());
            preparedStatement.setString(2, poemTo.getMisra2());
            ResultSet resultSet = preparedStatement.executeQuery();
            int poemId = 0;
            while (resultSet.next()) {
                poemId = resultSet.getInt("Poem_id");
            }
            query = "SELECT Misra_1, Misra_2 FROM `verses` WHERE Poem_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, poemId);
            resultSet = preparedStatement.executeQuery();

            String misra1 = null;
            String misra2 = null;
            while (resultSet.next()) {
                misra1 = resultSet.getString("Misra_1");
                misra2 = resultSet.getString("Misra_2");

                poem.add(new PoemsTO(null, poemTo.getPoemTitle(), misra1, misra2, "", ""));
            }
            if ((misra1 == null) && (misra2 == null)) {
                poem.add(new PoemsTO(null, poemTo.getPoemTitle(), null, null, "", ""));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Verses against poem:" + poemTo.getPoemTitle() + " returned successfully ");
        return poem;
    }

    @Override
    public boolean updatePoemTitleDAO(String bookName, String prevTitle, String newTitle, int poemNum) {
        try {
            String query = "UPDATE poems SET Poem_Name = ? WHERE Poem_id = "
                    + "(SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ? ORDER BY Poem_Name LIMIT ?, 1);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, prevTitle);
            preparedStatement.setInt(3, utilDao.getBookId(bookName));
            preparedStatement.setInt(4, (poemNum - 1));
            int rowsupdated = preparedStatement.executeUpdate();
            if (rowsupdated > 0) {
                logger.info("Poem title: " + prevTitle + " updated.");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updatePoemVerseDAO(PoemsTO prevPoem, PoemsTO newPoem) {
        try {
            String query = "SELECT Verse_id FROM verses WHERE Poem_id IN (SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ?)"
                    + " AND Misra_1 = ? AND Misra_2 = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prevPoem.getPoemTitle());
            preparedStatement.setInt(2, utilDao.getBookId(prevPoem.getBookTitle()));
            preparedStatement.setString(3, prevPoem.getMisra1());
            preparedStatement.setString(4, prevPoem.getMisra2());
            ResultSet resultSet = preparedStatement.executeQuery();
            int verseId = 0;
            while (resultSet.next()) {
                verseId = resultSet.getInt("Verse_id");
            }
            query = "UPDATE verses SET Misra_1 = ?, Misra_2 = ? WHERE Verse_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPoem.getMisra1());
            preparedStatement.setString(2, newPoem.getMisra2());
            preparedStatement.setInt(3, verseId);
            int rowsupdated = preparedStatement.executeUpdate();
            if (rowsupdated > 0) {
                logger.info("verse updated.");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deletePoemDAO(String bookName, String poemName, int poemNum) {
        try {
            connection.setAutoCommit(false);
            if (deletePoemAndMetadata(bookName, poemName, poemNum)) {
                connection.commit();
                logger.info("Poem metadata deleted");
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

    private boolean deletePoemAndMetadata(String bookName, String poemName, int poemNum) {
        try {
            String query = "SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ? ORDER BY Poem_Name LIMIT ?, 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, poemName);
            preparedStatement.setInt(2, utilDao.getBookId(bookName));
            preparedStatement.setInt(3, (poemNum - 1));
            ResultSet resultSet = preparedStatement.executeQuery();
            int poemId = 0;
            while (resultSet.next()) {
                poemId = resultSet.getInt("Poem_id");
            }
            query = "SELECT * FROM `verses` WHERE Poem_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, poemId);
            resultSet = preparedStatement.executeQuery();
            int rowsupdated;
            if (resultSet.next()) {
                query = "DELETE FROM verses WHERE Poem_id = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, poemId);
                preparedStatement.executeUpdate();
            }
            query = "DELETE FROM poems WHERE Poem_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, poemId);
            rowsupdated = preparedStatement.executeUpdate();
            if (rowsupdated > 0) {
                logger.info("poem and its metadata deleted ");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteVerseOfAPoemDAO(String bookName, String poemName, String[] verse) {
        try {
            String query = "SELECT Verse_id FROM verses WHERE Poem_id IN (SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ?)"
                    + " AND Misra_1 = ? AND Misra_2 = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, poemName);
            preparedStatement.setInt(2, utilDao.getBookId(bookName));
            preparedStatement.setString(3, verse[0]);
            preparedStatement.setString(4, verse[1]);
            ResultSet resultSet = preparedStatement.executeQuery();
            int verseId = 0;
            while (resultSet.next()) {
                verseId = resultSet.getInt("Verse_id");
            }
            query = "DELETE FROM verses WHERE Verse_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, verseId);
            int rowsupdated = preparedStatement.executeUpdate();
            if (rowsupdated > 0) {
                logger.info("verse deleted.");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addVerseDAO(PoemsTO transferObj, int poemNum) {
        int poemId = 0;
        try {
            String query = "SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ? ORDER BY Poem_Name LIMIT ?, 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, transferObj.getPoemTitle());
            preparedStatement.setInt(2, utilDao.getBookId(transferObj.getBookTitle()));
            preparedStatement.setInt(3, (poemNum - 1));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poemId = resultSet.getInt("Poem_id");
            }
            String insertQuery = "INSERT INTO verses (Poem_id,Misra_1,Misra1WOAraab,Misra_2,Misra2WOAraab)"
                    + " VALUES (?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, poemId);
            preparedStatement.setString(2, transferObj.getMisra1());
            preparedStatement.setString(3, transferObj.getMisra1WithoutAraab());
            preparedStatement.setString(4, transferObj.getMisra2());
            preparedStatement.setString(5, transferObj.getMisra2WithoutAraab());
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                logger.info("verse added.");
                return true;
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return false;
    }
}
