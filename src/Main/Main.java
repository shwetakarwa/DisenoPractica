package Main;

import javax.swing.JFrame;
import drawing.*;
import prefuse.data.Graph;
import prefuse.data.io.GraphGMLReader;
import prefuse.util.ui.UILib;
import analysis.random_graph;
import analysis.ratio;

public class Main {

	/**
	 * @param args
	 */
	  public static void main(String[] args) throws Exception{
	        UILib.setPlatformLookAndFeel();
	        GraphGMLReader a=new GraphGMLReader(); 
	        Graph g=a.readGraph("polbooks.gml");
	        int num_node=g.getNodeCount();
	        int num_edge=g.getEdgeCount();
	        int num[]=new int[3];
	        num=ratio.num_groups(g);
	        Graph graph = random_graph.ran_graph(num_node, num_edge,num, 1);
	        //System.out.println(g.getEdgeCount()+" g ");
	        JFrame frame = SpringForceModel.visual(g, "value");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        ratio.init_ratio(g);
	    }
}
