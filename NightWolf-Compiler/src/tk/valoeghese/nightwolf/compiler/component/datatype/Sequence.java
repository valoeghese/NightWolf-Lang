package tk.valoeghese.nightwolf.compiler.component.datatype;

import tk.valoeghese.nightwolf.compiler.SyntaxError;
import tk.valoeghese.nightwolf.compiler.component.Component;

public class Sequence extends Component {
	public Sequence() {
		super("Sequence", true);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		// TODO implement actual sequence tokeniser
		Component.skipPast('{', cursor);
		StringBuilder sb = new StringBuilder();
		char c;
		String type;

		while ((c = cursor.advance()) != '}') {
			if (c == ';') {
				type = sb.toString().trim();

				if (type.isEmpty()) {
					
				}
			} else if (Character.isWhitespace(c)) {
				if (!(type = sb.toString()).isEmpty()) {
					
				}
			} else {
				sb.append(c);
			}
		}
	}
}
