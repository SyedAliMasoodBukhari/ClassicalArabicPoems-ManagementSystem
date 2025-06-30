package businessLogicLayer;

import java.util.ArrayList;
import java.util.HashMap;
import transferObject.BookTO;
import transferObject.PoemsTO;
import transferObject.RootTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;

public class BLLFacade implements IBLLFacade {

    private final IBookBO objBookBO;
    private final IRootBO objRootBO;
    private final IPoemBO objPoemBO;
    private final ITokenBO objTokenBO;

    public BLLFacade(IPoemBO objPoemBO, IBookBO objBookBO, IRootBO objRootBO, ITokenBO objTokenBO) {
        this.objPoemBO = objPoemBO;
        this.objBookBO = objBookBO;
        this.objRootBO = objRootBO;
        this.objTokenBO = objTokenBO;
    }

    @Override
    public boolean addNewPoemBO(PoemsTO transferObj) {
        return objPoemBO.addNewPoemBO(transferObj);
    }

    @Override
    public boolean addNewVerseBO(PoemsTO transferObj,int poemNum)  {
        return objPoemBO.addNewVerseBO(transferObj, poemNum);
    }

    @Override
    public boolean importFileBO(String bookTitle, String filepath) {
        return objPoemBO.importFileBO(bookTitle, filepath);
    }

    @Override
    public boolean insertBookBO(BookTO bookTO) {
        return objBookBO.insertBookBO(bookTO);
    }

    @Override
    public boolean updateBookBO(String currBookName, String newBookName, String newBookAuthor) {
        return objBookBO.updateBookBO(currBookName, newBookName, newBookAuthor);
    }

    @Override
    public boolean deleteBookBO(String Name) {
        return objBookBO.deleteBookBO(Name);
    }

    @Override
    public ArrayList<BookTO> getAllBooksBO() {
        return objBookBO.getAllBooksBO();
    }

    @Override
    public ArrayList<String> getAllPoemsBO() {
        return objPoemBO.getAllPoemsBO();
    }

    @Override
    public ArrayList<String> getAllPoemsByBookBO(String bookName) {
        return objPoemBO.getAllPoemsByBookBO(bookName);
    }

    @Override
    public ArrayList<PoemsTO> getAPoemByBookBO(String bookName, String poemName, int poemNum) {
        return objPoemBO.getAPoemByBookBO(bookName, poemName, poemNum);
    }
    
    @Override
    public ArrayList<PoemsTO> getAPoemByVerseBO(PoemsTO poemTo){
        return objPoemBO.getAPoemByVerseBO(poemTo);
    }

    @Override
    public boolean updatePoemsTitleBO(String bookName, String prevTitle, String newTitle, int poemNum) {
        return objPoemBO.updatePoemsTitleBO(bookName, prevTitle, newTitle, poemNum);
    }

    @Override
    public boolean updatePoemVerseBO(PoemsTO prevPoem, PoemsTO newPoem) {
        return objPoemBO.updatePoemVerseBO(prevPoem, newPoem);
    }

    @Override
    public boolean deletePoemBO(String bookName, String poemName, int poemNum) {
        return objPoemBO.deletePoemBO(bookName, poemName, poemNum);
    }

    @Override
    public boolean deleteVerseOfAPoemBO(String bookName, String poemName, String[] verse) {
        return objPoemBO.deleteVerseOfAPoemBO(bookName, poemName, verse);
    }

    @Override
    public ArrayList<String> returnTokens(String misra1, String misra2) {
        return objTokenBO.returnTokens(misra1, misra2);
    }

    @Override
    public boolean insertTokens(PoemsTO objPoemTo, String[] token) {
        return objTokenBO.insertTokens(objPoemTo, token);
    }
    
    @Override
    public ArrayList<TokenTagsTO> getTagsOfAToken(String token){
        return objTokenBO.getTagsOfAToken(token);
    }
    
    @Override
    public ArrayList<TokenTO> getAllTokens(){
        return objTokenBO.getAllTokens();
    }

    @Override
    public HashMap<RootTO, Integer> getAllRoots() {
  return  objRootBO.getAllRoots();
    }

    @Override
    public ArrayList<PoemsTO> getVersesAgainstRoot(String root) {
      return objRootBO.getVersesAgainstRoot(root);
    }

    @Override
    public boolean insertRootsByVerse(RootTO rootObj, PoemsTO obj) {
      return objRootBO.insertRootsByVerse(rootObj, obj);
    }
    
    @Override
    public ArrayList<String> getRootsByToken(String token){
        return objTokenBO.getRootsByToken(token);
    }
}
