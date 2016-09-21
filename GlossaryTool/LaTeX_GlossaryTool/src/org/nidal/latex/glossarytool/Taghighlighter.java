/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.Color;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author nidal
 */
//public class Taghighlighter {
//public class TagHighlighter {

   
            
public class Taghighlighter extends DefaultHighlighter.DefaultHighlightPainter {
      GlossaryTool glossaryTool = new GlossaryTool();
       ReadGlossaryFile readGlossaryFile = new ReadGlossaryFile();
    
    
    
//        void highlightMethod() {
//      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    Boolean check = readGlossaryFile.GlossaryFileCheck("suitable") ;
//    if(check)
//     // if(readGlossaryFile.glossary_saved.contains("suitable")) 
//        highlight(glossaryTool.textArea,"suitable" );
//    }
        
        public Taghighlighter(Color color) {
        super(color);
    }
            
//}
    Highlighter.HighlightPainter taghighlighter = new Taghighlighter(Color.green) ;
    
    private void removehighlight(RSyntaxTextArea textAreahighlight) {
        Highlighter hilite = textAreahighlight.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();
        
        for (int i=0; i<hilites.length; i++)
        { 
            if (hilites[i].getPainter() instanceof Taghighlighter)
        {  
            hilite.removeHighlight(hilites[i]);
        }
        }
        
    }
    
    public void highlight(RSyntaxTextArea textAreahighlight,String stringpattern ) {
        removehighlight(textAreahighlight);
        try{
        Highlighter hilite = textAreahighlight.getHighlighter();
        Document doc = textAreahighlight.getDocument();
        String text = doc.getText(0, doc.getLength());
        int pos = 0;
        
        while((pos=text.toUpperCase().indexOf(stringpattern.toUpperCase(),pos))>=0){
            hilite.addHighlight(pos, pos+stringpattern.length(), taghighlighter) ;
            pos +=stringpattern.length();
            
        }
         } 
        catch(Exception e){
        }
    }
    

}  


