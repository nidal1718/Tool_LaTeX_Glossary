/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.ButtonGroup;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.TextAction;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.rsta.ui.SizeGripIcon;
import org.fife.rsta.ui.search.*;
//import org.fife.rsta.ui.search.FindDialog;
//import org.fife.rsta.ui.search.FindToolBar;
//import org.fife.rsta.ui.search.ReplaceDialog;
//import org.fife.rsta.ui.search.ReplaceToolBar;
//import org.fife.rsta.ui.search.SearchEvent;
import static org.fife.rsta.ui.search.SearchEvent.Type.FIND;
import static org.fife.rsta.ui.search.SearchEvent.Type.MARK_ALL;
import static org.fife.rsta.ui.search.SearchEvent.Type.REPLACE;
import static org.fife.rsta.ui.search.SearchEvent.Type.REPLACE_ALL;
import org.fife.rsta.ui.search.SearchListener;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.RecordableTextAction;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

/**
 *
 * @author nidal
 */
public class GlossaryTool extends JFrame implements SearchListener {

    RSyntaxTextArea textArea = new RSyntaxTextArea(30, 90);

    private static RecordableTextAction cutAction;
    private static RecordableTextAction copyAction;
    private static RecordableTextAction pasteAction;
    private static RecordableTextAction deleteAction;
    private static RecordableTextAction undoAction;
    private static RecordableTextAction redoAction;
    private static RecordableTextAction selectAllAction;

    Intelligence intelligence = new Intelligence(textArea);
    WriteToGlossaryFile writetoglossaryfile_object = new WriteToGlossaryFile();

    int flag = 0;

    String filename_s = "";
    String filename_final;
    private KeyListener k1;
    String applicationName = "LaTeX_GlossaryTool";
    String holdText;
    String fn;
    String dir;
    boolean textChanged = false;
    private String fileName = "";
    private CollapsibleSectionPanel csp;

    private FindDialog findDialog;
    private ReplaceDialog replaceDialog;
    private ReplaceDialog add_glsDialog;
    private FindToolBar findToolBar;
    private ReplaceToolBar replaceToolBar;
    private final StatusBar statusBar;

    String glossaryFileName;
    Clipboard clip = getToolkit().getSystemClipboard();

    ReadGlossaryFile readGlossaryFile = new ReadGlossaryFile();

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    Boolean checktagExists;

    GlossaryEntryClass glossaryentryclass ; // = new GlossaryEntryClass(gMap);
    private Map<String, GlossaryEntryClass> fieldsMap;
    public Map<String, Map> gMap ; //= new HashMap<>();
    
//     public HashMap<String, Map> getgMap() {
//        return gMap;
//    }  
     
      

    private Map<String, String> repeatTags;
    
    AddToGlossaryMap addtoglossary = new AddToGlossaryMap(textArea,gMap);

    List<Object> collect;

    JTextField name_tf = new JTextField("");
    JTextField plural_tf = new JTextField("");
    JTextField tag_tf = new JTextField("");
    JTextField symbol_tf = new JTextField("");
    RSyntaxTextArea desc_Area = new RSyntaxTextArea("");

    final org.nidal.latex.glossarytool.WordSearcher searcher;
    final org.nidal.latex.glossarytool.WordSearcher remove_highlight;

    private JMenuBar menubar = new JMenuBar(); //menubar item
    private JPopupMenu popup = textArea.getPopupMenu();

