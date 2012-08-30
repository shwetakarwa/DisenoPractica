package analysis;

import java.util.Iterator;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;

public class ratio 
{	
	/**
	 * Gives the ratio of number of edges between similar types of nodes to total edges
	 * 
	 * @param 
	 * @return Graph Random graph generated 
	 */
	public static float getSimEdgesRatio(Graph g) 
	{
		TupleSet tup=g.getEdges();
   		int totaledge=g.getEdgeCount();
   		int sim_edge=0;
   		Iterator<Tuple> a= tup.tuples();
   		while(a.hasNext())
   		{
   			Tuple n=a.next();
   			int source=Integer.parseInt(n.getString(0));
   			int target=Integer.parseInt(n.getString(1));
   			String src=g.getNode(source).getString("value");
   			String tar=g.getNode(target).getString("value");
   			if(src.equals(tar))
   			{
   				sim_edge++;
   			}	
   		}
   		float ratio=(float)sim_edge/totaledge;
   		return ratio;
	}
	
	public static void deg_variation(Graph g)
	{
		int[] num_node=num_groups(g);
		int a1=0;
		int a2=0;
		int a3=0;
		int totalnode=g.getNodeCount();
		double mean=(double)g.getEdgeCount()/totalnode;    //mean degree
		int deg_square=0;
   		for(int i=0;i<totalnode;i++)
   		{
   			Node cur=g.getNode(i);
   			int cur_deg=g.getDegree(cur);
   			deg_square=deg_square+(cur_deg*cur_deg);
   		}
   		double var=deg_square/totalnode;
   		double standard_dev=Math.sqrt(var-mean);  //standard deviation
   		for(int i=0;i<totalnode;i++)
   		{
   			Node cur=g.getNode(i);
   			int cur_deg=g.getDegree(cur);
   			if(cur_deg>=(int)(standard_dev+mean))
   			{
   				if(cur.getString("value").equals("c") || cur.getString("value").equals("0"))
   				{
   					a1++;
   				}
   				else if(cur.getString("value").equals("l") || cur.getString("value").equals("1"))
   				{
   					a2++;
   				}
   				else a3++;
   			}
   		}
   		if(num_node[0]!=0)
   			System.out.println("popular nodes in first group:"+(double)a1/num_node[0]);
   	   	if(num_node[1]!=0)
   	   		System.out.println("popular nodes in second group:"+(double)a2/num_node[1]);
   	   	if(num_node[2]!=0)
   	   		System.out.println("popular nodes in third group:"+(double)a3/num_node[2]);
	}
	
	public static void bridge_elem(Graph g)
	{
		int[] num_node=num_groups(g);
   		int totalnode=g.getNodeCount();
   		int a1=0;
   		int a2=0;
   		int a3=0;
   		
   		for(int i=0;i<totalnode;i++)
   		{
   			Node cur=g.getNode(i);
   			Iterator<Node> itr =g.neighbors(cur);
   			int tot_neigh=0;
   			int diff_neigh=0;
   			while(itr.hasNext())
   			{
   				Node n=itr.next();
   				if(!(n.getString("value").equals(cur.getString("value"))))
   					diff_neigh++;
   				tot_neigh++;
   			}
   			if(diff_neigh>(tot_neigh/2))
   			{
   				System.out.println("Node: "+cur + " Total Degree: "+ tot_neigh + " Different Neighbours: " + diff_neigh);
   				if(cur.getString("value").equals("c") || cur.getString("value").equals("0"))
   					a1++;
   				else if(cur.getString("value").equals("l") || cur.getString("value").equals("1"))
   					a2++;
   				else 
   					a3++;
   			}
   		}
   		if(num_node[0]!=0)
   			System.out.println("first group:"+(double)a1/num_node[0]);
   		if(num_node[1]!=0)
   			System.out.println("second group:"+(double)a2/num_node[1]);
   		if(num_node[2]!=0)
   			System.out.println("third group:"+(double)a3/num_node[2]);
	}
		
	public static int[] num_groups(Graph g) 
	{
		TupleSet tup=g.getNodes();
   		int num[]=new int[3];
   		num[0]=0;
   		num[1]=0;
   		num[2]=0;
   		Iterator<Tuple> a= tup.tuples();
   		while(a.hasNext())
   		{
   			Tuple n=a.next();
   			if(n.getString("value").equals("c") || n.getString("value").equals("0"))
   				num[0]++;
   			else if(n.getString("value").equals("l") || n.getString("value").equals("1"))
   				num[1]++;
   			else 
   				num[2]++;
   		}
   		return num;
	}
}