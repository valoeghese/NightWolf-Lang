package tk.valoeghese.nightwolf.compiler.component.op;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;

public class FuncRef extends Component {
	public FuncRef(String value) {
		super("FuncRef", false);
		this.addData(value);
		this.value = value;
	}

	public final String value;

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		throw new SyntaxError("Tokenizer Error: attempted to tokenise a Function Reference", cursor);
	}
}
