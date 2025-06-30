package dataAccessLayer;

import java.util.ArrayList;
import transferObject.PoemsTO;

public interface IPoemsDAO {

    public boolean addPoemDAO(String bookTitle, String poemTitle);

    public ArrayList<String> getAllPoemsDAO();

    public ArrayList<String> getAllPoemsByBookDAO(String bookName);

    public ArrayList<PoemsTO> getAPoemByBookDAO(String bookName, String poemName, int poemNum);

    public boolean updatePoemTitleDAO(String bookName, String prevTitle, String newTitle, int poemNum);

    public boolean updatePoemVerseDAO(PoemsTO prevPoem, PoemsTO newPoem);

    public boolean deletePoemDAO(String bookName, String poemName, int poemNum);

    public boolean deleteVerseOfAPoemDAO(String bookName, String poemName, String[] verse);

    public boolean addVerseDAO(PoemsTO transferObj, int poemNum);

    public boolean insertVerseDAO(PoemsTO transferObj);

    public ArrayList<PoemsTO> getAPoemByVerseDAO(PoemsTO poemTo);
}
