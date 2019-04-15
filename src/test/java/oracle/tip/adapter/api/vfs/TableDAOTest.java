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

public class TableDAOTest extends TableDAOMocker {
	public TableDAOTest() {
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
		TableDAO dao = null;
		try {
			mockNewTableEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = new TableDAOImpl(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean inserted = dao.insertTableEntry(getTableEntry());
			
			assertTrue(inserted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test(expected = DAOException.class)
	public void testDuplicateTableEntry() throws Exception {
		TableDAO dao = null;
			mockDuplicateTableEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = new TableDAOImpl(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean inserted = dao.insertTableEntry(getTableEntry());
			
			assertTrue(inserted);
			
	}
	
	@Test 
	public void testDeleteTableEntry() {
		TableDAO dao = null;
		try {
			mockDeleteTableEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = new TableDAOImpl(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean deleted = dao.deleteTableEntry(TEMP_PATH, FLOW_ID);
			
			assertTrue(deleted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDeleteNonExistingTableEntry() {
		TableDAO dao = null;
		try {
			mockDeleteNonExistingTableEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = new TableDAOImpl(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			boolean deleted = dao.deleteTableEntry(TEMP_PATH, FLOW_ID);
			
			assertFalse(deleted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTableEntry() {
		TableDAO dao = null;
		try {
			mockGetTableEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = new TableDAOImpl(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			TableEntry entry = dao.getTableEntry(TEMP_PATH, FLOW_ID);
			
			assertNotNull(entry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetNonExistingTableEntry() {
		TableDAO dao = null;
		try {
			mockGetNonExistingTableEntry();
			assertNotNull(datasource);
			assertNull(dao);
			
			dao = new TableDAOImpl(new DAOContext());
			
			assertNotNull(dao);
			
			dao.setDataSource(datasource);
			
			TableEntry entry = dao.getTableEntry(TEMP_PATH, FLOW_ID);
			
			assertNull(entry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
