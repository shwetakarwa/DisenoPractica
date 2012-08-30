package demo;

import javax.swing.JFrame;

import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphGMLReader;
import prefuse.util.ui.UILib;
import drawing.SpringForceModel;

public class VisualizeBooks {

	/**
	 * main class for visualization of polbooks.
	 * 
	 * @param args
	 * @throws DataIOException 
	 * 
	 */
	public static void main(String[] args) throws DataIOException {
		UILib.setPlatformLookAndFeel();
		GraphGMLReader a=new GraphGMLReader(); 
		Graph g=a.readGraph("polbooks.gml");
		JFrame frame = SpringForceModel.visual(g, "value",true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
