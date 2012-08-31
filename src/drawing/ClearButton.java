
package drawing;

import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JMenuItem;
import prefuse.Visualization;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.io.GraphGMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.visual.VisualItem;


@SuppressWarnings("serial")
public class ClearButton extends JMenuItem
{
	Visualization s;
	String set;
	/**
	 * Initializes the Clear Button 
	 * 
	 * @param name String Name of the menu 
	 * @param m_vis Visualization Visualization element
	 * @param dataset String Name of dataset
	 */
	ClearButton(String name,Visualization m_vis, String dataset)
	{
		super(name);
		s=m_vis;
		set=dataset;
	}
	/**
	 *Reads the dataset and returns the graph 
	 * @return g Graph returns the graph
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
	 * 
	 * @param e MouseEvent the mouse action performed
	 */
	public void processMouseEvent(MouseEvent e)
	{

		if (e.getButton()==1)
		{
			Graph g=readGraph1();
			TupleSet nodes=g.getNodes();
			TupleSet edges=g.getEdges();
			Iterator<Tuple> all=nodes.tuples();
			Iterator<Tuple> alledges=edges.tuples();
			while (all.hasNext())
			{
				VisualItem x=s.getVisualItem("graph.nodes", all.next());
				x.setHighlighted(false);
				
				String test=((String) x.get("label")).substring(0,6);
				if (!test.equals("Clique"))
				{
					x.setSize(x.getStartSize());	
				}
			}
			while (alledges.hasNext())
			{
				VisualItem x=s.getVisualItem("graph.edges", alledges.next());
				x.setStroke(new BasicStroke(1));
			}

		}


	}



}

