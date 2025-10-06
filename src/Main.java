import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class Main {
    static JLabel text = new JLabel("Gib deinen 1. Bruch ein:");
    static JTextField textfield1 = new JTextField(15); // Zähler 1
    static JTextField textfield2 = new JTextField(15); // Nenner 1
    static JTextField textfield3 = new JTextField(15); // Zähler 2
    static JTextField textfield4 = new JTextField(15); // Nenner 2
    static JLabel gleich = new JLabel("=");
    static JTextField textfield5 = new JTextField(15); // Ergebnis Zähler
    static JTextField textfield6 = new JTextField(15); // Ergebnis Nenner
    static JButton btnBerechnen = new JButton("Berechnen");

    static JComboBox<String> operatorBox = new JComboBox<>(new String[]{"+", "-", "*", "/"});

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::openUI);
    }

    public static void openUI() {
        JFrame frame = new JFrame("Test!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        textfield5.setEditable(false);
        textfield6.setEditable(false);

        NumberOnlyFilter onlyInts = new NumberOnlyFilter(true);
        ((AbstractDocument) textfield1.getDocument()).setDocumentFilter(onlyInts);
        ((AbstractDocument) textfield2.getDocument()).setDocumentFilter(onlyInts);
        ((AbstractDocument) textfield3.getDocument()).setDocumentFilter(onlyInts);
        ((AbstractDocument) textfield4.getDocument()).setDocumentFilter(onlyInts);

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        content.add(text, c);

        c.gridx = 0; c.gridy = 1; c.fill = GridBagConstraints.HORIZONTAL;
        content.add(textfield1, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        content.add(textfield2, c);

        c.gridx = 2; c.gridy = 1;
        content.add(operatorBox, c);

        c.gridx = 3;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        content.add(textfield3, c);

        c.gridx = 3;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        content.add(textfield4, c);

        c.gridx = 4;
        c.gridy = 1;
        content.add(gleich, c);

        c.gridx = 5;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        content.add(textfield5, c);

        c.gridx = 5;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        content.add(textfield6, c);

        c.gridx = 3;
        c.gridy = 3;
        content.add(btnBerechnen, c);


        btnBerechnen.addActionListener(e -> berechne());
        textfield4.addActionListener(e -> berechne());

        frame.setContentPane(content);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void berechne() {
        try {
            int z1 = parseInt(textfield1.getText(), "Zähler 1");
            int n1 = parseInt(textfield2.getText(), "Nenner 1");
            int z2 = parseInt(textfield3.getText(), "Zähler 2");
            int n2 = parseInt(textfield4.getText(), "Nenner 2");

            Bruch b1 = new Bruch(z1, n1);
            Bruch b2 = new Bruch(z2, n2);

            String op = (String) operatorBox.getSelectedItem();
            if (op == null) throw new IllegalStateException("Kein Operator ausgewählt.");

            Bruch erg;
            switch (op) {
                case "+": erg = b1.addiere(b2); break;
                case "-": erg = b1.subtrahiere(b2); break;
                case "*": erg = b1.multipliziere(b2); break;
                case "/": erg = b1.dividiere(b2); break;
                default: throw new IllegalStateException("Unbekannter Operator: " + op);
            }

            textfield5.setText(String.valueOf(erg.getZaehler()));
            textfield6.setText(String.valueOf(erg.getNenner()));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Eingabefehler", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static int parseInt(String s, String feldname) {
        if (s == null || s.trim().isEmpty())
            throw new NumberFormatException(feldname + " ist leer.");
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(feldname + " ist keine ganze Zahl: \"" + s + "\"");
        }
    }
}
