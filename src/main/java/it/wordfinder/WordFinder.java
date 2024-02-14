package it.wordfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordFinder {

	private static final int MAX_OUTPUT_BUFFER_SIZE = 224;

	private static final String DICTIONARY_FILE_PATH = "src/main/resources/dictionary.txt";

	public static void main ( String[] args ) {
		System.out.println ( "\n\nFound words: " + findWords ( 9, 'u', "xxxx" ) );
	}

	@SuppressWarnings ( "all" )
	private static int findWords ( final int numberOfTotalChar, final char initialCharacter, final String onlyPossibleChars ) {
		if ( onlyPossibleChars.length () > numberOfTotalChar ) {
			return error ( "Inconsistent data" );
		}

		var linesFromDB = loadDatabase ();

		var pattern = Pattern.compile ( "^" + initialCharacter + "[" + onlyPossibleChars + "]{" + ( numberOfTotalChar - 1 ) + "}$" );
		var found = 0;
		var lineCharCounter = 0;
		for ( var line : linesFromDB ) {
			final var matcher = pattern.matcher ( line );
			while ( matcher.find () ) {
				found++;
				lineCharCounter = formatOutput ( matcher, lineCharCounter, numberOfTotalChar );
			}
		}
		return found;
	}

	private static List<String> loadDatabase () {
		List<String> lines = new ArrayList<> ();
		try ( var reader = new BufferedReader ( new FileReader ( DICTIONARY_FILE_PATH ) ) ) {
			String line;
			while ( ( line = reader.readLine () ) != null ) {
				lines.add ( line );
			}
		} catch ( IOException e ) {
			error ( "Error while loading inmemory db, debug for more infos." );
			throw new RuntimeException ( e );
		}
		return lines;
	}

	private static int formatOutput ( final Matcher matcher, final int lineCharCounter, final int numberOfTotalChar ) {
		System.out.print ( matcher.group () + " " );
		var newValue = lineCharCounter + 1 + numberOfTotalChar;
		if ( lineCharCounter >= MAX_OUTPUT_BUFFER_SIZE - numberOfTotalChar * 2 ) {
			System.out.println ();
			newValue = 0;
		}
		return newValue;
	}

	private static int error ( final String message ) {
		System.err.println ( message );
		return -1;
	}
}
