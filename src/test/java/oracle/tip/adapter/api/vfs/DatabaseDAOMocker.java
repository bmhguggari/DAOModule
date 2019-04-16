/**
 * 
 */
package oracle.tip.adapter.api.vfs;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * @author hguggari
 *
 */
public class DatabaseDAOMocker {
	
	protected static final String TEMP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "file.txt";
	protected static final String FLOW_ID = "FlowId";
	protected static final String TENANT_ID = "TenantId";

	protected DataSource datasource = mock(DataSource.class);
	
	protected Connection connection = mock(Connection.class);
	
	protected PreparedStatement prepStatement = mock(PreparedStatement.class);
	
	protected Statement statement = mock(Statement.class);
	
	
	protected ResultSet resultSet = mock(ResultSet.class);
	
	
	protected VirtualFileEntry getFileEntry() {
		VirtualFileEntry entry = new VirtualFileEntry();
		File file = new File(TEMP_PATH);
		entry.setFilePath(file.getAbsolutePath());
		entry.setParent(file.getParent());
		entry.setAttachmentInputStream(getInputStream());
		entry.setFlowId(FLOW_ID);
		entry.setChunked(false);
		entry.setDirectory(false);
		entry.setMetadataInputStream(getInputStream());
		entry.setTenantId(TENANT_ID);
		return entry;
	}
	
	private InputStream getInputStream() {
		return new ByteArrayInputStream("Testing the stream storage on database".getBytes());
	}
	
	/**
	 * Mocker for adding new table entry
	 * @throws SQLException
	 */
	public void mockInsertFileEntry() throws SQLException {
		
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        	
        when(prepStatement.executeUpdate()).thenReturn(1);
	}
	
	/**
	 * Mocker for adding duplicate table entry, constraint violation, SQLException
	 * @throws SQLException
	 */
	public void mockDuplicateFileEntry() throws SQLException {
		
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        	
        when(prepStatement.executeUpdate()).thenThrow(new SQLException());
	}
	
	/**
	 * Mocker for the deleting existing table entry
	 * @throws SQLException
	 */
	public void mockDeleteFileEntry() throws SQLException {
		
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        	
        when(prepStatement.executeUpdate()).thenReturn(1);
	}
	
	/**
	 * Mocker for the deleting non-existing table entry. Which return 0 value
	 * @throws SQLException
	 */
	public void mockDeleteNonExistingFileEntry() throws SQLException {
		
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        	
        when(prepStatement.executeUpdate()).thenReturn(0);
	}
	
	/**
	 * 
	 * FILEPATH, ATTACHMENT_BLOB, METADATA_BLOB, PARENT, FLOWID, TENANTID, IS_CHUNKED, IS_DIRECTORY
	 * @throws SQLException
	 */
	public void mockGetFileEntry() throws SQLException {
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        
        when(prepStatement.executeQuery()).thenReturn(resultSet);
        
        VirtualFileEntry entry = getFileEntry();
        
        when(resultSet.next()).thenReturn(true);
        
        when(resultSet.getString(1)).thenReturn(entry.getFilePath());

        when(resultSet.getBinaryStream(2)).thenReturn(entry.getAttachmentInputStream());

        when(resultSet.getBinaryStream(3)).thenReturn(entry.getMetadataInputStream());
        
        when(resultSet.getString(4)).thenReturn(entry.getParent());
        
        when(resultSet.getString(5)).thenReturn(entry.getFlowId());
        
        when(resultSet.getString(6)).thenReturn(entry.getTenantId());
        
        when(resultSet.getBoolean(7)).thenReturn(entry.isChunked());
        
        when(resultSet.getBoolean(8)).thenReturn(entry.isDirectory());
        
	}
	
	/**
	 * mock for the get file entry on non-exising file
	 * @throws SQLException
	 */
	public void mockGetNonExistingFileEntry() throws SQLException {
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        
        when(prepStatement.executeQuery()).thenReturn(resultSet);
        
        when(resultSet.next()).thenReturn(false);
	}

	
	
	public void mockUpdateFileEntry() throws SQLException {
		mockGetFileEntry();
		when(datasource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(any(String.class))).thenReturn(prepStatement);
        
        when(prepStatement.executeUpdate()).thenReturn(1);
	}
}
