/**
 * 
 */
package oracle.tip.adapter.api.vfs;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import oracle.tip.adapter.api.vfs.DAOFactory.DAOType;

/**
 * @author hguggari
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableDAOTest {
	
	private static Connection connection;
	private static DAOContext context;
	private static TableDAO dao;
	private static String tempPath = null;
	private static String FLOWID ="flawId";
	private static String TENANTID ="tenantId";

	public TableDAOTest() {
	}
	
	@BeforeClass
	public static void setup() throws Exception {
		context = new DAOContext();
		context.setProperty(DAOContext.DATA_SOURCE_JNDI_NAME, "jndi/dataSource");
		dao = DAOFactory.getDAO(DAOType.DATABASE, context);
		connection = ((TableDAOImpl)dao).getConnectionFromDataSource();
		tempPath = System.getProperty("java.io.tmpdir")+File.separator+"temp.txt";
	}
	
	@AfterClass
	public static void teardown() throws SQLException {
		if(connection != null) {
			connection.close();
		}
	}

	@Test
	public void testRequiredInstance() {
		assertNotNull(connection);
		assertNotNull(tempPath);
	}
	
	
	
	
	@Test
	public void test1InsertEntry() {
		TableEntry entry = getTableEntry();
		boolean flag = false;
		try {
			flag = dao.insertTableEntry(entry);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertTrue(flag);
		
	}
	
	@Test
	public void test2GetEntry() {
		TableEntry entry = null;
		try {
			entry = dao.getTableEntry(tempPath, FLOWID);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertNotNull(entry);
		
	}
	
	@Test
	public void test3DeleteEntry() {
		boolean flag = false;
		try {
			flag = dao.deleteTableEntry(tempPath, FLOWID);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertTrue(flag);
		
	}

	private TableEntry getTableEntry() {
		TableEntry entry = new TableEntry();
		entry.setFilePath(tempPath);
		entry.setParent(new File(tempPath).getParent());
		entry.setAttachmentInputStream(getInputStream());
		entry.setFlowId(FLOWID);
		entry.setChunked(false);
		entry.setDirectory(false);
		entry.setMetadataInputStream(getInputStream());
		entry.setTenantId(TENANTID);
		return entry;
	}

	
	private InputStream getInputStream() {
		return new ByteArrayInputStream("Testing the stream storage on database".getBytes());
	}
}
