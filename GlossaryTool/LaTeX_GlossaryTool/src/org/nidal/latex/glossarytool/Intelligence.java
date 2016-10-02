/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.util.Iterator;
import java.util.Map;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *Class to implement the intelligence to detect the selected word and to put the appropriate tag
 * @author nidal
 */
public class Intelligence {

   
     final org.nidal.latex.glossarytool.WordSearcher searcher ;
          final org.nidal.latex.glossarytool.WordSearcher remove_highlight; 
 
     
     public Intelligence(RSyntaxTextArea textArea)
     {  //glossarytool = new GlossaryTool();
         searcher  = new org.nidal.latex.glossarytool.WordSearcher(textArea);
         remove_highlight = new org.nidal.latex.glossarytool.WordSearcher(textArea);
     
     }
     
     
     
    // for glossaries popup. #1 glossary
    public String addglsGlossariecePopup_Method(String tag,RSyntaxTextArea textArea) {

        String text_replacement = "\\gls{" + tag + "}";
        textArea.replaceSelection(text_replacement);
    //    textChanged = true;
        return text_replacement;
    }

    // for glossaries popup. #2 glossaries plural glspl
    public String addglsplGlossariecePopup_Method(String tag,RSyntaxTextArea textArea) {
        String text_replacement = "\\glspl{" + tag + "}";
        textArea.replaceSelection(text_replacement);
     //   textChanged = true;
        return text_replacement;
    }

    // for glossaries popup. #3 Glossaries Gls
    public String addGlosGlossariecePopup_Method(String tag,RSyntaxTextArea textArea) {
        String text_replacement = "\\Gls{" + tag + "}";
        textArea.replaceSelection(text_replacement);
       // textChanged = true;
        return text_replacement;
    }

    // for glossaries popup. #4 Glossary plural
    public String addGlosplGlossariecePopup_Method(String tag,RSyntaxTextArea textArea) {
        String text_replacement = "\\Glspl{" + tag + "}";
        textArea.replaceSelection(text_replacement);
      //  textChanged = true;
        return text_replacement;
    }

    // for glossaries popup. #5 glossaries symbol
    public String addglssymbolGlossariecePopup_Method(String tag,RSyntaxTextArea textArea) {
        String text_replacement = "\\glssymbol{" + tag + "}";
        textArea.replaceSelection(text_replacement);
       // textChanged = true;
        return text_replacement;
    }
        public Boolean capitalornot(String text_selected) {
            Boolean capitalcheck;
            if (Character.isUpperCase(text_selected.charAt(0)) == true) {
                capitalcheck = true;
            } else {
                capitalcheck = false;
            }

            return capitalcheck;
        }
//
        public String glossariseTheWord(RSyntaxTextArea textArea,String text_selected, Map<String, Map> gMap) {
            String tag = null;
            String text_replacement = null;
            Boolean capitalcheck = capitalornot(text_selected);
            Iterator<Map.Entry<String, Map>> iterator = gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                if (text_selected.toLowerCase().equals(entry.getValue().get("Tag"))) {
                    tag = (String) entry.getValue().get("Tag");
                    if (capitalcheck) {
                        addGlosGlossariecePopup_Method(tag,textArea);
                  
                    } else {
                       addglsGlossariecePopup_Method(tag,textArea);
                   
                    }

                    break;
                } else if (text_selected.toLowerCase().equals(entry.getValue().get("Plural"))) {
                    tag = (String) entry.getValue().get("Tag");
                    if (capitalcheck) {
                        addGlosplGlossariecePopup_Method(tag,textArea);
                    
                    } else {
                      addglsplGlossariecePopup_Method(tag,textArea);
                   
                    }
                    break;
                } else if (text_selected.equals(entry.getValue().get("Symbol"))) {
                    tag = (String) entry.getValue().get("Tag");
                    addglssymbolGlossariecePopup_Method(tag,textArea);
               
                    break;
                }

            }

