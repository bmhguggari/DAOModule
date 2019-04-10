package oracle.tip.adapter.api.vfs;

/**
 * @author hguggari
 *
 */
public interface TableDAO {

        boolean insertTableEntry(TableEntry entry) throws DAOException;

        TableEntry getTableEntry(String filePath, String flowId) throws DAOException;

        boolean updateTableEntry(TableEntry entry) throws DAOException;

        boolean deleteTableEntry(String filePath, String flowId) throws DAOException;

}
