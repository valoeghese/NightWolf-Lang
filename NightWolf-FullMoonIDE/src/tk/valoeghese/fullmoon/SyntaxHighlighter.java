package tk.valoeghese.fullmoon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;

class SyntaxHighlighter {
	SyntaxHighlighter(JTextPane parent) {
		this.parent = parent;
		this.formats = new HashMap<>();
		this.keywords = new ArrayList<>();
		
		add("import", null);
	}

	private JTextPane parent;
	private final Map<String, HighlightPainter> formats;
	private final List<String> keywords;
	private String currentWord;
	private String text;
	
	private void add(String text, Font font) {
		this.formats.put(text, font);
		this.keywords.add(text);
	}

	void highlight() {
		Highlighter highlighter = this.parent.getHighlighter();
		highlighter.removeAllHighlights();

		int prevWordIndex = 0;
		Document document = this.parent.getDocument();

		try {
			this.text = document.getText(0, document.getLength());
		} catch (BadLocationException e) {
		}

		while ((prevWordIndex = findText(prevWordIndex)) != -1) {
			int end = prevWordIndex + this.currentWord.length();
			
			try {
				highlighter.addHighlight(prevWordIndex, end, this.formats.get(text));
			}
		}
	}

	private int findText(int prevWordIndex) {
		int earliestIndex = -1;
		String text = null;

		for (String txt : this.keywords) {
			int i = this.text.indexOf(txt);

			if (i != -1 && i < earliestIndex) {
				earliestIndex = i;
				text = txt;
			}
		}

		if (text != null) {
			this.currentWord = text;
		}

		return earliestIndex;
	}
	
	private final HighlightPainter KEYWORD = new SyntaxPainter(Color.RED);

	private class SyntaxPainter implements HighlightPainter {
		SyntaxPainter(Color colour) {
			this.colour = colour;
		}
		
		private final Color colour;

		@Override
		public void paint(Graphics g, int start, int end, Shape bounds, JTextComponent c) {
//			c.setFont();
		}
	}
}