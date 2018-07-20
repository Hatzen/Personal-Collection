import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

import java.awt.Robot;

import java.awt.image.MemoryImageSource;
import java.awt.Cursor;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;

import java.util.ArrayList;

import java.io.FileInputStream;

public class Game implements ActionListener
{
    Canvas3D canvas3d;
    TransformGroup view;
    BranchGroup branchgroup;
    Timer repeat;
    WASDBehavior wasd;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    int grafik;
    int score;
    int max;
    ArrayList<Laser> laser;
    Font textType;
    
    Texture textureWand1;
    TextureAttributes texAttrWand1;
    Texture textureWand2;
    TextureAttributes texAttrWand2;
    Texture textureWand3;
    TextureAttributes texAttrWand3;
    Texture textureWand4;
    TextureAttributes texAttrWand4;
    Texture textureBoden1;
    TextureAttributes texAttrBoden1;
    Texture textureBoden2;
    TextureAttributes texAttrBoden2;
    
    TransformGroup objScale;

        public Game (int grafik) {
            this.grafik = grafik;
            
            branchgroup = new BranchGroup();
            
            objScale = new TransformGroup();
            objScale.setCapability(BranchGroup.ALLOW_DETACH);
            objScale.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
            objScale.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
            Transform3D t3dScale = new Transform3D();
            t3dScale.setScale(1.5);
            objScale.setTransform(t3dScale);
            branchgroup.addChild(objScale);
            
            try
            {
                textType = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream("res/other/BrightYoungThings.ttf"));
            }
            catch(Exception ex)
            {
                System.out.println("ERROR CANT LOAD FONT");
            }
            score = 0;
            GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            canvas3d = new Canvas3D (config)
            {
                private static final long serialVersionUID = 7144426579917281131L;
        
                public void postRender()
                {
                    this.getGraphics2D().setFont(textType.deriveFont((float) 40));
                    this.getGraphics2D().setColor(new Color(210,210,210));
                    this.getGraphics2D().drawString("" + score,canvas3d.getWidth()-100,canvas3d.getHeight()-40);
                    this.getGraphics2D().flush(false);
                }
            };

            SimpleUniverse universe = new SimpleUniverse(canvas3d);
            ViewingPlatform vp = universe.getViewingPlatform();
            vp.setNominalViewingTransform();
            
            view=vp.getViewPlatformTransform();
            Transform3D t3d=new Transform3D();  
            view.getTransform(t3d);  
            
            if(grafik == 3)
                canvas3d.getView().setSceneAntialiasingEnable(true);
            
            //wand links
            if(grafik > 1)
            {
               TextureLoader loaderWand1 = new TextureLoader("res/images/glas.png", "LUMINANCE", new Container());
               textureWand1 = loaderWand1.getTexture();
               textureWand1.setBoundaryModeS(Texture.WRAP);
               textureWand1.setBoundaryModeT(Texture.WRAP);
               textureWand1.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
               texAttrWand1 = new TextureAttributes();
               texAttrWand1.setTextureMode(TextureAttributes.MODULATE);
            }
               Appearance apWand1 = new Appearance();
            if(grafik > 1)
            {
               apWand1.setTexture(textureWand1);
               apWand1.setTextureAttributes(texAttrWand1);
            }
               apWand1.setMaterial(new Material(new Color3f(0.5f, 0.5f, 0.5f), new Color3f(0.5f, 0.5f, 0.5f), new Color3f(0.5f, 0.5f, 0.5f), new Color3f(0.5f, 0.5f, 0.5f), 1.0f));
               int primflagsWand1 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
            Box wand1 = new Box(0f,0.45f,3f, primflagsWand1, apWand1);
               Transform3D transWand1 = new Transform3D();
               transWand1.setTranslation(new Vector3f(0.9f,-0.45f,0));
               TransformGroup transrootWand1 = new TransformGroup(transWand1);
               transrootWand1.addChild(wand1);
               
