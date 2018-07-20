import java.util.ArrayList;
import java.awt.Color;
import java.io.Serializable;

public class SpielerDaten implements Serializable
{
    private ArrayList<Einheit> einheiten;
    private ArrayList<Haus> bauten;
    private ArrayList<String> nachrichten;
    private String ip;
    private String name;
    private Color farbe;
    private int pos;
    private int team;
    
    public SpielerDaten(String ip , String name)
    {
        this.ip = ip;
        this.name = name;
        pos = -1;
        team = -1;
        
        einheiten = new ArrayList<Einheit>();
        bauten = new ArrayList<Haus>();
        nachrichten = new ArrayList<String>();
        farbe =  Color.BLACK;
    }
    
    public void setLists(ArrayList<Einheit> einheiten , ArrayList<Haus> bauten , ArrayList<String> nachrichten )
    {
        this.einheiten = einheiten;
        this.bauten = bauten;
        this.nachrichten = nachrichten;
    }
    
    
    public void setTeam(int team)
    {
        this.team = team;
    }
    
    public void setPos(int pos)
    {
        this.pos = pos;
    }
    
    public void setFarbe(Color farbe)
    {
        this.farbe = farbe;
    }
    
    
    public ArrayList<Einheit> getEinheiten()
    {
        return einheiten;
    }
    
    public ArrayList<Haus> getBauten()
    {
        return bauten;
    }
    
    public ArrayList<String> getNachrichten()
    {
        return nachrichten;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getIp()
    {
        return ip;
    }
    
    public Color getFarbe()
    {
        return farbe;
    }
    
    public int getPos()
    {
        return pos;
    }
    
    public int getTeam()
    {
        return team;
    }
}
