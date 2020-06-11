package tk.valoeghese.nightwolf.compiler.component;

public class PackageDef extends SingleReadingComponent {
	public PackageDef() {
		super("PackageDef", false, ';');
	}

	public String getPackage() {
		return this.getData(0);
	}
}
