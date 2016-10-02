/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author nidal
 */
public class WriteToGlossaryFile {
    


    public void writeto_GlossaryFileMethod(Map<String,Map> gMap,String glossaryFileName) {

        
       
        GlossaryTool glossarytool = new GlossaryTool();
        String all_gls;
        String value;
    
         
        Iterator<Map.Entry<String, Map>> iterator = gMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map> entry = iterator.next();

            all_gls = null;

            value = (String) entry.getValue().get("Tag");
            // if(!value.equals(""))
            all_gls = "\n\\newglossaryentry{" + value + "}\n" + "{";

            value = (String) entry.getValue().get("Name");
            if (value != null && !value.isEmpty()) {
                all_gls = all_gls + "\n\tname={" + value + "},";
            }

            value = (String) entry.getValue().get("Plural");
            if (value != null && !value.isEmpty()) {
                all_gls = all_gls + "\n\tplural={" + value + "},";
            }

            value = (String) entry.getValue().get("Symbol");
            if (value != null && !value.isEmpty()) {
                all_gls = all_gls + "\n\tsymbol={" + value + "},";
            }

            value = (String) entry.getValue().get("Description");
            if (value != null && !value.isEmpty()) {
                all_gls = all_gls + "\n\tdescription={" + value + "}";
            }

            all_gls = all_gls + "\n}";

            System.out.println("after desc" + all_gls);

            try {
                FileWriter out_gls;
                // out = new FileWriter(fn);

                out_gls = new FileWriter(glossaryFileName, true);
                out_gls.write(all_gls);
                out_gls.close();

            } catch (Exception err) {
                System.out.println("Error in saving to glossary: " + err);
            }

        }

    }

}