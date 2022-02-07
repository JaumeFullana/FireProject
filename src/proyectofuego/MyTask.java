package proyectofuego;

import java.awt.Color;
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
        

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.put("nimbusBase", new Color(0,0,0));
                    //javax.swing.UIManager.put("nimbusBlueGrey", new Color(255,190,0));
                    javax.swing.UIManager.put("control", new Color(0,0,0));
                    javax.swing.UIManager.put("OptionPane.messageForeground", Color.white);
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
        MyTask tasca=new MyTask();
        tasca.createFire();
        
    }
    
    private FireControlPanel fireControlPanel;
    private ConvolutionControlPanel convolutionControlPanel;
    private GeneralControlPanel generalControlPanel;
    private Viewer view;
    private FlamePalette flamePalette;
    private Flame fire;
    private ConvolutionFilter convolutionFilter;

    public MyTask() {
        initComponents();
    }

    public ConvolutionFilter getConvolutionFilter() {
        return convolutionFilter;
    }

    public Flame getFire() {
        return fire;
    }
    
    public FlamePalette getFlamePalette() {
        return flamePalette;
    }

    public GeneralControlPanel getGeneralControlPanel() {
        return generalControlPanel;
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
        this.add(this.generalControlPanel,c);
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
        this.view=new Viewer(this);
        this.fireControlPanel=new FireControlPanel();
        this.fireControlPanel.setMyTask(this);
        this.convolutionControlPanel = new ConvolutionControlPanel();
        this.convolutionControlPanel.setMyTask(this);
        this.generalControlPanel=new GeneralControlPanel(this.fireControlPanel, this.convolutionControlPanel);
        this.convolutionFilter=new ConvolutionFilter(this.convolutionControlPanel.getInfoKernelValues());
        this.addPanels();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void createFire(){
        this.fire=new Flame(this.getView().getAmplada(),this.getView().getAltura(),Flame.TYPE_4BYTE_ABGR,this);
        fire.setPalette();
        this.view.setFire(fire);
    }
}
