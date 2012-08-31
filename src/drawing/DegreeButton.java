package drawing;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import prefuse.Visualization;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.io.GraphGMLReader;
import prefuse.visual.VisualItem;
import analysis.Ratios;

@SuppressWarnings("serial")
public class DegreeButton extends JMenuItem 
{
	Visualization s;
	String set;
	/**
	 * Initializes the Degree Menu 
	 * 
	 * @param name String Name of the menu 
	 * @param m_vis Visualization Visualization element
	 * @param dataset String Name of dataset
	 */
	DegreeButton(String name,Visualization m_vis, String dataset)
	{
		super(name);
		s=m_vis;
		set=dataset;
	}
	/**
	 *Reads the dataset and returns the graph 
	 * @return
	 */
	public Graph readGraph1()
	{
		Graph g=null;
		try
		{
			GraphGMLReader a=new GraphGMLReader(); 
			g=a.readGraph(set);
		}catch(Exception e)
		{}
		return g; 
	}
	/**
	 * Handles Mouse Click
	 */
	public void processMouseEvent(MouseEvent e)
	{

		if (e.getButton()==1)
		{
			Graph g=readGraph1();
			Ratios analysis=new Ratios();
			ArrayList<Node> ratio=analysis.degreeVariaton(g);

			int k=0;
			for (k=0;k<ratio.size();k++)
			{
				VisualItem x=s.getVisualItem("graph.nodes", ratio.get(k));
				x.setSize(x.getStartSize()*1.7);
				x.setHighlighted(true);
			}
		}
	}
}
