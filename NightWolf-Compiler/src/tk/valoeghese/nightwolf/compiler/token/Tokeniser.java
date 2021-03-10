package tk.valoeghese.nightwolf.compiler.token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokeniser {
	private final List<TokenMatcher> patterns = new ArrayList<>();

	public Tokeniser then(String regex, int token) {
		this.patterns.add(new TokenMatcher(Pattern.compile("^(" + regex + ")"), token));
		return this;
	}

	public Queue<Token> tokenise(String input) {
		input = input.replace('\r', '\n'); // Regex can't handle it for some reason

		Queue<Token> result = new LinkedList<>();

		sequence: while (input != "") {
			for (TokenMatcher pattern : this.patterns) {
				Matcher m = pattern.regex.matcher(input);

				if (m.find()) {
					if (pattern.token != Token.NONE) {
						result.add(pattern.create(m.group().trim()));
					}

					input = m.replaceFirst("").stripLeading(); // Who needs whitespace anyway
					continue sequence;
				}
			}

			throw new RuntimeException("Unexpected character in source!\n> " + input.charAt(0) + " < " + (input.length() > 1 ? input.substring(1) : ""));
		}

		return result;
	}

	private static class TokenMatcher {
		TokenMatcher(Pattern regex, int token) {
			this.regex = regex;
			this.token = token;
		}

		final Pattern regex;
		final int token;

		Token create(String literal) {
			return new Token(this.token, literal);
		}
	}
}
