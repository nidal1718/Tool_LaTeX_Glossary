/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.ButtonGroup;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.TextAction;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.rsta.ui.SizeGripIcon;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
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
 * Main Class 
 * Creates the UI for the TextArea
 * Allows you to open and save the document etc
 * @author nidal
 */
public class GlossaryTool extends JFrame implements SearchListener {

    String applicationName = "LaTeX_GlossaryTool"; //sets application name
    boolean textChanged = false; //to check if textArea changed or not
    private String fileName = ""; 
    String glossaryFileName; //stores the file name of the glossary file later
    Boolean checktagExists; //checks if tag exists in the glossary file
    public Map<String, Map> gMap; //creates the gMap hashmap
    
    RSyntaxTextArea textArea = new RSyntaxTextArea(40, 90);  //TextArea textArea
    private JMenuBar menubar = new JMenuBar(); //menubar on top
    private JPopupMenu popup = textArea.getPopupMenu(); //popup menu on right click
    private CollapsibleSectionPanel csp;
    private FindDialog findDialog;  // FindDialog on Edit Menu
    private ReplaceDialog replaceDialog; //ReplaceDialog on Edit Menu
    private final StatusBar statusBar; //status bar on bottom
    
    
    
    private KeyListener k1;
    //Clipboard clip = getToolkit().getSystemClipboard();
    int ctrl = getToolkit().getMenuShortcutKeyMask();
    int shift = InputEvent.SHIFT_MASK;
    KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl | shift);

//    private static RecordableTextAction cutAction;
//    private static RecordableTextAction copyAction;
//    private static RecordableTextAction pasteAction;
//    private static RecordableTextAction deleteAction;
//    private static RecordableTextAction undoAction;
//    private static RecordableTextAction redoAction;
//    private static RecordableTextAction selectAllAction;
    
    
    
    Intelligence intelligence = new Intelligence(textArea); 
    WriteToGlossaryFile writetoglossaryfile_object = new WriteToGlossaryFile();
    ReadGlossaryFile readGlossaryFile = new ReadGlossaryFile();
    GlossaryEntryClass glossaryentryclass; 
    AddToGlossaryMap addtoglossary = new AddToGlossaryMap(textArea, gMap);
    final WordSearcher searcher;  //object of Wordsearcher class -> HighlighterClass.java
    final WordSearcher remove_highlight; //object of Wordsearcher class -> HighlighterClass.java
   

    private JMenu file = new JMenu(); //  File menu
    private JMenu edit = new JMenu(); // edit menu
    private JMenu tools = new JMenu(); //  tools menu
    private JMenu help = new JMenu(); //  help menu
    //File Menu Items
    private JMenuItem newFile = new JMenuItem();  //   new file option
    private JMenuItem openFile = new JMenuItem();  //  open file option
    private JMenuItem saveFile = new JMenuItem(); //  save file option
    private JMenuItem saveasFile = new JMenuItem(); // save file as option
    private JMenuItem exitFile = new JMenuItem(); //  Exit application option!
    //Edit Menu Items
    private JMenuItem cut = new JMenuItem();  //   cut option
    private JMenuItem copy = new JMenuItem();  //  copy option
    private JMenuItem paste = new JMenuItem(); //  paste option
    private JMenuItem undo = new JMenuItem(); //  undo as option
    private JMenuItem redo = new JMenuItem(); //  redo option!
    //Tools Menu Items
    private JMenuItem search_and_highlight_Tool = new JMenuItem(); // a search and highlighttool menu option
    private JMenuItem remove_all_highlight_Tool = new JMenuItem(); // remove all highlight menu option
    private JMenuItem glossarise_All_WordsTool = new JMenuItem(); // glossarise all the words menu option
   //Help Menu Items
    private JMenuItem readme = new JMenuItem(); // readme option!


    
    public GlossaryTool() {

//Reference : https://github.com/bobbylight/RSyntaxTextArea
//reference : http://www.dreamincode.net/forums/topic/66176-creating-a-basic-notepad-application/
        

        initSearchDialogs();
        
        JPanel cp = new JPanel(new BorderLayout());
        int c = getToolkit().getMenuShortcutKeyMask();
        RTextScrollPane sp = new RTextScrollPane(textArea);
         ErrorStrip errorStrip = new ErrorStrip(textArea);
        
        gMap = new HashMap<>();
        glossaryentryclass = new GlossaryEntryClass(gMap);
        searcher = new WordSearcher(textArea);
        remove_highlight = new WordSearcher(textArea);
        statusBar = new StatusBar();
     
        csp = new CollapsibleSectionPanel();
        cp.add(csp);
        cp.add(sp);

       // this.setSize(500, 300);
        textArea.setSize(600, 400);
        this.setTitle("Glossary Tool");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textArea.setLineWrap(true);
       // sp.setLineNumbersEnabled(true);

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
        textArea.setCodeFoldingEnabled(true);
        textArea.setMarkOccurrences(true);

        this.setContentPane(cp);
        cp.add(errorStrip, BorderLayout.LINE_END);
        this.setContentPane(cp);

        textArea.addKeyListener(k1);
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
       // this.help.setLabel("Help");

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
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    openMenuActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(GlossaryTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, c));
        this.file.add(this.openFile); // add it to the "File" menu

        // and the save...
        this.saveFile.setLabel("Save");
        saveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, c));
        this.file.add(this.saveFile);

        // File Menu - SaveAs
        this.saveasFile.setLabel("Save As");
        saveasFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuActionPerformed(evt);
            } 
        });
        this.file.add(this.saveasFile); // add it to the "File" menu

        // File Menu - Exit
        this.exitFile.setLabel("Exit");
        exitFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt); } });
        exitFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, c));
        this.file.add(this.exitFile);

