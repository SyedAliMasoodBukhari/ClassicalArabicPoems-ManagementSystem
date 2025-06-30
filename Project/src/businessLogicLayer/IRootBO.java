
package businessLogicLayer;

import java.util.ArrayList;
import java.util.HashMap;
import transferObject.PoemsTO;
import transferObject.RootTO;

public interface IRootBO {
    public HashMap<RootTO,Integer> getAllRoots();
      public  ArrayList<PoemsTO>getVersesAgainstRoot(String root);
    public boolean insertRootsByVerse(RootTO rootObj,PoemsTO  obj);
    
}
