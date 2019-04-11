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
        	this.context = context;
            init();
        }

        private void init() throws Exception {
            String dsName = context.getProperty(DAOContext.DATA_SOURCE_JNDI_NAME);
            if(dsName != null && !dsName.isEmpty()) {
            	this.dataSource = getDataSource();
            }
        }


        public Connection getDatabaseConnection() throws Exception {
            try {
            	if(this.dataSource == null) {
            		return this.getUrlConnection();
            	} else {
            		return this.dataSource.getConnection();
            	}
            } catch(Exception ex) {
                    throw ex;
            }

        }

        private Connection getUrlConnection() {
        	Connection conn = null;
        	String url = null;
        	String user = null;
        	String password = null;
        	try {
        		url = this.context.getProperty(DAOContext.DB_URL);//"jdbc:oracle:thin:@den02nxn.us.oracle.com:1521:xe";
        		user = this.context.getProperty(DAOContext.USER);//"ISCS_SOAINFRA";
        		password = this.context.getProperty(DAOContext.PASSWORD);//"welcome1";
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, password);
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
        private DataSource getDataSource() throws Exception {
            Context ctx = null;
            try {
                ctx = new InitialContext();
                String inboundDS = this.context.getProperty(DAOContext.DATA_SOURCE_JNDI_NAME);
                return (DataSource) ctx.lookup(inboundDS);
            } finally  {
            	try { if (ctx != null) ctx.close(); ctx = null; } catch (Exception e) { }
            }
        }

}

