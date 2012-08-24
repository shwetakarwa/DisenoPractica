package analysis;

import prefuse.data.Graph;
import prefuse.data.Node;

public class triads 
{
	public static float get_triads(Graph g)
	{
		int tr=0;
		int total_connected = 0;
		int n=g.getNodeCount();
		for (int i=0;i<n;i++)
		{
			for(int j=1;j<n;j++)
			{
				if(g.getEdge(i,j)!=-1)
				{
					for(int k=1;k<n;k++)
					{
						total_connected++;
						if(g.getEdge(k,i)!=-1 && g.getEdge(j,k)!=-1 )
						{
							tr++;
						}
					}
				}
			}
		}
		return (float)tr/total_connected;
	}
}
