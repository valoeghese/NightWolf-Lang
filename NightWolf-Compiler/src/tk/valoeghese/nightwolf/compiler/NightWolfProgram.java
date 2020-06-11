package tk.valoeghese.nightwolf.compiler;

import java.io.File;
import java.io.IOException;

import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.ImportDef;
import tk.valoeghese.nightwolf.compiler.component.PackageDef;

public final class NightWolfProgram extends Component {
	public NightWolfProgram(String fileData) {
		super("Program", true);

		Cursor cursor = new Cursor(fileData.toCharArray());
		StringBuilder sb = new StringBuilder();

		// stage 1: parsing 1/2
		// don't even make notes about fields etc. yet, that is the second stage
		// the last (third) stage is the compile stage, which is done with ASM.
		this.parse(cursor);
	}

	@Override
	public void parse(Cursor cursor) throws SyntaxError {
		this.reset();

		StringBuilder sb = new StringBuilder();
		char c;

		while ((c = cursor.advance()) != '\u0000') {
			if (Character.isWhitespace(c)) {
				String type = sb.toString();

				switch (type) {
				case "package":
					PackageDef pkg = new PackageDef();
					pkg.parse(cursor);
					this.addComponent(pkg);
					break;
				case "using":
					ImportDef using = new ImportDef();
					using.parse(cursor);
					this.addComponent(using);
					break;
				}

				sb = new StringBuilder();
			} else {
				sb.append(c);
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
