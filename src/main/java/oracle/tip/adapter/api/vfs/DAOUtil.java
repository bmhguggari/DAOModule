package oracle.tip.adapter.api.vfs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hguggari
 *
 */
public class DAOUtil {

        private DAOUtil() {
        }


        public static void init(DAOContext context) {
        }

        /**
         * FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY FROM ATTACHMENT_TABLE
         * @param resultSet
         * @param entry
         * @throws DAOException
         */
        public static void populateEntry(ResultSet resultSet, TableEntry entry) throws DAOException {
                try {
                        if(resultSet == null || entry == null) {
                                throw new NullPointerException("Either resultSet or entry object are null");
                        }
                        if(resultSet.next()) {
	                        entry.setFilePath(resultSet.getString(1));
	                        entry.setAttachmentInputStream(resultSet.getBinaryStream(2));
	                        entry.setMetadataInputStream(resultSet.getBinaryStream(3));
	                        entry.setParent(resultSet.getString(4));
	                        entry.setFlowId(resultSet.getString(5));
	                        entry.setTenantId(resultSet.getString(6));
	                        entry.setChunked(resultSet.getBoolean(7));
	                        entry.setDirectory(resultSet.getBoolean(8));
                        }

                        return;
                } catch(SQLException ex) {
                        throw new DAOException(ex);
                }
        }


        /**
         * FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY
         * @param entry
         * @param prepStatement
         * @throws DAOException
         */
        public static void updateStatement(TableEntry entry, PreparedStatement prepStatement) throws DAOException {
            try {
                    if(prepStatement == null || entry == null) {
                            throw new NullPointerException("Either Statement or entry object are null");
                    }

                    prepStatement.setString(1, entry.getFilePath());
                    prepStatement.setBinaryStream(2, entry.getAttachmentInputStream());
                    prepStatement.setBinaryStream(3, entry.getMetadataInputStream());
                    prepStatement.setString(4, entry.getParent());
                    prepStatement.setString(5, entry.getFlowId());
                    prepStatement.setString(6, entry.getTenantId());
                    prepStatement.setBoolean(7, entry.isChunked());
                    prepStatement.setBoolean(8, entry.isDirectory());

                    return;
            } catch(SQLException ex) {
                    throw new DAOException(ex);
            }
    }

}
