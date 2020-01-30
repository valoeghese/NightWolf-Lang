package tk.valoeghese.nightwolf.compiler;

import java.util.ArrayList;

class VarCollection {
	ArrayList<Entry> entries = new ArrayList<>();
	private String typeCache;

	VarCollection add(String type, String value) {
		this.entries.add(new Entry(type, value));
		this.typeCache = type;
		return this;
	}

	VarCollection add(String value) {
		return this.add(this.typeCache, value);
	}

	static class Entry {
		private String type;
		private String value;

		Entry(String type, String value) {
			this.type = type;
			this.value = value;
		}

		String getType() {
			return this.type;
		}

		String getValue() {
			return this.value;
		}
	}
}
