/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOStub;

import java.util.ArrayList;
import java.util.HashMap;

import dataAccessLayer.IRootDAO;
import transferObject.PoemsTO;
import transferObject.RootTO;

/**
 *
 * @author Malaika Tariq
 */
public class RootDAOStub implements IRootDAO	{

	@Override
	public boolean insertRootDAO(RootTO rootObj, PoemsTO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertRoottToken(RootTO insertRootObj, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<RootTO, Integer> getAllRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PoemsTO> getSpecificRootVerse(String root) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRoots(PoemsTO obj) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
