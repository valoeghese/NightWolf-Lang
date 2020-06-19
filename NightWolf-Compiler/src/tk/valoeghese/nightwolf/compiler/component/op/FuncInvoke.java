package tk.valoeghese.nightwolf.compiler.component.op;

import java.util.List;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.CompoundedComponent;
import tk.valoeghese.nightwolf.compiler.component.Expression;
import tk.valoeghese.nightwolf.compiler.component.LiteralProtoComponent;
import tk.valoeghese.nightwolf.compiler.component.datatype.StringValue;

public class FuncInvoke extends CompoundedComponent {
	public FuncInvoke() {
		super("FuncInvoke", true);
	}

	@Override
	public void compound(int line, List<Component> components) {
		Component component = components.get(0);
		
		if (component instanceof StringValue) {
			this.addComponent(component);
		} else if (component instanceof LiteralProtoComponent) {
			Expression expr = new Expression();
			expr.tokenise(new Cursor(((LiteralProtoComponent) component).value.concat(";").toCharArray()));
			this.addComponent(component);
		} else {
			throw new SyntaxError("Invalid argument for method invoke.", line);
		}

		component = components.get(2);

		if (component instanceof LiteralProtoComponent) {
			this.addComponent(new FuncRef(((LiteralProtoComponent) component).value));
		} else {
			throw new SyntaxError("Invalid argument for invoked method.", line);
		}
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		throw new UnsupportedOperationException("Yeah this error should never occur");
	}
}
