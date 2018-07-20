import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.border.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

import java.awt.image.MemoryImageSource;
import java.awt.Cursor;

public class menu extends JFrame implements ActionListener, MouseListener, KeyListener
{
    JFrame frame;
    Vorspann vorspann;
    JPanel highscore;
    JPanel optionenp;
    
    JLabel fortsetzen;
    JLabel neu;
    JLabel beenden;
    JLabel optionen;
    JLabel score;
    
    JLabel screen;
    JLabel vol;
    JLabel grafiktext;
    
    boolean fullscreen;
    boolean sound;
    
    int grafik;
    
    Font textType;
    Timer custodian;
    Game spiel;
    Cursor transparentCursor;
    
    Sound musik;
    
    class Vorspann extends JPanel implements ActionListener
    {
        Timer repeat;
        float alpha;
        boolean start;
        boolean showmenu;
         
        public Vorspann()
        {
            start = true;
            showmenu = false;
            alpha = 1;
            repeat = new Timer( 25, this);
        }
        
        public void paintComponent(Graphics g) {
           
            if(showmenu == false)
            {
               g.setColor(new Color(0,0,0));
               g.fillRect(0,0,this.getWidth(), this.getHeight());
               g.drawImage(Toolkit.getDefaultToolkit().getImage("res/images/vorspann.png"), 0, 0,frame.getWidth(), frame.getHeight(),  null);
            }
            if(showmenu == true)
            {
               g.drawImage(Toolkit.getDefaultToolkit().getImage("res/images/menu.jpg"), 0, 0,frame.getWidth(), frame.getHeight(),  null);
            }
            g.setColor(new Color(0,0,0,alpha));
            g.fillRect(0,0,this.getWidth(), this.getHeight());
        }
        
