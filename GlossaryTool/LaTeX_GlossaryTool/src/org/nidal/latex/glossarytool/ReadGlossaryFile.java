/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nidal
 */
public class ReadGlossaryFile {
 
    // The RE pattern
    Pattern pattern = Pattern.compile("(?<=newglossaryentry[{]).*?(?=})");
    ArrayList<String> glossary_saved = new ArrayList<String>();
    Boolean checktagAvailability = false ;
    
   // GlossaryTool glossaryTool = new GlossaryTool();
    
    // private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);
 
   void addtoArrayList(String glossaryFileName) throws FileNotFoundException, IOException
  { 
      
      BufferedReader r = new BufferedReader(new FileReader(glossaryFileName));
 
  //  BufferedReader r = new BufferedReader(new FileReader("glossary.tex"));

    // For each line of input, try matching in it.
    String line;
    int i=0;
    while ((line = r.readLine()) != null) {
      // For each match in the line, extract and print it.
      Matcher m = pattern.matcher(line);
      while (m.find()) {
        // Simplest method:
        // System.out.println(m.group(0));

        // Get the starting position of the text
        int start = m.start(0);
        // Get ending position
        int end = m.end(0);
        // Print whatever matched.
        // Use CharacterIterator.substring(offset, end);

        glossary_saved.add(line.substring(start, end));
    
     //   System.out.println(line.substring(start, end));
       
     // 
        
      }
    }
  }
  
  void addtoArrayListFromDialogSave(String tagDialog)
  {  glossary_saved.add(tagDialog);
  }
  
  
  private void printarrayList(){
     System.out.println(glossary_saved);
  }

    void addtoArrayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    Boolean checkifSavedinGlossaryFile(String checktag) 
    { checktagAvailability = false;
if(glossary_saved.contains(checktag)) checktagAvailability=true;

return checktagAvailability ;
    }
    
    // for taghighlighter class
//      Boolean GlossaryFileCheck(String checktag3) 
//    {  Boolean schecktagAvailability = false ;
//if(glossary_saved.contains(checktag3)) schecktagAvailability=true;
//
//return schecktagAvailability ;
//    }
  
    
//    public boolean isCorrect(String word) {
//    List<String> possible = getWords(getCode(word));
//    if (possible.contains(word))
//      return true;
//    //JMH should we always try the lowercase version. If I dont then capitalized
//    //words are always returned as incorrect.
//    else if (possible.contains(word.toLowerCase()))
//      return true;
//    return false;
//  }
    
    
//    void highlightwords(RSyntaxTextArea textArea1){
//       
//        JTextArea glossarywordsList = new JTextArea();
//        Iterator<String> it = glossary_saved.iterator();
//       int tot = 0;
//       while(it.hasNext() && tot<3){
//           String element = it.next();
//           glossarywordsList.append(element + "\n");
//           System.out.println("elements are: " + element);
//           tot++;
//       }
//       
////        JTextArea glossarywordsList2 = new JTextArea(5, 50);
////        Iterator<String> it2 = textArea1.iterator();
////       int tot2 = 0;
////       while(it2.hasNext() && tot2<3){
////           String element = it2.next();
////           glossarywordsList.append(element + "\n");
////           System.out.println("elements are: " + element);
////           tot++;
////       }
//        
//        
//        //area.setText(glossary_saved);
//        Document doc1 = glossarywordsList.getDocument() ;
//        //Document doc2 = gt.textArea.getDocument();
//        Document doc2 = textArea1.getDocument();
//
//       // textArea1
//        
//         int max = Math.min(doc1.getLength(), doc2.getLength());
//        int startPos = 0;
//        try {
//            for (int pos = 0; pos < max; pos++) {
//
//                if (doc1.getText(pos, 1).equals(" ")) {
//
//                    int endPos = pos;
//                    String parent = doc1.getText(startPos, endPos - startPos);
//                    String child = doc2.getText(startPos, endPos - startPos);
//                    if (!parent.equals(child)) {
//
//                      //  highlight(field, startPos, endPos);
//                        highlight(gt.textArea, startPos, endPos);
//
//                    }
//
//                    startPos = endPos + 1;
//
//                }
//
//            }
//        } catch (BadLocationException exp) {
//            exp.printStackTrace();
//        }
//    }
//    
//    Boolean highlighter(RSyntaxTextArea textArea3) 
//    { checktagAvailability = false;
//    
//     JTextArea glossarywordsList = new JTextArea();
//        Iterator<String> it = glossary_saved.iterator();
//       int tot = 0;
//       while(it.hasNext() && tot<3){
//           String element = it.next();
//           if(textArea3.contains(element)) checktagAvailability=true;
//         //  glossarywordsList.append(element + "\n");
//           System.out.println("elements are: " + element);
//           tot++;
//       }
//
//
//return checktagAvailability ;
//    }
//    
//    public void highlight(RSyntaxTextArea textArea3, int startPos, int endPos) throws BadLocationException {
//
//        Highlighter hilite = textArea3.getHighlighter();
//        hilite.addHighlight(startPos, endPos, myHighlightPainter);
//
//    }
//    
//     class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
//
//        public MyHighlightPainter(Color color) {
//
//            super(color);
//
//        }
//    }
    
    //to highlight the code.
 
}
