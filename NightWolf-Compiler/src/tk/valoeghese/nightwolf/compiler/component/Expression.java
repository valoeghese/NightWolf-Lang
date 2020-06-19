package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

/**
 * An expression of some value.
 * Could be
 * <ul>
 * <li> a mathematical expression, such as {@code 2 + 2 * (a / 3)} </li>
 * <li> a single value, such as {@code "Hello, World!"} or {@code 9.5} </li>
 * <li> {@code null} </li>
 * </ul>
 */
public class Expression extends Component {
	public Expression() {
		super("Expression", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		// TODO implement this.
		Component.skipPast(';', cursor);
		// first take to get parts

		// then organise and add the parts
	}
}
