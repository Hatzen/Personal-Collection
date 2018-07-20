import javazoom.jl.player.advanced.*;
import javazoom.jl.decoder.*;
import java.io.*;

public class Sound 
{
    File[] sounds;
    int i;
    AdvancedPlayer p;
    
    public Sound() throws FileNotFoundException, JavaLayerException 
    {
        File f = new File("res/sounds/");
        sounds = f.listFiles();
        i = 0;
        
        p = new AdvancedPlayer(new FileInputStream(sounds[i]));
    }
    
    public void next() throws FileNotFoundException, JavaLayerException 
    {
        if(i < sounds.length)
        {
            //p.stop();
            i++;
            p = new AdvancedPlayer(new FileInputStream(sounds[i]));
            p.play();
        }
    }
    
    public void before() throws FileNotFoundException, JavaLayerException 
    {
        if(i > 0)
        {
            //p.stop();
            i--;
            p = new AdvancedPlayer(new FileInputStream(sounds[i]));
            p.play();
        }
    }
}