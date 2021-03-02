package tk.valoeghese.nightwolf.compiler;

import java.io.File;

import tk.valoeghese.common.ArgsData;
import tk.valoeghese.common.ArgsParser;
import tk.valoeghese.common.IProgramArgs;

public class CompilerMain implements IProgramArgs, Runnable {
	public File sourceFile;

	@Override
	public void run() {
		
	}

	@Override
	public void setArgs(ArgsData data) {
		this.sourceFile = new File(data.getString("src", () -> {
			throw new RuntimeException("Must specify a source file/folder! (-src <file>)");
		}));
	}

	public static void main(String[] args) {
		instance = new CompilerMain();
		ArgsParser.of(args, instance).run();
	}

	public static CompilerMain instance;
}
