package oracle.tip.adapter.api.vfs;

import javax.sql.DataSource;

/**
 * @author hguggari
 *
 */
public interface TableDAO {
	/**
	 * Method persists the TableEntry object into the database table "ATTACHMENT_TABLE"
	 * @param entry
	 * @return returns true, if entry is inserted successfully, else false.
	 * @throws DAOException
	 */
    boolean insertTableEntry(TableEntry entry) throws DAOException;

    /**
     * Method receives filePath and FlowId, finds the record associated with these two fields and return the entry
     * @param filePath
     * @param flowId
     * @return TableEntry object associated with filePath and FlowId. Null object if not present in the database
     * @throws DAOException
     */
    TableEntry getTableEntry(String filePath, String flowId) throws DAOException;

    /**
     * Update the Attachment BLOB and Meta data BLOB fields associated with file path and flow id
     * @param entry
     * @return returns true, if table entry is updated else false
     * @throws DAOException
     */
    boolean updateTableEntry(TableEntry entry) throws DAOException;

    /**
     * If there is table entry with file path and flow id in the database, it deletes it from the database. else does nothing 
     * @param filePath
     * @param flowId
     * @return returns true if the table entry deleted successfully else false
     * @throws DAOException
     */
    boolean deleteTableEntry(String filePath, String flowId) throws DAOException;
    
    void setDataSource(DataSource dataSource) throws DAOException;

}
