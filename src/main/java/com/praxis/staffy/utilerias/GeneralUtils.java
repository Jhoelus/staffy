package com.praxis.staffy.utilerias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public final class GeneralUtils {
	
	public static String clobToString(Clob data) {
	       StringBuilder sb = new StringBuilder();
	       try {
	           Reader reader = data.getCharacterStream();
	           BufferedReader br = new BufferedReader(reader);

	           String line;
	           while(null != (line = br.readLine())) {
	               sb.append(line);
	           }
	           br.close();
	       } catch (SQLException e) {
	           // handle this exception
	       } catch (IOException e) {
	           // handle this exception
	       }
	       
	       return sb.toString();
	}
	
	private GeneralUtils() {}
}
