import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Spieler implements Runnable
{
    private static int port = 8830;
    private String ip;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread thread;
    private SpielerDaten daten;

    public Spieler(String ip, SpielerDaten daten)
    {
        try
        {
            //ip = "192.168.2.103";
            this.ip = ip;
            this.daten = daten;
            socket = new Socket( ip, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
            System.out.println("Verbindung zum Server aufgenommen...");
        } catch (IOException e)
        {
            System.out.println("Verbindung zum Server fehlgeschlagen!");
        }
    }
    
    public void run()
    {
       try
       {
            //Daten die der Spieler zum Server schickt!
            ObjectOutputStream os = new ObjectOutputStream( out );
            byte[] spielerDaten;
            SpielerDaten puppe;
            while(true)
            {
                spielerDaten = toByteArray(daten);
                //Schreibe in den Ausgang von Verbindung (Server) die ArrayList eigene spielerDaten
                os.write( spielerDaten );
                os.flush();
                // Spielerdaten die vom Server kommen
                in.read(spielerDaten);   // Array in Bytes
                if(spielerDaten.length > 0)
                {
                    puppe = (SpielerDaten) toObject(spielerDaten);
                    if(puppe != null)
                    {
                        System.out.println(this + " Empfange: " + puppe.getIp());//spielerDaten zu Objekt --> ArrayList?
                    }
                }
                /*
                try
                {
                    thread.sleep(40);
                }
                catch(Exception x)
                {
                    System.out.println("Fehler beim Warten " + x);
                }*/
            }
       } catch (IOException e) 
       { 
            System.out.println("Verbindung zum Server abgebrochen \n" + e); 
       }
    }
    
    public static byte[] toByteArray (Object obj)
    {
        byte[] bytes = null;
        try 
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos); 
            oos.writeObject(obj);
            oos.flush(); 
            oos.close(); 
            bos.close();
            bytes = bos.toByteArray ();
        }
        catch (IOException ex) {
            //System.out.println("Fehler beim Parsen zum ByteArray! \n" + ex);
        }
        return bytes;
    }
    
    public static Object toObject (byte[] bytes)
    {
        Object obj = null;
        try 
        {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close(); 
            bis.close();
        }
        catch (IOException ex) {
            System.out.println("Fehler beim Parsen zum Objekt");
        }
        catch (ClassNotFoundException ex) {
            //System.out.println("Fehler beim Parsen zum Objekt");
        }
        catch (Exception ex) {
            //System.out.println("Fehler beim Parsen zum Objekt");
        }
        return obj;
    }
    
    public void stop()
    {
        try
        {
            socket.close();
        } catch (IOException e)
        {}
    
        if ((thread !=null) && thread.isAlive())
        {
            thread.stop();
            thread = null;
        }
    }
}
