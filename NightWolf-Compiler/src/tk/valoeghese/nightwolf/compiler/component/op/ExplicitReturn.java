package tk.valoeghese.nightwolf.compiler.component.op;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;

// implicit returns are inserted at the very end of a method if not specified
// where a value is needed (i.e. func not seq), the compiler tries to use the last value in the memory
// this means at the end of a method one can use a plain Expression to load a value
// if you have conditions surrounding stuff with no explicit return: beware
public class ExplicitReturn extends Component {
	public ExplicitReturn() {
		super("Return", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		// TODO Auto-generated method stub
		
	}
}
