package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.component.op.ProtoComponent;

public class ComponentDummy extends ProtoComponent {
	public ComponentDummy(String value) {
		super("Dummy");

		this.addData(value);
		this.value = value;
	}

	public final String value;
}
