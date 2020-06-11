package tk.valoeghese.fullmoon;

import javax.swing.UIManager;

public class FullMoonMain {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		FullMoonIDE ide = new FullMoonIDE();
		ide.setVisible(true);
	}
}
