package jdbc.builder;

import java.util.Map.Entry;

public class MySQLURLBuilder extends JDBCURLBuilder{

	public MySQLURLBuilder() {
		super.setDB("mysql");
	}
	
	public String getURL() {
		//URL = JDBC:dbType://hostAddress:portNumber/catalogName?  entry.getKey()=entry.getValue()&useUnicode=true

		StringBuilder builder = new StringBuilder();
		String result = String.format("%s:%s://%s:%d/%s", JDBC, dbType, hostAddress, portNumber, catalogName);
		
		builder.append(result);
		
		if(!properties.isEmpty()) {
			builder.append("?");
			for(Entry<String, String> entry:properties.entrySet()) {
				String resultAfterValues = String.format("%s=%s&",entry.getKey(), entry.getValue());
				builder.append(resultAfterValues);
			}
		}
		
		builder.append("useUnicode=true");

		return builder.toString();
	}
}