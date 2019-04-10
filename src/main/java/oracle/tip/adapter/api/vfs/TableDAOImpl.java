package oracle.tip.adapter.api.vfs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hguggari
 *
 */
public class TableDAOImpl extends BaseDAO implements TableDAO {

        private static final String INSERT_ENTRY = "INSERT INTO ATTACHMENT_TABLE(FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY) " +
                        " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        private static final String GET_ENTRY = "SELECT FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY FROM ATTACHMENT_TABLE" +
                        " WHERE FILEPATH = ? AND FLOWID = ?";

        private static final String DELETE_ENTRY = "DELETE FROM ATTACHMENT_TABLE WHERE FILEPATH = ? AND FLOWID = ?";

        public TableDAOImpl(DAOContext context) throws Exception {
                super(context);
        }

       
        public boolean insertTableEntry(TableEntry entry) throws DAOException {
            Connection connection = null;
            PreparedStatement prepStatement = null;
            int count = 0;
            try {
                    if(entry  == null) {
                            throw new DAOException("Entry should not be null");
                    }
                    //get Connection
                    connection = getConnectionFromDataSource();

                    //prepare SQL statement
                    prepStatement = connection.prepareStatement(INSERT_ENTRY);

                    //Ask helper to update the statement with values from entry
                    DAOUtil.updateStatement(entry, prepStatement);

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
        
       
        public TableEntry getTableEntry(String filePath, String flowId) throws DAOException {
                Connection connection = null;
                PreparedStatement prepStatement = null;
                ResultSet resultSet = null;
                try {
                        if(filePath == null || filePath.isEmpty() || flowId == null || flowId.isEmpty()) {
                                throw new DAOException("File path and flow Id should not be null");
                        }
                        //get Connection
                        connection = getConnectionFromDataSource();

                        //prepare SQL statement
                        prepStatement = connection.prepareStatement(GET_ENTRY);

                        //set parameters to statement
                        prepStatement.setString(1, filePath);
                        prepStatement.setString(2, flowId);

                        //execute the SQL
                        resultSet = prepStatement.executeQuery();
                        TableEntry entry = new TableEntry();

                        //Ask helper to populate the entry object from the result set
                        DAOUtil.populateEntry(resultSet, entry);

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


        public boolean updateTableEntry(TableEntry entry) {
                // TODO Auto-generated method stub
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
                        connection = getConnectionFromDataSource();

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
        
        /*public static void main(String[] args) {
        	DAOContext context = new DAOContext();
        	context.setProperty(DAOContext.DATA_SOURCE_JNDI_NAME, "jndi/DataSource");
			try {
				TableDAOImpl dd = new TableDAOImpl(context);
				Connection connection = dd.getConnectionFromDataSource();
				System.out.println(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}*/
}