            //wand rechts
            if(grafik > 1)
            {
               TextureLoader loaderWand2 = new TextureLoader("res/images/glas.png", "LUMINANCE", new Container());
               textureWand2 = loaderWand2.getTexture();
               textureWand2.setBoundaryModeS(Texture.WRAP);
               textureWand2.setBoundaryModeT(Texture.WRAP);
               textureWand2.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
               texAttrWand2 = new TextureAttributes();
               texAttrWand2.setTextureMode(TextureAttributes.MODULATE);
            }
               Appearance apWand2 = new Appearance();
            if(grafik > 1)
            {
               apWand2.setTexture(textureWand2);
               apWand2.setTextureAttributes(texAttrWand2);
            }
               apWand2.setMaterial(new Material(new Color3f(0.5f, 0.5f, 0.5f), new Color3f(0.5f, 0.5f, 0.5f), new Color3f(0.5f, 0.5f, 0.5f), new Color3f(0.5f, 0.5f, 0.5f), 1.0f));
               int primflagsWand2 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
            Box wand2 = new Box(0f,0.45f,3f, primflagsWand2, apWand2);
               Transform3D transWand2 = new Transform3D();
               transWand2.setTranslation(new Vector3f(-0.9f,-0.45f,0));
               TransformGroup transrootWand2 = new TransformGroup(transWand2);
               transrootWand2.addChild(wand2);
               
            //wand hinten
            if(grafik > 1)
            {
               TextureLoader loaderWand3 = new TextureLoader("res/images/wand.png", "LUMINANCE", new Container());
               textureWand3 = loaderWand3.getTexture();
               textureWand3.setBoundaryModeS(Texture.WRAP);
               textureWand3.setBoundaryModeT(Texture.WRAP);
               textureWand3.setBoundaryColor( new Color4f( 0.0f, 0f, 0.0f, 0.0f ) );
               texAttrWand3 = new TextureAttributes();
               texAttrWand3.setTextureMode(TextureAttributes.MODULATE);
            }
               Appearance apWand3 = new Appearance();
            if(grafik > 1)
            {
               apWand3.setTexture(textureWand3);
               apWand3.setTextureAttributes(texAttrWand3);
            }
               apWand3.setMaterial(new Material(new Color3f(0.25f, 0.25f, 0.25f), new Color3f(0.25f, 0.25f, 0.25f), new Color3f(0.25f, 0.25f, 0.25f), new Color3f(0.25f, 0.25f, 0.25f),0f));
               int primflagsWand3 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
            Box wand3 = new Box(0.9f,0.45f,0f, primflagsWand3, apWand3);
               Transform3D transWand3 = new Transform3D();
               transWand3.setTranslation(new Vector3f(0,-0.45f,3f));
               TransformGroup transrootWand3 = new TransformGroup(transWand3);
               transrootWand3.addChild(wand3);
               
            //wand vorne
            if(grafik > 1)
            {
               TextureLoader loaderWand4 = new TextureLoader("res/images/wand.png", "LUMINANCE", new Container());
               textureWand4 = loaderWand4.getTexture();
               textureWand4.setBoundaryModeS(Texture.WRAP);
               textureWand4.setBoundaryModeT(Texture.WRAP);
               textureWand4.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
               texAttrWand4 = new TextureAttributes();
               texAttrWand4.setTextureMode(TextureAttributes.MODULATE);
            }
               Appearance apWand4 = new Appearance();
            if(grafik > 1)
            {
               apWand4.setTexture(textureWand4);
               apWand4.setTextureAttributes(texAttrWand3);
            }
               apWand4.setMaterial(new Material(new Color3f(0.25f, 0.25f, 0.25f), new Color3f(0.25f, 0.25f, 0.25f), new Color3f(0.25f, 0.25f, 0.25f), new Color3f(0.25f, 0.25f, 0.25f), 1.0f));
               int primflagsWand4 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
            Box wand4 = new Box(0.9f,0.45f,0f, primflagsWand4, apWand4);
               Transform3D transWand4 = new Transform3D();
               transWand4.setTranslation(new Vector3f(0,-0.45f,-3f));
               TransformGroup transrootWand4 = new TransformGroup(transWand4);
               transrootWand4.addChild(wand4);
            
