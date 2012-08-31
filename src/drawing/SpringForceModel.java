package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.force.ForceSimulator;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;


public class SpringForceModel extends JPanel 
{	
    private static final String graph = "graph";
    private static final String nodes = "graph.nodes";
    private static final String edges = "graph.edges";

    private static Visualization m_vis;
    
    /**
	 * 
	 * @param g Graph graph to be visualized
	 * @param label String labeling string
	 */
    public SpringForceModel(Graph g, String label) 
    {
    	super(new BorderLayout());
        
    	m_vis = new Visualization();
        LabelRenderer tr = new LabelRenderer();
        tr.setRoundedCorner(20, 20);
        m_vis.setRendererFactory(new DefaultRendererFactory(tr));
        setGraph(g, label);
        
        TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS); 
        focusGroup.addTupleSetListener(new TupleSetListener() 
        {
            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem)
            {	
            	Iterator<Tuple> a=ts.tuples();
            	while(a.hasNext()){
            		System.out.println(a.next().getString("label"));
            	}
                m_vis.run("draw");
            }
        });
        
        TupleSet q=g.getNodes();
        Iterator<Tuple> w=q.tuples();
        while(w.hasNext()){
        	Tuple r=w.next();
        	VisualItem s=m_vis.getVisualItem("graph.nodes", r);
        	int mul=1;
        	if(Integer.parseInt(r.getString(0))>4){
        		mul=4;
        	}
        	else{
        		mul=Integer.parseInt(r.getString(0));
        	}
        	if(mul!=1){
        		mul=(int)mul/2;
        	}
        	s.setSize(s.getStartSize()*mul);
        	s.setInteractive(true);
        }
        
        int[] palette = {ColorLib.rgb(200, 0, 0),ColorLib.rgb(0, 200, 0),ColorLib.rgb(0, 0, 200)}; 
		DataColorAction fill = new DataColorAction(nodes, "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        fill.add(VisualItem.FIXED, ColorLib.rgb(255,100,100));
        fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,200,125));
        
        final GraphDistanceFilter filter = new GraphDistanceFilter(graph, 30);
        ActionList draw = new ActionList();
        draw.add(fill);
        //draw.add(filter);
        draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0,0,0)));
        draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib.gray(200)));
        draw.add(new ColorAction(edges, VisualItem.STROKECOLOR, ColorLib.gray(200)));
        
        ActionList animate = new ActionList(Activity.INFINITY);
        animate.add(new ForceDirectedLayout(graph));
        ForceSimulator fsim = ((ForceDirectedLayout)animate.get(0)).getForceSimulator();
        fsim.setSpeedLimit((float).05);
        animate.add(fill);
        animate.add(new RepaintAction());
        m_vis.putAction("draw", draw);
        m_vis.putAction("layout", animate);
        m_vis.runAfter("draw", "layout");
        
       

        Display display = new Display(m_vis);
        display.setSize(700,700);
        display.pan(350, 350);
        display.setForeground(Color.GRAY);
        display.setBackground(Color.WHITE);
        
        display.addControlListener(new FinalControlListener());
        display.addControlListener(new FocusControl(1));
        display.addControlListener(new DragControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new ZoomControl());
        display.addControlListener(new WheelZoomControl());
        display.addControlListener(new ZoomToFitControl());
        display.addControlListener(new NeighborHighlightControl());
        
        display.setForeground(Color.GRAY);
        display.setBackground(Color.WHITE);
        m_vis.run("draw");
        add(display);
    }
    
	/**
	 * 
	 * @param g Graph graph to be visualized
	 * @param label labeling String
	 */
    public void setGraph(Graph g, String label) {
        DefaultRendererFactory drf = (DefaultRendererFactory)
                                                m_vis.getRendererFactory();
        ((LabelRenderer)drf.getDefaultRenderer()).setTextField(label);
        
        m_vis.removeGroup(graph);
        VisualGraph vg = m_vis.addGraph(graph, g);
        m_vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
        VisualItem f = (VisualItem)vg.getNode(0);
        m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);
        f.setFixed(false);
    }
    
    /**
	 * 
	 * @param g Graph graph to be visualized
	 * @param label String labeling string 
	 * @param set boolean whether to display Analysis menu
	 * @return JFrame Frame to be displayed 
	 */
    
    public static JFrame visual(Graph g, String label, boolean set) {
    	final SpringForceModel view = new SpringForceModel(g, label);

		JMenuBar menu=null;
		if (set)
		{
			//set up menu
			menu=new JMenuBar();
			JMenu panel=new JMenu("Analysis");

			RatioButton innerRatio = new RatioButton("Inner Ratio",m_vis,"polbooks.gml");
			panel.add(innerRatio);

			AffilButton affiliations = new AffilButton("Other Affiliations",m_vis,"polbooks.gml");
			panel.add(affiliations);

			DegreeButton degree = new DegreeButton("Degree Popularity",m_vis,"polbooks.gml");
			panel.add(degree);

			ClearButton clear=new ClearButton("Clear All",m_vis,"polbooks.gml");
			panel.add(clear);

			menu.add(panel);  
		}
		
		JFrame frame = new JFrame("Information Visualization");

		frame.setContentPane(view);
		if (set)
		{
			frame.setJMenuBar(menu);
		}
		frame.pack();
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				view.m_vis.run("layout");
			}
			public void windowDeactivated(WindowEvent e) {
				view.m_vis.cancel("layout");
			}
		});

		return frame;
    }

    
}
