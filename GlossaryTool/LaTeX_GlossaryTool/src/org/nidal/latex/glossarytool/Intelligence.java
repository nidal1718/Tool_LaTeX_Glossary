/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.util.Iterator;
import java.util.Map;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;

/**
 *
 * @author nidal
 */

    public class Intelligence extends GlossaryTool {
 GlossaryTool glossarytool  ; // = new GlossaryTool();
 
  // boolean textChanged = glossarytool.getTextChanged() ;
        void glossariseTheWord() {
            String text_replacement;
            String tag = null;

          
            String text_selected = glossarytool.textArea.getSelectedText();

            try {
                int selStart = glossarytool.textArea.getSelectionStart();
                int selEnd = glossarytool.textArea.getSelectionEnd();
                if (selStart != selEnd) {
                    text_selected = glossarytool.textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.equals("")) {
                        checkavailabilityin_Map(text_selected);
                    }
                }
            } catch (BadLocationException ble) {
                ble.printStackTrace();
                UIManager.getLookAndFeel().provideErrorFeedback(glossarytool.textArea);
                return;
            }

      //      boolean blnExists = repeatTags.containsKey(text_selected);

            textChanged = true;

        }

        public String replaceOld(String aInput, String text_selected, final String aNewPattern) {
            if (text_selected.equals("")) {
                throw new IllegalArgumentException("Old pattern must have content.");
            }

            final StringBuffer result = new StringBuffer();

            int startIdx = 0;
            int idxOld = 0;
            while ((idxOld = aInput.indexOf(text_selected, startIdx)) >= 0) {
                //grab a part of aInput which does not include aOldPattern
                result.append(aInput.substring(startIdx, idxOld));
                //add aNewPattern to take place of aOldPattern
                result.append(aNewPattern);

                //reset the startIdx to just after the current match, to see
                //if there are any further matches
                startIdx = idxOld + text_selected.length();
            }
            //the final chunk will go to the end of aInput
            result.append(aInput.substring(startIdx));
            return result.toString();
        }

        public String replaceAllglossary(String aInput, String aOldPattern, String aNewPattern) {
            return aInput.replace(aOldPattern, aNewPattern);
        }

        private void glossariseAllTheWord() {

            String tag = null;

            String text_selected = glossarytool.textArea.getSelectedText();

            try {
                int selStart = glossarytool.textArea.getSelectionStart();
                int selEnd = glossarytool.textArea.getSelectionEnd();
                if (selStart != selEnd) {
                    text_selected = glossarytool.textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.equals("")) {
                        checkavailabilityin_Map4(text_selected);
                    }
                }
            } catch (BadLocationException ble) {
                ble.printStackTrace();
                UIManager.getLookAndFeel().provideErrorFeedback(glossarytool.textArea);
                return;
            }

            textChanged = true;

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

        public String checkavailabilityin_Map(String text_selected) {
            String tag = null;
            String text_replacement = null;
            Boolean capitalcheck = capitalornot(text_selected);
            Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                if (text_selected.toLowerCase().equals(entry.getValue().get("Tag"))) {
                    tag = (String) entry.getValue().get("Tag");
                    if (capitalcheck) {
                        text_replacement = glossarytool.addGlosGlossariecePopup_Method(tag);
                      //  repeattags_add(text_selected, text_replacement);
                    } else {
                        text_replacement = glossarytool.addglsGlossariecePopup_Method(tag);
                        //repeattags_add(text_selected, text_replacement);
                    }

                    break;
                } else if (text_selected.toLowerCase().equals(entry.getValue().get("Plural"))) {
                    tag = (String) entry.getValue().get("Tag");
                    if (capitalcheck) {
                        text_replacement = glossarytool.addGlosplGlossariecePopup_Method(tag);
                    //    repeattags_add(text_selected, text_replacement);
                    } else {
                        text_replacement = glossarytool.addglsplGlossariecePopup_Method(tag);
                       // repeattags_add(text_selected, text_replacement);
                    }
                    break;
                } else if (text_selected.equals(entry.getValue().get("Symbol"))) {
                    tag = (String) entry.getValue().get("Tag");
                    text_replacement = glossarytool.addglssymbolGlossariecePopup_Method(tag);
                  //  repeattags_add(text_selected, text_replacement);
                    break;
                }

            }

            return text_replacement;
        }

        // checks and highlights every instance of all words
        public String checkavailabilityin_Map1() {

            String value = null;
            glossarytool.remove_highlight.removehighlighter();

            Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                value = (String) entry.getValue().get("Tag");
                glossarytool.searcher.search(value);

                value = (String) entry.getValue().get("Plural");
                glossarytool.searcher.search(value);
                value = (String) entry.getValue().get("Symbol");
                glossarytool.searcher.search(value);

            }

            return value;
        }

        // replace all for instance of all the words
        public String checkavailabilityin_Map3() {
            String tag = null;
            String text_replacement = null;
            String value;
            String capValue;
            String lowerValue;

            Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                tag = (String) entry.getValue().get("Tag");

                value = (String) entry.getValue().get("Tag");

                capValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                //  text_replacement=addGlosGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Gls{" + tag + "}"));

                lowerValue = value.substring(0, 1).toLowerCase() + value.substring(1);
                //  text_replacement=addglsGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\gls{" + tag + "}"));

                value = (String) entry.getValue().get("Plural");

                capValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                // text_replacement=addGlosplGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Glspl{" + tag + "}"));

                lowerValue = value.substring(0, 1).toLowerCase() + value.substring(1);
                // text_replacement=addglsplGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\glspl{" + tag + "}"));

                value = (String) entry.getValue().get("Symbol");
                //text_replacement=addglssymbolGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + value + "(?!\\S)", "\\\\glssymbol{" + tag + "}"));

            }

            return text_replacement;
        }

        // checks and highlights  instance of a single word
        public String checkavailabilityin_Map10() {
            Boolean check;
            String value = null;
            String text_selected;

            try {
                int selStart = glossarytool.textArea.getSelectionStart();
                int selEnd = glossarytool.textArea.getSelectionEnd();
                if (selStart != selEnd) {
                    text_selected = glossarytool.textArea.getText(selStart, selEnd - selStart);

                    if (!text_selected.equals("")) {
                        Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
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
                                glossarytool.searcher.search(value);

                                value = (String) entry.getValue().get("Plural");
                                glossarytool.searcher.search(value);
                                value = (String) entry.getValue().get("Symbol");
                                glossarytool.searcher.search(value);
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
        public String checkavailabilityin_Map4(String text_selected) {
            String tag = null;
            String plural = null;
            String capValue;
            String lowerValue;
            Boolean check;
            String symbol = null;
            String text_replacement = null;
            Boolean capitalcheck = capitalornot(text_selected);
            Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
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
                    glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Gls{" + tag + "}"));

                    lowerValue = text_selected.substring(0, 1).toLowerCase() + text_selected.substring(1);

                    glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\gls{" + tag + "}"));

                    // plural = (String) entry.getValue().get("Plural");
                    capValue = plural.substring(0, 1).toUpperCase() + plural.substring(1);

                    glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\\\Glspl{" + tag + "}"));

                    lowerValue = plural.substring(0, 1).toLowerCase() + plural.substring(1);
                    glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\\\glspl{" + tag + "}"));

                    glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + symbol + "(?!\\S)", "\\\\glssymbol{" + tag + "}"));

                }
            }

            return text_replacement;
        }

        public void glossarycheckconditions(String str) {
            String capValue;
            String lowerValue;
            String value;
            String tag = null;
          //  HashMap<String, Map> gMap = 

            Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Map> entry = iterator.next();

                tag = (String) entry.getValue().get("Tag");

                value = (String) entry.getValue().get("Tag");

                capValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                //  text_replacement=addGlosGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\Gls{" + tag + "}"));

                lowerValue = value.substring(0, 1).toLowerCase() + value.substring(1);
                //  text_replacement=addglsGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\gls{" + tag + "}"));

                value = (String) entry.getValue().get("Plural");

                capValue = value.substring(0, 1).toUpperCase() + value.substring(1);
                // text_replacement=addGlosplGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + capValue + "(?!\\S)", "\\Glspl{" + tag + "}"));

                lowerValue = value.substring(0, 1).toLowerCase() + value.substring(1);
                // text_replacement=addglsplGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + lowerValue + "(?!\\S)", "\\glspl{" + tag + "}"));

                value = (String) entry.getValue().get("Symbol");
                //text_replacement=addglssymbolGlossariecePopup_Method(tag);
                glossarytool.textArea.setText(glossarytool.textArea.getText().replaceAll("(?<!\\S)" + value + "(?!\\S)", "\\glssymbol{" + tag + "}"));

            }
        }

//        public void repeattags_add(String selected_word2, String replacement_word) {
//            repeatTags.put(selected_word2, replacement_word);
//
//        }

    }
