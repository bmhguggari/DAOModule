package oracle.tip.adapter.api.vfs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author hguggari
 * This class accepts the DAOContext and creates data source and connection objects based on context details.
 *  If data source present in the context, all connection to database would be created from data source
 *  else connection would be created from the connection details in terms of URL, user name and password
 */
public class BaseDAO {
        protected DataSource dataSource;
        protected DAOContext context;

        public BaseDAO(DAOContext context) throws Exception {
        	this.context = context;
            init();
        }

        /**
         * Initialize the data source
         * @throws Exception
         */
        private void init() throws Exception {
            String dsName = context.getProperty(DAOContext.DATA_SOURCE_JNDI_NAME);
            if(dsName != null && !dsName.isEmpty()) {
            	this.dataSource = createDataSource();
            }
        }

        /**
         * creates data base connection either from data source or from the provided data base connection details
         * @return
         * @throws Exception
         */
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
        		//"jdbc:oracle:thin:@host:port:schema";
        		url = this.context.getProperty(DAOContext.DB_URL);
        		user = this.context.getProperty(DAOContext.USER);
        		password = this.context.getProperty(DAOContext.PASSWORD);
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
        private DataSource createDataSource() throws Exception {
            Context ctx = null;
            try {
                ctx = new InitialContext();
                String inboundDS = this.context.getProperty(DAOContext.DATA_SOURCE_JNDI_NAME);
                return (DataSource) ctx.lookup(inboundDS);
            } finally  {
            	try { if (ctx != null) ctx.close(); ctx = null; } catch (Exception e) { }
            }
        }
        
        public DataSource getDataSource() {
        	return this.dataSource;
        }
        
        //Mutator mostly used for JUNIT
        public void setDataSource(DataSource source) {
        	this.dataSource = source;
        }
}
