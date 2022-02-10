package proyectofuego;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private Flame flame;
    private boolean running;
    private int fps;
    private int skip_ticks;
    private BufferedImage defaultBackground;
    private BufferedImage chosenBackground;
    private BufferedImage convolutedImage;
    private boolean usingConvulatedImage;
    private boolean usingFireBackground;
    private int altura;
    private int anchura;
    private boolean stoped;

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
    
    /**
     * Normal getter.
     * @return altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Normal getter.
     * @return anchura
     */
    public int getAnchura() {
        return anchura;
    }
    
    /**
     * Normal getter.
     * @return flame
     */
    public Flame getFlame() {
        return flame;
    }
    
    /**
     * Normal getter.
     * @return myTask
     */
    public JFrame getMyTask() {
        return myTask;
    }

    /**
     * Normal getter.
     * @return chosenBackground
     */
    public BufferedImage getChosenBackground() {
        return chosenBackground;
    }

    /**
     * Normal getter.
     * @return convolutedImage
     */
    public BufferedImage getConvolutedImage() {
        return convolutedImage;
    }

    /**
     * Normal getter.
     * @return running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Normal Setter.
     * @param convolutedImage 
     */
    public void setConvolutedImage(BufferedImage convolutedImage) {
        this.convolutedImage = convolutedImage;
    }
    
    /**
     * Normal Setter.
     * @param chosenBackground 
     */
    public void setChosenBackground(BufferedImage chosenBackground) {
        this.chosenBackground = chosenBackground;
    }
    
    /**
     * Normal Setter.
     * @param stoped 
     */
    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }

    /**
     * Setter para añadir una nueva flame que ademas inizializa los mapaTemperatura 
     * de esta, cogiendo los atributos altura y anchura para eso.
     * @param flame 
     */
    public void setFlame(Flame flame) {
        this.flame = flame;
        this.flame.setMapaTemperatura(new int[altura][anchura]);
        this.flame.setMapaTemperatura2(new int[altura][anchura]);
    }

    /**
     * Normal Setter.
     * @param running 
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Normal Setter.
     * @param usingConvulatedImage 
     */
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
            if(this.flame.getMapaTemperatura()!=null){
                this.paintViewer();
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
     * tamaño a las matrices del mapaTemperatura.
     */
    private void getActualSize(){
        altura=this.getMyTask().getHeight();
        anchura=this.getMyTask().getWidth();
    }   
    
    /**
     * Pinta el fuego y las diferentes imagenes. Primero pinta en el fondo las 
     * imagenes seleccionadas, si no hay ninguna pinta la default, que es un fondo 
     * negro, y luego pinta el fuego.
     * @param bs BufferStrategy
     */
    private void paintImages(BufferStrategy bs) {
        Graphics g=bs.getDrawGraphics();
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
        
        if (chosenBackground==null){
            g.drawImage(this.defaultBackground, 0,0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
        }
        else {
            g.drawImage(this.chosenBackground, 0,0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
        }
        
        if (this.convolutedImage==null){
            g.drawImage(this.defaultBackground, (int)(this.getWidth()*0.33),0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
        }
        else {
            g.drawImage(this.convolutedImage, (int)(this.getWidth()*0.33),0, (int)(this.getWidth()*0.33), (int)(this.getHeight()*0.25), null);
        }
        g.drawImage(this.defaultBackground, (int)(this.getWidth()*0.65),0, (int)(this.getWidth()*0.35), (int)(this.getHeight()*0.25), null);
        
        if (!stoped){
            g.drawImage(this.flame, (int)(this.getWidth()*0.66),0, (int)(this.getWidth()*0.34), (int)(this.getHeight()*0.25), null);
            g.drawImage(this.flame, 0,(int)(this.getHeight()*0.25), this.getWidth(), (int)(this.getHeight()*0.75), null);
        }
        g.dispose();
        bs.show();
    }
    
    /**
     * Metodo que sirve para pintar la Flame y las imagenes en el canvas. Utiliza 
     * una bufferStrategy para evitar el flickering. 
     */
    private void paintViewer(){
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
            paintImages(bs);
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
        this.skip_ticks=1000/this.fps;
    }
}
