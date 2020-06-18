package tk.valoeghese.nightwolf.compiler.component.datatype;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;

/**
 * @implNote Inferenced types can be any type except sequence based types (including functions)
 */
public class InferencedType extends Component {
	public InferencedType() {
		super("InferencedType", true);
	}

	/**
	 * @return the inferenced component of this type.
	 * @throws IndexOutOfBoundsException if there is no inferenced component stored.
	 */
	public Component getTrueType() throws IndexOutOfBoundsException {
		return this.getComponent(0);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		char c;

		// get the non whitespace character
		do {
			c = cursor.advance();

			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}
		} while (Character.isWhitespace(c));

		switch (c) {
		case '(': // struct
			cursor.rewind();
			Struct struct = new Struct();
			struct.tokenise(cursor);
			this.addComponent(struct);
			break;
			// TODO " = string, others are numbers inferenced by type, etc
		}
	}
}
