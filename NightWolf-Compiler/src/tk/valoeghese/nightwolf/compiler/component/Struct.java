package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class Struct extends Component {
	public Struct() {
		super("Struct", false);
	}

	private Type type;

	public String getObject(int index) {
		return this.getData(index);
	}

	public String[] getDef(int index) {
		return new String[] {this.getData(index * 2), this.getData(index * 2 + 1)};
	}

	public Type getType() {
		return this.type;
	}

	//(type args... ) or (values... ) or empty: ()
	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		Component.skipPast('(', cursor);

		Type type = null;
		StringBuilder sb = new StringBuilder();
		String cachedVarType = null;

		mainLoop: while (true) {
			char c = cursor.advance();

			// useful for detecting (compact,people,who,write,like,this)
			boolean isComma = c == ',';
			boolean isEnd = c == ')';

			if (Character.isWhitespace(c) || isComma || isEnd) {
				String value = sb.toString();

				if (!value.isEmpty()) {
					if (VarDef.isVarType(value)) {
						// Must be template
						if (type == Type.OBJECT) {
							throw SyntaxError.unexpectedToken(value, cursor);
						}

						type = Type.TEMPLATE;
						cachedVarType = value;
						sb = new StringBuilder();
					} else if (type != Type.TEMPLATE) {
						// Must be object
						type = Type.OBJECT;
						this.addData(value);
						sb = new StringBuilder();

						if (!isComma) {
							if (isEnd || Component.skipPastEitherOnlyWhitespace(',', ')', cursor)) {
								break mainLoop;
							}
						}
					} else {
						// Must be template and thus must have gone through the cachedType setting loop.
						// Use cachedType.
						this.addData(cachedVarType);
						this.addData(value);
						sb = new StringBuilder();

						if (!isComma) {
							if (isEnd || Component.skipPastEitherOnlyWhitespace(',', ')', cursor)) {
								break mainLoop;
							}
						}
					}
				}
			} else {
				sb.append(c);
			}

			if (c == ')') { // catches empty tuples, ()
				break;
			}
		};

		this.type = type;
	}

	public static enum Type {
		TEMPLATE,
		OBJECT
	}
}