//        //Edit Menu tool
//        this.undo.setLabel("Undo"); // set the label of the menu item
//        this.undo.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                undoEditActionPerformed(evt);
//            } 
//        });
//        this.undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, c));
//        this.edit.add(this.undo); // add it to the "File" menu
//
//        // Redo
//        this.redo.setLabel("Redo"); // set the label of the menu item
//        this.redo.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                redoEditActionPerformed(evt);
//            } 
//        });
//        this.redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, c));
//        this.edit.add(this.redo); // add it to the "File" menu
//
//        // and cut
//        this.cut.setLabel("Cut");
//        this.cut.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                cutEditActionPerformed(evt);
//            }    
//        });
//        this.cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, c));
//        this.edit.add(this.cut); // add it to the "File" menu
//
//        // and copy
//        this.copy.setLabel("Copy");
//        this.copy.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                copyEditActionPerformed(evt);
//            }
//        });
//        this.copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, c));
//        this.edit.add(this.copy); // add it to the "File" menu
//
//        // and paste
//        this.paste.setLabel("Paste");
//        this.paste.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                pasteEditActionPerformed(evt);
//            }
//        });
//        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, c));
//        this.edit.add(this.paste); // add it to the "File" menu

        edit.addSeparator();
        edit.add(new JMenuItem(new ShowFindDialogAction()));
        edit.add(new JMenuItem(new ShowReplaceDialogAction()));

        // add highlight
        this.search_and_highlight_Tool.setLabel("Search & Highlight Words");
        search_and_highlight_Tool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightAllWordsToolMenuActionPerformed(evt);
            }
        });
        search_and_highlight_Tool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, c));
        this.tools.add(this.search_and_highlight_Tool); // add it to the "File" menu

        // remove highlight
        this.remove_all_highlight_Tool.setLabel("Remove All Highlights");
        remove_all_highlight_Tool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllHighlightToolMenuActionPerformed(evt);
            }
        });
        remove_all_highlight_Tool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, c));
        this.tools.add(this.remove_all_highlight_Tool); // add it to the "File" menu

        // and searchTool
        this.glossarise_All_WordsTool.setLabel("Glossarise all the words");
        glossarise_All_WordsTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                glossariseaAllWordsToolMenuActionPerformed(evt);
            }
        });
        glossarise_All_WordsTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, c));
        this.tools.add(this.glossarise_All_WordsTool); // add it to the "File" menu


