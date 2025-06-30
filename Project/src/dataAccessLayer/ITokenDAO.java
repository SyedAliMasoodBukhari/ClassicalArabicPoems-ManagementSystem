
package dataAccessLayer;

import java.util.ArrayList;
import transferObject.PoemsTO;
import transferObject.TokenTO;
import transferObject.TokenTagsTO;

/**
 *
 * @author Malaika Tariq
 */
public interface ITokenDAO {

    public boolean insertTokens(PoemsTO objPoemTo, String[] token);

    public boolean insertTags(String token, ArrayList<String> tags);

    public boolean deleteTokens(PoemsTO objTO);

    public ArrayList<TokenTO> getAllTokens();

    public ArrayList<TokenTO> getSpecificVerseTokens(PoemsTO obj);

    public ArrayList<TokenTagsTO> getTagsOfAToken(String token);

}
