package tk.valoeghese.nightwolf.compiler.token;

public class Token {
	public Token(int type, String data) {
		this.type = type;
		this.data = data;
	}

	public final int type;
	public final String data;

	@Override
	public String toString() {
		return this.type + "\t" + this.data;
	}

	public static final int NONE = -1;
	public static final int STRING_LITERAL = 0;
	public static final int NUMERIC_VALUE = 1;
	public static final int STRING_VALUE = 2;

	public static final int STRUCTURE_TOKEN = 3;
	public static final int RELATION_TOKEN = 4;

	public static final int MD_BINARY_OPERATOR = 5;
	public static final int AS_BINARY_OPERATOR = 6;
	public static final int BOOLEAN_BINARY_OPERATOR = 7;
	public static final int COMPARISON_BINARY_OPERATOR = 8;
}
