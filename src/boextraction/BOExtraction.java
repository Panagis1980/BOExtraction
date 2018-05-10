/**
 * 
 */
package boextraction;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.BOParentFrame;

public class BOExtraction extends ExcelExport 
{


    private static void createAndShowGUI() {
        BOParentFrame bo = new BOParentFrame();
        bo.setVisible(true);
        bo.pack();
    }
	
	public static void main(String[] args)
	{
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();     
            }
        });
	}
}

