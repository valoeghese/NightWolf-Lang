package tk.valoeghese.nightwolf.compiler.component;

import tk.valoeghese.nightwolf.compiler.component.op.ProtoComponent;

public class LiteralProtoComponent extends ProtoComponent {
	public LiteralProtoComponent(String value) {
		super("Literal");

		this.addData(value);
		this.value = value;
	}

	public final String value;
}
