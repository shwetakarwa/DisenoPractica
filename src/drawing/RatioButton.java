package drawing;

import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import prefuse.Visualization;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.io.GraphGMLReader;
import prefuse.visual.VisualItem;
import analysis.Ratios;


@SuppressWarnings("serial")
public class RatioButton extends JMenuItem
{
	Visualization s;
	String set;
	/**
	 * Initializes the Ratio Menu 
	 * 
	 * @param name String Name of the menu 
	 * @param m_vis Visualization Visualization element
	 * @param dataset String Name of dataset
	 */
	RatioButton(String name,Visualization m_vis, String dataset)
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
		try{
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
			Tuple t=g.getNode(0);
			//Visualization m_viz=item.getVisualization();
			//VisualItem x=s.getVisualItem("graph.nodes", t);
			Ratios analysis=new Ratios();
			ArrayList<Tuple> ratio=analysis.getDiffEdge(g);

			int k=0;
			for (k=0;k<ratio.size();k++)
			{
				VisualItem x=s.getVisualItem("graph.edges", ratio.get(k));
				//x.setHighlighted(true);
				x.setStroke(new BasicStroke(3));


			}

		}
		if (e.getButton()==0)
		{

			Graph g=readGraph1();
			Tuple t=g.getNode(0);
			//Visualization m_viz=item.getVisualization();
			VisualItem x=s.getVisualItem("graph.nodes", t);
			x.setSize(x.getStartSize());

		}

	}



}
