package Preprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class gmlblogs 
{
	public gmlblogs() throws Exception
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader("polblogs_nonedited.gml"));
			FileWriter writer = new FileWriter("polblogs.gml");
			String t;
			int size;
			System.out.println("Running");
			t=in.readLine();
			while (t!=null)
			{
				size = t.length();
				if (size >2 && t.charAt(size-1)=='[')
				{
					System.out.println("Here");
					writer.append(t.substring(0, size-2));
					writer.append("\n");
					writer.append("  [");
					writer.append("\n");
				}
				else
				{
					writer.append(t);
					writer.append("\n");
				}
				t=in.readLine();
			}
			writer.flush();
			writer.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
