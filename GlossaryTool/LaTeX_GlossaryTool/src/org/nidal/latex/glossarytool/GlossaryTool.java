/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.TextAction;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.rsta.ui.SizeGripIcon;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.FindToolBar;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.ReplaceToolBar;
import org.fife.rsta.ui.search.SearchEvent;
import static org.fife.rsta.ui.search.SearchEvent.Type.FIND;
import static org.fife.rsta.ui.search.SearchEvent.Type.MARK_ALL;
import static org.fife.rsta.ui.search.SearchEvent.Type.REPLACE;
import static org.fife.rsta.ui.search.SearchEvent.Type.REPLACE_ALL;
import org.fife.rsta.ui.search.SearchListener;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;
/**
 *
 * @author nidal
 */
//public class GlossaryTool extends JFrame implements ActionListener {
public class GlossaryTool extends JFrame implements SearchListener{
    RSyntaxTextArea textArea = new RSyntaxTextArea(25,80);
     

    String filename = "";
    String filename_final ;
    private KeyListener k1 ;
    String applicationName = "LaTeX_GlossaryTool";
    String holdText;
    String fn;
    String dir;
    boolean textChanged = false;
    private String fileName;
    private CollapsibleSectionPanel csp;
  //  private RSyntaxTextArea textArea;
    private FindDialog findDialog;
    private ReplaceDialog replaceDialog;
    private ReplaceDialog add_glsDialog;
    private FindToolBar findToolBar;
    private ReplaceToolBar replaceToolBar;
    private StatusBar statusBar;
    Clipboard clip = getToolkit().getSystemClipboard();
    
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    private JMenuBar menubar = new JMenuBar(); //menubar item
    private JPopupMenu popup = textArea.getPopupMenu();

    private JMenu file = new JMenu(); //  File menu
    private JMenu edit = new JMenu(); // edit menu
    private JMenu tools = new JMenu(); //  tools menu
    private JMenu help = new JMenu(); //  help menu
    private JDialog d1;

    private JMenuItem newFile = new JMenuItem();  //  a new option
    private JMenuItem openFile = new JMenuItem();  // an open option
    private JMenuItem saveFile = new JMenuItem(); // a save option
    private JMenuItem saveasFile = new JMenuItem(); // a save as option
    private JMenuItem exitFile = new JMenuItem(); // and a close option!

