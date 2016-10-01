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
//    Pattern pattern_name = Pattern.compile("(?<=name=[{]).*?(?=},)");
//    Pattern pattern_symbol = Pattern.compile("(?<=symbol=[{]).*?(?=},)");
//    Pattern pattern_plural = Pattern.compile("(?<=plural=[{]).*?(?=},)");
//    Pattern pattern_desc = Pattern.compile("(?<=description=[{]).*?(?=})");
    ArrayList<String> glossary_saved = new ArrayList<String>();
    Boolean checktagAvailability = false ;

 
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
    
  
}
