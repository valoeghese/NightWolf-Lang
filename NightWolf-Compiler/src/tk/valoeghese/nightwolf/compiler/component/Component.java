package tk.valoeghese.nightwolf.compiler.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import tk.valoeghese.nightwolf.compiler.SyntaxError;

public abstract class Component implements Iterable<Component> {
	public Component(String name, boolean container) {
		this.name = name;
		this.container = container;
	}

	private final String name;
	private final boolean container;
	private final List<Component> subComponents = new ArrayList<>();
	private final List<String> data = new ArrayList<>();

	/**
	 * Parse the current string of text, up until the section is over.
	 * @param cursor the characters to parse from; holds useful data about it as well.
	 */
	public abstract void tokenise(Cursor cursor) throws SyntaxError;

	protected void reset() {
		this.subComponents.clear();
		this.data.clear();
	}

	protected void addData(String data) {
		this.data.add(data);
	}

	protected String getData(int index) {
		return this.data.get(index);
	}

	protected void addComponent(Component c) throws UnsupportedOperationException {
		if (!this.container) {
			throw new UnsupportedOperationException("Component " + this.name + " not a container!");
		}

		this.subComponents.add(c);
	}

	protected Component getComponent(int index) {
		return this.subComponents.get(index);
	}

	@Override
	public Iterator<Component> iterator() {
		return this.subComponents.iterator();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(this.name);
		boolean flag = false;

		if (!this.data.isEmpty()) {
			result.append('<');

			for (String data : this.data) {
				if (flag) {
					result.append(',');
				} else {
					flag = true;
				}

				result.append(data);
			}

			result.append('>');
		}

		if (this.container) {
			result.append('{');
			flag = false;

			for (Component component : this.subComponents) {
				if (flag) {
					result.append(',');
				} else {
					flag = true;
				}

				result.append(component);
			}

			result.append('}');
		}

		return result.toString();
	}

	public static class Cursor {
		public Cursor(char[] text) {
			this.text = text;
		}

		private int line = 1;
		private int column = 1;
		private int current = 0;
		private Stack<Integer> edits = new Stack<>();
		private final char[] text;

		public char advance() throws SyntaxError {
			try {
				char c = this.text[this.current++];

				if (c == '\n') {
					++this.line;
					this.edits.push(this.column);
					this.column = 1;
				} else if (!Character.isISOControl(c)) {
					++this.column;
					this.edits.push(0);
				}

				return c;
			} catch (ArrayIndexOutOfBoundsException e) {
				return '\u0000';
			}
		}

		public boolean rewind() {
			if (this.current <= 0) {
				return false;
			}

			--this.current;
			System.out.println(this.text[this.current] == '\n');

			int edit = this.edits.pop();
			System.out.println(edit);

			if (edit == 0) {
				this.column--;
			} else {
				--this.line;
				this.column = edit;
			}

			return true;
		}

		public int getLine() {
			return this.line;
		}

		public int getColumn() {
			return this.column;
		}
	}

	/**
	 * Typical procession to the next whitespace character, ignoring and appending leading whitespace.
	 */
	public static void proceed(StringBuilder sb, Cursor cursor) {
		char c;

		while (!Character.isWhitespace(c = cursor.advance()) || sb.toString().trim().isEmpty()) {
			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}

			sb.append(c);
		}
	}

	public static void proceedNonWhitespace(StringBuilder sb, char target, Cursor cursor) {
		char c;

		while ((c = cursor.advance()) != target) {
			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}

			sb.append(c);
		}
	}

	public static void skipPast(char target, Cursor cursor) {
		char c = (char) ((int) target + 1);

		while (c != target) {
			c = cursor.advance();

			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}
		}
	}

	public static boolean skipPastEither(char falseTarget, char trueTarget, Cursor cursor) {
		char c = (char) ((int) falseTarget + 1);

		if (c == trueTarget) {
			++c;
		}

		while (c != falseTarget && c != trueTarget) {
			c = cursor.advance();

			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}
		}

		return c == trueTarget;
	}

	public static boolean skipPastEitherOnlyWhitespace(char falseTarget, char trueTarget, Cursor cursor) {
		char c;

		while (true) {
			c = cursor.advance();

			if (c == falseTarget || c == trueTarget) {
				break;
			}

			if (c == '\u0000') {
				throw SyntaxError.eof(cursor);
			}

			if (!Character.isWhitespace(c)) {
				throw SyntaxError.invalidCharacter(c, cursor);
			}
		}

		return c == trueTarget;
	}
}
