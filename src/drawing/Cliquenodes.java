package drawing;

import java.util.ArrayList;
import java.util.Iterator;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;
/**
 * class for identification and subsequent removal of cliques from given a graph 
 * 
 */
public class Cliquenodes {
	boolean marker[];
	int N;

	/**
	 * marks nodes as belonging to some clique
	 * 
	 * @param n : given nodes to be marked
	 * 
	 */
	public Cliquenodes(int n){
		marker=new boolean[n];
		for(int i=0;i<n;i++){
			marker[i]=true;
		}
	}

	/**
	 * checks if given node belongs to some clique
	 * 
	 * @param g: given graph g
	 * @param i: index of the node in g
	 * @return boolean true if node exists else false
	 *
	 */
	public boolean nodeexists(Graph g, int i){
		try{
			Node v=g.getNode(i);
			return marker[i];
		}
		catch(Exception e){
			return false;
		}
	}

	/**
	 * checks if node is connected to all elements of clique or not
	 * 
	 * @param a: a clique container containing nodes belonging to clique
	 * @param j: given node 
	 * @param g: given graph
	 * @return boolean true if node is connected
	 * 
	 */
	public boolean isconnected(Cliquecontainer a, int j, Graph g){
		float percent=(float).4;
		int count=0;
		if(!g.getNode(a.v[0]).getString(3).equals(g.getNode(j).getString(3))){
			return false;
		}
		boolean ans=true;
		for(int i=0;i<a.size;i++){
			if(nodeexists(g,a.v[i])&&nodeexists(g,j)){
				if(g.getEdge(j, a.v[i])==-1&&g.getEdge(a.v[i],j)==-1){
					count++;
				}
			}
		}
		if((float)count/a.size>percent){
			ans=false;
		}
		return ans;
	}

	/**
	 * checks if node belongs to a clique or not
	 * 
	 * @param a: a clique container containing nodes belonging to clique
	 * @param j: given node 
	 * @param g: given graph
	 * @return boolean true if node belongs to clique
	 * 
	 */
	public boolean isclique(Cliquecontainer a, int j, Graph g){
		if(!g.getNode(a.v[0]).getString(3).equals(g.getNode(j).getString(3))){
			return false;
		}
		boolean ans=true;
		for(int i=0;i<a.size;i++){
			if(nodeexists(g,a.v[i])&&nodeexists(g,j)){
				if(g.getEdge(j, a.v[i])==-1&&g.getEdge(a.v[i],j)==-1){
					ans=false;
				}
			}
		}
		return ans;	
	}

	/**
	 * adds edges of source to target vertex
	 * 
	 * @param g: given graph
	 * @param src: given source node 
	 * @param tar: given target node
	 * @return void
	 * 
	 */	
	public void addedges(Graph g, int src, int tar){
		Node source=g.getNode(src);
		Iterator itr=source.inEdges();
		while(itr.hasNext()){
			Edge v=(Edge)itr.next();
			if(g.getEdge(Integer.parseInt(v.getString(1)), tar)==-1)
				g.addEdge(Integer.parseInt(v.getString(1)), tar);
		}
		itr=source.outEdges();
		while(itr.hasNext()){
			Edge v=(Edge)itr.next();
			if(g.getEdge(tar,Integer.parseInt(v.getString(1)))==-1)
				g.addEdge(tar, Integer.parseInt(v.getString(1)));
		}
	}

	/**
	 * set marker for the node as false
	 * 
	 * @param g : given graph g
	 * @param n : node to be removed from clique
	 * 
	 */
	public void removenode(Graph g, int n){
		marker[n]=false;
	}

	/**
	 * generates clique out of a given Cliquecontainer
	 * 
	 * @param g : given graph 
	 * @param a : given Cliqueconatiner
	 * 
	 */
	public void makeclique(Graph g,Cliquecontainer a){
		String label="Clique: ";
		for(int i=0;i<a.size;i++){
			label+=String.valueOf(a.v[i])+" ";
		}
		int n=g.getNodeCount();
		Node n1=g.addNode();
		n1.set("size",a.size);
		n1.set("id", n);
		n1.set("label", label);
		n1.set("value",Integer.parseInt(g.getNode(a.v[0]).getString(3)));
		for(int i=0;i<a.size;i++){
			addedges(g,a.v[i],n);
		}
		for(int i=0;i<a.size;i++){
			removenode(g,a.v[i]);
		}
	}

	/**
	 * 
	 * generates clique out of a given array of nodes
	 * 
	 * @param g : given graph 
	 * @param a : given array of nodes
	 * 
	 */

	public void makeclique(Graph g, int[] a){
		Cliquecontainer b=new Cliquecontainer(a);
		makeclique(g,b);
	}

