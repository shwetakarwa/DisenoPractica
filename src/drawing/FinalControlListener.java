package drawing;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import prefuse.Visualization;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.io.GraphGMLReader;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

public class FinalControlListener extends ControlAdapter implements Control {
	JPopupMenu jpub;
		
	/**
	 * Highlights, magnifies and views node information when the mouse is passed over it
	 * 
	 * @param item VisualItem the node over which the mouse hovers
	 * @param e MouseEvent the mouse event 
	 */
	public void itemEntered(VisualItem item, MouseEvent e)
	{
		String test=((String) item.get("label")).substring(0,6);
		if (!test.equals("Clique"))
		{
			System.out.println("YO");
			item.setSize(item.getStartSize()*3);
		}
		if(item instanceof NodeItem)
		{
			String label = ((String) item.get("label"));
			int ID = ((Integer) item.get("id"));
			jpub = new JPopupMenu();
			jpub.add(ID+". " + label);
			jpub.show(e.getComponent(),(int) e.getX()+10,
                            (int) e.getY()+10);	
		}
				
	}
	/**
	 * Handles mouse exit condition
	 * 
	 * @param item VisualItem the node over which the mouse hovers
	 * @param e MouseEvent the mouse event
	 */
	public void itemExited(VisualItem item, MouseEvent e)
	{
		String test=((String) item.get("label")).substring(0,6);
		if (!test.equals("Clique"))
		{
			item.setSize(item.getStartSize());	
		}
		jpub.setVisible(false);
	}
}
