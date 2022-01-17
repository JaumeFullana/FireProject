package proyectofuego;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Jaume
 */
public class Viewer extends Canvas implements Runnable{
    
    private JFrame frame;
    private Flame fire;
    private boolean running;
    private int fps;
    private int skip_ticks;
    private Image defaultBackground;
    private Image chosenBackground;
    
    public Viewer() {
    }

    public Viewer(Flame fire, JFrame frame) {
        this.fire = fire;
        this.frame = frame;
        this.getActualSize();
        this.defaultBackground=new ImageIcon(this.getClass().getResource("Images\\fondoNegro.png")).getImage();
    }
    
    public Flame getFire() {
        return fire;
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public void setChosenBackground(Image chosenBackground) {
        this.chosenBackground = chosenBackground;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    /**
     * Metodo que mientras el atributo running sea true, va llamando al metodo 
     * paintFire en bucle. En cada iteracion del bucle hay un sleep que dura los
     * milisgundos especificados en skip_tics.
     */
    private void chargeBackground(){
        while(running){
            if(this.fire.getMapaTemperatura()!=null){
                try {
                    Thread.sleep(skip_ticks);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                this.paintFire();
            }
        }
    }    
    
    /**
     * Metodo que coge la altura i el amplio del frame principal i lo asigna como
     * tamaño a las matrices del mapaTemperatura.
     */
    private void getActualSize(){
        int altura=this.getFrame().getHeight();
        int amplada=this.getFrame().getWidth();
        this.fire.setMapaTemperatura(new int[altura][amplada]);
        this.fire.setMapaTemperatura2(new int[altura][amplada]);
    }   
    
    /**
     * Metodo que sirve para pintar la Flame en el canvas. Utiliza una bufferStrategy
     * para evitar el flickering. Primero pinta en el fondo la imagen seleccionada, si
     * no hay ninguna pinta la default, que es un fondo negro, y luego pinta el fuego.
     */
    private void paintFire(){
        BufferStrategy bs;
        bs=this.getBufferStrategy();
        if(bs==null){
            System.out.println("ERROR, kgd");
            this.createBufferStrategy(3);
        }else if(this.getParent()==null){
            System.out.println("ERROR, kgd 2");
        } 
        else {
            Graphics g=bs.getDrawGraphics();
            if (chosenBackground==null){
                g.drawImage(this.defaultBackground, 0,0, this.getWidth(), this.getHeight(), null);
            } else {
                g.drawImage(this.chosenBackground, 0,0, this.getWidth(), this.getHeight(), null);
            }
            g.drawImage(this.fire, 0,0, this.getWidth(), this.getHeight(), null);
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
