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
 * Class to read the glossary file.
 * @author nidal
 */
public class ReadGlossaryFile {

    // The RE pattern
    Pattern pattern = Pattern.compile("(?<=newglossaryentry[{]).*?(?=})"); // Regex pattern to get the tag
//    Pattern pattern_name = Pattern.compile("(?<=name=[{]).*?(?=},)"); // Regex pattern to get the name
//    Pattern pattern_symbol = Pattern.compile("(?<=symbol=[{]).*?(?=},)"); // Regex pattern to get the symbol
//    Pattern pattern_plural = Pattern.compile("(?<=plural=[{]).*?(?=},)"); // Regex pattern to get the plural
//    Pattern pattern_desc = Pattern.compile("(?<=description=[{]).*?(?=})"); // Regex pattern to get the description
    private static ArrayList<String> glossary_saved = new ArrayList<String>();
  

    void addtoArrayList(String glossaryFileName) throws FileNotFoundException, IOException {

        BufferedReader r = new BufferedReader(new FileReader(glossaryFileName));

        String line;
        int i = 0;
        while ((line = r.readLine()) != null) {
            // For each match in the line, extract and print it.
            Matcher m = pattern.matcher(line);
            while (m.find()) {

                int start = m.start(0);
                // Get ending position
                int end = m.end(0);

                glossary_saved.add(line.substring(start, end));

               // System.out.println(line.substring(start, end));

            }
        }
    }

    void addtoArrayListFromDialogSave(String tagDialog) {
        glossary_saved.add(tagDialog);
    }

    private void printarrayList() {
//     System.out.println("Print"+glossary_saved);
    }

    Boolean checkifSavedinGlossaryFile(String checktag) {
        Boolean checktagAvailability = false;
        if (glossary_saved.contains(checktag)) {
            checktagAvailability = true;
        }

        return checktagAvailability;
    }

}
