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
 */
public interface NewInterface extends SyntaxConstants {

    /**
     * Invoked when the value of the text has changed.
     * The code written for this method performs the operations
     * that need to occur when text changes.
     */
    /**
     *Copied from TextCompnonent.java #557
     * @param l **/
    void addTextListener(TextListener l);
    
}
