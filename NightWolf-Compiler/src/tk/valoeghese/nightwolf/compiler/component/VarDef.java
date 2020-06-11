package tk.valoeghese.nightwolf.compiler.component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public class VarDef extends Component {
	public VarDef(String type) {
		super("VarDef", true);
		this.type = REVERSE_TYPE.get(type);
	}

	private final Type type;

	public Type getType() {
		return this.type;
	}

	public String getTypeString() {
		return this.getData(0);
	}

	public String getName() {
		return this.getData(1);
	}

	public Component getValue() {
		return this.getComponent(0);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		this.addData(this.type.name);

		StringBuilder sb = new StringBuilder();
		Component.proceed(sb, cursor);

		String name = sb.toString().trim();
		this.addData(name);

		// get next equals for value
		Component.skipPast('=', cursor);

		Component value = this.type.createValue();
		value.tokenise(cursor);
		this.addComponent(value);

		// get next semicolon for end
		Component.skipPast(';', cursor);
	}

	public static boolean isVarType(String name) {
		return REVERSE_TYPE.containsKey(name);
	}

	private static final Map<String, Type> REVERSE_TYPE = new HashMap<>();

	public static enum Type {
		FUNC("func", FuncValue::new); //TODO actual func value

		private Type(String name, Supplier<Component> factory) {
			this.factory = factory;
			this.name = name;
			REVERSE_TYPE.put(name, this);
		}

		private final Supplier<Component> factory;
		private final String name;

		public Component createValue() {
			return this.factory.get();
		}
	}
}
