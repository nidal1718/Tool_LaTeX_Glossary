/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nidal
 */
//class GlossaryEntryClass {

   
       @SuppressWarnings("serial")
    public class GlossaryEntryClass {

        private HashMap<String, String> fields;
        //Map<String, Map> gMap = new HashMap<String, Map>();

        GlossaryEntryClass(Map<String, Map> gMap)
        {
       //  this.gMap = gMap ;
        }
        public GlossaryEntryClass(String tag_gls2, String name_gls2, String symbol_gls2, String plural_gls2, String desc_gls2,Map<String,Map> gMap) {
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

            //gMap= 
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
