package analysis;

import java.io.FileWriter;
import java.util.ArrayList;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;

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
			Ratios r = new Ratios();
			RandomGraph x = new RandomGraph();
			int[] num = r.numGroups(g);
			Triads t = new Triads();
			for(int i=0;i<100;i++)
			{
				Graph r_graph = x.randGraph(num_nodes, num_edges, num, i+2, dir);
				ArrayList<Tuple> diff_edges = r.getDiffEdge(r_graph);
				int size = diff_edges.size();
				float ratio = (float) size/num_edges;
				float global_coeff = t.globalCoefficient(r_graph);
				float local_coeff = t.localCoefficient(r_graph);
				writer.append(Float.toString(1-ratio));
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
