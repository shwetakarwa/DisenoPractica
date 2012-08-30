package drawing;

import java.util.ArrayList;
import java.util.Iterator;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;
public class Cliquenodes {
	boolean marker[];
	int N;
	public Cliquenodes(int n){
		marker=new boolean[n];
		for(int i=0;i<n;i++){
			marker[i]=true;
		}
	}
	public boolean nodeexists(Graph g, int i){
		try{
			Node v=g.getNode(i);
			return marker[i];
		}
		catch(Exception e){
			return false;
		}
	}
	public boolean isconnected(Cliquecontainer a, int j, Graph g){
		float percent=(float).4;
		int count=0;
		if(!g.getNode(a.v[0]).getString(2).equals(g.getNode(j).getString(2))){
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
	public boolean isclique(Cliquecontainer a, int j, Graph g){
		if(!g.getNode(a.v[0]).getString(2).equals(g.getNode(j).getString(2))){
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
	public void addedges(Graph g, int src, int tar){
		Node source=g.getNode(src);
		Iterator itr=source.inEdges();
		while(itr.hasNext()){
			Edge v=(Edge)itr.next();
			if(g.getEdge(Integer.parseInt(v.getString(0)), tar)==-1)
				g.addEdge(Integer.parseInt(v.getString(0)), tar);
		}
		itr=source.outEdges();
		while(itr.hasNext()){
			Edge v=(Edge)itr.next();
			if(g.getEdge(tar,Integer.parseInt(v.getString(0)))==-1)
				g.addEdge(tar, Integer.parseInt(v.getString(0)));
		}
	}
	public void removenode(Graph g, int n){
		
		marker[n]=false;
	}
	public void makeclique(Graph g,Cliquecontainer a){
		
		for(int i=0;i<a.size;i++){
			System.out.print(a.v[i]+ " ");
		}
		System.out.println(" cliqur");
		int n=g.getNodeCount();
		Node n1=g.addNode();
		n1.set(0,n);
		n1.set(1, "clique");
		//n1.set(2,g.getNode(a.v[0]).getString(2));
		n1.set(2,Integer.parseInt(g.getNode(a.v[0]).getString(2)));//TODO
		//TODO one more set
		for(int i=0;i<a.size;i++){
			addedges(g,a.v[i],n);
		}
		for(int i=0;i<a.size;i++){
			removenode(g,a.v[i]);
		}
	}
	public void makeclique(Graph g, int[] a){
		Cliquecontainer b=new Cliquecontainer(a);
		makeclique(g,b);
	}
	public Graph regenerategraph(Graph g){
		int[] n=new int[5*g.getNodeCount()];
		boolean dir=g.isDirected();
		Graph graph=new Graph(dir);

		int i=0;
		TupleSet b=g.getNodes();
		Iterator<Tuple> c=b.tuples();
		for(int j=0;j<3;j++){
			if(j==0||j==2){//TODO
				graph.addColumn(c.next().getColumnName(j), Integer.class);
			}
			else{
				graph.addColumn(c.next().getColumnName(j), String.class);
			}

		}
		b=g.getNodes();
		c=b.tuples();
		while(c.hasNext()){
			Tuple l=c.next();
			n[Integer.parseInt(l.getString(0))]=i;
			i++;
			Node v=graph.addNode();
			for(int j=0;j<3;j++){
				if(j==0||j==2){//TODO
					v.set(j,Integer.parseInt(l.getString(j)));
				}
				else{
					v.set(j,l.getString(j));
				}
			}
		}
		b=g.getEdges();
		c=b.tuples();
		while(c.hasNext()){
			Tuple l=c.next();
			graph.addEdge(n[Integer.parseInt(l.getString(0))], n[Integer.parseInt(l.getString(1))]);
		}
		return graph;
	}
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
			int r=Integer.parseInt(c.getString(0));
			if(k<marker.length){
				if(marker[r]){
					System.out.print(c.getString(0)+ " ");
				}}
			else{
				System.out.print(c.getString(0)+ " ");
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
				//System.out.println(i+" l");
				
				/*for(int q=0;q<i;q++ ){
					if(isconnected(a[q],i,g)&&a[q].size<k){
						a[q].add(i);
					}
				}*/
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
				/*
				for(int l=0;l<a.size;l++){
					System.out.print(a.v[l]+ " ");
				}
				System.out.println("clique");*/
				if(a[i].size==k){
					//System.out.println(a[i].size);
					makeclique(g,a[i]);
				}
				//displaynodes(g);
			}
		}
		}
		
		//displaynodes(g);
		removemarkednodes(g);
		return regenerategraph(g);
	}
	public int getcount(Graph g){
		int ct=0;
		int n=g.getNodeCount();
		for(int i=0;i<marker.length;i++){
			if(marker[i]==false){
				ct++;
			}
		}
		return n-ct;
	}
	public Graph getcliquegraph(Graph g, int num){
		N=g.getNodeCount();
		int a[]=new int[g.getNodeCount()];
		for(int i=0;i<a.length;i++){
			a[i]=i;
		}
		int[] b=getClique(g, a, 5);
		int cnt=0;
		while(b!=null&&cnt<25){
			//System.out.print(getcount(g)+ " ");
			makeclique(g,b);
			//System.out.println(getcount(g)+ " ");
			//displaynodes(g);
			cnt++;
			b=getClique(g,a,5);
		
		}
		b=getClique(g, a, 4);
		cnt=0;
		while(b!=null&&cnt<50){
			//System.out.print(getcount(g)+ " ");
			makeclique(g,b);
			//System.out.println(getcount(g)+ " ");
			//displaynodes(g);
			cnt++;
			b=getClique(g,a,4);
		
		}
		b=getClique(g, a, 3);
		cnt=0;
		while(b!=null&&cnt<100){
			//System.out.print(getcount(g)+ " ");
			makeclique(g,b);
			//System.out.println(getcount(g)+ " ");
			//displaynodes(g);
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
		int n =a.length;//TODO
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
