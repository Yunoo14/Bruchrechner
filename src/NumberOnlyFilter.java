import javax.swing.text.*;

// Erlaubt nur ganze Zahlen; optional mit Minus vorne.
// Wirkt auch bei Copy/Paste und Drag&Drop.
public class NumberOnlyFilter extends DocumentFilter {
    private final boolean allowNegative;

    public NumberOnlyFilter(boolean allowNegative) {
        this.allowNegative = allowNegative;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        String neu = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()))
                .insert(offset, string).toString();
        if (isValid(neu)) super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) text = "";
        String alt = fb.getDocument().getText(0, fb.getDocument().getLength());
        String neu = alt.substring(0, offset) + text + alt.substring(offset + length);
        if (isValid(neu)) super.replace(fb, offset, length, text, attrs);
    }

    private boolean isValid(String s) {
        if (s.isEmpty()) return true;                 // leeres Feld okay
        if (allowNegative) return s.matches("-?\\d+"); // optionales Minus am Anfang
        return s.matches("\\d+");                      // nur Ziffern
    }
}
