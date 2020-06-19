package tk.valoeghese.nightwolf.compiler.component.op;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;

public class ProtoComponent extends Component {
	protected ProtoComponent(String name) {
		super(name, false);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		throw new UnsupportedOperationException("Cannot Tokenise on a Dummy Component!");
	}

	public static final ProtoComponent APPLY = new ProtoComponent("Apply");
	public static final ProtoComponent ASSIGN = new ProtoComponent("Assign");
}
