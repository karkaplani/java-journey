package jdbc;

import java.sql.SQLException;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.builder.JDBCURLBuilder;

public class JDBCController implements AutoCloseable {
	
	private JDBCURLBuilder builder;
	private JDBCModel model;
	private StringProperty tableInUse;
	private ObservableList<String> tableNamesList;
	
	public JDBCController() {
		this.model = new JDBCModel();
		this.tableInUse = new SimpleStringProperty();
		tableInUse.addListener((value, oldValue, newValue) -> {
			try {
				model.getAndInitializeColumnNames(newValue); 
			} catch (SQLException ex) { 
				throw new IllegalStateException(ex);
			}
		});
		this.tableNamesList = FXCollections.observableArrayList();
	}

	public StringProperty tableInUseProperty() {
		return this.tableInUse;
	}
	
	public JDBCController setURLBuilder(JDBCURLBuilder builder) { 
		this.builder = builder;
		return this; 
	}
	
	public JDBCController setDatabase(String address, String port, String catalog) {
		builder.setAddress(address);
		builder.setPort(port);
		builder.setCatalog(catalog);
		return this; 
	}
	
	public JDBCController addConnectionURLProperty(String key, String value) {
		builder.addURLProperty(key, value);
		return this; 
	}
	
	public JDBCController setCredentials( String user, String pass) {
		model.setCredentials(user, pass);
		return this;
	}
	
	public JDBCController connect() throws SQLException {
		model.connectTo(builder.getURL());
		return this;
	}
	
	public boolean isConnected() throws SQLException { 
		return model.isConnected();
	}
	
	public List<String> getColumnNames() throws SQLException {
		return model.getAndInitializeColumnNames(this.tableInUse.get());
	}
	
	public List< List< Object>> getAll() throws SQLException{ 
		return model.getAll(this.tableInUse.get()); 
	}
	
	public List< List< Object>> search(String searchTerm) throws SQLException {
		return model.search(this.tableInUse.get(), searchTerm);
	}
	
	public void close() throws SQLException {
		model.close();
	}
	
	public ObservableList< String> getTableNames() throws SQLException {
		
		if(model.isConnected()) {
			this.tableNamesList.clear();
			this.tableNamesList.addAll(model.getAndInitializeTableNames());
		}
		return this.tableNamesList;
	}
}