            return text_replacement;
        }

        // checks and highlights every instance of all words
        public String highlightAllInstanceAllWords(Map<String, Map> gMap) {

            String value = null;
            remove_highlight.removehighlighter();

            Iterator<Map.Entry<String, Map>> iterator = gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                value = (String) entry.getValue().get("Tag");
                searcher.search(value);

                value = (String) entry.getValue().get("Plural");
                searcher.search(value);
                value = (String) entry.getValue().get("Symbol");
                searcher.search(value);

            }

            return value;
        }

        // replace all for instance of all the words
        public String glossariseAllInstanceAllWords(RSyntaxTextArea textArea,Map<String, Map> gMap) {
            String tag = null;
            String text_replacement = null;
            String value;
            String capValue;
            String lowerValue;

            Iterator<Map.Entry<String, Map>> iterator = gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                tag = (String) entry.getValue().get("Tag");

                value = (String) entry.getValue().get("Tag");

                capValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                //  text_replacement=addGlosGlossariecePopup_Method(tag);
                textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Gls{" + tag + "}"));

                lowerValue = value.substring(0, 1).toLowerCase() + value.substring(1);
                //  text_replacement=addglsGlossariecePopup_Method(tag);
                textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\gls{" + tag + "}"));

                value = (String) entry.getValue().get("Plural");

                capValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                // text_replacement=addGlosplGlossariecePopup_Method(tag);
                textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Glspl{" + tag + "}"));

                lowerValue = value.substring(0, 1).toLowerCase() + value.substring(1);
                // text_replacement=addglsplGlossariecePopup_Method(tag);
                textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\glspl{" + tag + "}"));

                value = (String) entry.getValue().get("Symbol");
                //text_replacement=addglssymbolGlossariecePopup_Method(tag);
                textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + value + "(?!\\S)", "\\\\glssymbol{" + tag + "}"));

            }

            return text_replacement;
        }

        // checks and highlights  instance of a single word
        String highlightAllInstanceSelectedWord(RSyntaxTextArea textArea,Map<String, Map> gMap) {
            
            Boolean check;
            String value = null;
            String text_selected;

            try {
                int selStart = textArea.getSelectionStart();
                int selEnd = textArea.getSelectionEnd();
                if (selStart != selEnd) {
                    text_selected = textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.equals("")) {
                        Iterator<Map.Entry<String, Map>> iterator = gMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Map> entry = iterator.next();
                            check = false;
                            if (text_selected.toLowerCase().equals(entry.getValue().get("Tag"))) {
                                check = true;
                            } else if (text_selected.toLowerCase().equals(entry.getValue().get("Plural"))) {
                                check = true;
                            } else if (text_selected.toLowerCase().equals(entry.getValue().get("Symbol"))) {
                                check = true;
                            }

                            if (check) {
                                value = (String) entry.getValue().get("Tag");
                                searcher.search(value);

                                value = (String) entry.getValue().get("Plural");
                                searcher.search(value);
                                value = (String) entry.getValue().get("Symbol");
                                searcher.search(value);
                            }

                        }
                    }
                }
            } catch (BadLocationException ble) {
                ble.printStackTrace();

            }

            return value;
        }

        // glossarise only the current word
        public String glossariseAllInstanceSelectedWord(RSyntaxTextArea textArea,String text_selected,Map<String,Map> gMap) {
            String tag = null;
            String plural = null;
            String capValue;
            String lowerValue;
            Boolean check;
            String symbol = null;
            String text_replacement = null;
            Boolean capitalcheck = capitalornot(text_selected);
            Iterator<Map.Entry<String, Map>> iterator = gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();
                tag = (String) entry.getValue().get("Tag");
                check = false;

                if (text_selected.toLowerCase().equals(entry.getValue().get("Tag"))) {
                    check = true;
                } else if (text_selected.toLowerCase().equals(entry.getValue().get("Plural"))) {
                    check = true;
                } else if (text_selected.toLowerCase().equals(entry.getValue().get("Symbol"))) {
                    check = true;
                }
                //else check = false ;

                if (check) {
                    tag = (String) entry.getValue().get("Tag");
                    plural = (String) entry.getValue().get("Plural");
                    symbol = (String) entry.getValue().get("Symbol");

                    capValue = text_selected.substring(0, 1).toUpperCase() + text_selected.substring(1);
                    textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Gls{" + tag + "}"));

                    lowerValue = text_selected.substring(0, 1).toLowerCase() + text_selected.substring(1);

                    textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\gls{" + tag + "}"));

                    // plural = (String) entry.getValue().get("Plural");
                    capValue = plural.substring(0, 1).toUpperCase() + plural.substring(1);

                   textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Glspl{" + tag + "}"));

                    lowerValue = plural.substring(0, 1).toLowerCase() + plural.substring(1);
                    textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\glspl{" + tag + "}"));

                    textArea.setText(textArea.getText().replaceAll("(?<!\\S)" + symbol + "(?!\\S)", "\\\\glssymbol{" + tag + "}"));

                }
            }

            return text_replacement;
        }
        
        
        
    }