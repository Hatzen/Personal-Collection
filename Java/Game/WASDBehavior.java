/*
 * Created on 22.11.2009
 */

 

import java.awt.AWTEvent;
import java.awt.event.*;
import java.util.*;

import javax.media.j3d.*;
import javax.vecmath.*;

/**
 * A Behavior class to add a more natural camera movement to 
 * Java3D applications. It allows typical game-like translation
 * using the WASD keys and rotation using the mouse and the Q, E
 * keys.
 * 
 * To use this class, just add something like
 * 
 * ViewingPlatform vp = m_Universe.getViewingPlatform();
 * TransformGroup tgVP = vp.getViewPlatformTransform();
 * 
 * WASDBehavior wasd = new WASDBehavior(tgVP, new Vector3d(0, 1, 0));
 * wasd.setSchedulingBounds(new BoundingSphere())
 * wasd.setEyeParameters(new Point3d(0, 0.6, 0.6), 
 *                       new Point3d(0, 0, 0), 
 *                       new Vector3d(0, -1, 1));
 * scene.addChild(wasd);
 * 
 * to your scene graph construction.
 * 
 * Terms of use:
 * 
 * This code may be used in compiled form in any way you desire. This
 * file may be redistributed unmodified by any means PROVIDING it is 
 * not sold for profit without the authors written consent, and 
 * providing that this notice (the complete comment) and the authors
 * name remains intact. 
 * 
 * This file is provided 'as is', without any express or implied 
 * warranty. In no event will the author be held liable for any damage 
 * arising from the use of this software.
 * 
 * If you like this and use it, please leave a comment at
 * 
 *   http://www.maneumann.com
 *   
 * 
 * Version history:
 * 
 * 1.0  - Initial release
 * 1.1  - Added FOCUS_LOST event handling.
 * 
 * Known issues:
 * 
 * - Mouse movement seems gradual, not smooth.
 * 
 * @author Mathias Neumann, Germany
 *         http://www.maneumann.com
 *         Copyright (c) 2009
 */
public class WASDBehavior extends Behavior
{
    // The view platform transform group we control.
    private TransformGroup m_tgView;
    // Translation factor. Controls translation speed.
    private double m_dTransFactor = 0.01;
    // Rotation factor. Controls rotation speed.
    private double m_dRotFactor = 0.01;
    // Left(A)/right(D)-rotation axis.
    private Vector3d m_vecRotLRAxis = null;
    
    // Default wakeup condition.
    private WakeupOr m_WUDefault;
    // Default wakeup condition + Wakeup on elapsed frames.
    private WakeupOr m_WUElapsed;
    
    // Set of pressed keys.
    private HashSet<Integer> m_pressedKeys = new HashSet<Integer>();
    // Last x-coordinate of the mouse.
    public int m_nLastX;
    // Last y-coordinate of the mouse.
    public int m_nLastY;
    // Last frame time.
    private long m_lLastFrameTime = -1;
    
    //eigene
    private boolean jump = false;
    private boolean ducken = false;
    private int i = 0;
    javax.swing.Timer t = new javax.swing.Timer(100, new ActionListener() 
                    {
                       public void actionPerformed(ActionEvent ae) {}
                    });
    // Transform generation data.
    
    // Eye point.
    private Point3d m_ptEye;
    // Lookat point.
    private Point3d m_ptLookat;
    // Up vector. Normalized.
    private Vector3d m_vecUp;
    // View direction. Normalized.
    private Vector3d m_vecViewDir;
    // Curent transform object.
    private Transform3D m_tfViewCur = new Transform3D();
    
    /**
     * Constructs the WASD behavior.
     * 
     * @param tgView The transform group of the viewing platform.
     */
    public WASDBehavior(TransformGroup tgView)
    {
        this(tgView,null, null);
    }
    
