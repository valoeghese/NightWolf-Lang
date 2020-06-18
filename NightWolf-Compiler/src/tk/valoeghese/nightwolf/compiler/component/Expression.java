package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class Expression extends Component {
	public Expression() {
		super("Expression", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		// first take to get parts
		// then organise and add the parts
	}
}
