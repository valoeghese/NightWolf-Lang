package tk.valoeghese.nightwolf.compiler;

import java.io.File;
import java.io.IOException;

import tk.valoeghese.nightwolf.compiler.component.Component;

public final class NightWolfProgram extends Component {
	public NightWolfProgram(String fileData) {
		super("Program", true);

		char[] data = fileData.toCharArray();
		StringBuilder sb = new StringBuilder();

		// stage 1: parsing 1/2
		// don't even make notes about fields etc. yet, that is the second stage
		// the last (third) stage is the compile stage, which is done with ASM.

		int line = 1;
		int column = 1;

		for (char c : data) {
			boolean whitespace = Character.isWhitespace(c);

			if (c == '\n') {
				++line;
				column = 1;
			} else if (Character.c != '\r') {
				++column;
			}
		}
	}

	public ClassFile compile() {
		return null;
	}

	public void compile(File output) throws IOException {
		ClassFile compiled = this.compile();

		// TODO byte output
	}

}