    private JMenuItem cut = new JMenuItem();  //  a cut option
    private JMenuItem copy = new JMenuItem();  // an copy option
    private JMenuItem paste = new JMenuItem(); // a paste option
    private JMenuItem undo = new JMenuItem(); // a undo as option
    private JMenuItem redo = new JMenuItem(); // a redo option!
    int ctrl = getToolkit().getMenuShortcutKeyMask();
    int shift = InputEvent.SHIFT_MASK;
    KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl | shift);

    private JMenuItem add_gls = new JMenuItem(); // a tools option

    private JMenuItem about = new JMenuItem(); // about option!

    public GlossaryTool() {
//Reference : https://github.com/bobbylight/RSyntaxTextArea
//reference : http://www.dreamincode.net/forums/topic/66176-creating-a-basic-notepad-application/

        initSearchDialogs();
        JPanel cp = new JPanel(new BorderLayout());
         int c = getToolkit().getMenuShortcutKeyMask();
            
    
        csp = new CollapsibleSectionPanel();
        cp.add(csp);

          this.setSize(500, 300);
        this.setTitle("Glossary Tool");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
        textArea.setCodeFoldingEnabled(false);
        textArea.setMarkOccurrences(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);

        //this.getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
        //this.getContentPane().add(textArea); 
        cp.add(sp);
        ErrorStrip errorStrip = new ErrorStrip(textArea);
		cp.add(errorStrip, BorderLayout.LINE_END);
        this.setContentPane(cp);

        
        textArea.addKeyListener(k1);
        
        statusBar = new StatusBar();
        cp.add(statusBar, BorderLayout.SOUTH);
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
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, c)); 
        newFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });

        this.file.add(this.newFile); // add it to the "File" menu

        // open
        this.openFile.setLabel("Open"); // set the label of the menu item
        //  this.openFile.addActionListener(this); // add an action listener (so we know when it's been clicked
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
      openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, c)); 
        this.file.add(this.openFile); // add it to the "File" menu

        // and the save...
        this.saveFile.setLabel("Save");
        //  this.saveFile.addActionListener(this);    
        saveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });     
          saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, c));    
        this.file.add(this.saveFile);
     

        // and the save as
        this.saveasFile.setLabel("Save As"); // set the label of the menu item
        //  this.saveasFile.addActionListener(this); // add an action listener (so we know when it's been clicked
        saveasFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveasMenuActionPerformed(evt);
            }
        });
       // saveasFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, c)); 
        this.file.add(this.saveasFile); // add it to the "File" menu

        //  close option
        this.exitFile.setLabel("Exit");
        exitFile.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        exitFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, c));   
        this.file.add(this.exitFile);

     
         
          //Edit Menu tool
         
           this.undo.setLabel("Undo"); // set the label of the menu item
      
          this.undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoEditActionPerformed(evt);
            }
        });
         this.undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, c)); 
          this.edit.add(this.undo); // add it to the "File" menu
        
        
     
         
          // Redo 
          this.redo.setLabel("Redo"); // set the label of the menu item
         this.redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoEditActionPerformed(evt);
            }
        });
          this.redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, c)); 
          this.edit.add(this.redo); // add it to the "File" menu
         
          // and cut 
          this.cut.setLabel("Cut");
         this.cut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutEditActionPerformed(evt);
            }
        });
          this.cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, c)); 
          this.edit.add(this.cut); // add it to the "File" menu
         
          // and copy 
          this.copy.setLabel("Copy");
         this.copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyEditActionPerformed(evt);
            }
        });
          this.copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, c)); 
           this.edit.add(this.copy); // add it to the "File" menu
         
          // and paste 
          this.paste.setLabel("Paste");
        this.paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteEditActionPerformed(evt);
            }
        });
         paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, c)); 
          this.edit.add(this.paste); // add it to the "File" menu
         
        
        
        edit.addSeparator();
        edit.add(new JMenuItem(new ShowFindDialogAction()));
        edit.add(new JMenuItem(new ShowReplaceDialogAction()));
      
//        Action a = csp.addBottomComponent(ks, findToolBar);
//        a.putValue(Action.NAME, "Show Find Search Bar");
//        edit.add(new JMenuItem(a));

        //ks = KeyStroke.getKeyStroke(KeyEvent.VK_H, ctrl|shift);
//        a = csp.addBottomComponent(ks, replaceToolBar);
//        a.putValue(Action.NAME, "Show Replace Search Bar");
//        edit.add(new JMenuItem(a));
        /*
        
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
        
         */
        
           popup.addSeparator();
        popup.add(new JMenuItem(new add_gls()));
        
        
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        
        
