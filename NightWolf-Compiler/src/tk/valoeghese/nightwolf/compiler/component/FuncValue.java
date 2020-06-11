package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class FuncValue extends Component {
	public FuncValue() {
		super("FuncValue", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();

		// value tuple

		// apply operator

		char prev = '\u0000';
		char c;

		while (true) {
			c = cursor.advance();

			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}

			// find apply operator, ->
			if (prev == '-') {
				if (c == '>') {
					break;
				}
			}

			prev = c;
		}

		// sequence
	}
}