    private JMenu glossaryMenu;
    private JMenuItem glsToolItem;
    private JMenuItem glsplToolItem;
    private JMenuItem GlsToolItem;
    private JMenuItem GlsplToolItem;
    private JMenuItem glssymbolToolItem;
//    private static RecordableTextAction toggleCurrentFoldAction;
//    private static RecordableTextAction collapseAllCommentFoldsAction;
//    private static RecordableTextAction collapseAllFoldsAction;
//    private static RecordableTextAction expandAllFoldsAction;

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

//     JTextComponent jtc ; //= new JTextComponent();
    private JMenuItem search_and_highlight_Tool = new JMenuItem(); // a searchandhighlighttool menu option
    private JMenuItem remove_all_highlight_Tool = new JMenuItem(); // remove all highlight menu option
    private JMenuItem glossarise_All_WordsTool = new JMenuItem(); // glossarise all the words menu option
    int ctrl = getToolkit().getMenuShortcutKeyMask();
    int shift = InputEvent.SHIFT_MASK;
    KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl | shift);

    private JMenuItem add_gls = new JMenuItem(); // a tools option

    private JMenuItem about = new JMenuItem(); // about option!
    File zip = new File("english_dic.zip");
    boolean usEnglish = true; // "false" will use British English

    public Highlighter highlighterh = new org.nidal.latex.glossarytool.UnderlineHighlighter(null);

    
     public RSyntaxTextArea gettextAreaName() {
        return textArea;
    } 
    public GlossaryTool() {

//Reference : https://github.com/bobbylight/RSyntaxTextArea
//reference : http://www.dreamincode.net/forums/topic/66176-creating-a-basic-notepad-application/
        initSearchDialogs();

        JPanel cp = new JPanel(new BorderLayout());
        int c = getToolkit().getMenuShortcutKeyMask();

        fieldsMap = new HashMap<>();
        gMap = new HashMap<>();
        //    repeatTags = new HashMap<>();

        csp = new CollapsibleSectionPanel();
        cp.add(csp);

        this.setSize(500, 300);
        this.setTitle("Glossary Tool");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);

        searcher = new org.nidal.latex.glossarytool.WordSearcher(textArea);
        remove_highlight = new org.nidal.latex.glossarytool.WordSearcher(textArea);

        //dictionary feature.
        textArea.setCodeFoldingEnabled(true);
        textArea.setMarkOccurrences(true);

        RTextScrollPane sp = new RTextScrollPane(textArea);

        //this.getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
        //this.getContentPane().add(textArea);
        cp.add(sp);
        ErrorStrip errorStrip = new ErrorStrip(textArea);
        cp.add(errorStrip, BorderLayout.LINE_END);
        this.setContentPane(cp);

        textArea.addKeyListener(k1);

        glossaryentryclass = new GlossaryEntryClass(gMap);
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
        //  this.saveFile.addActionListener(this);
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
        // saveasFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, c));
        this.file.add(this.saveasFile); // add it to the "File" menu

        // File Menu - Exit
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

        // add highlight
        this.search_and_highlight_Tool.setLabel("Search & Highlight Word");
        search_and_highlight_Tool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightAllWordsToolMenuActionPerformed(evt);
            }
        });
        // search_and_highlight_Tool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, c));
        this.tools.add(this.search_and_highlight_Tool); // add it to the "File" menu

        // remove highlight
        this.remove_all_highlight_Tool.setLabel("Remove All Highlights");
        remove_all_highlight_Tool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllHighlightToolMenuActionPerformed(evt);
            }
        });
        //search_and_highlight_Tool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, c));
        this.tools.add(this.remove_all_highlight_Tool); // add it to the "File" menu

        // and searchTool
        this.glossarise_All_WordsTool.setLabel("Glossarise all the words");
        glossarise_All_WordsTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                glossariseaAllWordsToolMenuActionPerformed(evt);
            }
        });
        //search_and_highlight_Tool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, c));
        this.tools.add(this.glossarise_All_WordsTool); // add it to the "File" menu

//help menu
// about us
//     this.about.setLabel("Help");
//    this.about.addActionListener(this);
//      //this.add_gls.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
//        this.help.add(this.about);
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
                // TODO add your handling code here:
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
            // textArea1.setText(sb.toString());
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
            textArea.setText(sb.toString());

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException ioe) {
        }

    }

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        // TODO add your handling code here:

        if (textArea.getText().length() < 1) {
            FileDialog fd = new FileDialog(this, "Choose File", FileDialog.LOAD);
            fd.show();
            if (fd.getFile() != null) {
                this.fileName = fd.getDirectory() + fd.getFile();
                this.filename_final = fd.getDirectory() + "glossary.tex";
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
                this.filename_final = fd.getDirectory() + "glossary.tex";
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
                    // this.filename_final = fd.getDirectory() + fd.getFile().replaceFirst(".tex", ".gls");
                    this.filename_final = fd.getDirectory() + "glossary.tex";
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
                    this.filename_final = fd.getDirectory() + "glossary.tex";
                    setTitle(fileName);
                    checkFile();
                    gMap.clear();
                }
            }
        }

        //FileDialog option to select the default file opener
        selectGlossaryFile();
    }

