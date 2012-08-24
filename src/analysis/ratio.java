package analysis;

import java.util.Iterator;

import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;

public class ratio {
	
	public static float init_ratio(Graph g) {
		TupleSet tup=g.getEdges();
   		int totaledge=g.getEdgeCount();
   		int sim_edge=0;
   		Iterator<Tuple> a= tup.tuples();
   		while(a.hasNext()){
   			//System.out.println(a.next().getString(1));
   			Tuple n=a.next();
   			//System.out.println(n.getString(1));
   			int source=Integer.parseInt(n.getString(0));
   			int target=Integer.parseInt(n.getString(1));
   			String src=g.getNode(source).getString("value");
   			//System.out.println(src+ "there");
   			String tar=g.getNode(target).getString("value");
   			if(src.equals(tar)){
   				sim_edge++;
   			}
   		}
   		float ratio=(float)sim_edge/totaledge;
   		//System.out.println(ratio);
   		return ratio;
   	}
	
	public static int[] num_groups(Graph g) {
		TupleSet tup=g.getNodes();
   		int totalnode=g.getNodeCount();
   		int num[]=new int[3];
   		num[0]=0;
   		num[1]=0;
   		num[2]=0;
   		Iterator<Tuple> a= tup.tuples();
   		while(a.hasNext()){
   			//System.out.println(a.next().getString(1));
   			Tuple n=a.next();
   			if(n.getString("value").equals("c")){
   				num[0]++;
   			}
   			else if(n.getString("value").equals("l")){
   				num[1]++;
   			}
   			else num[2]++;
   			}
   			
   		//System.out.println(num[0]);
   		//System.out.println(num[1]);
   		//System.out.println(num[2]);
   		return num;
	}
}