package analysis;

import prefuse.data.Graph;
import prefuse.data.Node;

public class triads 
{
	public static int get_triads(Graph g)
	{
		int tr=0;
		int n=g.getNodeCount();
		for (int i=0;i<n;i++)
		{
			//Node a=g.getNode(i);
			for(int j=i+1;j<n;j++)
			{
				if(g.getEdge(i,j)!=-1)
				{
					for(int k=i+1;k<n;k++)
					{
						if(g.getEdge(k,i)!=-1 && g.getEdge(j,k)!=-1 )
						{
							tr++;
						}
					}
				}
			}
		}
		if(g.isDirected())
		return tr;
		else
		return (int)tr/2;
	}
}