        public void actionPerformed ( ActionEvent event )
        {
            if (event.getSource() == repeat)
            {
                if(alpha >= 0.005 && start == true)
                {
                    alpha -= 0.005f;
                    frame.validate();
                    frame.repaint();
                }
                if((alpha < 1 && start == false) || alpha <= 0.005 && showmenu == false)
                {
                    start = false;
                    alpha += 0.005f;
                    frame.validate();
                    frame.repaint();
                }
                else if(start == false)
                {
                    showmenu = true;
                    start = true;
                    neu.setVisible(true);
                    beenden.setVisible(true);
                    optionen.setVisible(true);
                    score.setVisible(true);
                }
                int foreground = 0;
                try
                {
                    foreground = neu.getForeground().getRed();
                }
                catch(Exception ex)
                {}
                if(neu.isVisible() && foreground <= 190)
                {
                    beenden.setForeground(new Color(foreground+5,foreground+5,foreground+5));
                    optionen.setForeground(new Color(foreground+5,foreground+5,foreground+5));
                    score.setForeground(new Color(foreground+5,foreground+5,foreground+5));
                    neu.setForeground(new Color(foreground+5,foreground+5,foreground+5));
                }
                if(this.getSize() != frame.getSize())
                {
                    this.setSize(frame.getSize());
                    neu.setLocation(50,frame.getHeight()-300);
                    score.setLocation(50,frame.getHeight()-240);
                    optionen.setLocation(50,frame.getHeight()-180);
                    beenden.setLocation(50,frame.getHeight()-120);
                    fortsetzen.setLocation(50,frame.getHeight()-360);
                    highscore.setLocation(350 ,frame.getHeight()/3);
                    //highscore.setSize(frame.getWidth()-400,frame.getHeight()-320);
                    optionenp.setLocation(350 ,(int) (frame.getHeight()/2.5));
                    //optionenp.setSize(frame.getWidth()-400,frame.getHeight()-400);
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        new menu();
    }
    
    public menu()
    {
        frame = new JFrame("Game 3D");
        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        try
        {
            System.loadLibrary("j3dcore-ogl");
            System.loadLibrary("exthelper");
        }
        catch(Exception ex)
        {
            System.out.println("Laden fehlgeschlagen!");
        }
        
        transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16)), new Point(0, 0), "invisibleCursor");
        sound = false;
        fullscreen = false;
        grafik = 3;
        try{
            musik = new Sound();
        } catch (Exception e) {}
        try {
            BufferedReader in = new BufferedReader(new FileReader("res/doc/options.txt"));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                if(zeile.equals("fullscreen=on"))
                {
                    fullscreen = true;
                }
                if(zeile.equals("volume=on"))
                {
                    sound = true;
                }
                if(zeile.equals("grafik=low"))
                {
                    grafik = 1;
                }
                if(zeile.equals("grafik=middle"))
                {
                    grafik = 2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fullscreen == true)
        {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        
        vorspann = new Vorspann();
        vorspann.setSize(frame.getWidth(),frame.getHeight());
        vorspann.setLocation(0,0);
        vorspann.setLayout(null);
            
            try
            {
                textType = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream("res/other/BrightYoungThings.ttf"));
            }
            catch(Exception ex)
            {
                System.out.println("ERROR CANT LOAD FONT");
            }
            fortsetzen = new JLabel("Spiel Fortsetzen");
            fortsetzen.setLocation(50,frame.getHeight()-360);
            fortsetzen.setSize(200,40);
            fortsetzen.setForeground(new Color(0,0,0));
            fortsetzen.addMouseListener(this);
            fortsetzen.setFont(textType);
            fortsetzen.setFont(fortsetzen.getFont().deriveFont((float) 40));
            fortsetzen.setVisible(false);
            
            neu = new JLabel("Neues Spiel");
            neu.setLocation(50,frame.getHeight()-300);
            neu.setSize(200,40);
            neu.setForeground(new Color(0,0,0));
            neu.addMouseListener(this);
            neu.setFont(fortsetzen.getFont().deriveFont((float) 40));
            neu.setVisible(false);
            
            score = new JLabel("Highscore");
            score.setLocation(50,frame.getHeight()-240);
            score.setSize(200,40);
            score.setForeground(new Color(0,0,0));
            score.addMouseListener(this);
            score.setFont(fortsetzen.getFont().deriveFont((float) 40));
            score.setVisible(false);
            
            optionen = new JLabel("Optionen");
            optionen.setLocation(50,frame.getHeight()-180);
            optionen.setSize(200,40);
            optionen.setForeground(new Color(0,0,0));
            optionen.addMouseListener(this);
            optionen.setFont(fortsetzen.getFont().deriveFont((float) 40));
            optionen.setVisible(false);
            
            beenden = new JLabel("Beenden");
            beenden.setLocation(50,frame.getHeight()-120);
            beenden.setSize(200,40);
            beenden.setForeground(new Color(0,0,0));
            beenden.addMouseListener(this);
            beenden.setFont(fortsetzen.getFont().deriveFont((float) 40));
            beenden.setVisible(false);
           
            vorspann.add(fortsetzen);
            vorspann.add(neu);
            vorspann.add(score);
            vorspann.add(optionen);
            vorspann.add(beenden);
            
            highscore = new JPanel();
            highscore.setBackground(new Color(10,10,10));
            highscore.setLocation(350 ,frame.getHeight()/3);
            highscore.setSize(550,300);
            highscore.setBorder(new LineBorder(new Color(20,20,20), 1));
            highscore.setLayout(null);
            highscore.setVisible(false);
            
                for(int i = 1; i <= 10; i++)
                {
                    JLabel nummer = new JLabel("" + i + ".");
                    nummer.setSize(100,25);
                    nummer.setLocation(100,(i-1)*25);
                    
                    JLabel points = new JLabel("" + (9999999/i));
                    points.setSize(100,25);
                    points.setLocation(200,(i-1)*25);
                    
                    JLabel name = new JLabel("Player");
                    name.setSize(100,25);
                    name.setLocation(300,(i-1)*25);
                    
                    highscore.add(name);
                    highscore.add(points);
                    highscore.add(nummer);
                }
            
            optionenp = new JPanel();
            optionenp.setBackground(new Color(10,10,10));
            optionenp.setLocation(350 ,frame.getHeight()/3);
            optionenp.setSize(550, 200);
            optionenp.setBorder(new LineBorder(new Color(20,20,20), 1));
            optionenp.setLayout(null);
            optionenp.setVisible(false);
            
                JLabel voltext = new JLabel("Volumen");
                voltext.setSize(100,25);
                voltext.setLocation(100,40);
                
                vol = new JLabel("An");
                vol.setSize(100,25);
                vol.setLocation(300,40);
                vol.addMouseListener(this);
                
                JLabel screentext = new JLabel("Fullscreen");
                screentext.setSize(100,25);
                screentext.setLocation(100,90);
                
                screen = new JLabel("An");
                screen.setSize(100,25);
                screen.setLocation(300,90);
                screen.addMouseListener(this);
                
                JLabel grafiktex = new JLabel("Grafik");
                grafiktex.setSize(100,25);
                grafiktex.setLocation(100,140);
                
                grafiktext = new JLabel("Hoch");
                grafiktext.setSize(100,25);
                grafiktext.setLocation(300,140);
                grafiktext.addMouseListener(this);
                
                optionenp.add(screen);
                optionenp.add(vol);
                optionenp.add(screentext);
                optionenp.add(voltext);
                optionenp.add(grafiktex);
                optionenp.add(grafiktext);
                
                try {
                    BufferedReader in = new BufferedReader(new FileReader("res/doc/options.txt"));
                    String zeile = null;
                    while ((zeile = in.readLine()) != null) {
                        if(zeile.equals("fullscreen=on"))
                        {
                            screen.setText("An");
                        }
                        else
                        {
                            screen.setText("Aus");
                        }
                        if(zeile.equals("volume=on"))
                        {
                            vol.setText("An");
                        }
                        else
                        {
                            vol.setText("Aus");
                        }
                        if(zeile.equals("grafik=low"))
                        {
                            grafiktext.setText("Niedrig");
                        }
                        else if(zeile.equals("grafik=middle"))
                        {
                            grafiktext.setText("Mittel");
                        } 
                        else
                        {
                            grafiktext.setText("Hoch");
                        }   
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
        vorspann.add(highscore);
        vorspann.add(optionenp);
        frame.add(vorspann);
        frame.setVisible(true);
        frame.toFront();
        
        custodian = new Timer( 100, this);
        
        vorspann.repeat.start();
        
        if(sound == true)
        {
            try {
                musik.p.play();
            } catch (Exception e) {}
        }
    }
    
    public void actionPerformed ( ActionEvent event )
    {
        if(event.getSource() == custodian) 
        {
            if(spiel.canvas3d.isRendererRunning())
            {
                frame.add(spiel.canvas3d);
                custodian.stop();
            }
            else
            {
                System.out.println("[Lädt]");
            }
        }
    }
    
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == 27)
        {
            fortsetzen.setVisible(true);
            spiel.repeat.stop();
            frame.remove(spiel.canvas3d);
            frame.setCursor(DEFAULT_CURSOR);
            frame.add(vorspann);
        }
    }
            
