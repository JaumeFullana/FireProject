package proyectofuego;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

/**
 *
 * @author Jaume
 */
public class MyTask extends JFrame{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        MyTask tasca=new MyTask();
    }
    
    private ControlPanel panelDeControl;
    private Viewer view;
    private FlamePalette flamePalette;
    private Flame fire;

    public MyTask() {
        initComponents();
    }

    public Flame getFire() {
        return fire;
    }
    
    public FlamePalette getFlamePalette() {
        return flamePalette;
    }
    
    public Viewer getView() {
        return view;
    }

    public void setFlamePalette(FlamePalette flamePalette) {
        this.flamePalette = flamePalette;
    }
    
    /**
     * Metodo que asigna al JFrame el layout GridBagLayout y añade el panel de control
     * y el viewer.
     */
    private void addPanels(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;
        c.fill=GridBagConstraints.BOTH;
        c.weightx=0.001;
        c.weighty=1;
        this.add(this.panelDeControl,c);
        c.weightx=0.999;
        c.gridx=1;
        this.add(this.view,c);
    }
    
    /**
     * Metodo que assigna todos los valores que necesita el JFrame. Aqui dentro
     * se inizializan el fuego, el viewer y el control panel.
     */
    private void initComponents() {
        this.setSize(800,800);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fire=new Flame(this.getHeight(),this.getWidth(),Flame.TYPE_4BYTE_ABGR,this);
        fire.setPalette();
        this.view=new Viewer(fire, this);
        this.panelDeControl=new ControlPanel();
        this.panelDeControl.setMyTask(this);
        this.addPanels();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
