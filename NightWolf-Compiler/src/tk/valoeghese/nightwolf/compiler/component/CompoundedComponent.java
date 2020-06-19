package tk.valoeghese.nightwolf.compiler.component;

import java.util.List;

public abstract class CompoundedComponent extends Component {
	public CompoundedComponent(String name, boolean container) {
		super(name, container);
	}

	public abstract void compound(int line, List<Component> components);
}
