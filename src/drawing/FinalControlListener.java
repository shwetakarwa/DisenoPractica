package drawing;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

import prefuse.controls.ControlAdapter;
import prefuse.controls.Control;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

public class FinalControlListener extends ControlAdapter implements Control {
	public void itemClicked(VisualItem item, MouseEvent e) 
	{
		if(item instanceof NodeItem)
		{
			String label = ((String) item.get("label"));
			JPopupMenu jpub = new JPopupMenu();
			jpub.add("Label: " + label);
			jpub.show(e.getComponent(),(int) item.getX(),
                            (int) item.getY());
		}
	}
}
