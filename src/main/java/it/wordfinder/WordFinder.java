package it.wordfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;


public final class WordFinder {

	private static final int MAX_OUTPUT_BUFFER_SIZE = 222;

	private static final String DICTIONARY_FILE_PATH = "src/main/resources/dictionary.txt";

	@SuppressWarnings ( "all" )
	public static void main ( String[] args ) {
		Integer wordLength = 8;
		var initialChars = "p".toLowerCase ();
		var onlyPossibleChars = "pleodgsntainlosp".toLowerCase (); // but not all mandatories // copy the exaxct values from g
		var lastChars = "".toLowerCase ();
		formatOutput ( findWords ( wordLength, initialChars, onlyPossibleChars, lastChars ) );
	}

	private static List<String> findWords ( final Integer wordLength, final String initialChars, final String onlyPossibleChars, final String lastChars ) {
		final List<String> words = new ArrayList<> ();
		final var posCharLen = wordLength != null ? "{" + ( wordLength - initialChars.length () - lastChars.length () ) + "}" : "+";
		final var pattern = Pattern.compile ( "^" + initialChars + "[" + removeDuplicatesChars ( onlyPossibleChars ) + "]" + ( posCharLen ) + lastChars + "$" );
		for ( final var lineFromDB : loadDatabase () ) {
			final var matcher = pattern.matcher ( lineFromDB );
			if ( matcher.find () && compareCountChars ( lineFromDB, onlyPossibleChars ) ) {
				words.add ( lineFromDB );
			}
		}
		return words;
	}

	private static List<String> loadDatabase () {
		final List<String> lines = new ArrayList<> ();
		try ( final var reader = new BufferedReader ( new FileReader ( DICTIONARY_FILE_PATH ) ) ) {
			String line;
			while ( ( line = reader.readLine () ) != null ) {
				lines.add ( line );
			}
		} catch ( IOException e ) {
			System.err.println ( "Error while loading inmemory db, debug for more infos." );
			throw new RuntimeException ( e );
		}
		return lines;
	}

	private static void formatOutput ( final List<String> words ) {
		var lineCharCounter = 0;
		for ( final var word : words ) {
			lineCharCounter = formatOutputHelper ( word, lineCharCounter );
		}
		System.out.println ( "\n\nFound words: " + words.size () );
	}

	private static int formatOutputHelper ( final String word, final int lineCharCounter ) {
		System.out.print ( word + " " );
		final var numberOfTotalChar = word.length ();
		if ( lineCharCounter >= MAX_OUTPUT_BUFFER_SIZE - numberOfTotalChar * 2 ) {
			System.out.println ();
			return 0;
		} else {
			return lineCharCounter + 1 + numberOfTotalChar;
		}
	}

	private static String removeDuplicatesChars ( final String str ) {
		final LinkedHashSet<Character> set = new LinkedHashSet<> ();
		for ( int i = 0; i < str.length (); i++ ) {
			set.add ( str.charAt ( i ) );
		}
		StringBuilder sb = new StringBuilder ();
		for ( final Character ch : set ) {
			sb.append ( ch );
		}
		return sb.toString ();
	}

	private static boolean compareCountChars ( final String fromDB, final String fromInput ) {
		final int[] fromDBCounters = countChars ( fromDB );
		final int[] fromInputCounters = countChars ( fromInput );
		for ( var i = 0; i < 26; i++ ) {
			if ( fromDBCounters[i] > fromInputCounters[i] ) {
				return false;
			}
		}
		return true;
	}

	private static int[] countChars ( final String input ) {
		final var counts = new int[26];
		for ( var i = 0; i < input.length (); i++ ) {
			final var character = input.charAt ( i );
			if ( character >= 'a' && character <= 'z' ) {
				counts[character - 'a']++;
			}
		}
		return counts;
	}

}
