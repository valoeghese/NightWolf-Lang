package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class Immutable extends Component {
	public Immutable(Component value) {
		super("Immutable", true);
		this.addComponent(value);
	}

	public Component getValue() {
		return this.getComponent(0);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		throw new UnsupportedOperationException("Cannot tokenise directly with an immutable, as it is a wrapper class!");
	}
}
