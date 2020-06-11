package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class TupleDef extends Component {
	public TupleDef() {
		super("TupleDef", true);
	}

	//(args... )
	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		Component.skipPast('(', cursor);
	}
}
