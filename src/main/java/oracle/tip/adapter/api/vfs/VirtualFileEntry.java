package oracle.tip.adapter.api.vfs;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hguggari
 *
 */
public class VirtualFileEntry {

        public VirtualFileEntry() {
        }

         private String fileName;
         private String filePath;
         private InputStream attachmentInputStream;
         private InputStream metadataInputStream;
         private OutputStream attachmentOutputStream;
         private OutputStream metadataOutputStream;
         private String parent;
         private String flowId;
         private String tenantId;
         private boolean isChunked = false;;
         private boolean isDirectory = false;
         private long lastModifiedTime;
         private long lastUpdatedTime;
         private long createdTime;
         private byte[] data;


        public String getFileName() {
                return fileName;
        }
        public void setFileName(String fileName) {
                this.fileName = fileName;
        }
        public String getFilePath() {
                return filePath;
        }
        public void setFilePath(String filePath) {
                this.filePath = filePath;
        }
        public InputStream getAttachmentInputStream() {
                return attachmentInputStream;
        }
        public void setAttachmentInputStream(InputStream attachmentInputStream) {
                this.attachmentInputStream = attachmentInputStream;
        }
        public InputStream getMetadataInputStream() {
                return metadataInputStream;
        }
        public void setMetadataInputStream(InputStream metadataInputStream) {
                this.metadataInputStream = metadataInputStream;
        }
        public OutputStream getAttachmentOutputStream() {
            return attachmentOutputStream;
    }
    public void setAttachmentOutputStream(OutputStream attachmentOutputStream) {
            this.attachmentOutputStream = attachmentOutputStream;
    }
    public OutputStream getMetadataOutputStream() {
            return metadataOutputStream;
    }
    public void setMetadataOutputStream(OutputStream metadataOutputStream) {
            this.metadataOutputStream = metadataOutputStream;
    }
    public String getParent() {
            return parent;
    }
    public void setParent(String parent) {
            this.parent = parent;
    }
    public String getFlowId() {
            return flowId;
    }
    public void setFlowId(String flowId) {
            this.flowId = flowId;
    }
    public String getTenantId() {
            return tenantId;
    }
    public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
    }
    public boolean isChunked() {
            return isChunked;
    }
    public void setChunked(boolean isChunked) {
            this.isChunked = isChunked;
    }
    public boolean isDirectory() {
            return isDirectory;
    }
    public void setDirectory(boolean isDirectory) {
            this.isDirectory = isDirectory;
    }
    public long getLastModifiedTime() {
            return lastModifiedTime;
    }
    public void setLastModifiedTime(long lastModifiedTime) {
            this.lastModifiedTime = lastModifiedTime;
    }
    public long getLastUpdatedTime() {
        return lastUpdatedTime;
	}
	public void setLastUpdatedTime(long lastUpdatedTime) {
	        this.lastUpdatedTime = lastUpdatedTime;
	}
	public long getCreatedTime() {
	        return createdTime;
	}
	public void setCreatedTime(long createdTime) {
	        this.createdTime = createdTime;
	}
	public byte[] getData() {
	        return data;
	}
	public void setData(byte[] data) {
	        this.data = data;
	}
}
