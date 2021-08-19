package jdbc.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class JDBCURLBuilder {
	
	protected static String JDBC = "jdbc";
	protected Map<String, String> properties;
	protected String dbType;
	protected int portNumber;
	protected String hostAddress;
	protected String catalogName;
	
	public JDBCURLBuilder() {
		properties = new HashMap<>();
	}
	
	public void setPort(String port) { 
		Objects.requireNonNull(port);
		this.portNumber = Integer.parseInt(port); 
	}
	
	public void setPort(int port) {
		if(port < 0) throw new IllegalArgumentException();
		this.portNumber = port;
	}
	
	public void addURLProperty(String key, String value) {
		Objects.requireNonNull(key, value);
		this.properties.put(key, value); 
	}
	
	protected void setDB(String db) {
		Objects.requireNonNull(db);
		this.dbType = db;
	}
	
	public abstract String getURL();
	
	public void setAddress(String address) {
		Objects.requireNonNull(address);
		this.hostAddress = address;
	}
	
	public void setCatalog(String catalog) {
		Objects.requireNonNull(catalog);
		this.catalogName = catalog;
	}
}