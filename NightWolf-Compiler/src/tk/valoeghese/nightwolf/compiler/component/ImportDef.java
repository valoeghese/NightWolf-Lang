package tk.valoeghese.nightwolf.compiler.component;

public class ImportDef extends SingleReadingComponent {
	public ImportDef() {
		super("ImportDef", false, ';');
	}

	public String getImport() {
		return this.getData(0);
	}
}
