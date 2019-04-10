/**
 * 
 */
package oracle.tip.adapter.api.vfs;

/**
 * @author hguggari
 *
 */
public class DAOFactory {

	enum DAOType{
		DATABASE,
		INMEMORY;
	}
	
	public DAOFactory() {
	}
	
	public static TableDAO getDAO(DAOType type, DAOContext context) throws Exception {
		if(type == DAOType.DATABASE) {
			return new TableDAOImpl(context);
		} else if(type == DAOType.INMEMORY) {
			return null;
		} else {
			return null;
		}
	}

}