            //boden unten
            if(grafik > 1)
            {
               TextureLoader loaderBoden1 = new TextureLoader("res/images/boden.png", "LUMINANCE", new Container());
               textureBoden1 = loaderBoden1.getTexture();
               textureBoden1.setBoundaryModeS(Texture.WRAP);
               textureBoden1.setBoundaryModeT(Texture.WRAP);
               textureBoden1.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
               texAttrBoden1 = new TextureAttributes();
               texAttrBoden1.setTextureMode(TextureAttributes.MODULATE);
            }
               Appearance apBoden1 = new Appearance();
            if(grafik > 1)
            {
               apBoden1.setTexture(textureBoden1);
               apBoden1.setTextureAttributes(texAttrBoden1);
            }
               apBoden1.setMaterial(new Material(new Color3f(0.75f, 0.75f, 0.75f), new Color3f(0.75f, 0.75f, 0.75f), new Color3f(0.75f, 0.75f, 0.75f), new Color3f(0.75f, 0.75f, 0.75f), 1f));
               int primflagsBoden1 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
            Box boden1 = new Box(0.9f,0f,3f, primflagsBoden1, apBoden1);
               Transform3D transBoden1 = new Transform3D();
               transBoden1.setTranslation(new Vector3f(0,0f,0));
               TransformGroup transrootBoden1 = new TransformGroup(transBoden1);
               transrootBoden1.addChild(boden1);
               
            //boden oben (decke)
            if(grafik > 1)
            {
               TextureLoader loaderBoden2 = new TextureLoader("res/images/decke.png", "LUMINANCE", new Container());
               textureBoden2 = loaderBoden2.getTexture();
               textureBoden2.setBoundaryModeS(Texture.WRAP);
               textureBoden2.setBoundaryModeT(Texture.WRAP);
               textureBoden2.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
               texAttrBoden2 = new TextureAttributes();
               texAttrBoden2.setTextureMode(TextureAttributes.MODULATE);
            }
               Appearance apBoden2 = new Appearance();
            if(grafik > 1)
            {
               apBoden2.setTexture(textureBoden2);
               apBoden2.setTextureAttributes(texAttrBoden2);
            }
               apBoden2.setMaterial(new Material(new Color3f(0.75f, 0.75f, 0.75f), new Color3f(0.75f, 0.75f, 0.75f), new Color3f(0.75f, 0.75f, 0.75f), new Color3f(0.75f, 0.75f, 0.75f), 1f));
               int primflagsBoden2 = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
            Box boden2 = new Box(0.9f,0f,3f, primflagsBoden2, apBoden2);
               Transform3D transBoden2 = new Transform3D();
               transBoden2.setTranslation(new Vector3f(0,-0.9f,0));
               TransformGroup transrootBoden2 = new TransformGroup(transBoden2);
               transrootBoden2.addChild(boden2);
               
            if(grafik > 1)
            {
               //rahmen
               for(float i = -3 ;i < 3 ; i++)
               {
                    Appearance apRahmen = new Appearance();
                       TextureLoader loaderRahmen = new TextureLoader("res/images/rahmen.jpg", "LUMINANCE", new Container());
                       Texture textureRahmen = loaderRahmen.getTexture();
                       textureRahmen.setBoundaryModeS(Texture.WRAP);
                       textureRahmen.setBoundaryModeT(Texture.WRAP);
                       textureRahmen.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
                       TextureAttributes texAttrRahmen = new TextureAttributes();
                       texAttrRahmen.setTextureMode(TextureAttributes.MODULATE);
                       apRahmen.setTexture(textureRahmen);
                       apRahmen.setTextureAttributes(texAttrRahmen);
                       apRahmen.setMaterial(new Material(new Color3f(1f, 1f, 1f), new Color3f(1f, 1f, 1f), new Color3f(1f, 1f, 1f), new Color3f(1f, 1f, 1f), 1.0f));
                       int primflagsRahmen = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
                    Box rahmen = new Box(0.01f,0.45f,0.1f, primflagsRahmen, apRahmen);
                       Transform3D transRahmen = new Transform3D();
                       transRahmen.setTranslation(new Vector3f(-0.9f,-0.45f,i+0.5f));
                       TransformGroup transrootRahmen = new TransformGroup(transRahmen);
                       transrootRahmen.addChild(rahmen);
                       
                       objScale.addChild(transrootRahmen);
               }
               //rahmen
               for(float i = -3 ;i < 3 ; i++)
               {
                       Appearance apRahmen = new Appearance();
                       TextureLoader loaderRahmen = new TextureLoader("res/images/rahmen.jpg", "LUMINANCE", new Container());
                       Texture textureRahmen = loaderRahmen.getTexture();
                       textureRahmen.setBoundaryModeS(Texture.WRAP);
                       textureRahmen.setBoundaryModeT(Texture.WRAP);
                       textureRahmen.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
                       TextureAttributes texAttrRahmen = new TextureAttributes();
                       texAttrRahmen.setTextureMode(TextureAttributes.MODULATE);
                       apRahmen.setTexture(textureRahmen);
                       apRahmen.setTextureAttributes(texAttrRahmen);
                       apRahmen.setMaterial(new Material(new Color3f(1f, 1f, 1f), new Color3f(1f, 1f, 1f), new Color3f(1f, 1f, 1f), new Color3f(1f, 1f, 1f), 1.0f));
                       int primflagsRahmen = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
                    Box rahmen = new Box(0.01f,0.45f,0.1f, primflagsRahmen, apRahmen);
                       Transform3D transRahmen = new Transform3D();
                       transRahmen.setTranslation(new Vector3f(0.9f,-0.45f,i+0.5f));
                       TransformGroup transrootRahmen = new TransformGroup(transRahmen);
                       transrootRahmen.addChild(rahmen);
                       
                       objScale.addChild(transrootRahmen);
               }
            }
            