//    private void dictionarycheck() throws IOException {
//             SpellingParser parser = SpellingParser.createEnglishSpellingParser(zip, usEnglish);
//             //SpellingParser parser = SpellingParser.createEnglishSpellingParser(glossaryFileName, usEnglish);
//                textArea.addParser(parser);
//    }
    private void selectGlossaryFile() throws IOException {
        // TODO add your handling code here:

        FileDialog fd = new FileDialog(this, "Select the Glossary File", FileDialog.LOAD);
        fd.show();
        if (fd.getFile() != null) {
            glossaryFileName = fd.getDirectory() + fd.getFile();
            //   checkFile();
            readGlossaryFile.addtoArrayList(glossaryFileName);

        }

    }

    private void saveAsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        saveAs();
    }

    private void add_glsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        saveAs();
    }

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

    private void undoEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //undoAction.isEnabled();
    }

    private void redoEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //  redoAction.isEnabled();
    }

    private void cutEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // RTextAreaEditorKit.CutAction();
        //  cutAction.isEnabled();

    }

    private void copyEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //copyAction.isEnabled();

    }

    private void pasteEditActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //pasteAction.isEnabled();

    }

    private void highlightAllWordsToolMenuActionPerformed(java.awt.event.ActionEvent evt) {

        intelligence.highlightAllInstanceAllWords(gMap);
    }

    private void removeAllHighlightToolMenuActionPerformed(java.awt.event.ActionEvent evt) {

        remove_highlight.removehighlighter();
    }

    private void glossariseaAllWordsToolMenuActionPerformed(java.awt.event.ActionEvent evt) {

        intelligence.glossariseAllInstanceAllWords(textArea,gMap);
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

        FileDialog fd = new FileDialog(GlossaryTool.this, "Save As", FileDialog.SAVE);
        fd.show();

        if (fd.getFile() != null) {
            fn = fd.getFile();
            dir = fd.getDirectory();
            fileName = dir + fn + ".tex";
            // this.filename = dir + fn;
            // this.filename_s = dir + fn ;
            //   this.filename_final = fd.getDirectory() + "glossary.tex" ;

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

    private void saveGlossary_new_entry() {

        try {
            DataOutputStream d = new DataOutputStream(new FileOutputStream(filename_final));
            System.out.println(filename_final);

            holdText = textArea.getText();
            BufferedReader br = new BufferedReader(new StringReader(holdText));

            while ((holdText = br.readLine()) != null) {
                d.writeBytes(holdText + "\r\n");
                d.close();

            }
        } catch (Exception e) {
            System.out.println("File not found");
        }

        try {
            FileWriter out;
            // out = new FileWriter(fn);
            out = new FileWriter(filename_final);
            out.write(textArea.getText());
            out.close();

        } catch (Exception err) {
            System.out.println("Error: " + err);
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
        writetoglossaryfile_object.writeto_GlossaryFileMethod(gMap,glossaryFileName);
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

   // private void textAreaTextValueChanged(org.nidal.latex.glossarytool.RTextEvent evt) {
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
        checktagExists = false;
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

//    //to add \gls{text}
//    private void addglsprefix() {
//        String text_selected = textArea.getSelectedText();
//        String text_replacement = "\\gls{" + text_selected + "}";
//        textArea.replaceSelection(text_replacement);
//        textChanged = true;
//    }

    
    
    
  /*  
    private void createAndShowadd_glsDialog() {

        //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringBoxProject/src/layout/SpringBox.java
        JPanel dpan = new JPanel();
        JPanel dpan1 = new JPanel();
        JPanel dpanel_master = new JPanel();
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
        d1 = new JDialog(this, "Add to Glossary", true);

        // Set size
//        if (RIGHT_TO_LEFT)
//            dpan.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//
        // Set some layout
        //  d1.setLayout(new FlowLayout());
//    current_y += 30;
        JLabel selectedword_label = new JLabel("Selected Word");
        JTextField selectedword_tf = new JTextField(textArea.getSelectedText());
        selectedword_tf.setEditable(false);

        JLabel tag_label = new JLabel("Tag");

        JTextField tag_tf = new JTextField("");
        tag_label.setLabelFor(tag_tf);

        JLabel name_label = new JLabel("Name");
//
        JTextField name_tf = new JTextField("");
//
        JLabel plural_label = new JLabel("Plural");
//
        JTextField plural_tf = new JTextField("");
//
        JLabel symbol_label = new JLabel("Symbol");
//
        JTextField symbol_tf = new JTextField("");
//
        JLabel desc_label = new JLabel("Desciption");
//
        RSyntaxTextArea desc_Area = new RSyntaxTextArea("");

        JButton cancel_gls = new JButton("Cancel");

        JButton save_gls = new JButton("Save");
        JButton clear_gls = new JButton("Clear");

        tag_label.setLabelFor(tag_tf);
        // name_label.setLabelFor(name_tf);
        //plural_label.setLabelFor(plural_tf);
        //   symbol_label.setLabelFor(symbol_tf);
        //   desc_label.setLabelFor(desc_Area);

        dpan.add(selectedword_label);
        dpan.add(selectedword_tf);
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
        //if the word already exists then it disables the save button
        checktagExists = readGlossaryFile.checkifSavedinGlossaryFile(textArea.getSelectedText());

        if (checktagExists) {
            save_gls.setVisible(false);
        }

        cancel_gls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                d1.dispose();

            }
        });

        save_gls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String tag_gls = tag_tf.getText().trim();

                String name_gls = name_tf.getText().trim();;
                //   JTextField name_tf = new JTextField("");
                String symbol_gls = symbol_tf.getText().trim();
                String plural_gls = plural_tf.getText().trim();
                String desc_gls = desc_Area.getText().trim();
                GlossaryEntryClass gec;

                //sort use
                String all_gls;

                System.out.println(tag_gls + "" + name_gls + "" + symbol_gls + "" + plural_gls + "" + desc_gls);

                GlossaryEntryClass1 glossaryentryclass1 = new GlossaryEntryClass1(tag_gls.toLowerCase(), name_gls, symbol_gls, plural_gls, desc_gls);

                System.out.println(gMap);

               intelligence.checkIfWordAvailableInMap(textArea,textArea.getSelectedText(),gMap);
               // intelligence.repeattags_add(textArea.getSelectedText(), replacement);
                readGlossaryFile.addtoArrayListFromDialogSave(tag_gls);
                d1.dispose();

            }
        });

        clear_gls.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                name_tf.setText("");
                plural_tf.setText("");
                symbol_tf.setText("");
                desc_Area.setText("");

            }
        });
        int count = dpan.getComponentCount() / 2;
        System.out.println(count);

        SpringUtilities.makeCompactGrid(dpan, count, 2, 10, 10, 10, 10);

        dpanel_master.add(dpan, BorderLayout.CENTER);
        dpanel_master.add(dpan1, BorderLayout.SOUTH);

        d1.add(dpanel_master);

        d1.setSize(520, 500);
        d1.setMinimumSize(new Dimension(520, 400));

        d1.setLocationRelativeTo(null);

        d1.setVisible(true);
    } */
/*
    @SuppressWarnings("serial")
    public class GlossaryEntryClass1 {

        private HashMap<String, String> fields;

        public GlossaryEntryClass1(String tag_gls2, String name_gls2, String symbol_gls2, String plural_gls2, String desc_gls2) {
            fields = new HashMap<String, String>();

            //  fields.put(symbol_gls2, plural_gls2);
            if (tag_gls2 != null && !tag_gls2.isEmpty()) {
                fields.put("Tag", tag_gls2);
            }

            if (name_gls2 != null && !name_gls2.isEmpty()) {
                fields.put("Name", name_gls2);
            }

            if (symbol_gls2 != null && !symbol_gls2.isEmpty()) {
                fields.put("Symbol", symbol_gls2);
            }

            if (plural_gls2 != null && !plural_gls2.isEmpty()) {
                fields.put("Plural", plural_gls2);
            }

            if (desc_gls2 != null && !desc_gls2.isEmpty()) {
                fields.put("Description", desc_gls2);
            }

            gMap.put(tag_gls2, fields);

        }

        String getTag() {
            return fields.get("Tag");
        }

        String getName() {
            return fields.get("Name");
        }

        String getSymbol() {
            return fields.get("Symbol");
        }

        String getPlural() {
            return fields.get("Plural");
        }

        String getDesc() {
            return fields.get("Description");
        }

    }
    */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GlossaryTool().setVisible(true);

            }
        });
        // TODO code application logic here
    }



    // for glossaries popup. #6 glossaries
    private class glossariseSelectedWordPopup extends TextAction {

     //   Intelligence intelligence = new Intelligence();

        public glossariseSelectedWordPopup() {

            super("Glossarise the word");
        }

        public void actionPerformed(ActionEvent e) {
            
              String text_selected = textArea.getSelectedText();
            

            try {
                int selStart = textArea.getSelectionStart();
                int selEnd = textArea.getSelectionEnd();
                if (selStart != selEnd) {
                    text_selected = textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.equals("")) {
                     //   intelligence.checkIfWordAvailableInMap(textArea,text_selected, gMap);
                    }
                }
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

      //  Intelligence intelligence = new Intelligence();

        public glossariseAllTheWordPopup() {

            super("Glossarise all instances of this word");
        }

        public void actionPerformed(ActionEvent e) {
            intelligence.glossariseAllInstanceSelectedWord(textArea ,textArea.getSelectedText(),gMap);

        }

    }

    // for highlighting single occurance
    private class highlightSingleWordOccurance extends TextAction {

       // Intelligence intelligence = new Intelligence();

        public highlightSingleWordOccurance() {

            super("Highlight all instances of this word");
        }

        public void actionPerformed(ActionEvent e) {
            intelligence.highlightAllInstanceSelectedWord(textArea,gMap);

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

                    if (!text_selected.equals("")) {
                        addtoglossary.createAndShowadd_glsDialog(textArea,gMap);
                    }
                }
            } catch (BadLocationException ble) {
                ble.printStackTrace();

            }

        }

        private void add_to_glossary(String word) {
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
