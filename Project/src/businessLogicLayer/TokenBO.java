package businessLogicLayer;

import dataAccessLayer.IDALFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.oujda_nlp_team.entity.Result;
import transferObject.PoemsTO;
import transferObject.RootTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;


public class TokenBO implements ITokenBO {

    IDALFacade objDAL;

    public TokenBO(IDALFacade objDAO) {
        this.objDAL = objDAO;
    }

    @Override
    public ArrayList<String> returnTokens(String misra1, String misra2) {

        String misra1Array[] = misra1.split(" ");
        String misra2Array[] = misra2.split(" ");
        HashMap<String, Integer> returnObj = new HashMap<>();

        for (int i = 0; i < misra1Array.length; i++) {
            returnObj.put(misra1Array[i], i);
        }
        for (int i = 0; i < misra2Array.length; i++) {
            returnObj.put(misra2Array[i], i);
        }
        ArrayList<String> returnArray = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : returnObj.entrySet()) {
            String key = entry.getKey();
            if (!key.equals("")) {
                returnArray.add(key);
            }
        }
        return returnArray;
    }

    @Override
    public ArrayList<String> getRootsByToken(String token) {
        ArrayList<String> rootsList = new ArrayList<>();
        String roots = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(token).getAllRootString();
        String[] arr = roots.split(":");
        for (String s : arr) {
            if (!s.contains("-")) {
                rootsList.add(s);
            }
        }
        return rootsList;
    }

    private boolean assignRootsToToken(PoemsTO pTo, String token) {
        ArrayList<String> roots = getRootsByToken(token);
        int i = 0;
        for (String s : roots) {
            if (!(s.equals("") || s.contains(" ") || s.equals(null))) {
                if (objDAL.insertRootByTokenDAO(new RootTO(s, "Automatic"), pTo, token)) {
                    i++;
                }
            }
        }
        return i == roots.size();
    }

    private boolean createTags(String token) {
        List<Result> result = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(token).getAllResults();
        if(result.isEmpty()){
            return false;
        }
        String[] tempTags = result.get(0).getPartOfSpeech().split("\\|");
        ArrayList<String> tags = new ArrayList<>();
        for(String s : tempTags){
            if(!s.contains("-")){
                tags.add(s);
            }
        }
        return objDAL.insertTags(token, tags);
    }

    @Override
    public boolean insertTokens(PoemsTO objPoemTo, String[] token) {
        int i = 1;
        objDAL.insertTokens(objPoemTo, token);
        for (String s : token) {
            if (createTags(s)) {
                i++;
            }
            assignRootsToToken(objPoemTo, s);
        }
        return i == token.length;
    }

    @Override
    public ArrayList<TokenTO> getAllTokens() {
        return objDAL.getAllTokens();
    }

    @Override
    public ArrayList<TokenTagsTO> getTagsOfAToken(String token) {
        return objDAL.getTagsOfAToken(token);
    }
}
