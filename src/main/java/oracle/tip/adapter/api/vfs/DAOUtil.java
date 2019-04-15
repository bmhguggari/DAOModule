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
        public static TableEntry populateEntry(ResultSet resultSet) throws DAOException {
        	TableEntry entry = null;
            try {
                if(resultSet == null) {
                        throw new NullPointerException("Either resultSet or entry object are null");
                }
                if(resultSet.next()) {
                	entry = new TableEntry();
                    entry.setFilePath(resultSet.getString(1));
                    entry.setAttachmentInputStream(resultSet.getBinaryStream(2));
                    entry.setMetadataInputStream(resultSet.getBinaryStream(3));
                    entry.setParent(resultSet.getString(4));
                    entry.setFlowId(resultSet.getString(5));
                    entry.setTenantId(resultSet.getString(6));
                    entry.setChunked(resultSet.getBoolean(7));
                    entry.setDirectory(resultSet.getBoolean(8));
                }
            } catch(SQLException ex) {
                    throw new DAOException(ex);
            }
            return entry;
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


		public static String getUpdateQuery(TableEntry entry) {
			StringBuilder builder = new StringBuilder();
			boolean gotData = false;
            builder.append("UPDATE ATTACHMENT_TABLE SET ");
            if(entry.getAttachmentInputStream() != null) {
            	gotData = true;
            	builder.append("ATTACHMENT_BLOB = ?");
            } 
            if(entry.getMetadataInputStream() != null) {
            	gotData = true;
            	builder.append(", METADATA_BLOB = ? ");
            }
            if(gotData) {
            	builder.append(" WHERE FILEPATH = ? AND FLOWID = ? ");
            	return builder.toString();
            } else { 
            	return null;
            }
		}

}