    public void keyReleased(KeyEvent e) 
    {
    }
        
    public void keyTyped(KeyEvent e) 
    {
    }
    
    public void mouseClicked(MouseEvent e) { 
        if(e.getSource() == fortsetzen) 
        {
            fortsetzen.setVisible(false);
            spiel.repeat.start();
            frame.remove(vorspann);
            frame.setCursor(transparentCursor);
            frame.add(spiel.canvas3d);
        }
        if(e.getSource() == beenden)
        {
            System.exit(0);
        }
        if(e.getSource() == optionen)
        {
            if(!(optionenp.isVisible()))
            {
                highscore.setVisible(false);
                optionenp.setVisible(true);
            }
            else
                optionenp.setVisible(false);
        }
        if(e.getSource() == score)
        {
            if(!(highscore.isVisible()))
            {
                optionenp.setVisible(false);
                highscore.setVisible(true);
            }
            else
                highscore.setVisible(false);
        }
        if( e.getSource() == neu)
        {
            frame.remove(vorspann);
            frame.setCursor(transparentCursor);
            spiel = new Game(grafik);
            spiel.canvas3d.setSize(frame.getWidth(),frame.getHeight());
            spiel.canvas3d.setLocation(0,0);
            spiel.canvas3d.addKeyListener(this);
            custodian.start();
        }
        if(e.getSource() == screen)
        {
            if(screen.getText().equals("An"))
            {
                try
                {   
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("fullscreen=off\n" );
                    out.write("volume=");
                    out.write((sound == true)? "on\n" : "off\n");
                    out.write("grafik=");
                    out.write((grafik == 1)? "low\n" : (grafik == 2)? "middle\n" : "high\n");
                    out.close();
                    screen.setText("Aus");
                    
                    
                    frame.dispose();
                    frame.setUndecorated(false);
                    frame.setExtendedState(NORMAL);
                    frame.setSize(950, 600);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
                catch( java.io.IOException ex ) {}
            }
            else
            {
                try
                {   
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("fullscreen=on\n" );
                    out.write("volume=");
                    out.write((sound == true)? "on\n" : "off\n");
                    out.write("grafik=");
                    out.write((grafik == 1)? "low\n" : (grafik == 2)? "middle\n" : "high\n");
                    out.close();
                    screen.setText("An");
                    
                    frame.dispose();
                    frame.setUndecorated(true);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                }
                catch( java.io.IOException ex ) {}
            }
        }
        if(e.getSource() == vol)
        {
            if(vol.getText().equals("An"))
            {
                try
                {   
                    try {
                        musik.p.stop();
                    }
                    catch(Exception x ) {}
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("volume=off\n" );
                    out.write("fullscreen=");
                    out.write((fullscreen == true)? "on\n" : "off\n");
                    out.write("grafik=");
                    out.write((grafik == 1)? "low\n" : (grafik == 2)? "middle\n" : "high\n");
                    out.close();
                    vol.setText("Aus");
                }
                catch( java.io.IOException ex ) {}
            }
            else
            {
                try
                {   
                    try {
                        musik.p.play();
                    }
                    catch( Exception x ) {}
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("volume=on\n" );
                    out.write("fullscreen=");
                    out.write((fullscreen == true)? "on\n" : "off\n");
                    out.write("grafik=");
                    out.write((grafik == 1)? "low\n" : (grafik == 2)? "middle\n" : "high\n");
                    out.close();
                    vol.setText("An");
                }
                catch( java.io.IOException ex ) {}
            }
        }
        if(e.getSource() == grafiktext)
        {
            if(grafiktext.getText().equals("Hoch"))
            {
                try
                {   
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("volume=" );
                    out.write((sound == true)? "on\n" : "off\n");
                    out.write("fullscreen=");
                    out.write((fullscreen == true)? "on\n" : "off\n");
                    out.write("grafik=low\n");
                    out.close();
                    grafik = 1;
                    grafiktext.setText("Niedrig");
                }
                catch( java.io.IOException ex ) {}
            }
            else if(grafiktext.getText().equals("Mittel"))
            {
                try
                {   
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("volume=" );
                    out.write((sound == true)? "on\n" : "off\n");
                    out.write("fullscreen=");
                    out.write((fullscreen == true)? "on\n" : "off\n");
                    out.write("grafik=high\n");
                    out.close();
                    grafik = 3;
                    grafiktext.setText("Hoch");
                }
                catch( java.io.IOException ex ) {}
            }
            else if(grafiktext.getText().equals("Niedrig"))
            {
                try
                {   
                    FileWriter out = new FileWriter(new File("res/doc/options.txt"));
                    out.write("volume=" );
                    out.write((sound == true)? "on\n" : "off\n");
                    out.write("fullscreen=");
                    out.write((fullscreen == true)? "on\n" : "off\n");
                    out.write("grafik=middle\n");
                    out.close();
                    grafik = 2;
                    grafiktext.setText("Mittel");
                }
                catch( java.io.IOException ex ) {}
            }
        }
    } 
    
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == fortsetzen) 
        {
            fortsetzen.setForeground(new Color(255,255,255));
        }
        if(e.getSource() == beenden)
        {
            beenden.setForeground(new Color(255,255,255));
        }
        if(e.getSource() == optionen)
        {
            optionen.setForeground(new Color(255,255,255));
        }
        if(e.getSource() == score)
        {
            score.setForeground(new Color(255,255,255));
        }
        if( e.getSource() == neu)
        {
            neu.setForeground(new Color(255,255,255));
        }
        if(e.getSource() == screen)
        {
            screen.setForeground(new Color(60,60,60));
        }
        if(e.getSource() == vol)
        {
            vol.setForeground(new Color(60,60,60));
        }
        if(e.getSource() == grafiktext)
        {
            grafiktext.setForeground(new Color(60,60,60));
        }
    } 

    public void mouseExited(MouseEvent e) {
        if(e.getSource() == fortsetzen) 
        {
            fortsetzen.setForeground(new Color(190,190,190));
        }
        if(e.getSource() == beenden)
        {
            beenden.setForeground(new Color(190,190,190));
        }
        if(e.getSource() == optionen)
        {
            optionen.setForeground(new Color(190,190,190));
        }
        if(e.getSource() == score)
        {
            score.setForeground(new Color(190,190,190));
        }
        if( e.getSource() == neu)
        {
            neu.setForeground(new Color(190,190,190));
        }
        if(e.getSource() == screen)
        {
            screen.setForeground(new Color(50,50,50));
        }
        if(e.getSource() == vol)
        {
            vol.setForeground(new Color(50,50,50));
        }
        if(e.getSource() == grafiktext)
        {
            grafiktext.setForeground(new Color(50,50,50));
        }
    } 

    public void mousePressed(MouseEvent e) {
    } 

    public void mouseReleased(MouseEvent e) { 
    } 
}
