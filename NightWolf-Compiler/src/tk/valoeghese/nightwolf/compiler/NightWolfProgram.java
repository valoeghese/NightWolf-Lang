package tk.valoeghese.nightwolf.compiler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class NightWolfProgram {
	public NightWolfProgram(String fileData) {
		char[] data = fileData.toCharArray();
		StringBuilder sb = new StringBuilder();
		ReadMode mode = ReadMode.FILE_HEAD;
		boolean countingWhitespace = true;
		boolean functionFlag = true;

		int line = 1;
		int column = 1;

		for (char c : data) {
			boolean whitespace = Character.isWhitespace(c);

			if (countingWhitespace) {
				if (whitespace) {
					if (c == '\n') {
						++line;
						column = 1;
					} else if (c != '\r') {
						++column;
					}
					continue;
				} else {
					countingWhitespace = false;
				}
			}

			switch (c) {
			case ' ':
				String item = sb.toString();

				if (mode == ReadMode.FILE_HEAD) {
					if (item.equals("package")) {
						this.keyCacheType = KeyType.PACKAGE;
						mode = ReadMode.UNQUOTED_STRING;
						countingWhitespace = true;
					} else if (item.equals("using")) {
						this.keyCacheType = KeyType.IMPORT;
						mode = ReadMode.UNQUOTED_STRING;
						countingWhitespace = true;
					} else if (item.equals("func")) {
						this.keyCacheType = KeyType.VARNAME;
						mode = ReadMode.UNQUOTED_STRING;
						countingWhitespace = true;
						functionFlag = true;
					}
				} else if (mode == ReadMode.UNQUOTED_STRING) {
					break;
				}

				sb = new StringBuilder();
				break;
			case ';':
			case '{':
			case '}':
				if (mode == ReadMode.UNQUOTED_STRING) {
					if (this.keyCacheType == KeyType.PACKAGE) {
						if (c == ';') {
							mode = ReadMode.FILE_HEAD;

							if (this.pkg == null) {
								this.pkg = sb.toString();
								sb = new StringBuilder();
								countingWhitespace = true;
								break;
							}
						}
					} else if (this.keyCacheType == KeyType.IMPORT) {
						if (c == ';') {
							mode = ReadMode.FILE_HEAD;

							this.imports.add(sb.toString());
							sb = new StringBuilder();
							countingWhitespace = true;
							break;

						}
					} else if (this.keyCacheType == KeyType.IMPORT) {
						if (c == ';') {
							mode = ReadMode.FILE_HEAD;

							this.imports.add(sb.toString());
							sb = new StringBuilder();
							countingWhitespace = true;
							break;
						}
					}
				}

				throw new InvalidCharacterSyntaxError(c, line, column);
			default:
				if (!whitespace) {
					sb.append(c);
				}
				break;
			}

			if (c == '\n') {
				++line;
				column = 1;
			} else if (c != '\r') {
				++column;
			}
		}
	}

	private String keyCache;
	private KeyType keyCacheType;

	private String pkg = null;
	private final List<String> imports = new ArrayList<>();
	private final Map<String, Object> fields = new LinkedHashMap<>(); // Methods are treated as first-class citizens (probably will be quite hacky at compile time however)

	public ClassFile compile() {
		return null;
	}

	public void compile(File output) throws IOException {
		ClassFile compiled = this.compile();

		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output))) {
			compiled.write(out);
		}
	}

	public String getPackage() {
		return this.pkg;
	}

	public String[] getImports() {
		return this.imports.toArray(new String[0]);
	}

	public static class Method {
		private Method() {
		}
	}
}
