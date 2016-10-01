/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
//import org.nidal.latex.glossarytool.GlossaryTool.Intelligence;

/**
 *
 * @author nidal
 */
public class AddGlossaryDialogClass extends JFrame  {
    
//GlossaryTool glossarytool = new GlossaryTool();
//ReadGlossaryFile readGlossaryFile = new ReadGlossaryFile();
//Intelligence intelligence = new Intelligence();
//GlossaryEntryClass1 glossaryentryclass1  ; //= new GlossaryEntryClass1();
//       private JDialog d1;
//
//    void createAndShowadd_glsDialog() {
//
//        //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringBoxProject/src/layout/SpringBox.java
//        JPanel dpan = new JPanel();
//        JPanel dpan1 = new JPanel();
//        JPanel dpanel_master = new JPanel();
//        //  Container dpan = d1.getContentPane();
//
//        // dpan.setLayout(new BorderLayout());
//        dpan.setLayout(new SpringLayout());
//        //  dpan.setLayout(new GridLayout(5,2));
//        // dpan.setLayout(new FlowLayout());
//        dpan1.setLayout(new FlowLayout());
//        //  dpan1.setLayout(new GridLayout(0,3));
//
//        dpan.setMaximumSize(new Dimension(520, 400));
//        dpan1.setMaximumSize(new Dimension(520, 150));
//        // dpan1.setLayout(null);
//        dpanel_master.setLayout(new BorderLayout());
//        // dpanel_master.setLayout(new GridLayout(2,0));
//        //dpan.setLayout(new GridBagLayout());
//
//        //  GridBagConstraints c = new GridBagConstraints();
//        //       if(shouldFill) {
//        //natural height, maximum width
//        //   c.fill = GridBagConstraints.HORIZONTAL;
////}
//        // setDefaultCloseOperation(EXIT_ON_CLOSE);
//        //  d1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        // setLayout(new FlowLayout());
//        // Must be called before creating JDialog for
//        // the desired effect
//        JDialog.setDefaultLookAndFeelDecorated(true);
//
//        // A perfect constructor, mostly used.
//        // A dialog with current frame as parent
//        // a given title, and modal
//        d1 = new JDialog(this, "Add to Glossary", true);
//
//        // Set size
////        if (RIGHT_TO_LEFT)
////            dpan.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
////
//        // Set some layout
//        //  d1.setLayout(new FlowLayout());
////    current_y += 30;
//        JLabel selectedword_label = new JLabel("Selected Word");
//        JTextField selectedword_tf = new JTextField(glossarytool.textArea.getSelectedText());
//        selectedword_tf.setEditable(false);
//
//        JLabel tag_label = new JLabel("Tag");
//
//        JTextField tag_tf = new JTextField("");
//        tag_label.setLabelFor(tag_tf);
//
//        JLabel name_label = new JLabel("Name");
////
//        JTextField name_tf = new JTextField("");
////
//        JLabel plural_label = new JLabel("Plural");
////
//        JTextField plural_tf = new JTextField("");
////
//        JLabel symbol_label = new JLabel("Symbol");
////
//        JTextField symbol_tf = new JTextField("");
////
//        JLabel desc_label = new JLabel("Desciption");
////
//        RSyntaxTextArea desc_Area = new RSyntaxTextArea("");
//
//        JButton cancel_gls = new JButton("Cancel");
//
//        JButton save_gls = new JButton("Save");
//        JButton clear_gls = new JButton("Clear");
//
//        tag_label.setLabelFor(tag_tf);
//        // name_label.setLabelFor(name_tf);
//        //plural_label.setLabelFor(plural_tf);
//        //   symbol_label.setLabelFor(symbol_tf);
//        //   desc_label.setLabelFor(desc_Area);
//
//        dpan.add(selectedword_label);
//        dpan.add(selectedword_tf);
//        dpan.add(tag_label);
//        dpan.add(tag_tf);
//        dpan.add(name_label);
//        dpan.add(name_tf);
//        dpan.add(plural_label);
//        dpan.add(plural_tf);
//        dpan.add(symbol_label);
//        dpan.add(symbol_tf);
//        dpan.add(desc_label);
//        dpan.add(desc_Area);
//
//        dpan1.add(cancel_gls);
//        cancel_gls.setPreferredSize(new Dimension(120, 50));
//        dpan1.add(save_gls);
//        save_gls.setPreferredSize(new Dimension(120, 50));
////     dpan1.add(clear_gls,BorderLayout.LINE_END);
//        dpan1.add(clear_gls);
//        clear_gls.setPreferredSize(new Dimension(120, 50));
//
//        //Lay out the buttons in one row and as many columns
//        //as necessary, with 6 pixels of padding all around.
//        //if the word already exists then it disables the save button
//        String checktagExists = readGlossaryFile.checkifSavedinGlossaryFile(glossarytool.textArea.getSelectedText());
//
//        if (checktagExists) {
//            save_gls.setVisible(false);
//        }
//
//        cancel_gls.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                d1.dispose();
//
//            }
//        });
//
//        save_gls.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                String tag_gls = tag_tf.getText().trim();
//
//                String name_gls = name_tf.getText().trim();;
//                //   JTextField name_tf = new JTextField("");
//                String symbol_gls = symbol_tf.getText().trim();
//                String plural_gls = plural_tf.getText().trim();
//                String desc_gls = desc_Area.getText().trim();
//              //  GlossaryEntryClass gec;
//
//                //sort use
//                String all_gls;
//
//                System.out.println(tag_gls + "" + name_gls + "" + symbol_gls + "" + plural_gls + "" + desc_gls);
//
//                
//                 glossaryentryclass1 = new GlossaryTool.GlossaryEntryClass1(tag_gls.toLowerCase(), name_gls, symbol_gls, plural_gls, desc_gls);
//                //GlossaryTool.GlossaryEntryClass1 glossaryentryclass1 = new GlossaryTool.GlossaryEntryClass1(tag_gls.toLowerCase(), name_gls, symbol_gls, plural_gls, desc_gls);
//            //    glossaryentryclass1 = new org.nidal.latex.glossarytool.GlossaryEntryClass1(tag_gls.toLowerCase(), name_gls, symbol_gls, plural_gls, desc_gls);
//
//
//                System.out.println(glossarytool.gMap);
//
//                String replacement = intelligence.checkavailabilityin_Map(glossarytool.textArea.getSelectedText());
//              //  intelligence.repeattags_add(textArea.getSelectedText(), replacement);
//                readGlossaryFile.addtoArrayListFromDialogSave(tag_gls);
//                d1.dispose();
//
//            }
//        });
//
//        clear_gls.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                name_tf.setText("");
//                plural_tf.setText("");
//                symbol_tf.setText("");
//                desc_Area.setText("");
//
//            }
//        });
//        int count = dpan.getComponentCount() / 2;
//        System.out.println(count);
//
//        SpringUtilities.makeCompactGrid(dpan, count, 2, 10, 10, 10, 10);
//
//        dpanel_master.add(dpan, BorderLayout.CENTER);
//        dpanel_master.add(dpan1, BorderLayout.SOUTH);
//
//        d1.add(dpanel_master);
//
//        d1.setSize(520, 500);
//        d1.setMinimumSize(new Dimension(520, 400));
//
//        d1.setLocationRelativeTo(null);
//
//        d1.setVisible(true);
//    }
}