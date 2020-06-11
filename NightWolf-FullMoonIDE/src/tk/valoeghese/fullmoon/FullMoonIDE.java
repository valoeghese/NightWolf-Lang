package tk.valoeghese.fullmoon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FullMoonIDE extends JFrame implements ActionListener {
	public FullMoonIDE() {
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("FullMoon IDE");
		loadImage("assets/fullmoon/icon.png", this::setIconImage); // load image
		this.getContentPane().setBackground(Color.getHSBColor(0.6667f, 0.2f, 0.3f)); 

		this.editor = new JTextPane();
		this.editor.setFont(new Font("Courier", Font.PLAIN, 14));

		this.menuBar = new JMenuBar();
		this.menuBar.setBackground(Color.getHSBColor(0.6667f, 0.2f, 0.6f));

		// create menus
		this.createMenu("File", "New", "Open", "Save");

		this.addSyntaxHighlighting();

		// add components
		this.add(this.editor);
		this.setJMenuBar(this.menuBar);
	}

	private void addSyntaxHighlighting() {
		this.highlighter = new SyntaxHighlighter(this.editor);

		this.editor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				highlighter.highlight();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				highlighter.highlight();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}

	private final JTextPane editor;
	private final JMenuBar menuBar;
	private SyntaxHighlighter highlighter;

	private void createMenu(String name, String... buttons) {
		JMenu menu = new JMenu(name);

		for (String s : buttons) {
			JMenuItem item = new JMenuItem(s);
			item.addActionListener(this);
			menu.add(item);
		}
		
		this.menuBar.add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	private static final void loadImage(String location, Consumer<BufferedImage> handler) {
		try {
			URL url = FullMoonIDE.class.getClassLoader().getResource(location); // get resource in jar
			handler.accept(ImageIO.read(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final int WIDTH = 900;
	private static final int HEIGHT = 550;

	// why do I even need this
	private static final long serialVersionUID = -894001304187080569L;
}
