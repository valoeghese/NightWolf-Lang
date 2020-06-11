package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public abstract class SingleReadingComponent extends Component {
	public SingleReadingComponent(String name, boolean allowWhitespace, char terminating, boolean trimPrecedingWhitespace) {
		super(name, false);

		this.allowWhitespace = allowWhitespace;
		this.terminating = terminating;
		this.trimPrecedingWhitespace = trimPrecedingWhitespace;
	}

	private final boolean allowWhitespace;
	private final char terminating;
	private final boolean trimPrecedingWhitespace;

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		StringBuilder pkg = new StringBuilder();
		char c;
		boolean trimOver = false;

		while ((c = cursor.advance()) != this.terminating) {
			if (this.trimPrecedingWhitespace && !trimOver) {
				if (Character.isWhitespace(c)) {
					continue;
				} else {
					trimOver = true;
				}
			}

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
