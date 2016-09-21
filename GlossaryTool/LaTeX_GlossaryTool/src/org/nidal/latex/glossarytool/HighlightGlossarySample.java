/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class HighlightGlossarySample {

    private String Retext1 = "Extreme programming is one approach of agile software development which emphasizes on frequent releases on short development cycles which are called time boxes. This result in reducing the costs spend for changes, by having multiple short development cycles, rather than one long one. Extreme programming includes pair-wise programming (for code review, unit testing).Also it avoids implementing features which are not included in the current time box, so the schedule creep can be minimized.";
    private String Retext2 = "Extreme programming is one approach of agile software development which emphasizes on frequent releases in short development cycles which are called time boxes. This result in reducing the costs spend for changes, by having multiple short development cycles, rather than one long one. Extreme programming includes pair-wise programming (for code review, unit testing).Also it avoids implementing features which are not included in the current time box, so the schedule creep can be minimized.";

    private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

    public static void main(String args[]) {

        try {

            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    new HighlightGlossarySample().createAndShowGUI();
                }
            });

        } catch (InterruptedException ex) {
            ex.printStackTrace();

        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public void createAndShowGUI() {

        JFrame frame = new JFrame("Error-Highlighter");
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea field = new JTextArea(5, 50);
        field.setText(Retext2);
        field.setLineWrap(true);
        field.setWrapStyleWord(true);
        field.setEditable(false);

        frame.add(new JScrollPane(field));

        JTextArea area = new JTextArea(5, 50);
        area.setText(Retext1);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        Document doc1 = field.getDocument();
        Document doc2 = area.getDocument();

        int max = Math.min(doc1.getLength(), doc2.getLength());
        int startPos = 0;
        try {
            for (int pos = 0; pos < max; pos++) {

                if (doc1.getText(pos, 1).equals(" ")) {

                    int endPos = pos;
                    String parent = doc1.getText(startPos, endPos - startPos);
                    String child = doc2.getText(startPos, endPos - startPos);
                    if (!parent.equals(child)) {

                        highlight(field, startPos, endPos);
                        highlight(area, startPos, endPos);

                    }

                    startPos = endPos + 1;

                }

            }
        } catch (BadLocationException exp) {
            exp.printStackTrace();
        }

        frame.getContentPane().add(new JScrollPane(area));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void highlight(JTextComponent textComp, int startPos, int endPos) throws BadLocationException {

        Highlighter hilite = textComp.getHighlighter();
        hilite.addHighlight(startPos, endPos, myHighlightPainter);

    }

    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {

            super(color);

        }
    }
}
