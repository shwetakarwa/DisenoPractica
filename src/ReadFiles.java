import java.io.*;

public class ReadFiles {
	
	public static void main(String argv[])
	{
		try
		{
			read("polbooks.gml");
		}catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void read(String file) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(file));
		String t;
		t=in.readLine();
		while (t!=null)
		{
			if (t=="edge")
			System.out.println(t);
			t=in.readLine();
		}
	}
}
