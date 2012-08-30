package Main;

import java.util.Iterator;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.io.GraphGMLReader;

public class test1 {
	public static void main(String[] args) throws Exception{
		GraphGMLReader a=new GraphGMLReader(); 
	    Graph g=a.readGraph("polbooks.gml");
	    Node b=g.getNode(0);
	    Iterator c=b.edges();
	    while(c.hasNext()){
	    	Tuple d=(Tuple)c.next();
	    	System.out.println(d.getString(0)+" "+d.getString(1));
	    }
	}
}
