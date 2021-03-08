package tk.valoeghese.nightwolf.compiler.ast;

import java.util.Map;
import java.util.Queue;

import tk.valoeghese.nightwolf.compiler.token.Token;

public class AbstractSyntaxTree extends Node {
	public AbstractSyntaxTree() {
		super(false);
	}

	@Override
	public void parse(Queue<Token> tokens, Map<String, String> constantTable) {
		while (!tokens.isEmpty()) {
			
		}
	}
}
