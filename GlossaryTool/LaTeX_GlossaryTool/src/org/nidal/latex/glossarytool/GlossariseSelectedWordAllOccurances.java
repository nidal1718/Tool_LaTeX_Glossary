/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 *
 * @author nidal
 */

public class GlossariseSelectedWordAllOccurances {
      final JTextPane textPane = new JTextPane();
    final WordSearcher3 searcher = new WordSearcher3(textPane);
   
   
//     final JTextPane textPane = new JTextPane();
   // final WordSearcher3 searcher = new WordSearcher3(textPane);
    GlossaryTool glossarytool = new GlossaryTool();
    
    
    
          public String checkavailabilityin_Map3(String word){
          String tag = null ;
          String text_replacement = null ;
          int flag = 0 ;
          Boolean capitalcheck= capitalornot(word);
          Iterator<Map.Entry<String, Map>> iterator = glossarytool.gMap.entrySet().iterator();
           while(iterator.hasNext()){
            Map.Entry<String, Map> entry = iterator.next();

            if(word.toLowerCase().equals(entry.getValue().get("Tag")))
            { tag = (String) entry.getValue().get("Tag");
                if(capitalcheck)
                {text_replacement=glossarytool.addGlosGlossariecePopup_Method(tag);
                  flag = 1 ;
                 }
                 else
                { text_replacement=glossarytool.addglsGlossariecePopup_Method(tag);
              //  repeattags_add(word,text_replacement);
                  flag = 2 ;
                }

                break ; }

            else if(word.toLowerCase().equals(entry.getValue().get("Plural")))
            { tag = (String) entry.getValue().get("Tag");
                if(capitalcheck)
                { text_replacement=glossarytool.addGlosplGlossariecePopup_Method(tag);
                  //searcher3.search(value);
                    flag = 3 ;
               // repeattags_add(word,text_replacement); 
                }

                else
            { text_replacement=glossarytool.addglsplGlossariecePopup_Method(tag);
            // repeattags_add(word,text_replacement); 
              flag = 4 ;}
                break ;}

            else if(word.equals(entry.getValue().get("Symbol")))
            { tag = (String) entry.getValue().get("Tag");
                text_replacement=glossarytool.addglssymbolGlossariecePopup_Method(tag);
                   // repeattags_add(word,text_replacement);
                      flag = 5 ;
                break ; }

                //  iterator.remove(); // right way to remove entries from Map, // avoids ConcurrentModificationException
        }

           return text_replacement ;
      }
public Boolean capitalornot(String text_selected)
      { Boolean capitalcheck ;
          if(Character.isUpperCase(text_selected.charAt(0))==true)
                capitalcheck = true ;
          else
            capitalcheck = false ;

          return capitalcheck ;
      }
}          
                      
            
 class WordSearcher3 {
    
      GlossaryTool glossarytool = new GlossaryTool();
     GlossariseSelectedWordAllOccurances gswao= new GlossariseSelectedWordAllOccurances();
  public WordSearcher3(JTextComponent comp) {
    this.comp = comp;
 //   this.painter = new UnderlineHighlighter.UnderlineHighlightPainter(  Color.red);
  }

  // Search for a word and return the offset of the
  // first occurrence. Highlights are added for all
  // occurrences found.
  public int search3(String word) {
    int firstOffset = -1;
 //   Highlighter highlighter = comp.getHighlighter();
    //word = " "+word+" ";

    
//    if(repeatTags.containsKey(text_selected)==true)
////        textArea.setText(textArea.getText().replaceAll("(\\b"+text_selected+"\\b)", repeatTags.get(text_selected)));
//               textArea.setText(textArea.getText().replaceAll( "(?<!\\S)"+text_selected+"(?!\\S)", repeatTags.get(text_selected)));
    
    
    if (word == null || word.equals("")) {
      return -1;
    }

    // Look for the word we are given - insensitive search
    String content = null;
     String content1 = null;
    try {
      Document d = comp.getDocument();
      content = d.getText(0, d.getLength()).toLowerCase();
      content1 = "(?<!\\S)"+content+"(?!\\S)" ;
    } catch (BadLocationException e) {
      // Cannot happen
      return -1;
    }

    //word = word.toLowerCase();
   String word1 = "(?<!\\S)"+word+"(?!\\S)" ;
    int lastIndex = 0;
    int wordSize = word.length();

    while ((lastIndex = content.indexOf(word, lastIndex)) != -1) {
   
int endIndex = lastIndex + wordSize;
     // try {
        //highlighter.addHighlight(lastIndex, endIndex, painter);
        gswao.checkavailabilityin_Map3(word);
  //    } catch (BadLocationException e) {
        // Nothing to do
    //  }
      if (firstOffset == -1) {
        firstOffset = lastIndex;
      }
      lastIndex = endIndex;
    }

    return firstOffset;
  }

  protected JTextComponent comp;

 // protected Highlighter.HighlightPainter painter;

}
 
  

