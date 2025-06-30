/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOStub;

import java.util.ArrayList;

import dataAccessLayer.ITokenDAO;
import transferObject.PoemsTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;

/**
 *
 * @author Malaika Tariq
 */
public class TokenDAOStub  implements ITokenDAO{

	@Override
	public boolean insertTokens(PoemsTO objPoemTo, String[] token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertTags(String token, String voweledWords, String tags, String type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTokens(PoemsTO objTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<TokenTO> getAllTokens() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TokenTO> getSpecificVerseTokens(PoemsTO obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TokenTagsTO> getTagsOfAToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
