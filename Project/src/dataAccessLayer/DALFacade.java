
package dataAccessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import transferObject.BookTO;
import transferObject.PoemsTO;
import transferObject.RootTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;

public class DALFacade implements IDALFacade {

    private final IPoemsDAO poemsDao;
    private final IBookDAO DAO_Obj;
    private final IRootDAO rootDao;
    private final ITokenDAO tokenDao;

    public DALFacade(IPoemsDAO poemsDao, IBookDAO DAO_Obj, IRootDAO rootDao, ITokenDAO tokenDao) {
        this.poemsDao = poemsDao;
        this.DAO_Obj = DAO_Obj;
        this.rootDao = rootDao;
        this.tokenDao = tokenDao;
    }

    @Override
    public boolean addPoemDAO(String bookTitle, String poemTitle) {
        return poemsDao.addPoemDAO(bookTitle, poemTitle);
    }

    @Override
    public boolean addVerseDAO(PoemsTO transferObj,int poemNum) {
     return poemsDao.addVerseDAO(transferObj, poemNum);
    }

    @Override
    public boolean insertBookDAO(BookTO bookTO) {
        return DAO_Obj.insertBookDAO(bookTO);
    }

    @Override
    public boolean updateBookDAO(String currBookName, String newBookName, String newBookAuthor) {
        return DAO_Obj.updateBookDAO(currBookName, newBookName, newBookAuthor);
    }

    @Override
    public boolean deleteBookDAO(String Name) {
        return DAO_Obj.deleteBookDAO(Name);
    }

    @Override
    public ArrayList<BookTO> getAllBooksDAO() {
        return DAO_Obj.getAllBooksDAO();
    }

    @Override
    public ArrayList<String> getAllPoemsDAO() {
        return poemsDao.getAllPoemsDAO();
    }

    @Override
    public ArrayList<String> getAllPoemsByBookDAO(String bookName) {
        return poemsDao.getAllPoemsByBookDAO(bookName);
    }

    @Override
    public ArrayList<PoemsTO> getAPoemByBookDAO(String bookName, String poemName, int poemNum) {
        return poemsDao.getAPoemByBookDAO(bookName, poemName, poemNum);
    }
    
    @Override
    public ArrayList<PoemsTO> getAPoemByVerseDAO(PoemsTO poemTo){
        return poemsDao.getAPoemByVerseDAO(poemTo);
    }

    @Override
    public boolean updatePoemTitleDAO(String bookName, String prevTitle, String newTitle, int poemNum) {
        return poemsDao.updatePoemTitleDAO(bookName, prevTitle, newTitle, poemNum);
    }

    @Override
    public boolean updatePoemVerseDAO(PoemsTO prevPoem, PoemsTO newPoem) {
        return poemsDao.updatePoemVerseDAO(prevPoem, newPoem);
    }

    @Override
    public boolean deletePoemDAO(String bookName, String poemName, int poemNum) {
        return poemsDao.deletePoemDAO(bookName, poemName, poemNum);
    }

    @Override
    public boolean deleteVerseOfAPoemDAO(String bookName, String poemName, String[] verse) {
        return poemsDao.deleteVerseOfAPoemDAO(bookName, poemName, verse);
    }

    @Override
    public boolean insertTokens(PoemsTO objPoemTo, String[] token) {
        return tokenDao.insertTokens(objPoemTo, token);
    }

    @Override
    public boolean insertTags(String token, ArrayList<String> tags) {
        return tokenDao.insertTags(token, tags);
    }

    @Override
    public boolean deleteTokens(PoemsTO objTO) {
        return tokenDao.deleteTokens(objTO);
    }

    @Override
    public ArrayList<TokenTO> getAllTokens() {
        return tokenDao.getAllTokens();
    }

    @Override
    public ArrayList<TokenTO> getSpecificVerseTokens(PoemsTO obj) {
        return tokenDao.getSpecificVerseTokens(obj);
    }

    @Override
    public ArrayList<TokenTagsTO> getTagsOfAToken(String token) {
        return tokenDao.getTagsOfAToken(token);
    }

    @Override
    public boolean insertVerseDAO(PoemsTO transferObj) {
        return poemsDao.insertVerseDAO(transferObj);
    }
    @Override
    public boolean insertRootManuallyDAO(RootTO rootObj, PoemsTO obj) {
       return  rootDao.insertRootManuallyDAO(rootObj, obj);
    }

    @Override
    public boolean insertRootByTokenDAO(RootTO insertRootObj,PoemsTO poemTo, String token) {
      return rootDao.insertRootByTokenDAO(insertRootObj, poemTo, token);
    }

    @Override
    public HashMap<RootTO, Integer> getAllRoots() {
        return rootDao.getAllRoots();
       }

    @Override
    public ArrayList<PoemsTO> getSpecificRootVerse(String root) {
        return rootDao.getSpecificRootVerse(root);
    }

    @Override
    public boolean deleteRoots(PoemsTO obj) {
     return rootDao.deleteRoots(obj);
    }

	@Override
	public boolean deleteEmptyRoots() {
		return rootDao.deleteEmptyRoots();
	}
}