            wasd = new WASDBehavior(vp.getViewPlatformTransform(),transrootWand1, new Vector3d(0, 1, 0));
            wasd.setSchedulingBounds(new BoundingSphere());
            wasd.setRotationFactor(-0.003);
            wasd.setTranslationFactor(0.1);
            wasd.setEyeParameters(new Point3d(0, -0.85, -1.5), new Point3d(0, -0.6, 0), new Vector3d(0, -1, 1));
            
            objScale.addChild(wasd);
            objScale.addChild(transrootBoden1);
            objScale.addChild(transrootBoden2);
            objScale.addChild(transrootWand1);
            objScale.addChild(transrootWand2);
            objScale.addChild(transrootWand3);
            objScale.addChild(transrootWand4);
            
            canvas3d.preRender();
            branchgroup.compile();
            universe.addBranchGraph(branchgroup);
            
            laser = new ArrayList<Laser>();
            max = 2;
            score = 0;
            
            repeat = new Timer( 25, this);
            repeat.start();
        }
        
        public void actionPerformed ( ActionEvent event )
        {
            if (event.getSource() == repeat)
            {
                canvas3d.requestFocus();
                int x=(int)MouseInfo.getPointerInfo().getLocation().getX();
                int y=(int)MouseInfo.getPointerInfo().getLocation().getY();
                if(screenSize.width/2+200 < x || screenSize.width/2-200 > x || screenSize.height/2-200 > y || screenSize.height/2+200 < y)
                {
                    try
                    {
                        Robot rob = new Robot();
                        rob.mouseMove(screenSize.width/2,screenSize.height/2);
                                
                        wasd.m_nLastX = screenSize.width/2;
                        wasd.m_nLastY = screenSize.height/2;
                    }
                    catch(Exception ex)
                    {}
                }
                if(Math.round(Math.random()*100+1) == 1)
                {
                    if(laser.size() < max)
                    {
                        boolean horizontal = false;
                        if(Math.round(Math.random()) == 1)
                            horizontal = true;
                        laser.add(0, new Laser(horizontal, (int) Math.round(Math.random()*2+1), grafik));
                        
                        BranchGroup lasergroup = new BranchGroup();
                        lasergroup.setCapability(BranchGroup.ALLOW_DETACH);
                        lasergroup.addChild(laser.get(0).transrootLaser);
                        objScale.addChild(lasergroup);
                    }
                }
                if(Math.round(Math.random()*300+1) == 1 && laser.size() != 0)
                {
                    objScale.removeChild(laser.get(0).transrootLaser.getParent());
                    laser.remove(0);
                }
                score += 2;
                if(max < 10)
                    max = score/1000+2;
            }
        }
}