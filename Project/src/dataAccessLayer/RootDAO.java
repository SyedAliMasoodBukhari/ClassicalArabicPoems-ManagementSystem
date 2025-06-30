package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.*;

import transferObject.PoemsTO;
import transferObject.RootTO;

public class RootDAO implements IRootDAO {

    private Connection connection = null;
    private UtilDAO utilDao = null;
    private static final Logger logger = LogManager.getLogger(RootDAO.class);

    public RootDAO() {
        connection = DbConnection.getConnection();
        utilDao = new UtilDAO();
    }

    // Inserting Root
    @Override
    public boolean deleteEmptyRoots() {
        HashMap<RootTO, Integer> roots = getAllRoots();
        for (Map.Entry<RootTO, Integer> m : roots.entrySet()) {
            if (m.getValue() == 0) {
                int rootId = utilDao.getRootId(m.getKey().getRootName());
                try {
                    String query = "DELETE FROM roots WHERE Root_id = " + rootId;
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean insertRootByTokenDAO(RootTO insertRootObj, PoemsTO poemTo, String token) {
        int tokenId = utilDao.getTokenId(token);
        int rootId = utilDao.getRootId(insertRootObj.getRootName());
        int verseId = utilDao.getVerseId(poemTo);
        PreparedStatement preparedStatement;
        try {
            if (rootId == -1) {
                String insertQuery = "INSERT INTO roots (Root,Status) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, insertRootObj.getRootName());
                preparedStatement.setString(2, "Automatic");
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    rootId = utilDao.getRootId(insertRootObj.getRootName());
                } else {
                    return false;
                }
            }
            int rowCount = 0;
            String checkQuery = "SELECT * FROM roottoken WHERE Root_id=? AND Token_id =?";
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setInt(1, rootId);
            preparedStatement.setInt(2, tokenId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rowCount++;
            }
            if (rowCount == 0) {
                String insertQuery = "INSERT INTO roottoken (Root_id,Token_id) VALUES (" + rootId + "," + tokenId + ")";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.executeUpdate();
            }
            rowCount = 0;
            checkQuery = "SELECT * FROM rootverse WHERE Root_id=? AND Verse_id =?";
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setInt(1, rootId);
            preparedStatement.setInt(2, verseId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rowCount++;
            }
            if (rowCount == 0) {
                String insertQuery = "INSERT INTO rootverse (Root_id,Verse_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, rootId);
                preparedStatement.setInt(2, verseId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.getMessage();
            return false;
        }
        logger.info("root inserted by token ");
        return true;
    }

    @Override
    public boolean insertRootManuallyDAO(RootTO rootObj, PoemsTO obj) {
        try {
            Integer verseId = utilDao.getVerseId(obj);
            PreparedStatement preparedStatement;
            Integer rootId = utilDao.getRootId(rootObj.getRootName());
            if (rootId == -1) {
                String insertQuery = "INSERT INTO roots (Root,Status) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, rootObj.getRootName());
                preparedStatement.setString(2, "Verified");
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    rootId = utilDao.getRootId(rootObj.getRootName());
                } else {
                    logger.info("insertion failed !");
                    return false;
                }

            } else {
                String updateQuery = "UPDATE roots SET Status = ? WHERE Root_id = ?";
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, "Verified");
                preparedStatement.setInt(2, rootId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated <= 0) {
                    logger.info("updation failed !");
                    return false;
                }
            }
            int rowCount = 0;
            // if same root is assigned to the same verse
            String checkQuery3 = "SELECT * FROM rootverse WHERE Root_id=? AND Verse_id =?";
            preparedStatement = connection.prepareStatement(checkQuery3);
            preparedStatement.setInt(1, rootId);
            preparedStatement.setInt(2, verseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rowCount++;
            }
            if (rowCount == 0) {
                String insertQuery2 = "INSERT INTO rootverse (Root_id,Verse_id) VALUES (" + rootId + "," + verseId + ")";
                preparedStatement = connection.prepareStatement(insertQuery2);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated < 1) {
                    logger.info("insertion failed !");
                    return false;
                }
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        logger.info("root manually inserted ");
        return true;
    }

    private int getVerseCountByRoot(String root) {
        int rootId = utilDao.getRootId(root);
        int count = 0;
        String query = "SELECT COUNT(Verse_id) AS VerseCount FROM rootverse WHERE Root_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rootId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("VerseCount");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return count;
    }

    @Override
    public HashMap<RootTO, Integer> getAllRoots() {
        HashMap<RootTO, Integer> roots = new HashMap<>();

        String selectQuery = "SELECT Root,Status FROM roots";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String root = resultSet.getString("Root");
                String status = resultSet.getString("Status");
                int count = getVerseCountByRoot(root);
                roots.put(new RootTO(root, status), count);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        logger.info("all roots returned ");
        return roots;
    }

    @Override
    public ArrayList<PoemsTO> getSpecificRootVerse(String root) {
        ArrayList<PoemsTO> poems = new ArrayList<>();

        try {
            String selectQuery2 = "SELECT b.Book_Name,p.Poem_Name,v.Misra_1,v.Misra_2 FROM verses v JOIN poems p ON p.Poem_id=v.Poem_id JOIN books b on b.Book_id=p.Book_id WHERE Verse_id IN(SELECT Verse_id FROM rootverse WHERE Root_id = (SELECT Root_id FROM roots WHERE Root=?));";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery2);
            preparedStatement.setString(1, root);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String bookName = resultSet.getString("Book_Name");
                String poemName = resultSet.getString("Poem_Name");
                String misra1 = resultSet.getString("Misra_1");
                String misra2 = resultSet.getString("Misra_2");
                poems.add(new PoemsTO(bookName, poemName, misra1, misra2, "", ""));

            }

        } catch (SQLException ex) {
            	logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
        }

        return poems;
    }

    @Override
    public boolean deleteRoots(PoemsTO obj) {
        int verseId = utilDao.getVerseId(obj);
        // getting manually added roots against verse
        String selectQuery = "SELECT Root_id FROM roots WHERE Root_id IN (SELECT Root_id FROM rootverse WHERE Verse_id = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, verseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int rootId = resultSet.getInt("Root_id");
                //deleting the relation of manually added roots along a verse
                String deleteQuery = "DELETE FROM rootverse WHERE Root_id=? AND Verse_id=?";
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, rootId);
                preparedStatement.setInt(2, verseId);
                int rowAffected = preparedStatement.executeUpdate();
                if (rowAffected <= 0) {
                    return false;
                }
                // getting all tokens against that verse
                String checkQuery = "SELECT Token_id from tokenverse WHERE Verse_id = ? ";
                preparedStatement = connection.prepareStatement(checkQuery);
                preparedStatement.setInt(1, verseId);
                ResultSet resultSet2 = preparedStatement.executeQuery();
                while (resultSet2.next()) {
                    int tokenId = resultSet2.getInt("Token_id");
                    // checking whether this token is assigned to another verse or not
                    int verseCount = 0;
                    String checkQuery2 = "SELECT Verse_id FROM tokenverse WHERE Token_id = " + tokenId;
                    preparedStatement = connection.prepareStatement(checkQuery2);
                    ResultSet resultSet3 = preparedStatement.executeQuery();
                    while (resultSet3.next()) {
                        verseCount++; //
                    }
                    if (verseCount == 1) {
                        String deleteQuery2 = "DELETE FROM roottoken WHERE Root_id=? AND Token_id = ?";
                        preparedStatement = connection.prepareStatement(deleteQuery2);
                        preparedStatement.setInt(1, rootId);
                        preparedStatement.setInt(2, tokenId);

                        int rowAffected2 = preparedStatement.executeUpdate();
                        if (rowAffected2 <= 0) {
                            return false;
                        }
                    }

                }
                int tokenCount = 0;
                int verseCount = 0;

                String checkQuery2 = "SELECT Token_id FROM roottoken WHERE Root_id = " + rootId;
                preparedStatement = connection.prepareStatement(checkQuery2);
                ResultSet resultSet3 = preparedStatement.executeQuery();

                while (resultSet3.next()) {
                    tokenCount++;
                }
                String checkQuery3 = "SELECT Verse_id FROM rootverse WHERE Root_id = " + rootId;
                preparedStatement = connection.prepareStatement(checkQuery3);

                ResultSet resultSet4 = preparedStatement.executeQuery();
                while (resultSet4.next()) {
                    verseCount++;
                }

                if (verseCount == 0 && tokenCount == 0) {
                    String deleteQuery3 = "DELETE FROM roots WHERE Root_id = " + rootId;
                    preparedStatement = connection.prepareStatement(deleteQuery3);

                    int rowAffected2 = preparedStatement.executeUpdate();
                    if (rowAffected2 <= 0) {
                        return false;
                    }
                }
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
          logger.info("deleted root successfully  !");
        return true;
    }

}
