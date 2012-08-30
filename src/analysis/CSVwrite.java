package analysis;

import java.io.FileWriter;

import prefuse.data.Graph;
import prefuse.data.Node;

public class CSVwrite 
{
	public static void writer(Graph g, String s)
	{
		try
		{
			FileWriter writer = new FileWriter(s);
			int num_nodes = g.getNodeCount();
			int num_edges = g.getEdgeCount();
			boolean dir = g.isDirected();
			Node n;
			ratio r = new ratio();
			RandomGraph x = new RandomGraph();
			int[] num = r.num_groups(g);
			triads t = new triads();
			for(int i=0;i<100;i++)
			{
				Graph r_graph = x.randGraph(num_nodes, num_edges, num, i+2, dir);
				//int deg = 0;
				//for (int j=0; j<num_nodes; j++)
				//{
				//	n = g.getNode(j);
				//	deg = deg+n.getDegree();
				//}
				//float avg_deg = deg/num_nodes;
				float ratio = r.getSimEdgesRatio(r_graph);
				float global_coeff = t.globalCoefficient(r_graph);
				float local_coeff = t.localCoefficient(r_graph);
				writer.append(Float.toString(ratio));
				writer.append(',');
				writer.append(Float.toString(global_coeff));
				writer.append(',');
				writer.append(Float.toString(local_coeff));
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
