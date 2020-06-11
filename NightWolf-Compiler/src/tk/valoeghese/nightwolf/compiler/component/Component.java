package tk.valoeghese.nightwolf.compiler.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Component implements Iterable<Component> {
	public Component(String name, boolean container) {
		this.name = name;
		this.container = container;
	}

	private final String name;
	private final boolean container;
	private final List<Component> subComponents = new ArrayList<>();
	private final List<String> data = new ArrayList<>();

	protected void addData(String data) {
		this.data.add(data);
	}

	protected String getData(int index) {
		return this.data.get(index);
	}

	public void addComponent(Component c) throws UnsupportedOperationException {
		if (!this.container) {
			throw new UnsupportedOperationException("Component " + this.name + " not a container!");
		}

		this.subComponents.add(c);
	}

	@Override
	public Iterator<Component> iterator() {
		return new Iter(this.subComponents);
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

	private static class Iter implements Iterator<Component> {
		public Iter(List<Component> components) {
			this.components = components;
			this.firstInvalidIndex = components.size();
		}

		private final List<Component> components;
		private final int firstInvalidIndex;
		private int next = 0;

		@Override
		public boolean hasNext() {
			return this.next < this.firstInvalidIndex;
		}

		@Override
		public Component next() {
			return this.components.get(this.next++);
		}
	}
}
