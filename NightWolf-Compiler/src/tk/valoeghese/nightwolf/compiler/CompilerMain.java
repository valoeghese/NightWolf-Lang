package tk.valoeghese.nightwolf.compiler;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.function.Consumer;

import tk.valoeghese.common.ArgsData;
import tk.valoeghese.common.ArgsParser;
import tk.valoeghese.common.IProgramArgs;
import tk.valoeghese.common.util.FileUtils;
import tk.valoeghese.nightwolf.compiler.component.datatype.VarDef;

public final class CompilerMain implements IProgramArgs, Runnable {
	public File sourceFile;

	@Override
	public void setArgs(ArgsData data) {
		this.sourceFile = new File(data.getString("src", () -> {
			throw new RuntimeException("Must specify a source file/folder! (-src <file>)");
		}));
	}

	@Override
	public void run() {
		// make sure VarDef.Type is initialised
		VarDef.Type.FUNC.equals(null);

		if (sourceFile.isDirectory()) {
			FileUtils.trailFilesOfExtension(this.sourceFile, "nw", (file, trail) -> {
				try {
					String fileData = new String(Files.readAllBytes(file.toPath()));
					NightWolfProgram program = new NightWolfProgram(fileData);
				} catch (IOException e)  {
					throw new UncheckedIOException(e);
				}
			});
		} else {
			String fileData;

			try {
				fileData = new String(Files.readAllBytes(this.sourceFile.toPath()));
				NightWolfProgram program = new NightWolfProgram(fileData);
				System.out.println(program);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	private static <T> void debugEach(T[] array, Consumer<T> cb) {
		for (T i : array) {
			cb.accept(i);
		}
	}

	public static void main(String[] args) {
		instance = new CompilerMain();
		ArgsParser.of(args, instance).run();
	}

	public static CompilerMain instance;
}
