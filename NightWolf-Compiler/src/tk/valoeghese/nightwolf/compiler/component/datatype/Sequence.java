package tk.valoeghese.nightwolf.compiler.component.datatype;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.Expression;

public class Sequence extends Component {
	public Sequence() {
		super("Sequence", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		Component.skipPast('{', cursor);
		StringBuilder sb = new StringBuilder();
		char c;
		String type;

		while ((c = cursor.advance()) != '}') {
			if (c == ';') {
				// I could probably do it better than this but idk
				type = sb.toString().trim();

				Expression expr = new Expression();
				expr.tokenise(new Cursor(type.concat(";").toCharArray(), cursor.getLine(), false));
				this.addComponent(expr);
			} else if (c == '"') {
				
			} else if (Character.isWhitespace(c)) {
				if (!(type = sb.toString()).isEmpty()) {

				}

				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
		}
	}
}
