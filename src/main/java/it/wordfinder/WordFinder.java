package it.wordfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;


public class WordFinder {

	private static final int MAX_OUTPUT_BUFFER_SIZE = 224;

	private static final String DICTIONARY_FILE_PATH = "src/main/resources/dictionary.txt";

	public static void main ( String[] args ) {
		var numberOfTotalChar = 8;
		var initialChar = 't';
		var onlyPossibleChars = "tetagoapreogitri"; // but not all mandatories
		formatOutput ( findWords ( numberOfTotalChar, initialChar, onlyPossibleChars ) );
	}

	private static List<String> findWords ( final int numberOfTotalChar, final char initialCharacter, final String onlyPossibleChars ) {
		List<String> words = new ArrayList<> ();
		final var filteredOnlyPossibleChars = removeDuplicatesChars ( onlyPossibleChars );
		if ( filteredOnlyPossibleChars.length () < numberOfTotalChar ) {
			error ( "Inconsistent data" );
			return words;
		}

		var linesFromDB = loadDatabase ();

		@SuppressWarnings ( "all" )
		var pattern = Pattern.compile ( "^" + initialCharacter + "[" + filteredOnlyPossibleChars + "]{" + ( numberOfTotalChar - 1 ) + "}$" );
		for ( var lineFromDB : linesFromDB ) {
			final var matcher = pattern.matcher ( lineFromDB );
			if ( matcher.find () ) {
				words.add ( lineFromDB );
			}
		}
		return words;
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

	private static void formatOutput ( List<String> words ) {
		var lineCharCounter = 0;
		for ( var word : words ) {
			lineCharCounter = formatOutputHelper ( word, lineCharCounter );
		}
		System.out.println ( "\n\nFound words: " + words.size () );
	}

	private static int formatOutputHelper ( final String word, final int lineCharCounter ) {
		System.out.print ( word + " " );
		final var numberOfTotalChar = word.length ();
		var newValueLineCharCounter = lineCharCounter + 1 + numberOfTotalChar;
		if ( lineCharCounter >= MAX_OUTPUT_BUFFER_SIZE - numberOfTotalChar * 2 ) {
			System.out.println ();
			newValueLineCharCounter = 0;
		}
		return newValueLineCharCounter;
	}

	private static String removeDuplicatesChars ( String str ) {
		LinkedHashSet<Character> set = new LinkedHashSet<> ();
		for ( int i = 0; i < str.length (); i++ ) {
			set.add ( str.charAt ( i ) );
		}
		StringBuilder sb = new StringBuilder ();
		for ( Character ch : set ) {
			sb.append ( ch );
		}
		return sb.toString ();
	}

	private static void error ( final String message ) {
		System.err.println ( message );
	}
}
