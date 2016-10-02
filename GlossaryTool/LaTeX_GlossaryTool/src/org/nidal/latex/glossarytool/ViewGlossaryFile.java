/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * Class to View the Glossary File in a new window 
 * * not implemented at the moment*
 * @author nidal
 */

public class ViewGlossaryFile {    

  public void createAndShowGUI() {
  RSyntaxTextArea textAreaGlossaryFile = new RSyntaxTextArea(30, 90);
        JFrame frame = new JFrame("Glossary File");
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea field = new JTextArea(5, 50);
       // field.setText(Retext2);
        field.setLineWrap(true);
        field.setWrapStyleWord(true);
        field.setEditable(false);

        frame.add(new JScrollPane(field));

        Document doc1 = field.getDocument();
      
        frame.getContentPane().add(new JScrollPane(textAreaGlossaryFile));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

  

   
}

