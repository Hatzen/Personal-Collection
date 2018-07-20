import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;


import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Shape3D;
import javax.media.j3d.WakeupOnCollisionEntry;
import java.util.Enumeration;

public class Laser implements ActionListener 
{
    TransformGroup transrootLaser;
    Timer repeat;
    Cylinder laser;
    float zloc;
    float yloc;
    float xloc;
    float faktor;
    boolean horizontal;
    
    public Laser(boolean horizontal, int pos,int grafik)
    {
        zloc = 0.0f;
        faktor = 0.02f;
        this.horizontal = horizontal;
        yloc = 0f;
        xloc = 0f;
        if(horizontal == true)
            yloc = -0.25f*pos;
        else
            xloc = 0.5f*pos-1f;
            
        Appearance apLaser = new Appearance();
            if(grafik > 1)
            {
               TextureLoader loaderLaser = new TextureLoader("res/images/fade.png", "LUMINANCE", new Container());
               Texture textureLaser = loaderLaser.getTexture();
               textureLaser.setBoundaryModeS(Texture.WRAP);
               textureLaser.setBoundaryModeT(Texture.WRAP);
               textureLaser.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
               TextureAttributes texAttrLaser = new TextureAttributes();
               texAttrLaser.setTextureMode(TextureAttributes.BLEND);
               TransparencyAttributes transparLaser = new TransparencyAttributes();
               transparLaser.setTransparencyMode (transparLaser.BLENDED);
               transparLaser.setTransparency (0.5f);
               apLaser.setTransparencyAttributes (transparLaser);
               apLaser.setTexture(textureLaser);
               apLaser.setTextureAttributes(texAttrLaser);
            }
             apLaser.setMaterial(new Material(new Color3f(0.4f,0.7f,0.9f), new Color3f(0.4f,0.7f,0.9f), new Color3f(0.4f,0.7f,0.9f), new Color3f(0.4f, 0.7f, 0.9f), 1f));
             int primflagsLaser = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        laser = new Cylinder(0.007f,1.8f, primflagsLaser, apLaser);
            Transform3D transLaser = new Transform3D();
            if(horizontal == true)
            {
                transLaser.rotX(Math.toRadians(90));
                transLaser.rotZ(Math.toRadians(90));
            }
            transLaser.setTranslation(new Vector3f(xloc,yloc,zloc));
            transrootLaser = new TransformGroup(transLaser);
            transrootLaser.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            transrootLaser.addChild(laser);
            
            repeat = new Timer( 25, this);
            repeat.start();
    }
    
    public void actionPerformed ( ActionEvent event )
    {
        if (event.getSource() == repeat)
        {
            Transform3D transLaser = new Transform3D();
            if(zloc >= 2.75f || zloc <= -2.75f)
                faktor = -faktor;
            zloc += faktor;
            if(horizontal == true)
            {
                transLaser.rotX(Math.toRadians(90));
                transLaser.rotZ(Math.toRadians(90));
            }
            transLaser.setTranslation(new Vector3f(xloc,yloc,zloc));
            transrootLaser.setTransform(transLaser);
        }
    }
}
