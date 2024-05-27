/*
 * SPDX-FileCopyrightText: (C) Copyright 2023 Regione Piemonte
 *
 * SPDX-License-Identifier: EUPL-1.2 */

package it.wordfinder;

public class RegexTest {
	public static void main(String[] args) {
		String[] testStrings = {"gaaaaa", "gbbbbb", "gccccz", "ghello"};

		String regex1 = "g[a-z]{5}$";
		String regex2 = "g[abcdefghilmnopqers]{5}";

		System.out.println("Testing regex1: " + regex1);
		for (String s : testStrings) {
			System.out.println(s + " matches: " + s.matches(regex1));
		}

		System.out.println("\nTesting regex2: " + regex2);
		for (String s : testStrings) {
			System.out.println(s + " matches: " + s.matches(regex2));
		}
	}
}