	/**
	 * function to regenerate graph after removal of all cliques and replacing all incoming and outgoing edges from clique as edges of a single node
	 * 
	 * @param g : graph g
	 * @return regenerated graph
	 * 
	 */
	public Graph regenerategraph(Graph g){
		int[] n=new int[5*g.getNodeCount()];
		boolean dir=g.isDirected();
		Graph graph=new Graph(dir);

		int i=0;
		TupleSet b=g.getNodes();
		Iterator<Tuple> c=b.tuples();
		graph.addColumn("size", Integer.class);
		graph.addColumn("id", Integer.class);
		graph.addColumn("label", String.class);
		graph.addColumn("value", Integer.class);
		graph.addColumn("source", Integer.class);
		b=g.getNodes();
		c=b.tuples();
		while(c.hasNext()){
			Tuple l=c.next();
			n[Integer.parseInt(l.getString(1))]=i;
			i++;
			Node v=graph.addNode();
			v.set("size",Integer.parseInt(l.getString(0)));
			v.set("id",Integer.parseInt(l.getString(1)));
			v.set("label",l.getString(2));
			v.set("value",Integer.parseInt(l.getString(3)));
		}
		b=g.getEdges();
		c=b.tuples();
		while(c.hasNext()){
			Tuple l=c.next();
			graph.addEdge(n[Integer.parseInt(l.getString(0))], n[Integer.parseInt(l.getString(1))]);
		}
		return graph;
	}

	/**
	 * 
	 * removes all marked nodes from the graph
	 * 
	 * @param g: graph g
	 * 
	 */
	public void removemarkednodes(Graph g){
		for(int i=0;i<marker.length;i++){
			if(!marker[i]){
				Node r=g.getNode(i);
				Iterator itr=r.edges();
				ArrayList<Integer> b=new ArrayList<Integer>();
				while(itr.hasNext()){
					Edge v=(Edge)itr.next();
					b.add(g.getEdge(Integer.parseInt(v.getString(0)), Integer.parseInt(v.getString(1))));
				}
				Iterator<Integer> k=b.iterator();
				while(k.hasNext()){
					g.removeEdge(k.next());
				}
				g.removeNode(i);
			}
		}
	}
	public void displaynodes(Graph g){
		TupleSet a=g.getNodes();
		Iterator<Tuple> b=a.tuples();
		int k=0;
		while(b.hasNext()){
			Tuple c=b.next();
			int r=Integer.parseInt(c.getString(1));
			if(k<marker.length){
				if(marker[r]){
					System.out.print(c.getString(1)+ " ");
				}}
			else{
				System.out.print(c.getString(1)+ " ");
			}
			k++;
		}
	}
	public Graph getGraph(Graph g, int num){

		int n=g.getNodeCount();
		N=n;
		boolean[] check;

		Cliquecontainer a[]=new Cliquecontainer[n];
		for(int k=num;k>=2;k--){
			for(int i=0;i<n;i++){
				if(nodeexists(g,i)){
					check=new boolean[n];
					for(int q=0;q<n;q++){
						check[q]=false;
					}
					a[i]=new Cliquecontainer(n);
					a[i].add(i);
					check[i]=true;
					int j=1;
					while(j<n&&a[i].size<k){
						if(nodeexists(g, j)&&!check[j]){
							if(isconnected(a[i],j,g)){
								a[i].add(j);
								j=1;
							}}
						j++;
					}
					if(a[i].size==k){
						makeclique(g,a[i]);
					}
				}
			}
		}
		removemarkednodes(g);
		return regenerategraph(g);
	}

	/**
	 * searches for specified number of cliques of different sizes
	 * 
	 * @param g : given graph
	 * @param num : size of clique
	 * @return graph with identified cliques
	 * 
	 */
	public Graph getcliquegraph(Graph g){
		N=g.getNodeCount();
		int a[]=new int[g.getNodeCount()];
		for(int i=0;i<a.length;i++){
			a[i]=i;
		}
		int[] b=getClique(g, a, 5);
		int cnt=0;
		while(b!=null&&cnt<25){
			makeclique(g,b);
			cnt++;
			b=getClique(g,a,5);

		}
		b=getClique(g, a, 4);
		cnt=0;
		while(b!=null&&cnt<50){
			makeclique(g,b);
			cnt++;
			b=getClique(g,a,4);

		}
		b=getClique(g, a, 3);
		cnt=0;
		while(b!=null&&cnt<100){
			makeclique(g,b);
			cnt++;
			b=getClique(g,a,3);

		}
		removemarkednodes(g);
		return regenerategraph(g);
	}
	public int[] addadjacentnodes(Graph g,int a[],int i){
		int size=0;
		for(int r=0;r<a.length;r++){
			if(r!=i){
				if(isadjacent(g,a[i],a[r])&&nodeexists(g,a[r])){
					size++;
				}
			}
		}
		if(size==0){
			return null;
		}
		int b[]=new int[size];
		int k=0;
		for(int r=0;r<a.length;r++){
			if(r!=i){
				if(isadjacent(g,a[i],a[r])&&nodeexists(g,a[r])){
					b[k]=a[r];
					k++;
				}
			}

		}
		return b;
	}
	public boolean isadjacent(Graph g, int i, int k){
		if(g.getEdge(i, k)==-1&&g.getEdge(k,i)==-1){
			return false;
		}
		return true;
	}
	public int[] getClique(Graph g, int[] a,int num){
		if(a==null){
			return null;
		}
		int n =a.length;
		int b[];
		int[] g1=null;
		if(num==1){
			g1=new int[1];
			g1[0]=a[0];
			return g1;
		}
		int i=0;
		while(g1==null && i<n){
			if(marker[a[i]]){
				b=addadjacentnodes(g,a,i);
				g1=getClique(g,b,num-1);

			}
			i++;
		}
		if(i==n&&g1==null){
			return null;
		}
		int[] g2=new int[g1.length+1];
		for(int l=0;l<g1.length;l++){
			g2[l]=g1[l];
		}
		g2[g2.length-1]=a[i-1];
		return g2;
	}
}
