import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import javax.swing.border.MatteBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.BorderFactory;

public class GameBar extends JPanel implements MouseListener
{
    private JPanel bauPanel;
    private MiniKarte miniKarte;
    private JPanel auswahlPanel;

    private JLabel hq;
    private JLabel kaserne;
    private JLabel waffenfarbrik;
    private JLabel kraftwerk;
    private JLabel raketen;
    private JLabel mg;
    
    private JLabel headLine;
    private JLabel button1;
    private JLabel button2;
    private JLabel button3;
    private JLabel button4;
    private JLabel button5;
    private JLabel button6;
    private JLabel work;
    
    private JLabel[] bauListe;
    
    private Border lineBorder;
    private Border marginRight;
 
    private Spiel spiel;
    private Start start;
    private Haus haus;
    
    private int rectWidth;
    
    public GameBar(Start start, Spiel spiel, int height, Karte karte , SpielerDaten spielerDaten)
    {
        this.spiel = spiel;
        this.start = start;
        marginRight = BorderFactory.createEmptyBorder(0,20,0,0);
        lineBorder = BorderFactory.createLineBorder(new Color(39,64,139),1);
        Border jLabelBorder = BorderFactory.createCompoundBorder(lineBorder,marginRight);
        rectWidth = 0;
        
        this.setLayout(null);
        
        bauPanel = new JPanel();
        bauPanel.setLayout(null);
        bauPanel.setLocation(0, 35);
        bauPanel.setSize(250, height-250);
        bauPanel.setOpaque(false);
        
            hq = new JLabel("Hauptquatier");
            hq.setSize(250, 35);
            hq.setLocation(0, 35);
            hq.setBackground(new Color(190,190,190));
            hq.setBorder(jLabelBorder);
            hq.setOpaque(true);
            hq.addMouseListener(this);
            
            kaserne = new JLabel("Kaserne");
            kaserne.setSize(250, 35);
            kaserne.setLocation(0, 80);
            kaserne.setBackground(new Color(190,190,190));
            kaserne.setBorder(jLabelBorder);
            kaserne.setOpaque(true);
            kaserne.addMouseListener(this);
            
            waffenfarbrik = new JLabel("Waffenfarbrik");
            waffenfarbrik.setSize(250, 35);
            waffenfarbrik.setLocation(0, 125);
            waffenfarbrik.setBackground(new Color(190,190,190));
            waffenfarbrik.setBorder(jLabelBorder);
            waffenfarbrik.setOpaque(true);
            waffenfarbrik.addMouseListener(this);
            
            kraftwerk = new JLabel("Kraftwerk");
            kraftwerk.setSize(250, 35);
            kraftwerk.setLocation(0, 170);
            kraftwerk.setBackground(new Color(190,190,190));
            kraftwerk.setBorder(jLabelBorder);
            kraftwerk.setOpaque(true);
            kraftwerk.addMouseListener(this);
            
            raketen = new JLabel("Raketen-Stellung");
            raketen.setSize(250, 35);
            raketen.setLocation(0, 215);
            raketen.setBackground(new Color(190,190,190));
            raketen.setBorder(jLabelBorder);
            raketen.setOpaque(true);
            raketen.addMouseListener(this);
            
            mg = new JLabel("MG-Stellung");
            mg.setSize(250, 35);
            mg.setLocation(0, 260);
            mg.setBackground(new Color(190,190,190));
            mg.setBorder(jLabelBorder);
            mg.setOpaque(true);
            mg.addMouseListener(this);
            
        auswahlPanel = new JPanel();
        auswahlPanel.setLayout(null);
        auswahlPanel.setLocation(0, 0);
        auswahlPanel.setSize(250, height-250);
        auswahlPanel.setOpaque(false);
        auswahlPanel.setVisible(false);
        
            headLine = new JLabel();
            headLine.setSize(200, 35);
            headLine.setLocation(50, 20);
            headLine.setBackground(new Color(200,200,200,200));
            headLine.setBorder(jLabelBorder);
            headLine.setOpaque(true);
            headLine.addMouseListener(this);
        
            button1 = new JLabel();
            button1.setSize(250, 35);
            button1.setLocation(0, 70);
            button1.setBackground(new Color(190,190,190));
            button1.setBorder(jLabelBorder);
            button1.setOpaque(true);
            button1.addMouseListener(this);
            
            button2 = new JLabel();
            button2.setSize(250, 35);
            button2.setLocation(0, 115);
            button2.setBackground(new Color(190,190,190));
            button2.setBorder(jLabelBorder);
            button2.setOpaque(true);
            button2.addMouseListener(this);
            
            button3 = new JLabel();
            button3.setSize(250, 35);
            button3.setLocation(0, 160);
            button3.setBackground(new Color(190,190,190));
            button3.setBorder(jLabelBorder);
            button3.setOpaque(true);
            button3.addMouseListener(this);
            
            button4 = new JLabel();
            button4.setSize(250, 35);
            button4.setLocation(0, 205);
            button4.setBackground(new Color(190,190,190));
            button4.setBorder(jLabelBorder);
            button4.setOpaque(true);
            button4.addMouseListener(this);
            
            button5 = new JLabel();
            button5.setSize(250, 35);
            button5.setLocation(0, 250);
            button5.setBackground(new Color(190,190,190));
            button5.setBorder(jLabelBorder);
            button5.setOpaque(true);
            button5.addMouseListener(this);
            
            button6 = new JLabel();
            button6.setSize(250, 35);
            button6.setLocation(0, 295);
            button6.setBackground(new Color(190,190,190));
            button6.setBorder(jLabelBorder);
            button6.setOpaque(true);
            button6.addMouseListener(this);
            
            work = new JLabel("Auftragsliste")
            {
                @Override
                public void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    g.setColor(new Color(50,50,50,100));
                    g.fillRect(rectWidth,0,250-rectWidth, 33);
                }
            };
            work.setSize(248, 35);
            work.setLocation(0, 340);
            work.setForeground(new Color(0,0,70,100));
            work.setBorder(new MatteBorder(0,0,2,0,new Color(0,0,70,100)));
            
