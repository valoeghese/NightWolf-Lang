package tk.valoeghese.nightwolf.compiler.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	public abstract void parse(Cursor cursor) throws SyntaxError;

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
		private final char[] text;

		public char advance() throws SyntaxError {
			try {
				char c = this.text[this.current++];

				if (c == '\n') {
					++line;
					this.column = 1;
				} else if (Character.isISOControl(c)) {
					++this.column;
				}

				return c;
			} catch (ArrayIndexOutOfBoundsException e) {
				return '\u0000';
			}
		}

		public int getLine() {
			return this.line;
		}

		public int getColumn() {
			return this.column;
		}
	}
}
