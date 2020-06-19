package tk.valoeghese.nightwolf.compiler.component.op;

import java.util.List;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.CompoundedComponent;
import tk.valoeghese.nightwolf.compiler.component.Expression;
import tk.valoeghese.nightwolf.compiler.component.LiteralProtoComponent;

public class Assign extends CompoundedComponent {
	public Assign() {
		this("ReAssign", 1);
	}

	protected Assign(String name, int lhsLength) {
		super(name, true);

		this.lhsLength = lhsLength;
	}

	private int lhsLength;

	@Override
	public void compound(int line, List<Component> components) {
		String[] lhs = ((LiteralProtoComponent) components.get(0)).value.split(" +");

		if (lhs.length != this.lhsLength) {
			throw new SyntaxError("Exception tokenising left hand side of variable assignment: invalid token count", line);
		}

		Cursor rhs = new Cursor(((LiteralProtoComponent) components.get(2)).value.concat(";").toCharArray(), line);
		Expression expression = new Expression();
		expression.tokenise(rhs);
		this.addComponent(expression);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		throw new UnsupportedOperationException("//TODO add direct tokenisation to Reassign components.");
	}

	public static Component assignOrNew(int line, List<Component> components) {
		String[] lhs = ((LiteralProtoComponent) components.get(0)).value.split(" +");

		CompoundedComponent result;
		switch (lhs.length) {
		case 1: // reassignment
			result = new Assign();
			break;
		case 2: // new assignment
			result = new VarDef(lhs[0]);
			break;
		default:
			throw new SyntaxError("Exception tokenising left hand side of variable assignment: invalid token count", line);
		}

		result.compound(line, components);
		return result;
	}
}
