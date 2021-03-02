package tk.valoeghese.nightwolf.compiler.token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

public class Tokeniser {
	private final List<TokenMatcher> patterns = new ArrayList<>();

	public Tokeniser then(String regex, int token) {
		this.patterns.add(new TokenMatcher(Pattern.compile(regex), token));
		return this;
	}

	public Queue<Token> tokenise(String input) {
		Queue<Token> result = new LinkedList<>();
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
