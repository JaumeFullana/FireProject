package proyectofuego;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Jaume
 */
public class Viewer extends Canvas implements Runnable{
    
    private MyTask myTask;
    private Flame fire;
    private boolean running;
    private int fps;
    private int skip_ticks;
    private BufferedImage defaultBackground;
    private BufferedImage chosenBackground;
    private BufferedImage convolutedImage;
    private boolean usingConvulatedImage;
    private boolean usingFireBackground;
    private int altura;
    private int amplada;
    private boolean stoped;
    
    public Viewer() {
    }

    public Viewer(MyTask myTask) {
        this.myTask = myTask;
        this.getActualSize();
        ImageIcon iconBackground=new ImageIcon(this.getClass().getResource("Images\\fondoNegro.png"));
        this.defaultBackground=new BufferedImage(iconBackground.getIconWidth(),
                iconBackground.getIconHeight(),BufferedImage.TYPE_INT_RGB);
        this.usingConvulatedImage=false;
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getY()<(int)(getHeight()*0.25)){
                    if (e.getX()<(int)(getWidth()*0.33)){
                        usingConvulatedImage=false;
                        usingFireBackground=false;
                    }
                    else if (e.getX()>(int)(getWidth()*0.66)){
                        usingConvulatedImage=false;
                        usingFireBackground=true;
                    }
                    else {
                        usingConvulatedImage=true;
                        usingFireBackground=false;
                    }
                }
            }
            
        });
    }

    public int getAltura() {
        return altura;
    }

    public int getAmplada() {
        return amplada;
    }
    
    public Flame getFire() {
        return fire;
    }
    
    public JFrame getFrame() {
        return myTask;
    }

    public BufferedImage getChosenBackground() {
        return chosenBackground;
    }

    public BufferedImage getConvolutedImage() {
        return convolutedImage;
    }

    public boolean isRunning() {
        return running;
    }

    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.convolutedImage = convolutedImage;
    }
    
    public void setChosenBackground(BufferedImage chosenBackground) {
        this.chosenBackground = chosenBackground;
    }

    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }

    public void setFire(Flame fire) {
        this.fire = fire;
        this.fire.setMapaTemperatura(new int[altura][amplada]);
        this.fire.setMapaTemperatura2(new int[altura][amplada]);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setUsingConvulatedImage(boolean usingConvulatedImage) {
        this.usingConvulatedImage = usingConvulatedImage;
    }    
    
    /**
     * Metodo que mientras el atributo running sea true, va llamando al metodo 
     * paintFire en bucle. En cada iteracion del bucle hay un sleep que dura los
     * milisgundos especificados en skip_tics.
     */
    private void chargeBackground(){
        while(running){
            if(this.fire.getMapaTemperatura()!=null){
                this.paintFire();
                if(this.myTask.getGeneralControlPanel().getTabbedPane().getSelectedIndex()==1){
                    this.myTask.getGeneralControlPanel().getConvolutionControlPanel().paintPreviewConvolution();
                }
            }
            try {
                Thread.sleep(skip_ticks);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }    
    
    /**
     * Metodo que coge la altura i el amplio del myTask principal i lo asigna como
 tamaño a las matrices del mapaTemperatura.
     */
    private void getActualSize(){
        altura=this.getFrame().getHeight();
        amplada=this.getFrame().getWidth();
    }   
    
    /**
     * Metodo que sirve para pintar la Flame en el canvas. Utiliza una bufferStrategy
     * para evitar el flickering. Primero pinta en el fondo la imagen seleccionada, si
     * no hay ninguna pinta la default, que es un fondo negro, y luego pinta el fuego.
     */
    public void paintFire(){
        BufferStrategy bs;
        bs=this.getBufferStrategy();
        if(bs==null){
            System.out.println("ERROR, kgd");
            this.createBufferStrategy(3);
            bs=this.getBufferStrategy();
        }
        
        if(this.getParent()==null){
            System.out.println("ERROR, kgd 2");
        } 
        else {
            Graphics g=bs.getDrawGraphics();
            //fondo gran
            if (chosenBackground==null){
                g.drawImage(this.defaultBackground, 0,(int)(this.getHeight()*0.25), this.getWidth(), (int)(this.getHeight()*0.75), null);
            } 
            else {
                if (this.convolutedImage!=null && this.usingConvulatedImage){
                    g.drawImage(this.convolutedImage, 0,(int)(this.getHeight()*0.25), this.getWidth(), (int)(this.getHeight()*0.75), null);
                }
                else if (this.usingFireBackground){
                    g.drawImage(this.defaultBackground, 0,(int)(this.getHeight()*0.25), this.getWidth(), (int)(this.getHeight()*0.75), null);
                }
                else {
                    g.drawImage(this.chosenBackground, 0,(int)(this.getHeight()*0.25), this.getWidth(), (int)(this.getHeight()*0.75), null);
                }
            }
            //fondo petit
            if (chosenBackground==null){
                g.drawImage(this.defaultBackground, 0,0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
            } 
            else {
                g.drawImage(this.chosenBackground, 0,0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
            }
            //fondo convolucionat
            if (this.convolutedImage==null){
                g.drawImage(this.defaultBackground, (int)(this.getWidth()*0.33),0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
            } 
            else {
                g.drawImage(this.convolutedImage, (int)(this.getWidth()*0.33),0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
            }
            //fondo foc totsol
            g.drawImage(this.defaultBackground, (int)(this.getWidth()*0.65),0, (int)(this.getWidth()*0.35), (int)(this.getHeight()*0.25), null);
            
            if (!stoped){
                g.drawImage(this.fire, (int)(this.getWidth()*0.66),0, (int)(this.getWidth()*0.34), (int)(this.getHeight()*0.25), null);
                g.drawImage(this.fire, 0,(int)(this.getHeight()*0.25), this.getWidth(), (int)(this.getHeight()*0.75), null);
            }
            
            g.dispose();
            bs.show();
        }
    }

    /**
     * Metodo que llama al metodo chargeBackground. Este metodo es heredado de la
     * interfaz runnable y empieza a correr cuando llamamos al metodo start en un 
     * Thread en el cual hemos metido un objeto de esta clase.
     */
    @Override
    public void run() {
        this.chargeBackground();
    }
    
    /**
     * Metodo que asigna los fps, frames por segundo, i los skip_ticks, que es 
     * el tiempo de pausa que tendra el bucle principal de la clase que ira pintando el canvas.
     * @param rate int, frames por segundo que se veran en el programa.
     */
    public void setRate(int rate){
        this.fps = rate;
        this.skip_ticks=1000/rate;
    }
}
