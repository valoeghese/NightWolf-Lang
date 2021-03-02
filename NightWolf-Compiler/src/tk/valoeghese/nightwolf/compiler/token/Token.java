package tk.valoeghese.nightwolf.compiler.token;

public class Token {
	public Token(int type, String data) {
		this.type = type;
		this.data = data;
	}

	public final int type;
	public final String data;

	public static final int STRING_VALUE = -2;
	public static final int NUMERIC_VALUE = -1;
	public static final int STRING_LITERAL = 0;
}
