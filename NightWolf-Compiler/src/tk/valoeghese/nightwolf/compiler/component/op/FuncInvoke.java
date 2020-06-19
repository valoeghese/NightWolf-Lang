package tk.valoeghese.nightwolf.compiler.component.op;

import java.util.List;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.CompoundedComponent;

public class FuncInvoke extends CompoundedComponent {
	public FuncInvoke() {
		super("FuncInvoke", true);
	}

	@Override
	public void compound(int line, List<Component> components) {
		Component arg = components.get(0);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		throw new UnsupportedOperationException("Yeah this error should never occur");
	}
}
