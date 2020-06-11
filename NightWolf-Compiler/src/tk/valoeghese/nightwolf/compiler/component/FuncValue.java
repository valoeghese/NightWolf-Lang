package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class FuncValue extends Component {
	public FuncValue() {
		super("FuncValue", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
	}
}
