import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Grafiken
{
    private BufferedImage hq;
    private BufferedImage kaserne;
    private BufferedImage waffenfarbrik;
    private BufferedImage kraftwerk;
    private BufferedImage raketen;
    private BufferedImage mg;
    private BufferedImage soldat;
    private BufferedImage raketensoldat;
    private BufferedImage schuetze;
    private BufferedImage panzer;
    private BufferedImage mgPanzer;
    
    public Grafiken(Color color)
    {
        Graphics g;
        Graphics2D g2d;
        
        hq = new BufferedImage(400,200, BufferedImage.TYPE_INT_ARGB );
            g = hq.getGraphics();
            g2d = (Graphics2D) g;
            AffineTransform at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(200,200,200));
            g.fillRect(5,5, 390,190);
            g.setColor(color);
            g.fillRect(5,40, 390,10);
            g.fillRect(5,160, 390,10);
            g.setColor(new Color(130,130,130));
            g.drawString("Hauptquatier", 175, 110);
            g.drawRect(5,5, 390,190);
            
        kaserne = new BufferedImage(200,250, BufferedImage.TYPE_INT_ARGB );
            g = kaserne.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(200,200,200));
            g.fillRect(5,5, 190,240);
            g.setColor(color);
            g.fillRect(5,30, 190,10);
            g.fillRect(5,210, 190,10);
            g.setColor(new Color(130,130,130));
            g.drawString("Kaserne", 75, 135);
            g.drawRect(5,5, 190,240);
            
        waffenfarbrik = new BufferedImage(400,350, BufferedImage.TYPE_INT_ARGB );
            g = waffenfarbrik.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(200,200,200));
            g.fillRect(5,5, 390,340);
            g.setColor(color);
            g.fillRect(5,30, 390,10);
            g.fillRect(5,310, 390,10);
            g.setColor(new Color(130,130,130));
            g.drawString("Waffenfarbrik", 160, 185);
            g.drawRect(5,5, 390,340);
            
        kraftwerk = new BufferedImage(150,150, BufferedImage.TYPE_INT_ARGB );
            g = kraftwerk.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(200,200,200));
            g.fillRect(5,5, 140,140);
            g.setColor(color);
            g.fillRect(5,30, 140,10);
            g.fillRect(5,110, 140,10);
            g.setColor(new Color(130,130,130));
            g.drawString("Kraftwerk", 55, 65);
            g.drawRect(5,5, 140,140);
            
        raketen = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB );
            g = raketen.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(200,200,200));
            g.fillOval(10,10, 30,30);
            g.setColor(new Color(130,130,130));
            g.drawOval(10,10, 30,30);
            g.setColor(color);
            g.fillOval(15,17, 15,15);
            g.setColor(new Color(150,150,150));
            g.fillOval(5,19,45,10);
            
        mg = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB );
            g = mg.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(200,200,200));
            g.fillOval(10,10, 30,30);
            g.setColor(new Color(130,130,130));
            g.drawOval(10,10, 30,30);
            g.setColor(new Color(150,150,150));
            g.fillOval(5,12,45,7);
            g.fillOval(5,32,45,7);
            g.setColor(color);
            g.fillOval(20,20, 11,11);
            
        soldat = new BufferedImage(35,25, BufferedImage.TYPE_INT_ARGB );
            g = soldat.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(color);
            g.fillOval(5,0, 15,25);
            g.setColor(new Color(20,20,20));
            g.fillOval(12,13, 17,7);
            g.setColor(new Color(255,218,185));
            g.fillOval(3,8, 15,9);
            
        raketensoldat = new BufferedImage(35,25, BufferedImage.TYPE_INT_ARGB );
            g = raketensoldat.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(color);
            g.fillOval(5,0, 15,25);
            g.setColor(new Color(100,100,100));
            g.fillOval(3,16, 30,8);
            g.setColor(new Color(255,218,185));
            g.fillOval(3,8, 15,9);
            
        schuetze = new BufferedImage(35,25, BufferedImage.TYPE_INT_ARGB );
            g = schuetze.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),35));
            g.fillOval(5,0, 15,25);
            g.setColor(new Color(150,150,150, 35));
            g.fillOval(3,12, 27,4);
            g.setColor(new Color(255,218,185 , 35));
            g.fillOval(3,8, 15,9);
            
        mgPanzer = new BufferedImage(80,60, BufferedImage.TYPE_INT_ARGB );
            g = mgPanzer.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(Color.lightGray);
            g.fillRoundRect(0,5,60,50,10,10);
            g.setColor(Color.darkGray);
            g.drawRoundRect(0,5,60,50,10,10);
            g.setColor(color);
            g.fillRect(15,6, 10,48);
            
            g.setColor(Color.lightGray.darker());
            g.fillRoundRect(30,17,35,10,5,5);
            g.setColor(Color.darkGray);
            g.drawRoundRect(30,17,35,10,5,5);
            
            g.setColor(Color.lightGray.darker());
            g.fillRoundRect(30,33,35,10,5,5);
            g.setColor(Color.darkGray);
            g.drawRoundRect(30,33,35,10,5,5);
            
            g.setColor(Color.lightGray.darker());
            g.fillRoundRect(30,25,40,10,5,5);
            g.setColor(Color.darkGray);
            g.drawRoundRect(30,25,40,10,5,5);
            
        panzer = new BufferedImage(80,60, BufferedImage.TYPE_INT_ARGB );
            g = panzer.getGraphics();
            g2d = (Graphics2D) g;
            at = new AffineTransform();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setTransform(at);
            g.setColor(Color.lightGray);
            g.fillRoundRect(0,5,60,50,10,10);
            g.setColor(Color.darkGray);
            g.drawRoundRect(0,5,60,50,10,10);
            g.setColor(color);
            g.fillRect(15,6, 10,48);
            
            g.setColor(Color.lightGray);
            g.fillRoundRect(30,25,40,10,5,5);
            g.setColor(Color.darkGray);
            g.drawRoundRect(30,25,40,10,5,5);
            
            g.setColor(Color.lightGray);
            g.fillOval(10,10, 30,40);
            g.setColor(Color.darkGray);
            g.drawOval(10,10, 30,40);
    }
    
    public BufferedImage getHq()
    {
        return hq;
    }
    
    public BufferedImage getKaserne()
    {
        return kaserne;
    }
    
    public BufferedImage getWaffenfarbrik()
    {
        return waffenfarbrik;
    }
    
    public BufferedImage getKraftwerk()
    {
        return kraftwerk;
    }
    
    public BufferedImage getRaketen()
    {
        return raketen;
    }
    
    public BufferedImage getMg()
    {
        return mg;
    }
    
    public BufferedImage getSoldat()
    {
        return soldat;
    }
    
    public BufferedImage getRaketenSoldat()
    {
        return raketensoldat;
    }
    
    public BufferedImage getSchuetze()
    {
        return schuetze;
    }
    
    public BufferedImage getMgPanzer()
    {
        return mgPanzer;
    }
    
    public BufferedImage getPanzer()
    {
        return panzer;
    }
}