//        k1 = new KeyAdapter() {
    textArea.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		 // TODO add your handling code here:
        if (RTextEvent.TEXT_VALUE_CHANGED != 0) {
            if (!textChanged)
                setTitle("* " + getTitle());
            
            textChanged = true;
            saveFile.setEnabled(true);
		}
                }
	});
    
        pack();
        setLocationRelativeTo(null);

    }
            
    
    private void addItem(Action a, ButtonGroup bg, JMenu menu) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(a);
		bg.add(item);
		menu.add(item);
	}

    public void checkFile() {
        BufferedReader read;
        StringBuffer sb = new StringBuffer();

        try {
            read = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = read.readLine()) != null) {
                sb.append(line + "\n");
            }
            // textArea1.setText(sb.toString());
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.setText(sb.toString());

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException ioe) {
        }

    }

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // if (textArea1.getText().length() < 1) {
        if (textArea.getText().length() < 1) {
            FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
            fd.show();
            if (fd.getFile() != null) {
                fileName = fd.getDirectory() + fd.getFile();
                this.filename_final = fd.getDirectory() + fd.getFile().replaceFirst(".tex", ".gls");
                setTitle(fileName);
                checkFile();
            }
            // textArea1.requestFocus();
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.requestFocus();

        } else if (!textChanged) {
            FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
            fd.show();
            if (fd.getFile() != null) {
                fileName = fd.getDirectory() + fd.getFile();
                 this.filename_final = fd.getDirectory() + fd.getFile().replaceFirst(".tex", ".gls");
                setTitle(fileName);
                checkFile();

            }
            // textArea1.requestFocus();
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.requestFocus();

        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save before exiting this application? ");

            if (confirm == JOptionPane.YES_OPTION) {
                if ("".equals(filename)) {
                    saveAs();
                } else {
                    save(filename);
                }

                FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
                fd.show();

                if (fd.getFile() != null) {
                    fileName = fd.getDirectory() + fd.getFile();
                     this.filename_final = fd.getDirectory() + fd.getFile().replaceFirst(".tex", ".gls");
                    setTitle(fileName);
                    checkFile();

                }
                //   textArea1.requestFocus();
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
                textArea.requestFocus();

            } else if (confirm == JOptionPane.NO_OPTION) {

                FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
                fd.show();

                if (fd.getFile() != null) {
                    fileName = fd.getDirectory() + fd.getFile();
                     this.filename_final = fd.getDirectory() + fd.getFile().replaceFirst(".tex", ".gls");
                    setTitle(fileName);
                    checkFile();
                }
            }
        }
    }

    private void saveasMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        saveAs();
    }

    private void add_glsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        saveAs();
    }

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (filename.equals("")) {
            saveAs();
        } else {
            save(filename);
        }
    }

    
    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        newFile();
    }

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // if ("".equals(textArea1.getText())) {
        if ("".equals(textArea.getText())) {
            System.exit(0);
        } else if (!textChanged) {
            System.exit(0);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save before exiting this application");
            if (confirm == JOptionPane.YES_OPTION) {
                if (filename.equals("")) {
                    saveAs();
                } else {
                    save(filename);
                }

            }
            if (confirm == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    private void undoEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
       
    }
    
    private void redoEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        
    }
    private void cutEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
       
    }
    
    private void copyEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        
    }
    private void pasteEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
       
    }
  
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
        //  if ("".equals(textArea1.getText())) {
        if ("".equals(textArea.getText())) {
            System.exit(0);
        } else if (!textChanged) {
            System.exit(0);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save before exiting this application ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (filename.equals("")) {
                    saveAs();
                } else {
                    save(filename);
                }
                System.exit(0);
            }
            if (confirm == JOptionPane.NO_OPTION) {
                System.exit(0);

            }
        }
    }

    private void saveAs() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        FileDialog fd = new FileDialog(GlossaryTool.this, "Save", FileDialog.SAVE);
        fd.show();
        if (fd.getFile() != null) {
            fn = fd.getFile();
            dir = fd.getDirectory();
            filename = dir + fn + ".tex";
             this.filename_final = fd.getDirectory() + fd.getFile().replaceFirst(".tex", ".gls");

            setTitle(filename);

            try {
                DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
//                holdText = textArea1.getText();
                holdText = textArea.getText();
                BufferedReader br = new BufferedReader(new StringReader(holdText));

                while ((holdText = br.readLine()) != null) {
                    d.writeBytes(holdText + "\r\n");
                    d.close();

                }
            } catch (Exception e) {
                System.out.println("File not found");
            }

            //textArea1.requestFocus();
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.requestFocus();
            save(filename);
        }

    }
    
    private void saveGlossary_new_entry() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       // FileDialog fd = new FileDialog(GlossaryTool.this, "Save", FileDialog.SAVE);
      //  fd.show();
        //if (fd.getFile() != null) {
           // fn = fd.getFile();
           // dir = fd.getDirectory();
            //filename = dir + fn + ".gls";
            
          //  String filename_gls = filename_final + ".gls";

           // setTitle(filename);

            try {
                DataOutputStream d = new DataOutputStream(new FileOutputStream(filename_final));
                System.out.println(filename_final);
//                holdText = textArea1.getText();
                holdText = textArea.getText();
                BufferedReader br = new BufferedReader(new StringReader(holdText));

                while ((holdText = br.readLine()) != null) {
                    d.writeBytes(holdText + "\r\n");
                    d.close();

                }
            } catch (Exception e) {
                System.out.println("File not found");
            }

            //textArea1.requestFocus();
           // textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
           // textArea.requestFocus();
           
           try {
            FileWriter out;
           // out = new FileWriter(fn);
            out = new FileWriter(filename_final);
            out.write(textArea.getText());
            out.close();

        } catch (Exception err) {
            System.out.println("Error: " + err);
        }
      //  }

    }

    private void save(String filename) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        setTitle(applicationName + " " + filename);
        try {
            FileWriter out;
            out = new FileWriter(fn);
            //  out.write(textArea1.getText());
            out.write(textArea.getText());
            out.close();

        } catch (Exception err) {
            System.out.println("Error: " + err);
        }
        textChanged = false;
        saveFile.setEnabled(false);

    }

    private void newFile() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        if (textArea1.getText().length() < 1) {
        if (textArea.getText().length() < 1) {
            setTitle("Untitled-" + applicationName);
//            textArea1.setText("");
            textArea.setText("");
            textChanged = false;

        } else if (!textChanged) {
            setTitle("Untitled-" + applicationName);
//            textArea1.setText("");
            textArea.setText("");
            textChanged = false;

        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save before exiting this application?");

            if (confirm == JOptionPane.YES_OPTION) {
                if ("".equals(filename)) {
                    saveAs();
                } else {
                    save(filename);
                }

                setTitle(applicationName);
                filename = "";
                //  textArea1.setText("");
                textArea.setText("");
                textChanged = false;

            } else if (confirm == JOptionPane.NO_OPTION) {
                setTitle(applicationName);
                //  textArea1.setText("");
                textArea.setText("");
                textChanged = false;

            }
        }
    }

    // private void textAreaTextValueChanged(TextEvent evt) {    
     private void textAreaTextValueChanged(org.nidal.latex.glossarytool.RTextEvent evt) {                                           
        // TODO add your handling code here:
        if (RTextEvent.TEXT_VALUE_CHANGED != 0) {
            if (!textChanged)
                setTitle("* " + getTitle());
            
            textChanged = true;
            saveFile.setEnabled(true);
        }
    } 
 
    public String getSelectedText() {
        return textArea.getSelectedText();
    }

    public void initSearchDialogs() {

        findDialog = new FindDialog(this, this);
        replaceDialog = new ReplaceDialog(this, this);

        // This ties the properties of the two dialogs together (match case,
        // regex, etc.).
        SearchContext context = findDialog.getSearchContext();
        replaceDialog.setSearchContext(context);

        // Create tool bars and tie their search contexts together also.
      //  findToolBar = new FindToolBar((SearchListener) this);
        //findToolBar.setSearchContext(context);
        //replaceToolBar = new ReplaceToolBar((SearchListener) this);
        //replaceToolBar.setSearchContext(context);

    }

    @Override
    public void searchEvent(SearchEvent e) {

        SearchEvent.Type type = e.getType();
        SearchContext context = e.getSearchContext();
        SearchResult result = null;

        switch (type) {
            default: // Prevent FindBugs warning later
            case MARK_ALL:
                result = SearchEngine.markAll(textArea, context);
                break;
            case FIND:
                result = SearchEngine.find(textArea, context);
                if (!result.wasFound()) {
                    UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                }
                break;
            case REPLACE:
                result = SearchEngine.replace(textArea, context);
                if (!result.wasFound()) {
                    UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                }
                break;
            case REPLACE_ALL:
                result = SearchEngine.replaceAll(textArea, context);
                JOptionPane.showMessageDialog(null, result.getCount()
                        + " occurrences replaced.");
                break;
        }

        String text = null;
        if (result.wasFound()) {
            text = "Text found; occurrences marked: " + result.getMarkedCount();
        } else if (type == SearchEvent.Type.MARK_ALL) {
            if (result.getMarkedCount() > 0) {
                text = "Occurrences marked: " + result.getMarkedCount();
            } else {
                text = "";
            }
        } else {
            text = "Text not found";
        }
        statusBar.setLabel(text);

    }
    
    //to add \gls{text}
    private void addglsprefix(){
        String text_selected = textArea.getSelectedText();      
         String text_replacement = "\\gls{" + text_selected + "}" ;
        textArea.replaceSelection(text_replacement);
    }
    
    
      private void createAndShowadd_glsDialog()
    {
       
        //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringBoxProject/src/layout/SpringBox.java
        JPanel dpan=new JPanel();
          JPanel dpan1 =new JPanel();
           JPanel dpanel_master =new JPanel();
      //  Container dpan = d1.getContentPane();
        
      

        // dpan.setLayout(new BorderLayout());  
          dpan.setLayout(new SpringLayout());
         //  dpan.setLayout(new GridLayout(5,2)); 
          // dpan.setLayout(new FlowLayout());
           dpan1.setLayout(new FlowLayout());
          //  dpan1.setLayout(new GridLayout(0,3));  
            
          dpan.setMaximumSize(new Dimension(520, 400));
            dpan1.setMaximumSize(new Dimension(520, 150));
           // dpan1.setLayout(null);
                dpanel_master.setLayout(new BorderLayout());
               // dpanel_master.setLayout(new GridLayout(2,0));
       //dpan.setLayout(new GridBagLayout()); 
       
     //  GridBagConstraints c = new GridBagConstraints();
  //       if(shouldFill) {
                //natural height, maximum width
             //   c.fill = GridBagConstraints.HORIZONTAL;
//}
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
       
     //  d1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
       // setLayout(new FlowLayout());

        // Must be called before creating JDialog for
        // the desired effect
        JDialog.setDefaultLookAndFeelDecorated(true);
        
        // A perfect constructor, mostly used.
        // A dialog with current frame as parent
        // a given title, and modal
        d1=new JDialog(this,"Add to Glossary",true);
        
        // Set size
       
      
//        if (RIGHT_TO_LEFT) 
//            dpan.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        
        // Set some layout
      //  d1.setLayout(new FlowLayout());
      
//    current_y += 30;
    JLabel tag_label = new JLabel("Tag");

    JTextField tag_tf = new JTextField("");
   tag_label.setLabelFor(tag_tf);

     JLabel name_label = new JLabel("Name");

    JTextField name_tf = new JTextField("");

    JLabel plural_label = new JLabel("Plural");

    JTextField plural_tf = new JTextField("");

    JLabel symbol_label = new JLabel("Symbol");

    JTextField symbol_tf = new JTextField("");

    JLabel desc_label = new JLabel("Desciption");

     RSyntaxTextArea desc_Area = new RSyntaxTextArea("");

    JButton cancel_gls = new JButton("Cancel");

     JButton save_gls = new JButton("Save");
     JButton clear_gls = new JButton("Clear");

     tag_label.setLabelFor(tag_tf);
       name_label.setLabelFor(name_tf);
         plural_label.setLabelFor(plural_tf);
           symbol_label.setLabelFor(symbol_tf);
             desc_label.setLabelFor(desc_Area);
     
     
       dpan.add(tag_label);   
     dpan.add(tag_tf);  
     dpan.add(name_label);
       dpan.add(name_tf);
     dpan.add(plural_label);
      dpan.add(plural_tf);
     dpan.add(symbol_label);
     dpan.add(symbol_tf);
      dpan.add(desc_label);
     dpan.add(desc_Area);
     
     dpan1.add(cancel_gls);
      cancel_gls.setPreferredSize(new Dimension(120, 50));
    dpan1.add(save_gls);
     save_gls.setPreferredSize(new Dimension(120, 50));
//     dpan1.add(clear_gls,BorderLayout.LINE_END);
    dpan1.add(clear_gls); 
      clear_gls.setPreferredSize(new Dimension(120, 50));
         
       //Lay out the buttons in one row and as many columns
        //as necessary, with 6 pixels of padding all around.
        
        
        cancel_gls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                  
                   d1.dispose();
               
            }
        });
        
         save_gls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                   
                String tag_gls = tag_tf.getText();
                String name_gls = name_tf.getText();
                String symbol_gls = symbol_tf.getText();
                String plural_gls = plural_tf.getText();
                String desc_gls = desc_Area.getText();
                
                //sort use 
                String all_gls ;
                
                
                 all_gls = "\n\\newglossaryentry{" + tag_gls + "}\n" + "{" ;
                  if (!"".equals(name_gls)) 
                      all_gls=  all_gls + "\n\tname={" + name_gls + "}," ;
                 
                 if (!"".equals(symbol_gls)) 
                      all_gls=  all_gls + "\n\tsymbol={" + symbol_gls + "}," ;
                 
                   if (!"".equals(plural_gls)) 
                      all_gls=  all_gls + "\n\tplural={" + plural_gls + "}," ;
                   
                     if (!"".equals(desc_gls)) 
                      all_gls=  all_gls + "\n\tdescription={" + desc_gls + "}" ;
                     
                     all_gls= all_gls+ "\n}" ;
                      
                    
                     
          try {
         FileWriter out_gls;
           // out = new FileWriter(fn);
            out_gls = new FileWriter(filename_final,true);
            out_gls.write(all_gls);
            out_gls.close();

        } catch (Exception err) {
            System.out.println("Error: " + err);
        } 
                
                
         //       saveGlossary_new_entry();
 
        addglsprefix();
 
                  d1.dispose();
               
            }
        });
         
          clear_gls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
   
   //  dpan.add(tag_tf);  
     // dpan.add(name_tf);
 
    //  dpan.add(plural_tf);
      plural_tf.setText("");
       symbol_tf.setText("");
       desc_Area.setText("");

             //     d1.dispose();
               
            }
        });
         int count = dpan.getComponentCount()/2 ;
         System.out.println(count);
        
