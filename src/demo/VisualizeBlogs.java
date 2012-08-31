package demo;

import javax.swing.JFrame;

import prefuse.data.Graph;
import prefuse.data.io.GraphGMLReader;
import prefuse.util.ui.UILib;
import drawing.Cliquenodes;
import drawing.SpringForceModel;


public class VisualizeBlogs {

	/**
	 * main class for visualization of polblogs; replaces cliques by a single node in visualization
	 * 
	 * @param args 
	 * 
	 */
	  public static void main(String[] args) throws Exception{
	    UILib.setPlatformLookAndFeel();
	    GraphGMLReader a=new GraphGMLReader(); 
	    Graph g=a.readGraph("polblogs.gml");
	    Cliquenodes cliquenodes=new Cliquenodes(g.getNodeCount());
	    g=cliquenodes.getGraph(g,7);
	    
	    //g=cliquenodes.getcliquegraph(g);
	    JFrame frame = SpringForceModel.visual(g, "value",false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}