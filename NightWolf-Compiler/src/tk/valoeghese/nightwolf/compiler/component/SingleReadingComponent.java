package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public abstract class SingleReadingComponent extends Component {
	public SingleReadingComponent(String name, boolean allowWhitespace, char terminating) {
		super(name, false);

		this.allowWhitespace = allowWhitespace;
		this.terminating = terminating;
	}

	private final boolean allowWhitespace;
	private final char terminating;

	@Override
	public void parse(Cursor cursor) throws SyntaxError {
		this.reset();
		StringBuilder pkg = new StringBuilder();

		char c;
		while ((c = cursor.advance()) != this.terminating) {
			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}

			if (!this.allowWhitespace && Character.isWhitespace(c)) {
				throw SyntaxError.invalidCharacter(c, cursor);
			}

			pkg.append(c);
		}

		this.addData(pkg.toString());
	}
}
