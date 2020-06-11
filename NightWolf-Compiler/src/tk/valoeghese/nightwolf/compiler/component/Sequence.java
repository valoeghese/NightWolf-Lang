package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class Sequence extends Component {
	public Sequence() {
		super("Sequence", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		// TODO implement actual sequence tokeniser
		Component.skipPast('{', cursor);
		Component.skipPast('}', cursor);
	}
}