            bauListe = new JLabel[18];
            bauListe[0] = new JLabel();
            bauListe[1] = new JLabel();
            bauListe[2] = new JLabel();
            bauListe[3] = new JLabel();
            bauListe[4] = new JLabel();
            bauListe[5] = new JLabel();
            bauListe[6] = new JLabel();
            bauListe[7] = new JLabel();
            bauListe[8] = new JLabel();
            bauListe[9] = new JLabel();
            bauListe[10] = new JLabel();
            bauListe[11] = new JLabel();
            bauListe[12] = new JLabel();
            bauListe[13] = new JLabel();
            bauListe[14] = new JLabel();
            bauListe[15] = new JLabel();
            bauListe[16] = new JLabel();
            bauListe[17] = new JLabel();
            
            for(int i = 0; i < bauListe.length; i++)
            {
                bauListe[i].setSize(248, 25);
                bauListe[i].setLocation(0, 375+(i*25));
                bauListe[i].setBorder(new MatteBorder(0,0,1,0,new Color(0,0,170,100)));
                if( i % 2 == 0)
                {
                    bauListe[i].setBackground(new Color(190,190,190));
                    bauListe[i].setOpaque(true);
                }
                else
                {
                    bauListe[i].setBackground(new Color(255,255,255,100));
                    bauListe[i].setOpaque(true);
                }
            }
            
        miniKarte = new MiniKarte(karte,spiel.getWidth(),spiel.getHeight(), this, spielerDaten);
        miniKarte.setLocation(0, height-250);
        miniKarte.setSize(248, 250);
        
