package analysis;

import java.util.ArrayList;
import java.util.Iterator;

import prefuse.data.Graph;
import prefuse.data.Node;

public class triads 
{
	public static float globalCoefficient(Graph g)
	{
		int tr=0;
		int total_connected = 0;
		int n=g.getNodeCount();
		boolean dir = g.isDirected();
		for (int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(!dir)
				{
					if(g.getEdge(i,j)!=-1 || g.getEdge(j,i)!=-1)
					{
						for(int k=0;k<n;k++)
						{
							if(g.getEdge(k,i)!=-1 || g.getEdge(j,k)!=-1 || g.getEdge(i,j)!=-1 || g.getEdge(k,j)!=-1 )
							{
								total_connected++;
							}
							if((g.getEdge(k,i)!=-1 || g.getEdge(i,k)!=-1) && (g.getEdge(j,k)!=-1  || g.getEdge(k,j)!=-1))
							{
								tr++;
							}
						}
					}
				}
				else
				{
					if(g.getEdge(i,j)!=-1)
					{
						for(int k=0;k<n;k++)
						{
							if(g.getEdge(k,i)!=-1 || g.getEdge(j,k)!=-1 )
							{
								total_connected++;
							}
							if(g.getEdge(k,i)!=-1 && g.getEdge(j,k)!=-1 )
							{
								tr++;
							}
						}
					}
				}
			}
		}
		return (float)tr/total_connected;
	}
	
	public static void num_triads(Graph g)
	{
		int total_triads=0;
		int all_same = 0;
		int two_same = 0;
		int all_diff = 0;
		Node node1;
		Node node2;
		Node node3;
		int n=g.getNodeCount();
		boolean dir = g.isDirected();
		for (int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(!dir)
				{
					if(g.getEdge(i,j)!=-1 || g.getEdge(j,i)!=-1)
					{
						for(int k=0;k<n;k++)
						{
							if((g.getEdge(k,i)!=-1 || g.getEdge(i,k)!=-1) && (g.getEdge(j,k)!=-1  || g.getEdge(k,j)!=-1))
							{
								total_triads++;
								node1 = g.getNode(i);
								node2 = g.getNode(j);
								node3 = g.getNode(k);
								if (node1.getString("value").equals(node2.getString("value")) && node1.getString("value").equals(node3.getString("value")))
								{
									all_same++;
								}
								else if (node1.getString("value").equals(node2.getString("value")) || node1.getString("value").equals(node3.getString("value")) || node2.getString("value").equals(node3.getString("value")))
								{
									two_same++;
								}
								else
								{
									all_diff++;
								}
							}
						}
					}
				}
				else
				{
					if(g.getEdge(i,j)!=-1)
					{
						for(int k=0;k<n;k++)
						{
							if(g.getEdge(k,i)!=-1 && g.getEdge(j,k)!=-1 )
							{
								total_triads++;
								node1 = g.getNode(i);
								node2 = g.getNode(j);
								node3 = g.getNode(k);
								if (node1.getString("value").equals(node2.getString("value")) && node1.getString("value").equals(node3.getString("value")))
								{
									all_same++;
								}
								else 
								{
									all_diff++;
								}
							}
						}
					}
				}
			}
		}
		if (!dir)
			System.out.println(total_triads/6 +" Total number of triads ");
		else
			System.out.println(total_triads/3 +" Total number of triads ");
		System.out.println((float) all_same/total_triads+" All nodes of same type ");
		System.out.println((float) two_same/total_triads+" Two nodes of same type ");
		System.out.println((float) all_diff/total_triads+" No node of same type ");
	}	
	
	public static float localCoefficient(Graph g)
	{
		int n=g.getNodeCount();
		Node node;
		float[] coefficients = new float[n];
		for (int i=0;i<n;i++)
		{
			int num = 0;
			ArrayList<Node> adjnodes = new ArrayList<Node>();
			node = g.getNode(i);
			Iterator<Node> neighbors = node.neighbors();
			while (neighbors.hasNext())
			{
				adjnodes.add(neighbors.next());
			}
			int size = adjnodes.size();
			if (size==0 || size==1)
			{
				coefficients[i] = 0;
			}
			else
			{
				for(int j=0;j<size;j++)
				{
					for(int k=0; k<size; k++)
					{
						if(g.getEdge(j,k)!=-1 || g.getEdge(k,j)!=-1)
							num++;
					}
				}
				coefficients[i] = (float) num/(size*(size-1));
			}
		}
		float sum = 0;
		for(int i=0;i<n;i++)
		{
			sum=sum+coefficients[i];
		}
		return sum/n;
	}
}
