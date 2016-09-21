/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;


import java.util.regex.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Print all the strings that match a given pattern from a file.
 */
//http://www.java2s.com/Code/Java/Regular-Expressions/Printallthestringsthatmatchagivenpatternfromafile.htm
public class ReaderIter {
    
 
    
  public static void main(String[] args) throws IOException {
    // The RE pattern
    Pattern pattern = Pattern.compile("(?<=newglossaryentry[{]).*?(?=})");
   // Pattern pattern2 = Pattern.compile("(?<=newglossaryentry[{]).*?(?=})");
     //HashMap<Object,String> glossary_saved = new HashMap<Object,String>();
     
        ArrayList<String> glossary_saved = new ArrayList<String>();

    // A FileReader (see the I/O chapter)
    BufferedReader r = new BufferedReader(new FileReader("glossary.tex"));

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
       // glossary_saved.put(new Integer(i),line.substring(start, end));
        glossary_saved.add(line.substring(start, end));
        i++;
     //   System.out.println(line.substring(start, end));
       
      System.out.println(glossary_saved);
        
      }
    }
    
//     Iterator itr=glossary_saved.keySet().iterator();
//    while (itr.hasNext()) {
//        String key =  itr.next().toString();
//        String value=glossary_saved.get(key).toString();
//        System.out.println(key+"="+value);
//    }
  
   

// System.out.println("This is a map of Maps:   " + glossary_saved); 
  }
  
  private void addtoArrayList()
  {
  }
  

  
  private void printarrayList(){
     
  }
  
   
  
}