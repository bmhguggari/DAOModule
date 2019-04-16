package oracle.tip.adapter.api.vfs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hguggari
 *
 */
public class DatabaseDAOImpl extends BaseDAO implements DatabaseDAO {

        private static final String INSERT_ENTRY = "INSERT INTO ATTACHMENT_TABLE(FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY) " +
                        " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        private static final String GET_ENTRY = "SELECT FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY FROM ATTACHMENT_TABLE" +
                        " WHERE FILEPATH = ? AND FLOWID = ?";
        
        private static final String DELETE_ENTRY = "DELETE FROM ATTACHMENT_TABLE WHERE FILEPATH = ? AND FLOWID = ?";

        public DatabaseDAOImpl(DAOContext context) throws Exception {
                super(context);
        }

       
        public boolean insertTableEntry(VirtualFileEntry entry) throws DAOException {
            Connection connection = null;
            PreparedStatement prepStatement = null;
            int count = 0;
            try {
                    if(entry  == null) {
                            throw new DAOException("TableEntry should not be null, Please provide the valid table entry to persist");
                    }
                    //get Connection
                    connection = getDatabaseConnection();

                    //prepare SQL statement
                    prepStatement = connection.prepareStatement(INSERT_ENTRY);

                    //Ask helper to update the SQL statement with values from entry
                    DAOUtil.updateStatement(entry, prepStatement);

                    //execute the SQL
                    count = prepStatement.executeUpdate();

                    return count == 1 ? true : false;

            } catch(Exception ex) {
                    try { if(connection != null) connection.rollback();} catch (SQLException e) {}
                    throw new DAOException(ex);
            } finally {
                    try { if(prepStatement != null) prepStatement.close(); } catch(SQLException ex) {}
                    try { if(connection != null) connection.close(); } catch(SQLException ex) {}
            }
        }
        
       
        public VirtualFileEntry getTableEntry(String filePath, String flowId) throws DAOException {
                Connection connection = null;
                PreparedStatement prepStatement = null;
                ResultSet resultSet = null;
                try {
                        if(filePath == null || filePath.isEmpty() || flowId == null || flowId.isEmpty()) {
                                throw new DAOException("File path and flow Id should not be null");
                        }
                        //get Connection
                        connection = getDatabaseConnection();

                        //prepare SQL statement
                        prepStatement = connection.prepareStatement(GET_ENTRY);

                        //set parameters to statement
                        prepStatement.setString(1, filePath);
                        prepStatement.setString(2, flowId);

                        //execute the SQL
                        resultSet = prepStatement.executeQuery();

                        //Ask helper to populate the entry object from the result set
                        VirtualFileEntry entry = DAOUtil.populateEntry(resultSet);

                        return entry;

                } catch(Exception ex) {
                        try { if(connection != null) connection.rollback();} catch (SQLException e) {}
                        throw new DAOException(ex);
                } finally {
                        try { if(resultSet != null) resultSet.close(); } catch(SQLException ex) {}
                        try { if(prepStatement != null) prepStatement.close(); } catch(SQLException ex) {}
                        try { if(connection != null) connection.close(); } catch(SQLException ex) {}
                }
        }


        public boolean updateTableEntry(VirtualFileEntry entry) throws DAOException {
            Connection connection = null;
            PreparedStatement prepStatement = null;
            int count = 0;
            try {
            	if(entry  == null) {
                    throw new DAOException("Entry should not be null");
            	}
            	
            	VirtualFileEntry oldEntry = getTableEntry(entry.getFilePath(), entry.getFlowId());
            	if(oldEntry == null) {
            		throw new DAOException("Entry not found in the database with given details");
            	}
            	
            	//check FileBlob and Meta data Blob for updation.
            	String query = DAOUtil.getUpdateQuery(entry);
            	if(query != null) {
	                //get Connection
	                connection = getDatabaseConnection();
	
	                //prepare SQL statement
	                prepStatement = connection.prepareStatement(query);
	
	                //set parameters to statement
	                int fields = 0;
	                if(entry.getAttachmentInputStream() != null && entry.getMetadataInputStream() != null) {
	                	prepStatement.setBinaryStream(1, entry.getAttachmentInputStream());
	                	prepStatement.setBinaryStream(2, entry.getMetadataInputStream());
	                	fields +=2;
	                } else if(entry.getAttachmentInputStream() != null) {
	                	prepStatement.setBinaryStream(1, entry.getAttachmentInputStream());
	                	fields++;
	                } else if(entry.getMetadataInputStream() != null) {
	                	prepStatement.setBinaryStream(1, entry.getMetadataInputStream());
	                	fields++;
	                }
	                if(fields == 1) {
		                prepStatement.setString(2, entry.getFilePath());
		                prepStatement.setString(3, entry.getFlowId());
	                } else if(fields == 2) {
	                	prepStatement.setString(3, entry.getFilePath());
		                prepStatement.setString(4, entry.getFlowId());
	                }
	
	                //execute the SQL
	                count = prepStatement.executeUpdate();
	
	                return count > 0 ? true : false;
            	}

            } catch(Exception ex) {
                    try { if(connection != null) connection.rollback();} catch (SQLException e) {}
                    throw new DAOException(ex);
            } finally {
                    try { if(prepStatement != null) prepStatement.close(); } catch(SQLException ex) {}
                    try { if(connection != null) connection.close(); } catch(SQLException ex) {}
            }
    
                return false;
        }
        
        public boolean deleteTableEntry(String filePath, String flowId) throws DAOException {
                Connection connection = null;
                PreparedStatement prepStatement = null;
                int count = 0;
                try {
                        if(filePath == null || filePath.isEmpty() || flowId == null || flowId.isEmpty()) {
                                throw new DAOException("File path and flow Id should not be null");
                        }
                        //get Connection
                        connection = getDatabaseConnection();

                        //prepare SQL statement
                        prepStatement = connection.prepareStatement(DELETE_ENTRY);

                        //set parameters to statement
                        prepStatement.setString(1, filePath);
                        prepStatement.setString(2, flowId);

                        //execute the SQL
                        count = prepStatement.executeUpdate();

                        return count > 0 ? true : false;

                } catch(Exception ex) {
                        try { if(connection != null) connection.rollback();} catch (SQLException e) {}
                        throw new DAOException(ex);
                } finally {
                        try { if(prepStatement != null) prepStatement.close(); } catch(SQLException ex) {}
                        try { if(connection != null) connection.close(); } catch(SQLException ex) {}
                }
        }
}