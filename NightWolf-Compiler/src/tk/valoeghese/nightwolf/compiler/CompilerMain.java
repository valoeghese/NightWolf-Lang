package tk.valoeghese.nightwolf.compiler;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Queue;

import tk.valoeghese.common.ArgsData;
import tk.valoeghese.common.ArgsParser;
import tk.valoeghese.common.IProgramArgs;
import tk.valoeghese.common.util.FileUtils;
import tk.valoeghese.nightwolf.compiler.token.Token;
import tk.valoeghese.nightwolf.compiler.token.Tokeniser;

public class CompilerMain implements IProgramArgs, Runnable {
	public File sourceFile;
	private static File binFile;

	@Override
	public void run() {
		binFile.mkdir();

		Tokeniser tokeniser = new Tokeniser()
				.then("\\/\\/.*\n", Token.NONE) // COMMENT \/\/.*\n
				.then("\".+(?<!\\\\)\"", Token.STRING_VALUE) // ".+(?<!\\)"
				.then("\\(|;|,|\\[|\\]|\\)|\\{|\\}|\\.", Token.STRUCTURE_TOKEN) // \(|;|,|\[|\]|\)|\{|\}|\.
				.then("!=|==|>=|<=", Token.COMPARISON_BINARY_OPERATOR) // !=|==|>=|<=
				.then("=|->", Token.RELATION_TOKEN) // =|->
				.then("\\*|\\/", Token.MD_BINARY_OPERATOR) // \*|/
				.then("\\+|-", Token.AS_BINARY_OPERATOR) // \+|-
				.then("\\||&|!", Token.BOOLEAN_BINARY_OPERATOR) // \||&|!
				.then("<|>", Token.COMPARISON_BINARY_OPERATOR) // <|>
				.then("[0-9]+", Token.NUMERIC_VALUE) // [0-9]+
				.then("([A-Z]|[a-z])([A-Z]|[a-z]|[0-9])*", Token.STRING_LITERAL); // ([A-Z]|[a-z])([A-Z]|[a-z]|[0-9])*

		// TODO run process in parallel
		if (this.sourceFile.isDirectory()) {
			FileUtils.trailFilesOfExtension(this.sourceFile, "nw", (file, trail) -> {
				try {
					String fileData = new String(Files.readAllBytes(file.toPath()));
					compile(tokeniser, fileData);
				} catch (IOException e)  {
					throw new UncheckedIOException(e);
				}
			});
		} else {
			try {
				String fileData = new String(Files.readAllBytes(this.sourceFile.toPath()));
				compile(tokeniser, fileData);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	private static void compile(Tokeniser tokeniser, String fileData) {
		Queue<Token> tokens = tokeniser.tokenise(fileData);
		
		tokens.forEach(System.out::println);
	}

	@Override
	public void setArgs(ArgsData data) {
		this.sourceFile = new File(data.getString("src", () -> {
			throw new RuntimeException("Must specify a source file/folder! (-src <file>)");
		}));

		binFile = new File(data.getStringOrDefault("out", "bin"));
	}

	public static void main(String[] args) {
		instance = new CompilerMain();
		ArgsParser.of(args, instance).run();
	}

	public static CompilerMain instance;
}
