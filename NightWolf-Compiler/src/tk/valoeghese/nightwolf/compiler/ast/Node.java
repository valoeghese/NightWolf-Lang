package tk.valoeghese.nightwolf.compiler.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import tk.valoeghese.nightwolf.compiler.token.Token;

public abstract class Node {
	public Node(boolean terminating) {
		this.children = terminating ? null : new ArrayList<>();
	}

	protected final List<Node> children;

	public abstract void parse(Queue<Token> tokens, Map<String, String> constantTable);
}
