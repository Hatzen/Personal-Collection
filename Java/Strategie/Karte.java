import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Karte
{
    private int kind;
    private Dimension size;
    private ArrayList<Point> startPoint;
    private ArrayList<Rectangle> resource;
    private ArrayList<Rectangle> barrier;

    public Karte(int kind, Dimension size, ArrayList<Point> startPoint,ArrayList<Rectangle> resource,ArrayList<Rectangle> barrier)
    {
        this.kind = kind;
        this.size = size;
        this.startPoint = new ArrayList(startPoint);
        this.resource = new ArrayList(resource);
        this.barrier = new ArrayList(barrier);
    }
    
    public int getKind()
    {
        return kind;
    }
    
    public Dimension getSize()
    {
        return size;
    }
    
    public ArrayList<Point> getStartPoint()
    {
        return startPoint;
    }
    
    public ArrayList<Rectangle> getResource()
    {
        return resource;
    }
    
    public ArrayList<Rectangle> getBarrier()
    {
        return barrier;
    }
}
