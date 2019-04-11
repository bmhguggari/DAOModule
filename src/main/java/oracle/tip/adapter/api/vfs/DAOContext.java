package oracle.tip.adapter.api.vfs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hguggari
 *
 */
public class DAOContext {
        Map<String, String> properties;

        public DAOContext() {
                this.properties = new HashMap<String, String>();
        }

        public static final String DATA_SOURCE_JNDI_NAME = "jndiName";
        public static final String DB_URL = "dbUrl";
        public static final String USER = "user";
        public static final String PASSWORD = "password";

        public String getProperty(String key) {
                return this.properties.get(key);
        }

        public void setProperty(String key, String value) {
                this.properties.put(key, value);
        }

}
