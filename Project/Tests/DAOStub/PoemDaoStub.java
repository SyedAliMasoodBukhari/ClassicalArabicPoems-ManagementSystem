/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOStub;

import java.util.ArrayList;

import dataAccessLayer.IPoemsDAO;
import transferObject.PoemsTO;

/**
 *
 * @author Malaika Tariq
 */
public class PoemDaoStub implements IPoemsDAO	{

	@Override
	public boolean addPoemDAO(String bookTitle, String poemTitle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getAllPoemsDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAllPoemsByBookDAO(String bookName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PoemsTO> getAPoemByBookDAO(String bookName, String poemName, int poemNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePoemTitleDAO(String bookName, String prevTitle, String newTitle, int poemNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePoemVerseDAO(PoemsTO prevPoem, PoemsTO newPoem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePoemDAO(String bookName, String poemName, int poemNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteVerseOfAPoemDAO(String bookName, String poemName, String[] verse) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addVerseDAO(PoemsTO transferObj, int poemNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertVerseDAO(PoemsTO transferObj) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
