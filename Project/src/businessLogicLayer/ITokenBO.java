package businessLogicLayer;

import java.util.ArrayList;
import transferObject.PoemsTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;

/**
 *
 * @author Malaika Tariq
 */
public interface ITokenBO {

    public ArrayList<String> returnTokens(String misra1, String misra2);

    public boolean insertTokens(PoemsTO objPoemTo, String[] token);
    
    public ArrayList<TokenTO> getAllTokens();
    
    public ArrayList<TokenTagsTO> getTagsOfAToken(String token);
    
    public ArrayList<String> getRootsByToken(String token);
}
