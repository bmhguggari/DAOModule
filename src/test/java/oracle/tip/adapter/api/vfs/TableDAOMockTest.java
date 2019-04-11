/**
 * 
 */
package oracle.tip.adapter.api.vfs;

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author hguggari
 *
 */
public class TableDAOMockTest {
	private static TableDAOImpl mock; 

	public TableDAOMockTest() {
	}
	
	@BeforeClass
	public static void setup() {
		mock = EasyMock.mock(TableDAOImpl.class);
	}
	
	@AfterClass
	public static void teardown() {
		mock = null;
	}
	
	@Test
	public void testMockObjects() {
		assertNotNull(mock);
	}
}
