/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.TextAction;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.folding.FoldManager;
import org.fife.ui.rtextarea.RecordableTextAction;

/**
 *
 * @author nidal
 */
public class PopupSubclass extends RSyntaxTextArea {


    private JMenu ToolsPopupMenu;
 //   private FoldManager foldManager;
    
        private JMenuItem glsToolItem;
	private JMenuItem glsplToolItem;
	private JMenuItem GlsToolItem;
	private JMenuItem GlsplToolItem;
	private JMenuItem glssymbolToolItem;
        private FoldManager foldManager;
        
        private static final String MSG	= "org.fife.ui.rtextarea.RTextArea";
        
        private static RecordableTextAction glsToolItemAction;
	private static RecordableTextAction glsplToolItemAction;
	private static RecordableTextAction GlsToolItemAction;
	private static RecordableTextAction GlsplToolItemAction;
        private static RecordableTextAction glssymbolToolItemAction;

        
            static void creatPopupMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
        
        /**
	 * Overridden to toggle the enabled state of various
	 * RSyntaxTextArea-specific menu items.
	 *
	 * If you set the popup menu via {@link #setPopupMenu(JPopupMenu)}, you
	 * will want to override this method, especially if you removed any of the
	 * menu items in the default popup menu.
	 *
	 * @param popupMenu The popup menu.  This will never be <code>null</code>.
	 * @see #createPopupMenu()
	 * @see #setPopupMenu(JPopupMenu)
	 */
	@Override
	protected void configurePopupMenu(JPopupMenu popupMenu) {

		super.configurePopupMenu(popupMenu);

		// They may have overridden createPopupMenu()...
		if (popupMenu!=null && popupMenu.getComponentCount()>0 &&
				ToolsPopupMenu!=null) {
			ToolsPopupMenu.setEnabled(foldManager.
						isCodeFoldingSupportedAndEnabled());
		}
	}
        
        


        
        /**
	 * See createPopupMenuActions() in RTextArea.
	 * TODO: Remove these horrible hacks and move localizing of actions into
	 * the editor kits, where it should be!  The context menu should contain
	 * actions from the editor kits.
	 */
	static void createToolsPopupMenuActions() {

		ResourceBundle msg = ResourceBundle.getBundle(MSG);
System.out.println("reached glossary class");
		glsToolItemAction = new RSyntaxTextAreaEditorKit.
				ToggleCurrentFoldAction();
		glsToolItemAction.setProperties(msg, "Action.ToggleCurrentFold");

		glsplToolItemAction = new RSyntaxTextAreaEditorKit.
				CollapseAllCommentFoldsAction();
		glsplToolItemAction.setProperties(msg, "Action.CollapseCommentFolds");

		GlsToolItemAction = new RSyntaxTextAreaEditorKit.CollapseAllFoldsAction(true);
		GlsplToolItemAction = new RSyntaxTextAreaEditorKit.ExpandAllFoldsAction(true);
                glssymbolToolItemAction = new RSyntaxTextAreaEditorKit.ExpandAllFoldsAction(true);

	}
        
        /**
	 * Creates the right-click popup menu. Subclasses can override this method
	 * to replace or augment the popup menu returned.
	 *
	 * @return The popup menu.
	 * @see #setPopupMenu(JPopupMenu)
	 * @see #configurePopupMenu(JPopupMenu)
	 * @see #createPopupMenuItem(Action)
	 */
//    @Override
//	protected JPopupMenu createPopupMenu() {
//		JPopupMenu menu = new JPopupMenu();
//		menu.addSeparator();
//		//menu.add(redoMenuItem = createPopupMenuItem(redoAction));
//		//menu.add(createPopupMenuItemglsToolItem));
//                menu.add(glsToolItem);
//                menu.add(glsplToolItem);
//                menu.add(GlsToolItem);
//                menu.add(GlsplToolItem);
//		menu.add(glssymbolToolItem);
//	
//		return menu;
//	}
//        
//        
        
        
//        @Override
	protected JPopupMenu createPopupMenu() {
	JPopupMenu popup = super.createPopupMenu();
              //  super.appendFoldingMenu(popup);
		appendToolsMenu(popup);
		return popup;
	}
        
        /**
	 * Sets the popup menu used by this text area.<p>
	 *
	 * If you set the popup menu with this method, you'll want to consider also
	 * overriding {@link #configurePopupMenu(JPopupMenu)}, especially if you
	 * removed any of the default menu items.
	 *
	 * @param popupMenu The popup menu.  If this is <code>null</code>, no
	 *        popup menu will be displayed.
	 * @see #getPopupMenu()
	 * @see #configurePopupMenu(JPopupMenu)
	 */
//	public void setPopupMenu(JPopupMenu popupMenu) {
//		popupMenu = popupMenu;
//		//popupMenuCreated = true;
//	}
        
//        private void copyAction(){
//        String text_selected = textArea.getSelectedText();      
//         String text_replacement = "\\gls{" + text_selected + "}" ;
//        textArea.replaceSelection(text_replacement);
//        textChanged = true;
//    }
//        
//        private void addglsprefix(){
//        String text_selected = textArea.getSelectedText();      
//         String text_replacement = "\\gls{" + text_selected + "}" ;
//        textArea.replaceSelection(text_replacement);
//        textChanged = true;
//    }
    
        
        /**
	 * Appends a submenu with code folding options to this text component's
	 * popup menu.
	 *
	 * @param popup The popup menu to append to.
	 * @see #createPopupMenu()
	 */
	protected void appendToolsMenu(JPopupMenu popup) {
		popup.addSeparator();
		ResourceBundle bundle = ResourceBundle.getBundle(MSG);
		//ToolsPopupMenu = new JMenu(bundle.getString("ContextMenu.Folding"));
                ToolsPopupMenu = new JMenu("ContextMenu.TextArea");
        
                ToolsPopupMenu.add(createPopupMenuItem(glsToolItemAction));
		ToolsPopupMenu.add(createPopupMenuItem(glsplToolItemAction));
		ToolsPopupMenu.add(createPopupMenuItem(GlsToolItemAction));
		ToolsPopupMenu.add(createPopupMenuItem(GlsplToolItemAction));
                ToolsPopupMenu.add(createPopupMenuItem(glssymbolToolItemAction));
		popup.add(ToolsPopupMenu);

	}
        
//         private class ToolsPopupTextGlossaryPopup  {
//  
//        public ToolsPopupTextGlossaryPopup() {                     
//
//           super("Tools");
//        }}
        
        
}
