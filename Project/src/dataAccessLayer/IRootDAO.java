package dataAccessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import transferObject.PoemsTO;
import transferObject.RootTO;

/**
 *
 * @author Malaika Tariq
 */
public interface IRootDAO {
      public boolean insertRootManuallyDAO(RootTO rootObj,PoemsTO obj);
      public boolean insertRootByTokenDAO(RootTO insertRootObj,PoemsTO poemTo, String token);
      public HashMap<RootTO,Integer> getAllRoots();
      public ArrayList<PoemsTO> getSpecificRootVerse(String root);
      public boolean deleteRoots(PoemsTO obj);
      public boolean deleteEmptyRoots();
}
