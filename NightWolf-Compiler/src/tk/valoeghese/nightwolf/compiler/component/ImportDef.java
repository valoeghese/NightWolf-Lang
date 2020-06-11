package tk.valoeghese.nightwolf.compiler.component;

public class ImportDef extends SingleReadingComponent {
	public ImportDef() {
		super("ImportDef", false, ';', true);
	}

	public String getImport() {
		return this.getData(0);
	}
}
