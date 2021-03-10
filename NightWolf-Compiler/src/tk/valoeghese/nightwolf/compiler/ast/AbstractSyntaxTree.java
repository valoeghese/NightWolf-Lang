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
		String pkg = null;

		while (!tokens.isEmpty()) {
			Token next = tokens.remove();
			
			switch (next.type) {
			case Token.STRING_LITERAL:
				switch (next.data) {
				case "package":
					
					break;
				default:
					throw new RuntimeException("Unexpected string literal " + next.data);
				}
				break;
			default:
				throw new RuntimeException("Unexpected token " + next);
			}

			// root package if no pkgdef to start
			if (pkg == null) {
				pkg = "";
			}
		}
	}
	// notes for later
	// file name :: class
	// classes within :: separate files
	// except maybe "inline class"? Or maybe if they reference the stuff in the main class they are automatically adapted.
	// class within with same name :: same file
	// I'd prefer to manually adapt the public/private stuff
}
