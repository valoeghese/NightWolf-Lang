package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class ComponentDummy extends Component {
	public ComponentDummy(String value) {
		super("Dummy", false);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		throw new UnsupportedOperationException("Cannot Tokenise on a Dummy Component!");
	}
}
