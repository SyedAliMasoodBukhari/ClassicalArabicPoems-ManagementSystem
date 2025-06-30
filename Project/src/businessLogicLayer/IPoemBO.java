package businessLogicLayer;

import java.util.ArrayList;
import transferObject.PoemsTO;

public interface IPoemBO {

    public boolean addNewPoemBO(PoemsTO transferObj);

    public boolean addNewVerseBO(PoemsTO transferObj, int poemNum);

    public boolean importFileBO(String bookTitle, String filepath);

    public ArrayList<String> getAllPoemsBO();

    public ArrayList<String> getAllPoemsByBookBO(String bookName);

    public ArrayList<PoemsTO> getAPoemByBookBO(String bookName, String poemName, int poemNum);

    public ArrayList<PoemsTO> getAPoemByVerseBO(PoemsTO poemTo);

    public boolean updatePoemsTitleBO(String bookName, String prevTitle, String newTitle, int poemNum);

    public boolean updatePoemVerseBO(PoemsTO prevPoem, PoemsTO newPoem);

    public boolean deletePoemBO(String bookName, String poemName, int poemNum);

    public boolean deleteVerseOfAPoemBO(String bookName, String poemName, String[] verse);
}
