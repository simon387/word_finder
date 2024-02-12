package it.wordfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Main {

	public static void main ( String[] args ) {
		var filePath = "C:\\dev\\word_finder\\src\\main\\resources\\dictionary.txt";
		var regex = "^f[ercmaoth]{8}$";

		List<String> lines = new ArrayList<> ();

		try ( var reader = new BufferedReader ( new FileReader ( filePath ) ) ) {
			String line;
			while ( ( line = reader.readLine () ) != null ) {
				lines.add ( line );
			}
		} catch ( IOException e ) {
			e.printStackTrace ();
		}

		var pattern = Pattern.compile ( regex );
		for ( var line : lines ) {
			var matcher = pattern.matcher ( line );
			while ( matcher.find () ) {
				System.out.println ( matcher.group () );
			}
		}
	}
}
