package analysis;

import java.util.Random;

import prefuse.data.Graph;
import prefuse.data.Node;

public class RandomGraph 
{
	/**
	 * Generates a random graph with given specifications
	 * 
	 * @param num_node int Number of nodes in the graph to be generated
	 * @param num_edge int Number of edges in the graph to be generated
	 * @param num int[] Number of nodes of each type
	 * @param m int Seed for random generator
	 * @param dir boolean Is the graph directed or undirected
	 * @return Graph Random graph generated 
	 */
	public static Graph randGraph(int num_node,int num_edge,int[] num,int seed, boolean dir) 
	{
		Graph graph = new Graph(dir);
		graph.addColumn("value", String.class);
		
		// Creating different types of nodes
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<=num[i];j++)
			{
				Node n= graph.addNode();
				n.set("value", String.valueOf(i));
			}
		}
		
		// Create random connections
		Random rand = new Random();
		rand.setSeed(seed);
		for(int i = 0; i < num_edge; i++)
		{	
			int first = rand.nextInt(num_node);
			int second = rand.nextInt(num_node);
			if(graph.getEdge(first,second)==-1 && first!=second)
			{
				graph.addEdge(first, second);
			}
			else
			{
				i--;
			}
		}
		return graph;
	}
}
