package dataAccessLayer;

import transferObject.PoemsTO;
import org.apache.logging.log4j.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;

public class TokensDAO implements ITokenDAO {

    private Connection connection = null;
    private UtilDAO utilDao = null;
    private static final Logger logger=LogManager.getLogger(TokensDAO.class);

    public TokensDAO() {
        connection = DbConnection.getConnection();
        utilDao = new UtilDAO();
    }

    @Override
    public boolean insertTokens(PoemsTO objPoemTo, String[] token) {

        int verseId = utilDao.getVerseId(objPoemTo);

        for (String token1 : token) {
            int tokenId = utilDao.getTokenId(token1);
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            try {
                if (tokenId == -1) {
                    String insertQuery = "INSERT INTO tokens (Token) VALUES (?);";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, token1);
                    int rowCovered = preparedStatement.executeUpdate();
                    if (rowCovered <= 0) {
                        return false;
                    } else {
                        tokenId = utilDao.getTokenId(token1);
                    }
                }
                int tokenId2Count = 0;
                String checkQuery = "SELECT * FROM tokenverse WHERE Verse_id = " + verseId + " AND Token_id = " + tokenId + ";";
                preparedStatement = connection.prepareStatement(checkQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    tokenId2Count++;
                }
                if (tokenId2Count == 0) {
                    String insertQuery = "INSERT INTO tokenverse (Verse_id,Token_id) VALUES (" + verseId + "," + tokenId + ");";
                    preparedStatement = connection.prepareStatement(insertQuery);

                    int rowCovered = preparedStatement.executeUpdate();
                    if (rowCovered <= 0) {
                        return false;
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        logger.info("tokens inserted successfully");
        return true;
    }

    @Override
    public boolean insertTags(String token, ArrayList<String> tags) {
        try {
            int tokenId = utilDao.getTokenId(token);
            for (String tag : tags) {
                int tagId = utilDao.getTagId(tag);
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                if (tagId == -1) {
                    String insertQuery = "INSERT INTO tags (Tag) VALUES (?);";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, tag);
                    int rowCovered = preparedStatement.executeUpdate();
                    if (rowCovered <= 0) {
                        return false;
                    } else {
                        tagId = utilDao.getTagId(tag);
                    }
                }
                int tagIdCount = 0;
                String checkQuery = "SELECT * FROM tokentags WHERE Token_id = " + tokenId + " AND Tag_id = " + tagId + ";";
                preparedStatement = connection.prepareStatement(checkQuery);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    tagIdCount++;
                }
                if (tagIdCount == 0) {
                    String insertQuery2 = "INSERT INTO tokentags (Tag_id, Token_id) VALUES (" + tagId + "," + tokenId + ");";
                    preparedStatement = connection.prepareStatement(insertQuery2);
                    int rowCovered1 = preparedStatement.executeUpdate();
                    if (rowCovered1 <= 0) {
                        return false;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        logger.info("tokens inserted successfully");
        return true;
    }

    @Override
    public ArrayList<TokenTO> getAllTokens() {

        ArrayList<TokenTO> tokens = new ArrayList<>();
        try {
            String selectQuery = "SELECT Token FROM tokens ";
            TokenTO objTokenTO;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String token = resultSet.getString("Token");
                objTokenTO = new TokenTO(token);
                tokens.add(objTokenTO);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tokens;
    }

    @Override
    public ArrayList<TokenTO> getSpecificVerseTokens(PoemsTO obj) {
        int verseID = utilDao.getVerseId(obj);
        ArrayList<TokenTO> tokens = new ArrayList<>();
        try {
            String selectQuery = "SELECT Token FROM tokens WHERE Token_id IN (SELECT Token_id FROM tokenverse WHERE Verse_id = ? );";
            TokenTO objTokenTO;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, verseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String token = resultSet.getString("Token");
                objTokenTO = new TokenTO(token);
                tokens.add(objTokenTO);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tokens;
    }

    @Override
    public ArrayList<TokenTagsTO> getTagsOfAToken(String token) {
        int tokenId = utilDao.getTokenId(token);

        ArrayList<TokenTagsTO> tokenTags = new ArrayList<>();
        try {
            String selectQuery = "SELECT Tag FROM tags WHERE Tag_id IN (SELECT Tag_id FROM tokentags WHERE Token_id = ? )";
            TokenTagsTO objTokenTagsTO;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, tokenId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tag = resultSet.getString("Tag");

                objTokenTagsTO = new TokenTagsTO(tag);
                tokenTags.add(objTokenTagsTO);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tokenTags;
    }

    @Override
    public boolean deleteTokens(PoemsTO objTO) {

        int verseId = utilDao.getVerseId(objTO);

        String selectQuery = "SELECT Token_id FROM tokenverse WHERE Verse_id=" + verseId + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer tokenIdCount = 0;
                Integer tokenId = resultSet.getInt("Token_id");

                String deleteSql2 = "DELETE FROM tokenverse WHERE Token_id=? AND Verse_id = ? ";
                preparedStatement = connection.prepareStatement(deleteSql2);
                preparedStatement.setInt(1, tokenId);
                preparedStatement.setInt(2, verseId);
                preparedStatement.executeUpdate();
                String checkQuery = "SELECT Token_id from tokenverse WHERE Token_id=?";
                preparedStatement = connection.prepareStatement(checkQuery);
                preparedStatement.setInt(1, tokenId);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    tokenIdCount++;
                }
                if (tokenIdCount == 0) {
                    deleteTokenTags(tokenId);
                    String deleteSql = "DELETE FROM tokens WHERE Token_id = ?  ";
                    preparedStatement = connection.prepareStatement(deleteSql);
                    preparedStatement.setInt(1, tokenId);
                    int rowCovered = preparedStatement.executeUpdate();
                    if (rowCovered <= 0) {
                        return false;
                    }
                }

            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }

        return true;

    }

    private boolean deleteTokenTags(int tokenId) {

        String selectSql = "SELECT Tag_id FROM tokentags WHERE Token_id=" + tokenId + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int tagIdCount = 0;
                int tagId = resultSet.getInt("Tag_id");

                String deleteSql = "DELETE FROM tokentags WHERE Token_id = ? AND Tag_id = ? ";
                preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, tokenId);
                preparedStatement.setInt(2, tagId);
                preparedStatement.executeUpdate();
                String checkQuery = "SELECT Tag_id from tokentags WHERE Tag_id=?";
                preparedStatement = connection.prepareStatement(checkQuery);
                preparedStatement.setInt(1, tagId);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    tagIdCount++;
                }
                if (tagIdCount == 0) {
                    deleteSql = "DELETE FROM tags WHERE Tag_id = ?  ";
                    preparedStatement = connection.prepareStatement(deleteSql);
                    preparedStatement.setInt(1, tagId);
                    int rowCovered = preparedStatement.executeUpdate();
                    if (rowCovered <= 0) {
                        return false;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return true;
    }
}
