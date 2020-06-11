package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class FuncValue extends Component {
	public FuncValue() {
		super("FuncValue", true);
	}

	public Component getParameter() {
		return this.getComponent(0);
	}

	public Component getSequence() {
		return this.getComponent(1);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();

		// value (often, but not always, a struct). If not a struct, a value type.
		InferencedType parameter = new InferencedType();
		parameter.tokenise(cursor);
		this.addComponent(parameter.getTrueType());

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
		Sequence sequence = new Sequence();
		sequence.tokenise(cursor);
		this.addComponent(sequence);
	}
}