        this.add(miniKarte);
        this.add(auswahlPanel);
            auswahlPanel.add(headLine);
            auswahlPanel.add(button1);
            auswahlPanel.add(button2);
            auswahlPanel.add(button3);
            auswahlPanel.add(button4);
            auswahlPanel.add(button5);
            auswahlPanel.add(button6);
            auswahlPanel.add(work);
            for(JLabel tmp: bauListe)
            {
                auswahlPanel.add(tmp);
            }
        this.add(bauPanel);
            bauPanel.add(hq);
            bauPanel.add(kaserne);
            bauPanel.add(waffenfarbrik);
            bauPanel.add(kraftwerk);
            bauPanel.add(raketen);
            bauPanel.add(mg);
    }
    
    public void translateSpiel(int x, int y)
    {
        spiel.translate(x,y);
    }
    
    public MiniKarte getMiniKarte()
    {
        return miniKarte;
    }
    
    public void refresh()
    {
        if(haus != null)
        {
            rectWidth = (int) Math.round( haus.getCounter()*250/haus.getCounterMax());
            work.repaint();
            
            int z = 0;
            for(JLabel tmp : bauListe)
            {
                if(tmp.isVisible())
                {
                    z++;
                }
            }
            if(z != haus.getOrder().size())
            {
                setWahl(haus);
            }
        }
    }
    
    public void setWahl(Haus wahl)
    {
        if(wahl != null)
        {
            bauPanel.setVisible(false);
            auswahlPanel.setVisible(true);
            
            button1.setVisible(false);
            button2.setVisible(false);
            button3.setVisible(false);
            button4.setVisible(false);
            button5.setVisible(false);
            button6.setVisible(false);
            
            ArrayList<Integer> auftrag = wahl.getOrder();
            switch(wahl.getTyp())
            {
                case 1:
                    headLine.setText("Hauptquatier");
                    button1.setText("Fallschirmspringer");
                    button2.setText("Wasserstoffbombe");
                    button3.setText("Radar");
                    button4.setText("Säure");
                    button5.setText("XXXX");
                    button6.setText("XXXX");
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                    button4.setVisible(true);
                    button5.setVisible(true);
                    button6.setVisible(true);
                    for(int i = 0; i < bauListe.length; i++)
                    {
                        if(i < auftrag.size())
                        {
                            bauListe[i].setVisible(true);
                            switch(auftrag.get(i))
                            {
                                case 1:
                                    bauListe[i].setText("Fallschirmspringer");
                                    break;
                                case 2:
                                    bauListe[i].setText("Wasserstoffbombe");
                                    break;
                                case 3:
                                    bauListe[i].setText("Radar");
                                    break;
                                case 4:
                                    bauListe[i].setText("Säure");
                                    break;
                            }
                        }
                        else
                        {
                            bauListe[i].setVisible(false);
                        }
                    }
                    break;
                case 2:
                    headLine.setText("Kaserne");
                    button1.setText("MG-Mann");
                    button2.setText("Panzer-Knacker");
                    button3.setText("Scharfschuetze");
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                    for(int i = 0; i < bauListe.length; i++)
                    {
                        if(i < auftrag.size())
                        {
                            bauListe[i].setVisible(true);
                            switch(auftrag.get(i))
                            {
                                case 1:
                                    bauListe[i].setText("MG-Mann");
                                    break;
                                case 2:
                                    bauListe[i].setText("Panzer-Knacker");
                                    break;
                                case 3:
                                    bauListe[i].setText("Scharfschuetze");
                                    break;
                            }
                        }
                        else
                        {
                            bauListe[i].setVisible(false);
                        }
                    }
                    break;
                case 3:
                    headLine.setText("Waffenfarbrik");
                    button1.setText("MG-Panzer");
                    button2.setText("Panzer");
                    button3.setText("Baufahrzeug");
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                    for(int i = 0; i < bauListe.length; i++)
                    {
                        if(i < auftrag.size())
                        {
                            bauListe[i].setVisible(true);
                            switch(auftrag.get(i))
                            {
                                case 1:
                                    bauListe[i].setText("MG-Panzer");
                                    break;
                                case 2:
                                    bauListe[i].setText("Panzer");
                                    break;
                                case 3:
                                    bauListe[i].setText("Baufahrzeug");
                                    break;
                            }
                        }
                        else
                        {
                            bauListe[i].setVisible(false);
                        }
                    }
                    break;
                case 4:
                    headLine.setText("Kraftwerk");
                    button1.setText("Energieforschung");
                    button1.setVisible(true);
                    for(int i = 0; i < bauListe.length; i++)
                    {
                        if(i < auftrag.size())
                        {
                            bauListe[i].setVisible(true);
                            switch(auftrag.get(i))
                            {
                                case 1:
                                    bauListe[i].setText("Energieforschung");
                                    break;
                            }
                        }
                        else
                        {
                            bauListe[i].setVisible(false);
                        }
                    }
                    break;
                case 5:
                    headLine.setText("Raketen-Stellung");
                    button1.setText("Stabilität");
                    button2.setText("Schaden");
                    button1.setVisible(true);
                    button2.setVisible(true);
                    for(int i = 0; i < bauListe.length; i++)
                    {
                        if(i < auftrag.size())
                        {
                            bauListe[i].setVisible(true);
                            switch(auftrag.get(i))
                            {
                                case 1:
                                    bauListe[i].setText("Stabilität");
                                    break;
                                case 2:
                                    bauListe[i].setText("Schaden");
                                    break;
                            }
                        }
                        else
                        {
                            bauListe[i].setVisible(false);
                        }
                    }
                    break;
                case 6:
                    headLine.setText("MG-Stellung");
                    button1.setText("Stabilität");
                    button2.setText("Schaden");
                    button1.setVisible(true);
                    button2.setVisible(true);
                    for(int i = 0; i < bauListe.length; i++)
                    {
                        if(i < auftrag.size())
                        {
                            bauListe[i].setVisible(true);
                            switch(auftrag.get(i))
                            {
                                case 1:
                                    bauListe[i].setText("Stabilität");
                                    break;
                                case 2:
                                    bauListe[i].setText("Schaden");
                                    break;
                            }
                        }
                        else
                        {
                            bauListe[i].setVisible(false);
                        }
                    }
                    break;
            }
            if(bauListe[bauListe.length-1].isVisible())
            {
                bauListe[bauListe.length-1].setText(auftrag.size()-bauListe.length-1 + " Weitere");
            }
        }
        else
        {
            bauPanel.setVisible(true);
            auswahlPanel.setVisible(false);
        }
        this.haus = wahl;
    }
    
    public void mouseReleased(MouseEvent e) 
    {
        if(e.getSource() == hq)
        {
            spiel.setBauen(1);
        }
        else if(e.getSource() == kaserne)
        {
            spiel.setBauen(2);
        }
        else if(e.getSource() == waffenfarbrik)
        {
            spiel.setBauen(3);
        }
        else if(e.getSource() == kraftwerk)
        {
            spiel.setBauen(4);
        }
        else if(e.getSource() == raketen)
        {
            spiel.setBauen(5);
        }
        else if(e.getSource() == mg)
        {
            spiel.setBauen(6);
        }
        else if(e.getSource() == button1)
        {
            switch(haus.getTyp())
            {
                case 1://Soldat
                case 2://Fallschirmspringer
                case 3://MG-Panzer
                case 4://Energieforschung
                case 5://Stabilität
                case 6://Stabilität
                    haus.addOrder(1);
                    break;
            }
            setWahl(haus);
        }
        else if(e.getSource() == button2)
        {
            switch(haus.getTyp())
            {
                case 1://Raketensoldat
                case 2://Wasserstoffbombe
                case 3://Panzer
                    haus.addOrder(2);
                    break;
                case 4:
                    break;
                case 5://Schaden
                case 6://Schaden
                    haus.addOrder(2);
                    break;
            }
            setWahl(haus);
        }
        else if(e.getSource() == button3)
        {
            switch(haus.getTyp())
            {
                case 1://Scharfschütze
                case 2://Säure
                case 3://Baufahrzeug
                    haus.addOrder(3);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
            setWahl(haus);
        }
        else if(e.getSource() == button4)
        {
            switch(haus.getTyp())
            {
                case 1:
                    break;
                case 2://Radar
                    haus.addOrder(4);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
            setWahl(haus);
        }
    } 
    
    public void mouseEntered(MouseEvent e) 
    {
        if(e.getSource() != headLine)
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
    } 

    public void mouseExited(MouseEvent e) 
    {
        if(e.getSource() != headLine)
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
    } 
    
    public void mousePressed(MouseEvent e) 
    {} 
    public void mouseClicked(MouseEvent e) 
    {} 
}
