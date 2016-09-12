/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.nidal.latex.glossarytool;

import org.fife.ui.rsyntaxtextarea.TextListener;
import org.nidal.latex.glossarytool.RTextEvent;

/**
 * The listener interface for receiving text events.
 *
 * The class that is interested in processing a text event
 * implements this interface. The object created with that
 * class is then registered with a component using the
 * component's <code>addTextListener</code> method. When the
 * component's text changes, the listener object's
 * <code>textValueChanged</code> method is invoked.
 *
 * @author Georges Saab
 *
 * @see RTextEvent
 *
 * @since 1.1
 */
public interface RTextListener extends TextListener {

    /**
     * Invoked when the value of the text has changed.
     * The code written for this method performs the operations
     * that need to occur when text changes.
     * @param e
     */
    public void textValueChanged(RTextEvent e);
    

}