//        this.readme.setLabel("Read Me");
//        this.readme.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                pasteEditActionPerformed(evt);
//            }
//        });
//        this.help.add(this.readme); // add it to the "Help" menu

        
        
            
        
        popup.addSeparator();
        popup.add(new JMenuItem(new glossariseSelectedWordPopup())); //#6

        popup.addSeparator();
        popup.add(new JMenuItem(new glossariseAllTheWordPopup()));

        popup.addSeparator();
        popup.add(new JMenuItem(new highlightSingleWordOccurance()));

        popup.addSeparator();
        popup.add(new JMenuItem(new addtoGlossaryPopup()));

        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // TODO add your handling code here:
                if (TextEvent.TEXT_VALUE_CHANGED != 0) { //chg
                    if (!textChanged) {
                        setTitle("* " + getTitle());
                    }
                    textChanged = true;
                    saveFile.setEnabled(true);
                }
            }
        });

        textArea.addCaretListener(new CaretListener() {

            public void caretUpdate(CaretEvent e) {
                if (TextEvent.TEXT_VALUE_CHANGED != 0) { ///chg
                    if (!textChanged) {
                        setTitle("* " + getTitle());
                    }

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

            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.setText(sb.toString());

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException ioe) {
        }

    }

    // Method to open the .Tex file
    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        // TODO add your handling code here:

       //  gMap.clear();
        if (textArea.getText().length() < 1) {
            FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
            fd.show();
            if (fd.getFile() != null) {
                this.fileName = fd.getDirectory() + fd.getFile();
                setTitle(fileName);
                checkFile();
            }

               gMap.clear();
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.requestFocus();

        } else if (!textChanged) {
            FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
            fd.show();
            if (fd.getFile() != null) {
                this.fileName = fd.getDirectory() + fd.getFile();
                setTitle(fileName);
                checkFile();

            }

            gMap.clear();
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.requestFocus();

        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save before exiting this application? ");

            if (confirm == JOptionPane.YES_OPTION) {
                if ("".equals(fileName)) {
                    saveAs();
                } else {
                    save(fileName);
                }

                FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
                fd.show();

                if (fd.getFile() != null) {
                    this.fileName = fd.getDirectory() + fd.getFile();

                    setTitle(fileName);
                    checkFile();

                }

                gMap.clear();
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
                textArea.requestFocus();

            } else if (confirm == JOptionPane.NO_OPTION) {

                FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
                fd.show();

                if (fd.getFile() != null) {
                    this.fileName = fd.getDirectory() + fd.getFile();
                  
                    setTitle(fileName);
                    checkFile();
                    gMap.clear();
                }
            }
        }

        //FileDialog option to select the default file opener
        selectGlossaryFile();
    }

    private void selectGlossaryFile() throws IOException {
        // TODO add your handling code here:

        FileDialog fd = new FileDialog(this, "Select the Glossary File", FileDialog.LOAD);
        fd.show();
        if (fd.getFile() != null) {
            glossaryFileName = fd.getDirectory() + fd.getFile();
            readGlossaryFile.addtoArrayList(glossaryFileName);

        }

    }

    private void saveAsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        saveAs();
    }

