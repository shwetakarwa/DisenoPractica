package read;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.RandomLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.render.DefaultRendererFactory;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class ReadFiles {
	public static Graph graph = null; 
	public static Visualization vis; 
	public static Display d; 
	public static void main(String argv[])
	{
		try
		{
			read("polbooks.gml");
			setUpVisualization();
			setUpRenderers();
			setUpActions();
			setUpDisplay();
			JFrame frame = new JFrame("Prefuse Example");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.add(d);
		    frame.pack();           
		    frame.setVisible(true); 
		    vis.run("color");
		    vis.run("layout");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Graph read(String file) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(file));
		String t, column="";
		t=in.readLine();
		boolean flag = true;
		int index, source, target;
		
		//boolean directed =false;
		while (t!=null)
		{
			if (t.matches("..directed.."))
			{
				if (t.charAt(11)=='0')
					graph = new Graph(false);
				else
					graph = new Graph(true);
			}
			else if (t.matches("  node"))
			{
				Node n = graph.addNode();
				if (flag)
				{
					in.readLine();
					t = in.readLine();
					while (!t.equals("  ]"))
						
					{
						//System.out.println(t);
						t = t.substring(4,t.length());
						index = t.indexOf(" ");
						column = t.substring(0,index);
						
						if (t.charAt(index+1)== '"')
						{
							graph.addColumn(column, String.class);
							n.set(column, t.substring(index+2,t.length()-1));
						}
						else
						{
							graph.addColumn(column, Integer.class);
							n.set(column, Integer.parseInt(t.substring(index+1,t.length())));
						}
						t = in.readLine();
					}
					flag = false;
				}
				else
				{
					in.readLine();
					t = in.readLine();
					while (!t.equals("  ]"))
					{
						t = t.substring(4,t.length());
						index = t.indexOf(" ");
						column = t.substring(0,index);
						if (t.charAt(index+1)== '"')
						{
							n.set(column, t.substring(index+2,t.length()-1));
						}
						else
						{
							//System.out.println(t.substring(index+1,t.length()));
							n.set(column, Integer.parseInt(t.substring(index+1,t.length())));
						}
						t = in.readLine();
					}
				}
			}
			else if (t.matches("  edge"))
			{
				in.readLine();
				t = in.readLine();
				source = Integer.parseInt(t.substring(11,t.length()));
				t = in.readLine();
				target = Integer.parseInt(t.substring(11,t.length()));
				graph.addEdge(source,target);
				in.readLine();
			}	
			t=in.readLine();
		}
		return graph;
	}
	public static void setUpVisualization()
	{
		vis = new Visualization();
		vis.add("Graph",graph);
	}
	
    // -- 3. the renderers and renderer factory -------------------------
	public static void setUpRenderers()
	{
		FinalRenderer r = new FinalRenderer();
 		vis.setRendererFactory(new DefaultRendererFactory(r));
	}
	
	// -- 4. the actions --------------------------------------
	public static void setUpActions()
	{
		int[] palette = {ColorLib.rgb(200, 0, 0),ColorLib.rgb(0, 200, 0),ColorLib.rgb(0, 0, 200)}; 
		DataColorAction fill = new DataColorAction("Graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
		ColorAction edges = new ColorAction("Graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
		ActionList color = new ActionList();
		color.add(fill);//, ColorLib.red(200));
		color.add(edges);
		ActionList layout = new ActionList();//Activity.INFINITY);
		layout.add(new RandomLayout("Graph"));
		layout.add(new RepaintAction());
		layout.add(new ForceDirectedLayout("Graph", true));
		vis.putAction("color", color);
		vis.putAction("layout", layout);
	}
	// -- 5. the display ----------------------------------------
	public static void setUpDisplay()
	{
		d = new Display(vis);
		d.setSize(720, 500);  
		d.addControlListener(new DragControl());
		d.addControlListener(new PanControl()); 
		d.addControlListener(new ZoomControl());
		//d.addControlListener(new FinalControlListener());
	} 
}
