package it.wordfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Main {

	public static void main ( String[] args ) {
		var filePath = "src/main/resources/dictionary.txt";
		var numberOfTotalChar = 5;
		var initialCharacter = 'f';
		var onlyPossibleChars = "ihagmoer";
		numberOfTotalChar--;
		var regex = "^" + initialCharacter + "[" + onlyPossibleChars + "]{" + numberOfTotalChar + "}$";

		List<String> lines = new ArrayList<> ();

		try ( var reader = new BufferedReader ( new FileReader ( filePath ) ) ) {
			String line;
			while ( ( line = reader.readLine () ) != null ) {
				lines.add ( line );
			}
		} catch ( IOException e ) {
			System.out.println ( "Error while loading memory db, debug for more infos." );
		}

		var found = 0;
		var pattern = Pattern.compile ( regex );
		for ( var line : lines ) {
			var matcher = pattern.matcher ( line );
			while ( matcher.find () ) {
				found++;
				System.out.print ( matcher.group () + " " );
			}
		}
		System.out.println ( "\nFound: " + found );
	}
}
