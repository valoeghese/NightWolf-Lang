package tk.valoeghese.nightwolf.compiler.component.datatype;

import java.util.ArrayList;
import java.util.List;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.CompoundedComponent;
import tk.valoeghese.nightwolf.compiler.component.Expression;
import tk.valoeghese.nightwolf.compiler.component.LiteralProtoComponent;
import tk.valoeghese.nightwolf.compiler.component.NoOp;
import tk.valoeghese.nightwolf.compiler.component.op.Assign;
import tk.valoeghese.nightwolf.compiler.component.op.FuncInvoke;
import tk.valoeghese.nightwolf.compiler.component.op.ProtoComponent;

public class Sequence extends Component {
	public Sequence() {
		super("Sequence", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();
		Component.skipPast('{', cursor);
		char c;

		while ((c = cursor.advance()) != '}') {
			if (!Character.isWhitespace(c)) {
				cursor.rewind(); // I added this hacky method and now use it all the time wtf

				Line line = new Line();
				line.tokenise(cursor);
				this.addComponent(line.getTrueComponent());
			}
		}
	}

	public static class Line extends Component {
		public Line() {
			super("Line", true);
		}

		public Component getTrueComponent() {
			return this.getComponent(0);
		}

		@Override
		public void tokenise(Cursor cursor) throws SyntaxError {
			this.reset();
			StringBuilder sb = new StringBuilder();
			char c;
			List<Component> proto = new ArrayList<>();

			// collect syntax structure via proto-tokens
			while ((c = cursor.advance()) != ';') {
				if (Character.isWhitespace(c)) {
					sb.append(' ');
				} else {
					switch (c) {
					case '}':
						throw SyntaxError.invalidCharacter(c, cursor);
					case '"':
						// ### Dump ###
						if (dump(sb, proto)) {
							sb = new StringBuilder();
						}

						StringValue str = new StringValue();
						str.tokenise(cursor);
						proto.add(str);
						break;
					case '-':
						if (cursor.advance() == '>') {
							// ### Dump ###
							if (dump(sb, proto)) {
								sb = new StringBuilder();
							}

							proto.add(ProtoComponent.APPLY);
						} else {
							cursor.rewind();
							sb.append(c);
						}
						break;
					case '=':
						// ### Dump ###
						if (dump(sb, proto)) {
							sb = new StringBuilder();
						}

						proto.add(ProtoComponent.ASSIGN);
						break;
					default:
						sb.append(c);
						break;
					}
				}
			}

			// ### Dump ###
			if (dump(sb, proto)) {
				sb = new StringBuilder();
			}

			//			for (Component co : proto) {
			//				this.addComponent(co);
			//			}

			// choose what this line represents from proto structure and properly tokenise it therewith
			switch (proto.size()) {
			case 0: // no expression
				this.addComponent(new NoOp());
				break;
			case 1:
				if (proto.get(0) instanceof LiteralProtoComponent) {
					Component expression = new Expression();
					expression.tokenise(new Cursor(((LiteralProtoComponent) proto.get(0)).value.concat(";").toCharArray(), cursor.getLine()));
					this.addComponent(expression);
					break;
				} else {
					throw new SyntaxError("Unable to tokenise line!", cursor.getLine());
				}
			case 3:
				Component centre = proto.get(1);

				if (centre == ProtoComponent.ASSIGN) {
					this.addComponent(Assign.assignOrNew(cursor.getLine(), proto));
					break;
				} else if (centre == ProtoComponent.APPLY) {
					// calling a method probably
					CompoundedComponent invoke = new FuncInvoke();
					invoke.compound(cursor.getLine(), proto);
					this.addComponent(invoke);
					break;
				}
			default:
				throw new SyntaxError("Unable to tokenise line!", cursor.getLine());
			}
		}

		private static boolean dump(StringBuilder sb, List<Component> proto) {
			if (!sb.toString().trim().isEmpty()) {
				proto.add(new LiteralProtoComponent(sb.toString().trim()));
				return true;
			}

			return false;
		}
	}
}
