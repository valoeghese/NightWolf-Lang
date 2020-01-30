package tk.valoeghese.nightwolf.compiler;

import java.io.DataOutputStream;
import java.io.IOException;

public final class ClassFile {
	public ClassFile() {
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeInt(0xCAFEBABE);
	}
}
