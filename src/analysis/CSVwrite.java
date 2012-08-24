package analysis;

import java.io.FileWriter;

import prefuse.data.Graph;
import prefuse.data.Node;

public class CSVwrite 
{
	public static void writer(Graph g)
	{
		try
		{
			FileWriter writer = new FileWriter("analysis.csv");
			int num_nodes = g.getNodeCount();
			int num_edges = g.getEdgeCount();
			boolean dir = g.isDirected();
			Node n;
			ratio r = new ratio();
			random_graph x = new random_graph();
			int[] num = r.num_groups(g);
			triads t = new triads();
			for(int i=0;i<30;i++)
			{
				Graph r_graph = x.ran_graph(num_nodes, num_edges, num, i+2, dir);
				int deg = 0;
				for (int j=0; j<num_nodes; j++)
				{
					n = g.getNode(j);
					deg = deg+n.getDegree();
				}
				float avg_deg = deg/num_nodes;
				float ratio = r.init_ratio(r_graph);
				float triads = t.get_triads(r_graph);
				writer.append(Integer.toString(i+1));
				writer.append(',');
				writer.append(Float.toString(ratio));
				writer.append(',');
				writer.append(Float.toString(triads));
				writer.append(',');
				writer.append(Float.toString(avg_deg));
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
