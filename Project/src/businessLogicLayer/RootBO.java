package businessLogicLayer;

import dataAccessLayer.IDALFacade;

import java.util.ArrayList;
import java.util.HashMap;
import transferObject.PoemsTO;
import transferObject.RootTO;

/**
 *
 * @author Malaika Tariq
 */
public class RootBO implements IRootBO {

    IDALFacade objDAL;

    public RootBO(IDALFacade objDAL) {
        this.objDAL = objDAL;
    }

    @Override
    public HashMap<RootTO, Integer> getAllRoots() {
        return objDAL.getAllRoots();
    }

    @Override
    public ArrayList<PoemsTO> getVersesAgainstRoot(String root) {
        return objDAL.getSpecificRootVerse(root);
    }

    @Override
    public boolean insertRootsByVerse(RootTO rootObj, PoemsTO obj) {
        return objDAL.insertRootManuallyDAO(rootObj, obj);
    }

}
