package Main;

import javax.swing.JFrame;

import prefuse.data.Graph;
import prefuse.data.io.GraphGMLReader;
import prefuse.util.ui.UILib;
import drawing.Cliquenodes;
import drawing.SpringForceModel;


public class Main {

	/**
	 * @param args
	 */
	  public static void main(String[] args) throws Exception{
	  	//Preprocessing.gmlblogs x = new gmlblogs();
	    UILib.setPlatformLookAndFeel();
	    GraphGMLReader a=new GraphGMLReader(); 
	    Graph g=a.readGraph("polblogs.gml");
	    
	    //Node r=g.getNode(5);
	    //g=Cliquenodes.getGraph(g, 3);
	    //int num_node=g.getNodeCount();
	    //int num_edge=g.getEdgeCount();
	    //int num[]=new int[3];
	    //CSVwrite.writer(g);
	    //num=ratio.num_groups(g);
	    Cliquenodes cliquenodes=new Cliquenodes(g.getNodeCount());
	    
	    System.out.println(g.getNodeCount()+"hi");
	   // g=cliquenodes.getGraph(g,5);
	    g=cliquenodes.getcliquegraph(g, 10);
	    //g=cliquenodes.getGraph(g, 7);
	    System.out.println(g.getNodeCount()+"hi");
	   /* TupleSet m=g.getNodes();
		Iterator<Tuple> b=m.tuples();
		while(b.hasNext()){
			System.out.print(b.next().getString(0)+ " ");
		}*/
	    /*GraphMLReader a=new GraphMLReader();
	    Graph g=a.readGraph("outout.xml");*///
	    //Graph graph = random_grap h.ran_graph(num_node, num_edge,num, 1, false);
	    //System.out.println(g.getEdgeCount()+" g ");
	    JFrame frame = SpringForceModel.visual(g, "value");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
