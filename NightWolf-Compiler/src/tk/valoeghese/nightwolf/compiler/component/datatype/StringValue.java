package tk.valoeghese.nightwolf.compiler.component.datatype;

import tk.valoeghese.nightwolf.compiler.component.SingleReadingComponent;

public class StringValue extends SingleReadingComponent {
	public StringValue() {
		super("String", true, '"', false);
	}
}