//     SpringUtilities.makeCompactGrid(dpan, 6,dpan.getComponentCount(),6, 6, 6, 6);
     SpringUtilities.makeCompactGrid(dpan, count,2,10, 10, 10, 10);  
     // SpringUtilities.makeCompactGrid(dpan1, 1,3,10, 10, 10, 10); 
     dpanel_master.add(dpan,BorderLayout.CENTER);  
      dpanel_master.add(dpan1, BorderLayout.SOUTH); 
     //  SpringUtilities.makeCompactGrid(dpanel_master, dpanel_master.getComponentCount(),1,6, 6, 6, 6); 
      
      
      d1.add(dpanel_master);
      
         d1.setSize(520,500);
         d1.setMinimumSize(new Dimension(520,400));
       // setSize(80,20);
  d1.setLocationRelativeTo(null);
        
    
        d1.setVisible(true);
    }
      //part of the gls dialog
      
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

   

    

    // private class add_gls extends  TextAction {
    private class add_gls extends TextAction {

        public add_gls() {
            super("Add to Glossary");
        }

        public void actionPerformed(ActionEvent e) {

//            JTextComponent tc = getTextComponent(e);
//            String gls_word = null;
//
//            int selStart = tc.getSelectionStart();
//            int selEnd = tc.getSelectionEnd();
//            if (selStart != selEnd) {
//                try {
//                    gls_word = tc.getText(selStart, selEnd - selStart);
//                } catch (BadLocationException ex) {
//                    Logger.getLogger(GlossaryTool.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                add_to_glossary(gls_word);
//            } else {
//                // gls_word = getFilenameAtCaret(tc);
//                add_to_glossary(gls_word);
//            }
//
//            if (findDialog.isVisible()) {
//                findDialog.setVisible(false);
//            }
//            replaceDialog.setVisible(true);
                    
                createAndShowadd_glsDialog();

        }
        
      

        private void add_to_glossary(String word) {
//
        }

    }

    private class ShowFindDialogAction extends AbstractAction {

        public ShowFindDialogAction() {
            super("Find...");
            int c = getToolkit().getMenuShortcutKeyMask();
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, c));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (replaceDialog.isVisible()) {
                replaceDialog.setVisible(false);
            }
            findDialog.setVisible(true);
        }

    }

    private class ShowReplaceDialogAction extends AbstractAction {

        public ShowReplaceDialogAction() {
            super("Replace...");
            int c = getToolkit().getMenuShortcutKeyMask();
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, c));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (findDialog.isVisible()) {
                findDialog.setVisible(false);
            }
            replaceDialog.setVisible(true);
        }

    }
    
//   private static class RTTextEvents extends java.awt.TextArea {
//
//  
//    }

    private static class StatusBar extends JPanel {

        private JLabel label;

        public StatusBar() {
            label = new JLabel("Ready");
            setLayout(new BorderLayout());
            add(label, BorderLayout.LINE_START);
            add(new JLabel(new SizeGripIcon()), BorderLayout.LINE_END);
        }

        public void setLabel(String label) {
            this.label.setText(label);
        }

    }

}
