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
		
	
	public void itemEntered(VisualItem item, MouseEvent e)
	{
				
		item.setSize(item.getStartSize()*3);
		if(item instanceof NodeItem)
		{
			
			String label = ((String) item.get("label"));
			int ID = ((Integer) item.get("id"));
			//JPopupMenu jpub = new JPopupMenu();
			jpub = new JPopupMenu();
			jpub.add(ID+". " + label);
			//jpub.add("ID: "+ ID);
			jpub.show(e.getComponent(),(int) e.getX()+10,
                            (int) e.getY()+10);
			
		}
				
	}
	public void itemExited(VisualItem item, MouseEvent e)
	{
		item.setSize(item.getStartSize());	
		jpub.setVisible(false);
	}
}
