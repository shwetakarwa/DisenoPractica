package read;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import prefuse.render.AbstractShapeRenderer;
import prefuse.visual.VisualItem;


public class FinalRenderer extends AbstractShapeRenderer
{
	//protected RectangularShape m_box = new Rectangle2D.Double();
	protected Ellipse2D m_box = new Ellipse2D.Double();
	
	@Override
	protected Shape getRawShape(VisualItem item) 
	{	
		m_box.setFrame(item.getX(), item.getY(), 10,10);
		return m_box;
	}
}