    /**
     * Constructs the WASD behavior.
     * 
     * @param tgView The transform group of the viewing platform.
     * @param vecRotLRAxis The left(A)/right(D)-rotation axis. Usually
     *                     the up-direction of the world coordinate system
     *                     works well. When you pass null, the internal
     *                     up axis of the viewing platform is used.
     */
    public WASDBehavior(TransformGroup tgView,TransformGroup tgBox, Vector3d vecRotLRAxis)
    {
        m_tgView = tgView;
        
            
        m_WUDefault = new WakeupOr(
                new WakeupCriterion[] {
                        //new WakeupOnViewPlatformEntry(),
                        new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED),
                        new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED),
                        new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED),
                        new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
                        new WakeupOnAWTEvent(KeyEvent.KEY_TYPED),
                        new WakeupOnAWTEvent(FocusEvent.FOCUS_LOST),
                        new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED)
                });
        
        // Use WakeupOnElapsedFrames since WakeupOnElapsedTime seems to
        // sleep sporadically.
        m_WUElapsed = new WakeupOr(
                new WakeupCriterion[] {
                        new WakeupOnViewPlatformEntry(tgBox.getBounds()),
                        new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED),
                        new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED),
                        new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED),
                        new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED),
                        new WakeupOnAWTEvent(KeyEvent.KEY_TYPED),
                        new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED),
                        new WakeupOnAWTEvent(FocusEvent.FOCUS_LOST),
                        new WakeupOnElapsedFrames(0), // every frame
                });
        m_vecRotLRAxis = vecRotLRAxis;

        setEyeParameters(new Point3d(0, 0, 2), new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
    }
    
    /**
     * Sets eye parameters to configure the viewing platform
     * transformation.
     * 
     * @param ptEye Eye position in world space.
     * @param ptLookat Lookat position in world space.
     * @param vecUp Up vector (non-zero). Normalization is done internally.
     */
    public void setEyeParameters(Point3d ptEye, Point3d ptLookat, Vector3d vecUp)
    {
        m_ptEye = ptEye;
        m_ptLookat = ptLookat;
        m_vecUp = vecUp;
        m_vecUp.normalize();
        
        // Get the view direction.
        m_vecViewDir = new Vector3d(m_ptLookat);
        m_vecViewDir.sub(m_ptEye);
        m_vecViewDir.normalize();
        
        updateViewPlatform();
    }
    
    /**
     * Sets a new rotation speed factor. You can invert the
     * rotation directions by passing an inverted value.
     * 
     * @param rotFactor The new factor.
     */
    public void setRotationFactor(double rotFactor)
    {
        m_dRotFactor = rotFactor;
    }
    
    /**
     * Sets a new translation speed factor. You can invert the
     * translation directions by passing an inverted value.
     * 
     * @param transFactor The new factor.
     */
    public void setTranslationFactor(double transFactor)
    {
        m_dTransFactor = transFactor;
    }
    
    /**
     * Applies the current transform.
     */
    private void updateViewPlatform()
    {
        m_tfViewCur.lookAt(m_ptEye, m_ptLookat, m_vecUp);
        m_tfViewCur.invert();
        m_tgView.setTransform(m_tfViewCur);
    }
    

    /* (non-Javadoc)
     * @see javax.media.j3d.Behavior#initialize()
     */
    @Override
    public void initialize()
    {
        wakeupOn(m_WUDefault);
    }

    /* (non-Javadoc)
     * @see javax.media.j3d.Behavior#processStimulus(java.util.Enumeration)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void processStimulus(Enumeration criteria)
    {
        WakeupCriterion wakeup;
        AWTEvent[] events;
        
        // Check all wakeup criteria...
        while(criteria.hasMoreElements()) 
        {
            wakeup = (WakeupCriterion)criteria.nextElement();
          
            if (wakeup instanceof WakeupOnViewPlatformEntry) 
            {
                System.out.println("Funktioniert :)");
            }
            // Handle AWT event. This can be a mouse or a key event.
            if (wakeup instanceof WakeupOnAWTEvent) 
            {    
                // Process all events.
                events = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
                for(AWTEvent e : events)
                {
                    // Do processing of the event in the corresponding methods.
                    if(e instanceof MouseEvent)
                    {
                        MouseEvent eM = (MouseEvent)e;
                        processMouse(eM);
                    }
                    else if(e instanceof KeyEvent)
                    {
                        KeyEvent eK = (KeyEvent)e;
                        int id = eK.getID();
                        int keyCode = eK.getKeyCode();
                        
                        // Process, but do not apply any change (scale = 0).
                        processKey(id, keyCode, 0.0);
                    }
                    else if(e instanceof FocusEvent)
                    {
                        // If we lose the focus, this will stop any movement.
                        processFocusEvent((FocusEvent)e);
                    }
                }
            }
            // Handle elapsed time event. This event is necessary because
            // the interval key pressed events are genereated depends on
            // the operating systems configuration. To ensure smooth
            // movement we listen to these events when a key is pressed.
            else if (wakeup instanceof WakeupOnElapsedFrames) 
            {
                long lOldTime = m_lLastFrameTime;
                m_lLastFrameTime = System.currentTimeMillis();
                
                long lDiff_ns = m_lLastFrameTime - lOldTime;
                
                // Every 40 ms for one "step".
                double dEventScale = lDiff_ns/40.0;
                
                for(int code : m_pressedKeys)
                    processKey(KeyEvent.KEY_PRESSED, code, dEventScale);
            }
        }
        
        // Update the view platform after processing all relevant events.
        updateViewPlatform();
        
        
        // Listen to input events *and* elapsed time events when a key
        // is pressed. Else listen only to input events. 
        if(!m_pressedKeys.isEmpty())
            wakeupOn(m_WUElapsed);
        else
            wakeupOn(m_WUDefault);
    }
    
    /**
     * Processes the given key event.
     * 
     * @param idEvent Event ID.
     * @param keyCode Event key code.
     * @param dEffectScale Controls the magnitude of the effect. Pass 0 for no
     *                     effect on the transformation.
     * @return True if the event was processed, else false.
     */
    boolean processKey(final int idEvent, int keyCode,final double dEffectScale) 
    {
        final Vector3d vecTrans = new Vector3d();
        final Vector3d vecStrafe = new Vector3d();
        vecStrafe.cross(m_vecViewDir, m_vecUp);
        vecStrafe.normalize();
        
        // If the key was released, reset last key entry.
        if(idEvent == KeyEvent.KEY_RELEASED)
        {
            m_pressedKeys.remove(keyCode);
            return true;
        }
        
        if(idEvent != KeyEvent.KEY_PRESSED && idEvent != KeyEvent.KEY_TYPED)
            return false;
        
        if(keyCode != KeyEvent.VK_W && keyCode != KeyEvent.VK_S && 
           keyCode != KeyEvent.VK_A && keyCode != KeyEvent.VK_D && 
           keyCode != KeyEvent.VK_Q && keyCode != KeyEvent.VK_E && 
           keyCode != KeyEvent.VK_SPACE && keyCode != KeyEvent.VK_C)
            return false;
        
        if(keyCode == KeyEvent.VK_Q)
        {
            //addRotationViewDir(m_dRotFactor*dEffectScale);
        }
        else if(keyCode == KeyEvent.VK_E)
        {
            //addRotationViewDir(-m_dRotFactor*dEffectScale);
        }
        else
        {
            
            if(keyCode == KeyEvent.VK_W)
            {
                vecTrans.set(new Vector3d(m_vecViewDir.x, 0 , m_vecViewDir.z));
                vecTrans.scale(m_dTransFactor*dEffectScale);
            }
            else if(keyCode == KeyEvent.VK_S)
            {
                vecTrans.set(new Vector3d(m_vecViewDir.x, 0 , m_vecViewDir.z));
                vecTrans.scale(-m_dTransFactor*dEffectScale);
            }
            else if(keyCode == KeyEvent.VK_A)
            {
                vecTrans.set(new Vector3d(vecStrafe.x, 0 , vecStrafe.z));
                vecTrans.scale(-m_dTransFactor*dEffectScale);
            }
            else if(keyCode == KeyEvent.VK_D)
            {
                vecTrans.set(new Vector3d(vecStrafe.x, 0 , vecStrafe.z));
                vecTrans.scale(m_dTransFactor*dEffectScale);
            }else if(keyCode == KeyEvent.VK_SPACE )
            {
                if(!(t.isRunning()))
                {
                    t = new javax.swing.Timer(100, new ActionListener() 
                    {
                       public void actionPerformed(ActionEvent ae) {
                            if(( i > 19) || jump == true) // !(idEvent == KeyEvent.KEY_PRESSED) &&
                            {
                                jump = true;
                                vecTrans.set(new Vector3d(0,10,0));
                                vecTrans.scale(m_dTransFactor*dEffectScale);
                                i--;
                                System.out.println("" + i);
                                if(i < 1)
                                {
                                    jump = false;
                                    t.stop();
                                }
                            }
                            else
                            {
                                vecTrans.set(new Vector3d(0,-10,0));
                                vecTrans.scale(m_dTransFactor*dEffectScale);
                                i++;
                            }
                                            
                            m_ptEye.add(vecTrans);
                            m_ptLookat.add(vecTrans);
                       }
                    });
                    t.start();
                }
                
//                 if(idEvent == KeyEvent.KEY_RELEASED)
//                 {
//                     jump = false;
//                     vecTrans.set(new Vector3d(0,-1000000000,0));
//                     vecTrans.scale(m_dTransFactor*dEffectScale);
//                 }
            }
            else if(keyCode == KeyEvent.VK_C )
            {
                if(ducken == false)
                {
                    ducken = true;
                    vecTrans.set(new Vector3d(0,1000000,0));
                    vecTrans.scale(m_dTransFactor*dEffectScale);
                }
                if(idEvent == KeyEvent.KEY_RELEASED)
                {
                    ducken = false;
                    vecTrans.set(new Vector3d(0,10,0));
                    vecTrans.scale(m_dTransFactor*dEffectScale);
                }
            }
            m_ptEye.add(vecTrans);
            m_ptLookat.add(vecTrans);
        } 
        
        
        
        m_lLastFrameTime = System.currentTimeMillis();
        m_pressedKeys.add(keyCode);
        
        return true;
    }
    
    /**
     * Processes the mouse event.
     * 
     * @param evt The event.
     * @return True if the event was processed, else false.
     */
    boolean processMouse(MouseEvent evt) 
    {
        int id = evt.getID();
        
        if(id == MouseEvent.MOUSE_MOVED || id == MouseEvent.MOUSE_DRAGGED)
        {
            int x = evt.getX();
            int y = evt.getY();
            
            int dx = x - m_nLastX;
            int dy = y - m_nLastY;
            
            // Ignore large mouse movements.
            if(Math.abs(dy) < 20 && Math.abs(dx) < 20) 
            {                
                addRotationLR(dx*m_dRotFactor);
                addRotationUD(dy*m_dRotFactor);
            }

            m_nLastX = x;
            m_nLastY = y;
            return true;
        }
        else if (id == MouseEvent.MOUSE_PRESSED) 
        {
//             m_nLastX = evt.getX();
//             m_nLastY = evt.getY();
//             return true;
        }
        
        return false;
    }
    
    /**
     * Processes the given focus event. Usually we lost the focus
     * and have to stop any movement.
     * 
     * @param e The focus event.
     */
    private void processFocusEvent(FocusEvent e)
    {
        int id = e.getID();
        if(id == FocusEvent.FOCUS_LOST)
        {
            // Stop movement.
            m_pressedKeys.clear();
        }
    }
    
    /**
     * Applies up(W)/down(S)-rotation. Only internal parameters are
     * updated.
     * 
     * @param dAngle Rotation angle.
     */
    private void addRotationUD(double dAngle)
    {
        // Our rotation axis:
        Vector3d vecAxis = new Vector3d();
        vecAxis.cross(m_vecViewDir, m_vecUp);
        vecAxis.normalize();
        
        addRotationAxis(vecAxis, dAngle);
    }
    
    /**
     * Applies left(A)/right(D)-rotation. Only internal parameters are
     * updated.
     * 
     * @param dAngle Rotation angle.
     */
    private void addRotationLR(double dAngle)
    {
        // Our rotation axis: m_vecRotLRAxis or m_vecUp
        Vector3d vecAxis = m_vecUp;
        if(m_vecRotLRAxis != null)
            vecAxis = m_vecRotLRAxis;
        
        addRotationAxis(vecAxis, dAngle);
    }
    
    /**
     * Applies rotation around the view direction axis. 
     * Only internal parameters are updated.
     * 
     * @param dAngle Rotation angle.
     */
    private void addRotationViewDir(double dAngle)
    {
        // Our rotation axis: m_vecViewDir.
        addRotationAxis(m_vecViewDir, dAngle);
    }
    
    /**
     * Applies rotation around the given axis.
     * 
     * @param vecAxis Rotation axis.
     * @param dAngle Rotation angle.
     */
    private void addRotationAxis(Vector3d vecAxis, double dAngle)
    {
        // Construct rotation matrix.
        Transform3D tfRotate = new Transform3D();
        tfRotate.setRotation(new AxisAngle4d(vecAxis, dAngle));
        tfRotate.invert();
        
        // Rotate vectors.
        tfRotate.transform(m_vecUp);
        m_vecUp.normalize();
        tfRotate.transform(m_vecViewDir);
        m_vecViewDir.normalize();
     
        // Get distance from eye to lookat.
        double dEye2Lookat = m_ptLookat.distance(m_ptEye);
        
        m_ptLookat.x = m_ptEye.x + m_vecViewDir.x * dEye2Lookat;
        m_ptLookat.y = m_ptEye.y + m_vecViewDir.y * dEye2Lookat;
        m_ptLookat.z = m_ptEye.z + m_vecViewDir.z * dEye2Lookat;
    }
}
