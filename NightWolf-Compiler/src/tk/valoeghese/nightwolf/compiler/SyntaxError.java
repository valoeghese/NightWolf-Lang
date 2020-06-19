package tk.valoeghese.nightwolf.compiler;

import tk.valoeghese.nightwolf.compiler.component.Component.Cursor;

public class SyntaxError extends RuntimeException {
	// Generated
	private static final long serialVersionUID = -8909798216502250264L;

	public SyntaxError(String message, Cursor cursor) {
		this(message, cursor.getLine(), cursor.getColumn(), cursor.showColumn());
	}

	public SyntaxError(String message, int line) {
		this(message, line, 0, false);
	}

	protected SyntaxError(String message, int line, int column, boolean showColumn) {
		super(message + " [line " + String.valueOf(line) + (showColumn ? (", column " + String.valueOf(column)) : "") + "]");
	}

	public static SyntaxError eof(Cursor cursor) {
		return new SyntaxError("Found end of file earlier than expected!", cursor);
	}

	public static SyntaxError invalidCharacter(char c, Cursor cursor) {
		return new SyntaxError("Found invalid character! '" + c + "'", cursor);
	}

	public static SyntaxError unexpectedToken(String token, Cursor cursor) {
		return new SyntaxError("Unexpected Token: \"" + token + "\"", cursor);
	}
}
