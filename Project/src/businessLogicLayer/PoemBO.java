package businessLogicLayer;

import dataAccessLayer.IDALFacade;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import transferObject.PoemsTO;

public class PoemBO implements IPoemBO {

    IDALFacade objDAL;

    public PoemBO(IDALFacade objDAL) {
        this.objDAL = objDAL;
    }

    @Override
    public boolean addNewPoemBO(PoemsTO transferObj) {
        if (objDAL.addPoemDAO(transferObj.getBookTitle(), transferObj.getPoemTitle())) {
            transferObj.setMisra1WithoutAraab(removeAraab(transferObj.getMisra1()));
            transferObj.setMisra2WithoutAraab(removeAraab(transferObj.getMisra2()));
            int totalPoems = objDAL.getAllPoemsByBookDAO(transferObj.getBookTitle()).size();
            // i did this count thing so that when we already have a poem with same name, then it ensures that 
            // it insert verse in the last added poem of that name.
            if(transferObj.getMisra1().equals("")&&transferObj.getMisra2().equals("")){
                return true;
            }
            if (addNewVerseBO(transferObj, totalPoems)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addNewVerseBO(PoemsTO transferObj,int poemNum) {
        transferObj.setMisra1WithoutAraab(removeAraab(transferObj.getMisra1()));
        transferObj.setMisra2WithoutAraab(removeAraab(transferObj.getMisra2()));
        int c  = getSameTitledPoemNum(transferObj.getBookTitle(), transferObj.getPoemTitle(), poemNum);
        return objDAL.addVerseDAO( transferObj,c);
    }

    // made by Malaika Tariq 21f-9408
    private String removeAraab(String misra) {
        return misra.replaceAll("[\\u064B-\\u0652\\u0670\\u0671]", "");
    }

    @Override
    public boolean importFileBO(String bookTitle, String filepath) {
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(filepath));

            ImportPoemState stateObj;
            String line;
            if (bookTitle == null) {
                stateObj = new BookTitleState();
                while ((line = file.readLine()) != null) {

                    stateObj = stateObj.processLine(objDAL, line);
                    if (stateObj instanceof StopState) {
                        return false;
                    }
                }
                return true;
            } else {
                stateObj = new PoemState(bookTitle, null);
                while ((line = file.readLine()) != null) {

                    stateObj = stateObj.processLine(objDAL, line);
                    if (stateObj instanceof StopState) {
                        return false;
                    }
                }
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<String> getAllPoemsBO() {
        return objDAL.getAllPoemsDAO();
    }

    @Override
    public ArrayList<String> getAllPoemsByBookBO(String bookName) {
        return objDAL.getAllPoemsByBookDAO(bookName);
    }

    private int getSameTitledPoemNum(String bookName, String poemName, int poemNum){
        ArrayList<String> result = objDAL.getAllPoemsByBookDAO(bookName);
        int c = 0;
        for (int i = 0; i < poemNum; ++i) {
            if (result.get(i).equals(poemName)) {
                c++;
            }
        }
        return c;
    }
    
    @Override
    public ArrayList<PoemsTO> getAPoemByBookBO(String bookName, String poemName, int poemNum) {
        //sameTitledPoemsCount
        int c = getSameTitledPoemNum(bookName, poemName, poemNum);
        return objDAL.getAPoemByBookDAO(bookName, poemName, c);
    }
    
    @Override
    public ArrayList<PoemsTO> getAPoemByVerseBO(PoemsTO poemTo) {
        return objDAL.getAPoemByVerseDAO(poemTo);
    }

    @Override
    public boolean updatePoemsTitleBO(String bookName, String prevTitle, String newTitle, int poemNum) {
        int c = getSameTitledPoemNum(bookName, prevTitle, poemNum);
        return objDAL.updatePoemTitleDAO(bookName, prevTitle, newTitle, c);
    }

    @Override
    public boolean updatePoemVerseBO(PoemsTO prevPoem, PoemsTO newPoem) {
        return objDAL.updatePoemVerseDAO(prevPoem, newPoem);
    }

    @Override
    public boolean deletePoemBO(String bookName, String poemName, int poemNum) {
        int c = getSameTitledPoemNum(bookName, poemName, poemNum);
        int i = 0;
        ArrayList<PoemsTO> objTOList = objDAL.getAPoemByBookDAO(bookName, poemName,c);
        for (PoemsTO o : objTOList) {
            PoemsTO objTO = new PoemsTO(bookName, poemName, o.getMisra1(), o.getMisra2(), "", "");
            if (i < objTOList.size()) {
                objDAL.deleteRoots(objTO);
                objDAL.deleteTokens(objTO);
                i++;
            }
        }

        if (i == objTOList.size()) {
            objDAL.deleteEmptyRoots();
            return objDAL.deletePoemDAO(bookName, poemName, c);
        }
        return false;
    }

    @Override
    public boolean deleteVerseOfAPoemBO(String bookName, String poemName, String[] verse) {
        PoemsTO objTO = new PoemsTO(bookName, poemName, verse[0], verse[1], "", "");
        if (objDAL.deleteTokens(objTO)) {
            if (objDAL.deleteVerseOfAPoemDAO(bookName, poemName, verse)) {
                return true;
            }
        }
        return false;
    }

  
}
