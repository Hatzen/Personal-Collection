import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Haus
{
    private int x;
    private int y;
    private int typ;
    private int width;
    private int height;
    private int counter;
    private int counterMax;
    private ArrayList<Integer> bauList;
    private Rectangle umriss;
    private BufferedImage img;
    private Grafiken grafiken;
    
    public Haus(int x, int y, int typ, Grafiken tmp)
    {
        this.x = x;
        this.y = y;
        this.typ = typ;
        switch(typ)
        {
            case 1:
                img = tmp.getHq();
                break;
            case 2:
                img = tmp.getKaserne();
                break;
            case 3:
                img = tmp.getWaffenfarbrik();
                break;
            case 4:
                img = tmp.getKraftwerk();
                break;
            case 5:
                img = tmp.getRaketen();
                break;
            case 6:
                img = tmp.getMg();
                break;
        }
        bauList = new ArrayList<Integer>();
        counterMax = 20;
        counter = counterMax;
        width = img.getWidth();
        height = img.getHeight();
        umriss = new Rectangle(x,y, width, height);
        grafiken = tmp;
    }
    
    public void addOrder(int einheit)
    {
        bauList.add(einheit);
    }
    
    public ArrayList getOrder()
    {
        return bauList;
    }
    
    public Einheit redCounter()
    {
        if(bauList.size() > 0)
        {
            if(counter > 0)
            {
                counter--;
            }
            else
            {
                int tmp = bauList.get(0);
                bauList.remove(0);
                counter = counterMax;
                return new Einheit(x-50,y-50,tmp,grafiken,getTyp());
            }
        }
        return null;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getTyp()
    {
        return typ;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public BufferedImage getImg()
    {
        return img;
    }
    
    public Rectangle getUmriss()
    {
        return umriss;
    }
    
    public int getCounterMax()
    {
        return counterMax;
    }
    
    public int getCounter()
    {
        return counter;
    }
}
