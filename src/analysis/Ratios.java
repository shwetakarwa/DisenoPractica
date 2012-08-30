package analysis;

import java.util.ArrayList;
import java.util.Iterator;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;

public class Ratios 
{	
	/**
	 * Get the list of edges whose two nodes are of different types
	 * 
	 * @param g Graph 
	 * @return ArrayList<Edge> ArrayList of edges whose two nodes are of different types
	 */
	public ArrayList<Tuple> getDiffEdge(Graph g) 
	{
		TupleSet tup=g.getEdges();
   		int totaledge=g.getEdgeCount();
   		int sim_edge=0;
   		Iterator<Tuple> a= tup.tuples();
   		ArrayList<Tuple> diff_edges = new ArrayList<Tuple>();
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
   			else
   			{
   				diff_edges.add(n);
   			}
   		}
   		return diff_edges;
   		//float ratio=(float)sim_edge/totaledge;
   		//return ratio;
	}
	
	/**
	 * Prints list of all the nodes that have degree more than mean+std_dev of degree and and ratio of such nodes in each group
	 * 
	 * @param g Graph 
	 */
	public ArrayList<Node> degreeVariaton(Graph g)
	{
		ArrayList<Node> popular_nodes = new ArrayList<Node>();
		int[] num_node=numGroups(g);
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
   				popular_nodes.add(cur);
   				System.out.println("Node: " + cur + " Degree: " + cur_deg);
   				if(cur.getString("value").equals("c") || cur.getString("value").equals("0"))
   				{
   					a1++;
   				}
   				else if(cur.getString("value").equals("l") || cur.getString("value").equals("-1"))
   				{
   					a2++;
   				}
   				else 
   					a3++;
   			}
   		}
   		if(num_node[0]!=0)
   			System.out.println("Popular nodes in first group:"+(double)a1/num_node[0]);
   	   	if(num_node[1]!=0)
   	   		System.out.println("Popular nodes in second group:"+(double)a2/num_node[1]);
   	   	if(num_node[2]!=0)
   	   		System.out.println("Popular nodes in third group:"+(double)a3/num_node[2]);
   	   	return popular_nodes;
	}
	
	/**
	 * Prints list of all the nodes whose more than half the neighbours are of different type and ratio of such nodes in each group
	 * 
	 * @param g Graph 
	 */
	public ArrayList<Node> bridgeElements(Graph g)
	{
		int[] num_node=numGroups(g);
   		int totalnode=g.getNodeCount();
   		int a1=0;
   		int a2=0;
   		int a3=0;
   		ArrayList<Node> bridging_nodes = new ArrayList<Node>();
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
   				bridging_nodes.add(cur);
   				System.out.println("Node: "+cur + " Total Degree: "+ tot_neigh + " Different Neighbours: " + diff_neigh);
   				if(cur.getString("value").equals("c") || cur.getString("value").equals("0"))
   					a1++;
   				else if(cur.getString("value").equals("l") || cur.getString("value").equals("-1"))
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
   		return bridging_nodes;
	}
	
	/**
	 * Gives the number of each type
	 * 
	 * @param g Graph 
	 * @return int[] The number of each type
	 */
	public static int[] numGroups(Graph g) 
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
   			else if(n.getString("value").equals("l") || n.getString("value").equals("-1"))
   				num[1]++;
   			else 
   				num[2]++;
   		}
   		return num;
	}
}