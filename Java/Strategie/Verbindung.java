import java.net.*;
import java.io.*;
import java.util.ArrayList;

class Verbindung extends Thread
{
    protected Socket client;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected Server server;
    
    public Verbindung(Server server, Socket client)
    {
        this.server = server;
        this.client = client;
    
        try
        {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } 
        catch (IOException e)
        {
            try 
            { 
                client.close(); 
            } 
            catch (IOException e2) 
            {} 
            System.out.println("Fehler beim Erzeugen der Streams: " + e);
        }
        this.start();
    }
    
    public void run()
    {
        // Löst endlosschleife aus? sobald etwas reinkommt wird server aufgefordert jedem die dateien neu zu schicken und diese fordern dann wiederrum auf
        // Ist diese Mehtohde überhaupt sinnvoll?
        byte[] spielerDaten;
        ArrayList<Byte> bytesSammeln = new ArrayList();
        try
        {
            while(true)
            {
                //Wandel den Input in ein ByteArray mit passender Größe
                bytesSammeln.clear();
                while(in.available() != 0)
                {
                    bytesSammeln.add(in.readByte());   // Array in Bytes
                }
                spielerDaten = new byte[bytesSammeln.size()];
                for(int i = 0; i < bytesSammeln.size(); i++)
                {
                    spielerDaten[i] = bytesSammeln.get(i).byteValue();
                }
                
                if(spielerDaten.length > 0)
                    server.broadcast( spielerDaten , this);
                /*try
                {
                    this.sleep(20);
                }
                catch(Exception ex)
                {}*/
            }
        } catch (IOException e)
        {
            System.out.println("Fehler beim Lesen des Inputs: " + e);
        }
    }
}