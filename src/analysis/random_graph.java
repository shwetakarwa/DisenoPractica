package analysis;

import java.util.Random;

import prefuse.data.Graph;
import prefuse.data.Node;

public class random_graph 
{
	/**
	 * @param args
	 */
	public static Graph ran_graph(int num_node,int num_edge, int[] num,int m, boolean dir) 
	{
		Graph graph = new Graph(dir);
		graph.addColumn("value", String.class);
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<num[i];j++)
			{
				Node n= graph.addNode();
				n.set("value", String.valueOf(i));
			}
		}
		// Create random connections
		Random rand = new Random();
		rand.setSeed(m);
		for(int i = 0; i < num_edge; i++)
		{
			int first = rand.nextInt(num_node);
			int second = rand.nextInt(num_node);
			if(graph.getEdge(first,second)==-1 && first!=second){
				graph.addEdge(first, second);
			}
			else
			{
				i--;
			}
		}
		ratio.init_ratio(graph);
		return graph;
	}

	public static void r_graphs(int num_node,int num_edge,int[] num, boolean dir){
		for(int i=0;i<1;i++){
			Graph g=ran_graph(num_node,num_edge,num,2, dir);
			/*TupleSet tup=g.getEdges();
	   		int totaledge=g.getEdgeCount();
	   		Iterator<Tuple> a= tup.tuples();
	   		while(a.hasNext()){
	   			System.out.println(a.next().toString());
	   			}	
	   		}*/
		}
	}
}
