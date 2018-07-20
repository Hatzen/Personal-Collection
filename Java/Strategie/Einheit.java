import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Point;

public class Einheit
{
    private int x;
    private int y;
    private int xziel;
    private int yziel;
    private int typ;
    private int width;
    private int height;
    private int leben;
    private int maxLeben;
    private int umgehen;
    private double rotate;
    private Rectangle umriss;
    private Rectangle range;
    private BufferedImage img;
    
    public Einheit(int x, int y, int typ, Grafiken tmp,int haus)
    {
        this.x = x;
        this.y = y;
        this.typ = typ;
        switch(haus)
        {
            case 1://HQ
                switch(typ)
                {
                    case 1:
                        img = tmp.getSoldat();
                        leben = 120;
                        maxLeben = 120;
                        range = new Rectangle(x-300,y-300, 600, 600);
                        break;
                    case 2:
                        img = tmp.getRaketenSoldat();
                        leben = 200;
                        maxLeben = 200;
                        range = new Rectangle(x-400,y-400, 800, 800);
                        break;
                    case 3:
                        img = tmp.getSchuetze();
                        leben = 40;
                        maxLeben = 40;
                        range = new Rectangle(x-500,y-500, 1000, 1000);
                        break;
                }
                break;
            case 2://Kaserne
                switch(typ)
                {
                    case 1:
                        img = tmp.getSoldat();
                        leben = 120;
                        maxLeben = 120;
                        range = new Rectangle(x-300,y-300, 600, 600);
                        break;
                    case 2:
                        img = tmp.getRaketenSoldat();
                        leben = 200;
                        maxLeben = 200;
                        range = new Rectangle(x-400,y-400, 800, 800);
                        break;
                    case 3:
                        img = tmp.getSchuetze();
                        leben = 40;
                        maxLeben = 40;
                        range = new Rectangle(x-500,y-500, 1000, 1000);
                        break;
                }
                break;
            case 3://Waffenfarbrik
                switch(typ)
                {
                    case 1:
                        img = tmp.getMgPanzer();
                        leben = 530;
                        maxLeben = 530;
                        range = new Rectangle(x-400,y-400, 800, 800);
                        break;
                    case 2:
                        img = tmp.getPanzer();
                        leben = 800;
                        maxLeben = 850;
                        range = new Rectangle(x-450,y-450, 900, 900);
                        break;
                    case 3:
                        img = tmp.getSchuetze();
                        leben = 40;
                        maxLeben = 40;
                        range = new Rectangle(x-250,y-250, 500, 500);
                        break;
                }
                break;
        }
        width = img.getWidth();
        height = img.getHeight();
        umriss = new Rectangle(x,y, width, height);
        xziel = x;
        yziel = y;
        rotate = 0;
        umgehen = 0;
    }
    
    private long calculateAngle(int ax, int ay, int bx, int by) {
        if (ax == bx && ay == by)
            return 0;
            
        double dx = bx - ax;
        double dy = ay - by;
        double dz = Math.sqrt(dx*dx + dy*dy);
        
        double gamma = Math.asin(dy / dz);

        gamma = 180 * gamma / Math.PI;

        long angle = Math.round(gamma);

        if (bx >= ax && by <= ay)
            ;//angle = angle - 90; // Ich
        else if (bx <= ax && by <= ay)
            angle = 180 - angle;
        else if (bx <= ax && by >= ay)
            angle = 180 - angle;
        else if (bx >= ax && by >= ay)
            angle = 360 + angle;

        return angle;
    }
    
    public void setZiel(int xziel, int yziel)
    {
        this.xziel = xziel;
        this.yziel = yziel;
    }
    
    public Point getZiel()
    {
        return new Point(xziel,yziel);
    }
    
    public int getUmgehen()
    {
        return umgehen;
    }
    
    public void setUmgehen(int umgehen)
    {
        this.umgehen = umgehen;
    }
    
    public Point calculateTranslation()
    {
        if(!new Rectangle(x-5,y-5,10,10).contains(xziel,yziel))
        {
            int xd = (xziel-x);
            int yd = (yziel-y);
            double faktor = 0;
            if(xd != 0)
            {
                faktor = (double) yd/ (double) xd ;
            }
            double b = y-faktor*x;
            
            int ytmp = y;
            int xtmp = x;
            
            /*if(Math.abs(faktor) < 20)
            {
                if(xziel > x)
                {
                    xtmp += 5;
                }
                else
                {
                    xtmp -= 5;
                }
                ytmp = (int) Math.round(faktor*xtmp+b);
            }
            else
            {
                if(yziel > ytmp)
                {
                    ytmp += 5;
                }
                else
                {
                    ytmp -= 5;
                }
                xtmp = (int) Math.round(ytmp/faktor-b);
            }//
            
            int schrittzahl = 10;
            int xtmp = -(int) Math.round( -(Math.sqrt(-b*-b-2*b*(faktor*x-y)+faktor*faktor*(schrittzahl*schrittzahl-x*-x)+2*faktor*x*y+schrittzahl*schrittzahl-y*-y)+b*faktor-faktor*y-x )/(faktor*faktor+1) );
            int ytmp = (int) Math.round(faktor*xtmp+b);//*/
            
            if(yziel-y > 5)
            {
                ytmp = y+4;
            }
            else if(yziel-y < -5)
            {
                ytmp = y-4;
            }
            if(xziel-x > 5)
            {
                xtmp = x+4;
            }
            else if(xziel-x < -5)
            {
                xtmp = x-4;
            }//*/
            
            return new Point(xtmp,ytmp);
        }
        return null;
    }
    
    public void setTranslation(Point punkt)
    {
        x = (int) Math.round(punkt.getX());
        y = (int) Math.round(punkt.getY());
        rotate = calculateAngle(x,y,xziel,yziel);
        umriss = new Rectangle(x,y, width, height);
        
        range.setFrame((int) (x-range.getWidth()/2),(int) (y-range.getHeight()/2),(int) range.getWidth(),(int) range.getHeight());
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
    
    public Rectangle getRange()
    {
        return range;
    }
    
    public double getRotate()
    {
        return rotate;
    }
    
    public int getLeben()
    {
        return leben;
    }
    
    public int getMaxLeben()
    {
        return maxLeben;
    }
}
