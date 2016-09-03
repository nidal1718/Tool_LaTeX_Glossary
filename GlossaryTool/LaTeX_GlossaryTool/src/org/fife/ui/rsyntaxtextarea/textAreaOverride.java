/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fife.ui.rsyntaxtextarea;

import java.awt.event.TextListener;


/**
 *
 * @author nidal
 * public class TextComponent extends Component implements Accessible {
 * public class textAreaOverride extends RSyntaxTextArea implements SyntaxConstants {
 */
//public class textAreaOverride extends Component implements Accessible {
public class textAreaOverride extends RSyntaxTextArea implements NewInterface {
    /**
     * Invoked when the value of the text has changed.
     * The code written for this method performs the operations
     * that need to occur when text changes.
     */
  /**
     *Copied from TextCompnonent.java #557
     * @param l **/
    @Override
    public void addTextListener(TextListener l) {
		listenerList.add(TextListener.class, l);
	}
    
  
}
