package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import transferObject.PoemsTO;
import org.apache.logging.log4j.*;

public class UtilDAO {

    private Connection connection = null;
    private static final Logger logger=LogManager.getLogger(UtilDAO.class);

    public UtilDAO() {
        connection = DbConnection.getConnection();
    }

    public int getBookId(String bookName) {
        try {
            String query = "SELECT Book_id FROM books WHERE Book_Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookName);
            ResultSet resultSet = preparedStatement.executeQuery();
            int bookId = 0;
            while (resultSet.next()) {
                bookId = resultSet.getInt("Book_id");
            }
            if (bookId > 0) {
            	logger.info("book id returned ! ");
                return bookId;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("book id -1 returned because there exist no book of this name");
        return -1;
    }

    public int getVerseId(PoemsTO objPoemTo) {
        try {
            String query = "SELECT Verse_id FROM verses WHERE Poem_id IN (SELECT Poem_id FROM `poems` WHERE Poem_Name = ? AND Book_id = ?)"
                    + " AND Misra_1 = ? AND Misra_2 = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, objPoemTo.getPoemTitle());
            preparedStatement.setInt(2, getBookId(objPoemTo.getBookTitle()));
            preparedStatement.setString(3, objPoemTo.getMisra1());
            preparedStatement.setString(4, objPoemTo.getMisra2());
            ResultSet resultSet = preparedStatement.executeQuery();
            int verseId = 0;
            while (resultSet.next()) {
                verseId = resultSet.getInt("Verse_id");
            }
            if (verseId > 0) {
                logger.info("verse id returned !");
                return verseId;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("verse id -1 returned because there no verse of this misras");
        return -1;
    }

    public int getTokenId(String token) {
        try {
            String query = "SELECT Token_id FROM tokens WHERE  Token = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            int tokenId = 0;
            while (resultSet.next()) {
                tokenId = resultSet.getInt("Token_id");
            }
            if(tokenId > 0){
                logger.info("token id returned !");
            return tokenId;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("token id -1 returned because there exist no token of this name");
        return -1;
    }
    
    public int getTagId(String tag) {
        try {
            String query = "SELECT Tag_id FROM tags WHERE Tag = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tag);
            ResultSet resultSet = preparedStatement.executeQuery();
            int tagId = 0;
            while (resultSet.next()) {
                tagId = resultSet.getInt("Tag_id");
            }
            if(tagId > 0){
            	logger.info("Tag id of respective tag returned ");
                return tagId;
            }
        } catch (SQLException e) {
        	logger.error(e.getMessage());
        }
        logger.debug("tag id -1 returned because there exist no tag of this name");
        return -1;
    }
    
    public int getRootId(String root) {
        try {
            String query = "SELECT Root_id FROM roots WHERE Root = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, root);
            ResultSet resultSet = preparedStatement.executeQuery();
            int rootId = 0;
            while (resultSet.next()) {
                rootId = resultSet.getInt("Root_id");
            }
            if(rootId > 0){
            	logger.info("root id of respective root returned ");
                return rootId;
            }
        } catch (SQLException e) {
        	logger.error(e.getMessage());
        }
        logger.debug("root id -1 returned because there exist no root of this name");
        return -1;
    }
}
