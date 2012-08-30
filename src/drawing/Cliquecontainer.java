package drawing;

/**
 * class to store elements in a clique; two constructors 1)to generate new container of given size 2)copy elements from a given container 
 * 
 */
public class Cliquecontainer {
	int[] v;
	int size;
	boolean todraw;
	boolean exists;
	Cliquecontainer(int n){
		v=new int[n];
		size=0;
	}
	Cliquecontainer(int[] a){
		size=a.length;
		v=new int[size];
		for(int i=0;i<size;i++){
			v[i]=a[i];
		}
	}
	
	void add(int n){
		v[size]=n;
		size++;
	}
	int pop(){
		size--;
		return v[size];
	}
}
