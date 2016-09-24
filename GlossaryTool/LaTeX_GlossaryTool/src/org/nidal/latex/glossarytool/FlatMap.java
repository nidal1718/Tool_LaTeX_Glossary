/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author nidal
 */


  //flatten map class
  public class FlatMap {
      GlossaryTool gt = new GlossaryTool();
            // Map<String, Map> gMap1 ;

    public static Stream<Object> flatten(Object o) {
        if (o instanceof Map<?, ?>) {
            return ((Map<?, ?>) o).values().stream().flatMap(FlatMap::flatten);
        }
        return Stream.of(o);
    }

//    public static void main(String[] args) {
//        Map<String, Object> map0 = new TreeMap<>();
//        map0.put("key1", "value1");
//        map0.put("key2", "value2");
//        Map<String, Object> map1 = new TreeMap<>();
//        map0.put("key3", map1);
//        map1.put("key3.1", "value3.1");
//        map1.put("key3.2", "value3.2");
//        Map<String, Object> map2 = new TreeMap<>();
//        map1.put("key3.3", map2);
//        map2.put("key3.3.1", "value3.3.1");
//        map2.put("key3.3.2", "value3.3.2");
//
//        List<Object> collect = gt.gmap.values().stream().flatMap(FlatMap::flatten).collect(Collectors.toList());
//        // or
//        List<Object> collect2 = flatten(map0).collect(Collectors.toList());
//        System.out.println(collect); 
//    }
    
    public FlatMap(Map<String, Map> gMap1) {
        List<Object> collect = gMap1.values().stream().flatMap(FlatMap::flatten).collect(Collectors.toList());
        
        storemap(collect);
        // or
//        List<Object> collect2 = flatten(map0).collect(Collectors.toList());
//        System.out.println(collect);

    }
    
     public static void storemap(List<Object> collect1) 
     {
             System.out.println("test"+collect1);
     }
   
            
}