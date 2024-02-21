package it.wordfinder;

public class OccorrenzeLettere {

//	public static void main ( String[] args ) {
//		String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
//		int[] conteggi = countChars ( input );
//
//		// Stampa i conteggi delle occorrenze
//		for ( int i = 0; i < conteggi.length; i++ ) {
//			char lettera = (char) ( 'a' + i );
//			System.out.println ( lettera + ": " + conteggi[i] );
//		}
//	}
//
//	private static int[] countChars ( final String input ) {
//		final var counts = new int[26];
//		for ( var i = 0; i < input.length (); i++ ) {
//			final var character = input.charAt ( i );
//			if ( character >= 'a' && character <= 'z' ) {
//				counts[character - 'a']++;
//			}
//		}
//		return counts;
//	}
//
//	private static boolean compareCountChars ( final String fromDB, final String fromInput ) {
//		final int[] fromDBCounters = countChars ( fromDB );
//		final int[] fromInputCounters = countChars ( fromInput );
//		for ( var i = 0; i < fromDBCounters.length; i++ ) {
//			if ( fromDBCounters[i] <= fromInputCounters[i] ) {
//				return true;
//			}
//		}
//		return false;
//	}
}
