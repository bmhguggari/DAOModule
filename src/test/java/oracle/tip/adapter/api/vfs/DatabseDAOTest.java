/**
 * 
 */
package oracle.tip.adapter.api.vfs;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author hguggari
 *
 */

public class DatabseDAOTest extends DatabaseDAOMocker {
	public DatabseDAOTest() {
	}
	
	@Test
	public void testMockObjects() {
		assertNotNull(datasource);
		assertNotNull(connection);
		assertNotNull(prepStatement);
		assertNotNull(statement);
		assertNotNull(resultSet);
		
	}
	
	@Test 
	public void testNewTableEntry() {
		DatabaseDAO dao = null;
		try {
			mockInsertFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean inserted = dao.insertFileEntry(getFileEntry());
			
			assertTrue(inserted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test(expected = DAOException.class)
	public void testDuplicateTableEntry() throws Exception {
		DatabaseDAO dao = null;
			mockDuplicateFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean inserted = dao.insertFileEntry(getFileEntry());
			
			assertTrue(inserted);
			
	}
	
	@Test 
	public void testDeleteTableEntry() {
		DatabaseDAO dao = null;
		try {
			mockDeleteFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean deleted = dao.deleteFileEntry(TEMP_PATH, FLOW_ID);
			
			assertTrue(deleted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDeleteNonExistingTableEntry() {
		DatabaseDAO dao = null;
		try {
			mockDeleteNonExistingFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean deleted = dao.deleteFileEntry(TEMP_PATH, FLOW_ID);
			
			assertFalse(deleted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTableEntry() {
		DatabaseDAO dao = null;
		try {
			mockGetFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			VirtualFileEntry entry = dao.getFileEntry(TEMP_PATH, FLOW_ID);
			
			assertNotNull(entry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetNonExistingTableEntry() {
		DatabaseDAO dao = null;
		try {
			mockGetNonExistingFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			VirtualFileEntry entry = dao.getFileEntry(TEMP_PATH, FLOW_ID);
			
			assertNull(entry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DatabaseDAO getDatabaseDAO(DAOContext context) throws Exception {
		return DAOFactory.getDAO(DAOFactory.DAOType.DATABASE, context);
	}
	
	@Test
	public void testUpdateTableEntry() {
		DatabaseDAO dao = null;
		try {
			mockUpdateFileEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = getDatabaseDAO(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean updated = dao.updateFileEntry(getFileEntry());
			
			assertTrue(updated);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
