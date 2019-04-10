package oracle.tip.adapter.api.vfs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author hguggari
 *
 */
public class BaseDAO {
        protected DataSource dataSource;
        protected DAOContext context;

        public BaseDAO(DAOContext context) throws Exception {
                init(context);
        }

        private void init(DAOContext context) throws Exception {
                String dsName = context.getProperty(DAOContext.DATA_SOURCE_JNDI_NAME);
                if(dsName == null || dsName.isEmpty()) {
                        throw new Exception("Datasource name should be not be null" + dsName);
                } else {
//                        this.dataSource = getDataSource();
                }
        }


        public Connection getConnectionFromDataSource() throws Exception {
                try {
                	if(this.dataSource == null) {
                		return getConnection();
                	} else {
                		return dataSource.getConnection();
                	}
                } catch(Exception ex) {
                        throw ex;
                }

        }

        private Connection getConnection() {
        	Connection conn = null;
        	String URL = "jdbc:oracle:thin:@den02nxn.us.oracle.com:1521:xe";
        	String USER = "ISCS_SOAINFRA";
        	String PASSWORD ="welcome1";
        	try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	return conn;
		}

		/**
         * Method creates and returns the DataSource object from the specified JNDI name
         * @return DataSource
         * @throws Exception
         */
        public DataSource getDataSource() throws Exception {
                Context ctx = null;
                try {
                    ctx = new InitialContext();
                    String inboundDS = context.getProperty(DAOContext.DATA_SOURCE_JNDI_NAME);
                    return (DataSource) ctx.lookup(inboundDS);
                } finally  {
                        try { if (ctx != null) ctx.close(); ctx = null; } catch (Exception e) { }
                }
        }

}

