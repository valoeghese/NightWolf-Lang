package tk.valoeghese.nightwolf.compiler;

public class SyntaxError extends RuntimeException {
	// Generated
	private static final long serialVersionUID = -8909798216502250264L;

	public SyntaxError(String message, int line, int column) {
		super(message + " [line " + String.valueOf(line) + ", column " + String.valueOf(column) + "]");
	}
}
