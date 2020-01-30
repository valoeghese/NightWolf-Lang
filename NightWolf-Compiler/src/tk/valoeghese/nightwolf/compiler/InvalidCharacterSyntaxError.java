package tk.valoeghese.nightwolf.compiler;

public class InvalidCharacterSyntaxError extends SyntaxError {
	// generated
	private static final long serialVersionUID = -3003462420281904097L;

	public InvalidCharacterSyntaxError(char c, int line, int column) {
		super("Invalid Character! " + String.valueOf(c), line, column);
	}
}
