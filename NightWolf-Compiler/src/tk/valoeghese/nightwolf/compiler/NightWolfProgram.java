package tk.valoeghese.nightwolf.compiler;

import java.io.File;
import java.io.IOException;

import tk.valoeghese.nightwolf.compiler.component.Component;
import tk.valoeghese.nightwolf.compiler.component.ImportDef;
import tk.valoeghese.nightwolf.compiler.component.PackageDef;
import tk.valoeghese.nightwolf.compiler.component.VarDef;

public final class NightWolfProgram extends Component {
	public NightWolfProgram(String fileData) {
		super("Program", true);

		Cursor cursor = new Cursor(fileData.toCharArray());

		// stage 1: tokeniser
		// don't even make notes about fields etc. yet, that is the second stage
		// the last (third) stage is the compile stage, which is done with ASM.
		this.tokenise(cursor);
	}

	@Override
	public void tokenise(Cursor cursor) throws SyntaxError {
		this.reset();

		StringBuilder sb = new StringBuilder();
		char c;
		String type;
		boolean head = true;

		while ((c = cursor.advance()) != '\u0000') {
			if (Character.isWhitespace(c)) {
				if (!(type = sb.toString()).isEmpty()) {
					// head parts, such as 'package', and 'using'.
					if (head) {
						switch (type) {
						case "package":
							PackageDef pkg = new PackageDef();
							pkg.tokenise(cursor);
							this.addComponent(pkg);
							break;
						case "using":
							ImportDef using = new ImportDef();
							using.tokenise(cursor);
							this.addComponent(using);
							break;
						default:
							head = false;
							break;
						}
					}

					// in case it's switching out of head mode and falling back from earlier (see default block), don't use else.s
					if (!head) {
						switch (type) {
						default:
							if (VarDef.isVarType(type)) {
								VarDef variable = new VarDef(type);
								variable.tokenise(cursor);
								this.addComponent(variable);
							} else {
								throw SyntaxError.unexpectedToken(type, cursor);
							}
							break;
						}
					}

					sb = new StringBuilder();
				}
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
