package tk.valoeghese.nightwolf.compiler.component.datatype;

import java.util.ArrayList;
import java.util.List;

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

		while (cursor.advance() != '}') {
			if (!Character.isWhitespace(c)) {
				cursor.rewind(); // I added this hacky method and now use it all the time wtf

				Line line = new Line();
				line.tokenise(cursor);
				this.addComponent(line);
			}
		}
	}

	public static class Line extends Component {
		public Line() {
			super("Line", true);
		}

		@Override
		public void tokenise(Cursor cursor) throws SyntaxError {
			StringBuilder sb = new StringBuilder();
			char c;
			char prev = '\u0000';
			String type;
			Cursor backup = new Cursor(cursor);
			List<String> proto = new ArrayList<>();

			// collect syntax structure via proto-tokens
			while ((c = cursor.advance()) != ';') {
				if (c == '}') {
					throw SyntaxError.invalidCharacter(c, cursor);
				}
			}

			// choose what this line represents from proto structure and properly tokenise it therewith
		}

	}
}
