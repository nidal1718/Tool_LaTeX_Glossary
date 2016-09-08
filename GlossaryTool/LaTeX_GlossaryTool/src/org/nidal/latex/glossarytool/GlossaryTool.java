/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.BorderLayout;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author nidal
 */
public class GlossaryTool extends JFrame implements ActionListener {

    private RSyntaxTextArea textArea = new RSyntaxTextArea("", 550, 320);
   
    private JMenuBar menubar = new JMenuBar(); //menubar item
    
    
     private JMenu file = new JMenu(); //  File menu
    
    private JMenu edit = new JMenu(); // edit menu
    private JMenu tools = new JMenu(); //  tools menu
    private JMenu help = new JMenu(); //  help menu
    
     
   
    private JMenuItem newFile = new JMenuItem();  //  a new option
    private JMenuItem openFile = new JMenuItem();  // an open option
    private JMenuItem saveFile = new JMenuItem(); // a save option
    private JMenuItem saveAsFile = new JMenuItem(); // a save as option
    private JMenuItem close = new JMenuItem(); // and a close option!
    
    private JMenuItem cut = new JMenuItem();  //  a cut option
    private JMenuItem copy = new JMenuItem();  // an copy option
    private JMenuItem paste = new JMenuItem(); // a paste option
    private JMenuItem undo = new JMenuItem(); // a undo as option
    private JMenuItem redo = new JMenuItem(); // a redo option!
    
    private JMenuItem add_gls = new JMenuItem(); // a tools option
    
      private JMenuItem about = new JMenuItem(); // about option!

    public GlossaryTool() {
//Reference : https://github.com/bobbylight/RSyntaxTextArea
//reference : http://www.dreamincode.net/forums/topic/66176-creating-a-basic-notepad-application/


        JPanel cp = new JPanel(new BorderLayout());
        
        
        this.setSize(500, 300);
        this.setTitle("Glossary Tool");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        
        //this.getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
      //this.getContentPane().add(textArea); 
        
       cp.add(sp);
       this.setContentPane(cp);

   
        
        // add our menu bar into the GUI
       this.setJMenuBar(this.menubar);
    
  
        this.menubar.add(this.file);
        this.menubar.add(this.edit);
        this.menubar.add(this.tools);
    
        
    //   this.menubar.add(this.help);


        this.file.setLabel("File");
        
        this.edit.setLabel("Edit");
        this.tools.setLabel("Tools");
    //    this.help.setLabel("Help");

         // new
        this.newFile.setLabel("New"); // set the label of the menu item
        this.newFile.addActionListener(this); // add an action listener (so we know when it's been clicked
      //  this.newFile.setShortcut(new MenuShortcut(KeyEvent.VK_N, false)); // set a keyboard shortcut
        this.file.add(this.newFile); // add it to the "File" menu
        
         // open
        this.openFile.setLabel("Open"); // set the label of the menu item
        this.openFile.addActionListener(this); // add an action listener (so we know when it's been clicked
   //     this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); // set a keyboard shortcut
        this.file.add(this.openFile); // add it to the "File" menu

        // and the save...
        this.saveFile.setLabel("Save");
        this.saveFile.addActionListener(this);
  //      this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
        this.file.add(this.saveFile);
        
        
        // and the save as
         this.saveAsFile.setLabel("Save As"); // set the label of the menu item
        this.saveAsFile.addActionListener(this); // add an action listener (so we know when it's been clicked
       // this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_N, false)); // set a keyboard shortcut
        this.file.add(this.saveAsFile); // add it to the "File" menu

        //  close option
        this.close.setLabel("Close");
        this.close.addActionListener(this);
      //  this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
        this.file.add(this.close);
        
        
        //Edit Menu tool
        
        // Undo
        this.undo.setLabel("Undo"); // set the label of the menu item
        this.undo.addActionListener(this); // add an action listener (so we know when it's been clicked
    //    this.undo.setShortcut(new MenuShortcut(KeyEvent.VK_Z, false)); // set a keyboard shortcut
        this.edit.add(this.undo); // add it to the "File" menu
        
         // Redo
        this.redo.setLabel("Redo"); // set the label of the menu item
        this.redo.addActionListener(this); // add an action listener (so we know when it's been clicked
    //    this.redo.setShortcut(new MenuShortcut(KeyEvent.VK_Y, false)); // set a keyboard shortcut
        this.edit.add(this.redo); // add it to the "File" menu

        // and cut
        this.cut.setLabel("Cut");
        this.cut.addActionListener(this);
      //  this.cut.setShortcut(new MenuShortcut(KeyEvent.VK_X, false));
        this.edit.add(this.cut);
        
         // and copy
        this.copy.setLabel("Copy");
        this.copy.addActionListener(this);
       // this.copy.setShortcut(new MenuShortcut(KeyEvent.VK_C, false));
        this.edit.add(this.copy);
        
         // and paste
        this.paste.setLabel("Paste");
        this.paste.addActionListener(this);
      //  this.paste.setShortcut(new MenuShortcut(KeyEvent.VK_V, false));
        this.edit.add(this.paste);

        
        //Tools menu
        // Add to Gls
        this.add_gls.setLabel("Add to Glossary");
        this.add_gls.addActionListener(this);
        //this.add_gls.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
        this.tools.add(this.add_gls);
        
        
           //help menu
        // about us
   //     this.about.setLabel("About Us");
    //    this.about.addActionListener(this);
  //      //this.add_gls.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
//        this.help.add(this.about);
        
        
        pack();
       // setLocationRelativeTo(null);
      
      
    }
      
      

    public void actionPerformed(ActionEvent e) {
        // if the source of the event was our "close" option
        if (e.getSource() == this.close) {
            this.dispose(); // dispose all resources and close the application
        } // if the source was the "open" option
        else if (e.getSource() == this.openFile) {
            JFileChooser open = new JFileChooser(); // open up a file chooser (a dialog for the user to browse files to open)
            int option = open.showOpenDialog(this); // get the option that the user selected (approve or cancel)
            // NOTE: because we are OPENing a file, we call showOpenDialog~
            // if the user clicked OK, we have "APPROVE_OPTION"
            // so we want to open the file

            if (option == JFileChooser.APPROVE_OPTION) {

                this.textArea.setText(""); // clear the TextArea before applying the file contents

                try {

                    // create a scanner to read the file (getSelectedFile().getPath() will get the path to the file)
                    Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));

                    while (scan.hasNext()) // while there's still something to read
                    {
                        this.textArea.append(scan.nextLine() + "\n"); // append the line to the TextArea
                    }
                } catch (Exception ex) { // catch any exceptions, and...

                    // ...write to the debug console
                    System.out.println(ex.getMessage());

                }

            }

        } // and lastly, if the source of the event was the "save" option
        else if (e.getSource() == this.saveFile) {
            JFileChooser save = new JFileChooser(); // again, open a file chooser
            int option = save.showSaveDialog(this); // similar to the open file, only this time we call
            // showSaveDialog instead of showOpenDialog
            // if the user clicked OK (and not cancel)
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    // create a buffered writer to write to a file
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(this.textArea.getText()); // write the contents of the TextArea to the file
                    out.close(); // close the file stream
                } catch (Exception ex) { // again, catch any exceptions and...
                    // ...write to the debug console
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      //  System.setProperty("apple.laf.useScreenMenuBar", "true");
        // Start all Swing applications on the EDT.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GlossaryTool().setVisible(true);
            }
        });
        // TODO code application logic here
    }

}
