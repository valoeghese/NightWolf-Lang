package tk.valoeghese.nightwolf.compiler.component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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
		this.addComponent(this.type.createValue(cursor));

		// get next semicolon for end
		Component.skipPast(';', cursor);
	}

	public static boolean isVarType(String name) {
		// TODO allow classes. will probably require a rewrite.
		// or a keyword ;)
		// TODO tokenise [] -> Array{}

		int l = name.length();

		if (l > 2) { // a[] is the smallest length of array type: 3
			if (name.substring(l - 2, l).equals("[]")) {
				return REVERSE_TYPE.containsKey(name.substring(0, l - 2));
			}
		}

		return REVERSE_TYPE.containsKey(name);
	}

	private static final Map<String, Type> REVERSE_TYPE = new HashMap<>();

	public static enum Type {
		FUNC("func", FuncValue::new),
		VAR("var", cursor -> {
			InferencedType component = new InferencedType();
			component.tokenise(cursor);
			return component.getTrueType();
		}),
		VAL("val", cursor -> new Immutable(VAR.createValue(cursor))),
		SEQ("seq", Sequence::new);

		private Type(String name, Supplier<Component> factory) {
			this(name, cursor -> {
				Component c = factory.get();
				c.tokenise(cursor);
				return c;
			});
		}

		private Type(String name, Function<Cursor, Component> factory) {
			this.factory = factory;
			this.name = name;
			REVERSE_TYPE.put(name, this);
		}

		private final Function<Cursor, Component> factory;
		private final String name;

		public Component createValue(Cursor cursor) {
			return this.factory.apply(cursor);
		}
	}
}
