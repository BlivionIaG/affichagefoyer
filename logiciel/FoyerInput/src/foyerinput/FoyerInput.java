/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foyerinput;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author kevin
 */
public class FoyerInput extends JPanel implements ActionListener {

    public FoyerInput() {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(text + newline);
        textField.selectAll();
        
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private static void gui() {
        JFrame frame = new JFrame("Liste d'attente Foyer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new FoyerInput());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui();
            }
        });
    }

    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
}
