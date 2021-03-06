import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import java.net.*;
import java.io.*;

public class Start extends JFrame implements MouseListener, ActionListener
{
    private JFrame frame;
    private Spiel spiel;
        private GameBar gameBar;
    private JPanel menu;
        private JTextField ip;
        private JPanel sideBar;
            private JLabel fortsetzen;
            private JLabel neustart;
            private JLabel beitreten;
            private JLabel optionen;
            private JLabel beenden;
        private JPanel over;
        private JPanel optionPanel;
        private JPanel beitrittPanel;
            private JTextField serverIP;
            private JLabel beitritt;
        private JPanel startPanel;
            private JLabel starten;
            private JSlider speed;
            private JList lvlList;
            private JPanel confSammlung;
            private MiniKarte miniKarte;
    
    private ArrayList<Karte> karten;
    private ArrayList<SpielerConf> conf;
    private SpielerDaten spielerDaten;
    private Timer repeat;
    private Cursor standard;
    private Cursor error;
    
    private Dimension screen;
    private boolean fadeout;
    private boolean gestartet;
    private int alpha;
    private int zaehler;
    private String change;
    private String spielerName;
    private String ipText;
    
    private Server server;
    private Spieler verbindung;
    
    public Start()
    {
        server = new Server();
        alpha = 255;
        zaehler = 0;
        gestartet = false;
        fadeout = false;
        change = "";
        spielerName = "Spieler1";
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        karten = new ArrayList<Karte>();
        fuelleKarten();
        try
        {
            String iptmp = "" + InetAddress.getLocalHost();
            ipText = iptmp.substring(iptmp.indexOf('/')+1);
            spielerDaten = new SpielerDaten(ipText, spielerName);
        } catch (IOException e)
        {
            ip.setText( "Fehler beim Lesen der IP" );
        }
        
        BufferedImage cursorBlau = new BufferedImage((int) getToolkit().getBestCursorSize(16,16).getWidth(),(int) getToolkit().getBestCursorSize(16,16).getHeight(), BufferedImage.TYPE_INT_ARGB );
            Graphics g = cursorBlau.getGraphics();
            Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setStroke(new BasicStroke(1));
                
            g.setColor(new Color(100,100,255,200));
            g.fillRect(0,0,16,4);
            g.fillRect(0,4,4,8);
            g.fillRect(12,4,4,8);
            g.fillRect(0,12,16,4);
            g.setColor(Color.BLACK);
            g.drawRect(0,0,16,16);
            g.drawRect(4,4,8,8);
            
        BufferedImage cursorRot = new BufferedImage((int) getToolkit().getBestCursorSize(16,16).getWidth(),(int) getToolkit().getBestCursorSize(16,16).getHeight(), BufferedImage.TYPE_INT_ARGB );
            g = cursorRot.getGraphics();
            g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setStroke(new BasicStroke(1));
                
            g.setColor(new Color(255,100,100,200));
            g.fillRect(0,0,16,4);
            g.fillRect(0,4,4,8);
            g.fillRect(12,4,4,8);
            g.fillRect(0,12,16,4);
            g.setColor(Color.BLACK);
            g.drawRect(0,0,16,16);
            g.drawRect(4,4,8,8);
            
        standard = getToolkit().createCustomCursor(cursorBlau,new Point(8,8), "Cursor" );
        error = getToolkit().createCustomCursor(cursorRot,new Point(8,8), "Cursor2" );
        
        frame = new JFrame("");
        frame.setSize(screen);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        try
        {
            frame.setCursor(standard);
            frame.setIconImage(new ImageIcon(new URL("http://hartzkai.freehostia.com/archiv/TowerDefense/res/icon.png")).getImage());
        }
        catch(Exception ex)
        {}
        
        spiel = new Spiel(this, spielerDaten);
        spiel.setLocation(0,0);
        spiel.setSize(frame.getSize());
        spiel.setLayout(null);
        spiel.setVisible(false);
        
        gameBar = new GameBar(this, spiel , frame.getHeight(), karten.get(0),spielerDaten);
        gameBar.setLocation(0,0);
        gameBar.setSize(250, frame.getHeight());
        gameBar.setOpaque(true);
        gameBar.setBackground(new Color(39,64,139).brighter());
        gameBar.setBorder(new MatteBorder(0,0,0,2,new Color(39,64,139)));
        gameBar.setVisible(false);
        spiel.setGameBar(gameBar);
        
        menu = new JPanel();
        menu.setLocation(0,0);
        menu.setSize(frame.getSize());
        menu.setOpaque(true);
        menu.setLayout(null);
        menu.setBackground(new Color(79,148,205));
        
        ip = new JTextField();
        ip.setLocation(25, 80);
        ip.setSize(200,30);
        ip.setOpaque(false);
        ip.setEditable(false);
        ip.setBorder(null);
        try
        {
            ip.setText( "" + InetAddress.getLocalHost() );
        } catch (IOException e)
        {
            ip.setText( "Fehler beim Lesen der IP" );
        }
        
        sideBar = new JPanel();
        sideBar.setLocation(0,0);
        sideBar.setSize(frame.getWidth(),70);
        sideBar.setOpaque(true);
        sideBar.setLayout(null);
        sideBar.setBackground(new Color(39,64,139).brighter());
        sideBar.setBorder(new MatteBorder(0,0,2,0,new Color(39,64,139)));
        
            fortsetzen = new JLabel("Fortsetzen");
            fortsetzen.setLocation(50,0);
            fortsetzen.setSize(150,70);
            fortsetzen.setForeground(Color.WHITE);
            fortsetzen.addMouseListener(this);
            
            neustart = new JLabel("Neustarten");
            neustart.setLocation(200,0);
            neustart.setSize(150,70);
            neustart.setForeground(Color.WHITE);
            neustart.addMouseListener(this);
            
            beitreten = new JLabel("Beitreten");
            beitreten.setLocation(350,0);
            beitreten.setSize(150,70);
            beitreten.setForeground(Color.WHITE);
            beitreten.addMouseListener(this);
            
            optionen = new JLabel("Optionen");
            optionen.setLocation(500,0);
            optionen.setSize(150,70);
            optionen.setForeground(Color.WHITE);
            optionen.addMouseListener(this);
            
            beenden = new JLabel("Beenden");
            beenden.setLocation(650,0);
            beenden.setSize(150,70);
            beenden.setForeground(Color.WHITE);
            beenden.addMouseListener(this);
            
        over = new JPanel()
        {
            public void paintComponent(Graphics g)
            {
                g.setColor(new Color(79,148,205,alpha));
                g.fillRect(0,0,900,600);
            }
        };
        over.setLocation((frame.getWidth()-900)/2,200);
        over.setSize(900,600);
        over.setOpaque(false);
        
        optionPanel = new JPanel();
        optionPanel.setLocation(over.getLocation());
        optionPanel.setSize(over.getSize());
        optionPanel.setBackground(new Color(39,64,139).brighter());
        optionPanel.setBorder(new MatteBorder(2,2,2,2,new Color(39,64,139)));
        optionPanel.setOpaque(true);
        optionPanel.setVisible(false);
        
        beitrittPanel = new JPanel();
        beitrittPanel.setSize((int) (over.getWidth()/1.5),(int) (over.getHeight()/2.5));
        beitrittPanel.setLocation(over.getX()+((over.getWidth()-beitrittPanel.getWidth())/2),over.getY()+((over.getHeight()-beitrittPanel.getHeight())/2));
        beitrittPanel.setBackground(new Color(39,64,139).brighter());
        beitrittPanel.setBorder(new MatteBorder(2,2,2,2,new Color(39,64,139)));
        beitrittPanel.setOpaque(true);
        beitrittPanel.setLayout(null);
        beitrittPanel.setVisible(false);
        
            JLabel ipLabel = new JLabel("Server IP:");
            ipLabel.setLocation(70,50);
            ipLabel.setSize(120,30);
            ipLabel.setForeground(Color.WHITE);
        
            serverIP = new JTextField();
            serverIP.setLocation(70, 80);
            serverIP.setSize(200,30);
            serverIP.setBorder(new MatteBorder(2,2,2,2,new Color(39,64,139)));
        
            beitritt = new JLabel("Spielen starten");
            beitritt.setLocation(480,170);
            beitritt.setSize(120,70);
            beitritt.setForeground(Color.WHITE);
            beitritt.addMouseListener(this);
        
        startPanel = new JPanel();
        startPanel.setLocation(over.getLocation());
        startPanel.setSize(over.getSize());
        startPanel.setBackground(new Color(39,64,139).brighter());
        startPanel.setBorder(new MatteBorder(2,2,2,2,new Color(39,64,139)));
        startPanel.setLayout(null);
        startPanel.setOpaque(true);
        startPanel.setVisible(false);
        
            starten = new JLabel("Spielen starten");
            starten.setLocation(780,530);
            starten.setSize(120,70);
            starten.setForeground(Color.WHITE);
            starten.addMouseListener(this);
            
            miniKarte = new MiniKarte(karten.get(0),0,0, null, spielerDaten);
            miniKarte.setLocation(650,25);
            miniKarte.setSize(225, 210);
            miniKarte.setBackground(new Color(240,240,240));
            miniKarte.setBorder(new LineBorder(new Color(39,64,139),1));
            miniKarte.setOpaque(true);
            
            speed = new JSlider();
            speed.setLocation(25,245);
            speed.setSize(200, 25);
            speed.setValue(80);
            speed.setMaximum(120);
            speed.setMinimum(1);
            speed.setOpaque(false);
            
            String[] data = {"Level 1", "Level 2"};
            lvlList = new JList(data);
            lvlList.setLocation(25,25);
            lvlList.setSize(200, 210);
            lvlList.setBackground(new Color(240,240,240));
            lvlList.setBorder(new LineBorder(new Color(39,64,139),1));
            lvlList.setOpaque(true);
            lvlList.setSelectedIndex(0);
            
            confSammlung = new JPanel();
            confSammlung.setLocation(230,25);
            confSammlung.setSize(415, 250);
            confSammlung.setOpaque(false);
            confSammlung.setLayout(null);
            
            conf = new ArrayList();
            for(int i = 0;i < karten.get(lvlList.getSelectedIndex()).getStartPoint().size();i++)
            {
                SpielerConf spielerConf = new SpielerConf();
                spielerConf.setLocation(0,i*45);
                spielerConf.setSize(415, 35);
                spielerConf.setBackground(new Color(240,240,240,50));
                spielerConf.setBorder(new LineBorder(new Color(39,64,139),1));
                spielerConf.setOpaque(true);
                conf.add(spielerConf);
                confSammlung.add(spielerConf);
            }
            
                sideBar.add(fortsetzen);
                sideBar.add(neustart);
                sideBar.add(beitreten);
                sideBar.add(optionen);
                sideBar.add(beenden);
            menu.add(sideBar);
            menu.add(ip);
            menu.add(over);
                beitrittPanel.add(ipLabel);
                beitrittPanel.add(serverIP);
                beitrittPanel.add(beitritt);
            menu.add(beitrittPanel);
                optionPanel.add(new JLabel("Hans"));
            menu.add(optionPanel);
            menu.add(startPanel);
                startPanel.add(starten);
                startPanel.add(miniKarte);
                startPanel.add(speed);
                startPanel.add(confSammlung);
                startPanel.add(lvlList);
        frame.add(menu);
        frame.add(gameBar);
        frame.add(spiel);
        frame.setVisible(true);
        
        menu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        menu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        menu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        menu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        menu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc");
        ActionMap action = menu.getActionMap();
        action.put("right", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                translateCenter(20,0);
            }
        });
        action.put("left", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                translateCenter(-20,0);
            }
        });
        action.put("up", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                translateCenter(0,-20);
            }
        });
        action.put("down", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                translateCenter(0,20);
            }
        });
        action.put("esc", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                if(gestartet && menu.isVisible() && zaehler != 1)
                {
                    show(2);
                    spiel.runTimer(true);
                }
                setZaehler(0);
            }
        });
        
        repeat = new Timer(20, this);
        repeat.start();
    }
    
    private void translateCenter(int x, int y)
    {
        if(menu.isVisible())
        {
            startPanel.setLocation(startPanel.getX()+x,startPanel.getY()+y);
            optionPanel.setLocation(startPanel.getX()+x,startPanel.getY()+y);
            over.setLocation(startPanel.getX()+x,startPanel.getY()+y);
            beitrittPanel.setLocation(startPanel.getX()+x,startPanel.getY()+y);
        }
    }
    
    public void setCursor(int i)
    {
        switch(i)
        {
            case 1:
                frame.setCursor(standard);
                break;
            case 2:
                frame.setCursor(error);
                break;
        }
    }
    
    public void setZaehler(int i)
    {
        zaehler = i;
    }
    
    private void fuelleKarten()
    {
        ArrayList<Point> tmpStart = new ArrayList<Point>();
        ArrayList<Rectangle> tmpRessource = new ArrayList<Rectangle>();
            int resWidth = 75;
            int resHeight = 75;
        ArrayList<Rectangle> tmpBarrier = new ArrayList<Rectangle>();
    
        tmpStart.add(new Point(500,1250));
        tmpStart.add(new Point(5500,1250));
        tmpRessource.add(new Rectangle(0,1250,resWidth, resHeight));
        tmpRessource.add(new Rectangle(5950-resWidth,1250,resWidth, resHeight));
        tmpBarrier.add(new Rectangle(2000,0, 500, 2000 ));
        tmpBarrier.add(new Rectangle(4000,500,500,2000));
        karten.add(new Karte(1,new Dimension(6000,2500),tmpStart,tmpRessource,tmpBarrier));
        
        tmpStart.clear();
        tmpRessource.clear();
        tmpBarrier.clear();
        
        tmpStart.add(new Point(500,500));
        tmpStart.add(new Point(7500,7500));
        tmpStart.add(new Point(7500,500));
        tmpStart.add(new Point(500,7500));
        tmpRessource.add(new Rectangle(100,100,resWidth, resHeight));
        tmpRessource.add(new Rectangle(7900-resWidth,7900,resWidth, resHeight));
        karten.add(new Karte(2,new Dimension(8000,8000),tmpStart,tmpRessource,tmpBarrier));
    }
    
    public void neuZeichnen()
    {
        frame.repaint();
        frame.validate();
    }
    
    public void show(int id)
    {
        if(id == 1)
        {
           spiel.setVisible(false);
           gameBar.setVisible(false);
           menu.setVisible(true);
        }
        else if(id == 2)
        {
           spiel.setVisible(true);
           gameBar.setVisible(true);
           menu.setVisible(false);
        }
    }
    
    public void mouseReleased(MouseEvent e) 
    {
        if(e.getSource() == optionen)
        {
            if(alpha == 255)
            {
                optionPanel.setVisible(true);
            }
            change = "optionen";
            fadeout = !fadeout;
            server.exit();
        }
        else if(e.getSource() == neustart )
        {
            if(alpha == 255)
            {
                startPanel.setVisible(true);
            }
            change = "neustarten";
            fadeout = !fadeout;
            
            verbindung = new Spieler(ipText,spielerDaten);
        }
        else if(e.getSource() == beitreten )
        {
            if(alpha == 255)
            {
                beitrittPanel.setVisible(true);
            }
            change = "beitreten";
            fadeout = !fadeout;
        }
        else if(e.getSource() == beenden )
        {
            server.exit();
            System.exit(0);
        }
        else if(e.getSource() == fortsetzen)
        {
            if(gestartet)
            {
                show(2);
                spiel.runTimer(true);
            }
        }
        else if(e.getSource() == starten )
        {
            spiel.neustart(speed.getValue(), karten.get(lvlList.getSelectedIndex()),spielerDaten );
            gestartet = true;
            show(2);
        }
        else if(e.getSource() == beitritt )
        {
            if(!serverIP.getText().equals(""))
            {
                verbindung = new Spieler(ipText,spielerDaten);
            }
        }
    } 
    
    public void mouseEntered(MouseEvent e) 
    {
        try
        {
            JLabel event = (JLabel) (e.getSource());
            event.setForeground(Color.BLACK);
            repaint();
        }
        catch(Exception x)
        {}
    } 

    public void mouseExited(MouseEvent e) 
    {
        try
        {
            JLabel event = (JLabel) (e.getSource());
            event.setForeground(Color.WHITE);
            repaint();
        }
        catch(Exception x)
        {}
    } 
    
    public void actionPerformed ( ActionEvent e )
    {
        if(e.getSource() == repeat) 
        {
            if(fadeout && alpha > 1)
            {
                alpha -= 5;
            }
            else if(!fadeout && alpha < 255)
            {
                alpha += 5;
                if(!change.equals("") && alpha == 255)
                {
                    fadeout = true;
                    optionPanel.setVisible(false);
                    startPanel.setVisible(false);
                    beitrittPanel.setVisible(false);
                    if(change.equals("neustarten"))
                    {
                        startPanel.setVisible(true);
                    }
                    else if(change.equals("optionen"))
                    {
                        optionPanel.setVisible(true);
                    }
                    else if(change.equals("beitreten"))
                    {
                        beitrittPanel.setVisible(true);
                    }
                    change = "";
                }
            }
            if(karten.indexOf(miniKarte.getKarte()) != lvlList.getSelectedIndex())
            {
                miniKarte.setKarte(karten.get(lvlList.getSelectedIndex()));
                
                for(int i = conf.size();i < karten.get(lvlList.getSelectedIndex()).getStartPoint().size();i++)
                {
                    SpielerConf spielerConf = new SpielerConf();
                    spielerConf.setLocation(0,i*45);
                    spielerConf.setSize(415, 35);
                    spielerConf.setBackground(new Color(240,240,240,50));
                    spielerConf.setBorder(new LineBorder(new Color(39,64,139),1));
                    spielerConf.setOpaque(true);
                    conf.add(spielerConf);
                    confSammlung.add(spielerConf);
                }
                for(int i = 0; i < conf.size(); i++)
                {
                    if(i < karten.get(lvlList.getSelectedIndex()).getStartPoint().size())
                    {
                        conf.get(i).setVisible(true);
                    }
                    else
                    {
                        conf.get(i).setVisible(false);
                    }
                }
            }
            if(startPanel.isVisible() && alpha == 0)
            {
                int i = 0;
                for(SpielerConf tmp: conf)
                {
                    //setze farbe etc.
                    if(i == spielerDaten.getPos() && i !=  -1)
                    {
                        tmp.refreshSpielerDaten(spielerDaten);
                    }
                    
                    if(tmp.getSpielerTyp().equals("Offen"))
                    {
                        i++;
                    }
                    //aktualisiere die anzeige
                    tmp.refresh();
                }
                //Lege die Anzahl der Maximalen verbindungen auf anzahl von offen
                server.setMaxCon(i);
            }
            over.repaint();
        }
    }

    public void mousePressed(MouseEvent e) 
    {} 
    public void mouseClicked(MouseEvent e) 
    {} 
}