//    private void add_glsMenuActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        saveAs();
//    }

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (fileName.equals("")) {
            saveAs();
        } else {
            save(fileName);
        }
    }

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        newFile();
    }

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

        if ("".equals(textArea.getText())) {
            System.exit(0);
        } else if (!textChanged) {
            System.exit(0);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save before exiting this application");
            if (confirm == JOptionPane.YES_OPTION) {
                if (fileName.equals("")) {
                    saveAs();
                } else {
                    save(fileName);
                }

            }
            if (confirm == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

//    private void undoEditActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        //undoAction.isEnabled();
//    }
//
//    private void redoEditActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        //  redoAction.isEnabled();
//    }
//
//    private void cutEditActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        // RTextAreaEditorKit.CutAction();
//        //  cutAction.isEnabled();
//
//    }
//
//    private void copyEditActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        //copyAction.isEnabled();
//
//    }
//
//    private void pasteEditActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//        //pasteAction.isEnabled();
//
//    }

    private void highlightAllWordsToolMenuActionPerformed(java.awt.event.ActionEvent evt) {

        intelligence.highlightAllInstanceAllWords(gMap);
    }

    private void removeAllHighlightToolMenuActionPerformed(java.awt.event.ActionEvent evt) {

        remove_highlight.removehighlighter();
    }

    private void glossariseaAllWordsToolMenuActionPerformed(java.awt.event.ActionEvent evt) {

        intelligence.glossariseAllInstanceAllWords(textArea, gMap);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:

        if ("".equals(textArea.getText())) {
            System.exit(0);
        } else if (!textChanged) {
            System.exit(0);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save before exiting this application ?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (fileName.equals("")) {
                    saveAs();
                } else {
                    save(fileName);
                }
                System.exit(0);
            }
            if (confirm == JOptionPane.NO_OPTION) {
                System.exit(0);

            }
        }
    }

    private void saveAs() {
    String fn;
    String dir;
      String holdText;
        FileDialog fd = new FileDialog(GlossaryTool.this, "Save As", FileDialog.SAVE);
        fd.show();

        if (fd.getFile() != null) {
            fn = fd.getFile();
            dir = fd.getDirectory();
            fileName = dir + fn + ".tex";

            setTitle(fileName);

            try {
                DataOutputStream d = new DataOutputStream(new FileOutputStream(fileName));
                holdText = textArea.getText();
                BufferedReader br = new BufferedReader(new StringReader(holdText));

                while ((holdText = br.readLine()) != null) {
                    d.writeBytes(holdText + "\r\n");
                    d.close();

                }

            } catch (Exception e) {
                System.out.println("File not found");
            }

            textArea.requestFocus();
            save(fileName);
        }

    }

    private void save(String filename) {
        setTitle(applicationName + " " + filename);
        try {
            FileWriter out;
            out = new FileWriter(filename);
            textArea.requestFocus();
            out.write(textArea.getText());
            out.close();

        } catch (Exception err) {
            System.out.println("Error: " + err);
        }

        textChanged = false;
        saveFile.setEnabled(false);
        writetoglossaryfile_object.writeto_GlossaryFileMethod(gMap, glossaryFileName);
        gMap.clear();
    }

    private void newFile() {

        if (textArea.getText().length() < 1) {
            setTitle("Untitled-" + applicationName);
            textArea.setText("");
            fileName = "";
            textChanged = false;

        } else if (!textChanged) {
            setTitle("Untitled-" + applicationName);
            textArea.setText("");
            fileName = "";
            textChanged = false;

        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save the existing file before opening a new one?");

            if (confirm == JOptionPane.YES_OPTION) {
                if ("".equals(fileName)) {
                    saveAs();
                } else {
                    save(fileName);
                }

                setTitle(applicationName);
                fileName = "";
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

    private void textAreaTextValueChanged(TextEvent evt) {
        // TODO add your handling code here:
        if (TextEvent.TEXT_VALUE_CHANGED != 0) {
            if (!textChanged) {
                setTitle("* " + getTitle());
            }

            textChanged = true;
            saveFile.setEnabled(true);
        }
    }

    public String getSelectedText() {
       // checktagExists = false;
        return textArea.getSelectedText();
    }

    public void initSearchDialogs() {

        findDialog = new FindDialog(this, this);
        replaceDialog = new ReplaceDialog(this, this);

        // This ties the properties of the two dialogs together (match case,
        // regex, etc.).
        SearchContext context = findDialog.getSearchContext();
        replaceDialog.setSearchContext(context);

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GlossaryTool().setVisible(true);

            }
        });

    }

    // for glossaries popup. #6 glossaries
    private class glossariseSelectedWordPopup extends TextAction {

        public glossariseSelectedWordPopup() {

            super("Glossarise the word");
        }

        public void actionPerformed(ActionEvent e) {

            String text_selected ;

            try {
                int selStart = textArea.getSelectionStart();
                int selEnd = textArea.getSelectionEnd();
                if (selStart != selEnd ) {
                    text_selected = textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.trim().equals("")) 
                        intelligence.glossariseTheWord(textArea, text_selected, gMap);
                    else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
                    
                 
                    
                }
                   else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
            } catch (BadLocationException ble) {
                ble.printStackTrace();
                UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                return;
            }
            // textChanged = true;

        }

    }

    // for glossaries popup. #6 glossaries
    private class glossariseAllTheWordPopup extends TextAction {

        public glossariseAllTheWordPopup() {

            super("Glossarise all instances of this word");
        }

        public void actionPerformed(ActionEvent e) {
           String text_selected ;

            try {
                int selStart = textArea.getSelectionStart();
                int selEnd = textArea.getSelectionEnd();
                if (selStart != selEnd ) {
                    text_selected = textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.trim().equals("")) 
                     intelligence.glossariseAllInstanceSelectedWord(textArea, textArea.getSelectedText(), gMap);
                     else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
//                    
                
                }
                   else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
            } catch (BadLocationException ble) {
                ble.printStackTrace();
                UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                return;
            }

        }

    }

    // for highlighting single occurance
    private class highlightSingleWordOccurance extends TextAction {

        public highlightSingleWordOccurance() {

            super("Highlight all instances of this word");
        }

        public void actionPerformed(ActionEvent e) {
       
            
               String text_selected ;

            try {
                int selStart = textArea.getSelectionStart();
                int selEnd = textArea.getSelectionEnd();
                if (selStart != selEnd ) {
                    text_selected = textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.trim().equals("")) 
                       intelligence.highlightAllInstanceSelectedWord(textArea, gMap);
                     else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
//                    
                
                }
                   else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
            } catch (BadLocationException ble) {
                ble.printStackTrace();
                UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                return;
            }
            

        }

    }

    // private class add_gls extends  TextAction {
    private class addtoGlossaryPopup extends TextAction {

        public addtoGlossaryPopup() {

            super("Add to Glossary");
        }

        public void actionPerformed(ActionEvent e) {
            String text_selected;

            try {
                int selStart = textArea.getSelectionStart();
                int selEnd = textArea.getSelectionEnd();
                if (selStart != selEnd) {
                    text_selected = textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.trim().equals("")) 
                        addtoglossary.createAndShowadd_glsDialog(textArea, gMap);
                    else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
//                   
                }
                 else 
                       JOptionPane.showMessageDialog(null, "Please select a word");
            } catch (BadLocationException ble) {
                ble.printStackTrace();

            }

        }

//        private void add_to_glossary(String word) {
//        }
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
