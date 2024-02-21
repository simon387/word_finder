package it.wordfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;


public final class WordFinder {

	private static final int MAX_OUTPUT_BUFFER_SIZE = 224;

	private static final String DICTIONARY_FILE_PATH = "src/main/resources/dictionary.txt";

	public static void main ( String[] args ) {
		var wordLength = 8;
		var initialChar = 'a';
		var onlyPossibleChars = "oteoainnsgsnubimoet"; // but not all mandatories // copy the exaxct values from g
		formatOutput ( findWords ( wordLength, initialChar, onlyPossibleChars.toLowerCase () ) );
	}

	private static List<String> findWords ( final int wordLength, final char initialChar, final String onlyPossibleChars ) {
		final List<String> words = new ArrayList<> ();
		@SuppressWarnings ( "all" )
		final var pattern = Pattern.compile ( "^" + initialChar + "[" + removeDuplicatesChars ( onlyPossibleChars ) + "]{" + ( wordLength - 1 ) + "}$" );
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
