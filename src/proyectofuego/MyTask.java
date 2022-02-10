package proyectofuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

/**
 *
 * @author Jaume
 */
public class MyTask extends JFrame{
    

    public static void main(String[] args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.put("nimbusBase", new Color(0,0,0));
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
        tasca.setTabedPaneSize();
        tasca.fireControlPanel.initViewThread();
    }
    
    private FireControlPanel fireControlPanel;
    private ConvolutionControlPanel convolutionControlPanel;
    private GeneralControlPanel generalControlPanel;
    private Viewer viewer;
    private FlamePalette flamePalette;
    private Flame flame;
    private ConvolutionFilter convolutionFilter;

    public MyTask() {
        initComponents();
    }
    
    /**
     * Normal getter.
     * @return convolutionFilter
     */
    public ConvolutionFilter getConvolutionFilter() {
        return convolutionFilter;
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
     * @return flamePalette
     */
    public FlamePalette getFlamePalette() {
        return flamePalette;
    }

    /**
     * Normal getter.
     * @return generalControlPanel
     */
    public GeneralControlPanel getGeneralControlPanel() {
        return generalControlPanel;
    }
    
    /**
     * Normal getter.
     * @return viewer
     */
    public Viewer getViewer() {
        return viewer;
    }

    /**
     * Normal Setter
     * @param flamePalette 
     */
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
        this.add(this.viewer,c);
    }
    
    /**
     * Inicializa la varaible flame, le añade la paleta y añade el flame al viewer.
     */
    private void createFire(){
        this.flame=new Flame(this.getViewer().getAnchura(),this.getViewer().getAltura(),Flame.TYPE_4BYTE_ABGR,this);
        flame.setPalette();
        this.viewer.setFlame(flame);
    }    
    
    /**
     * Metodo que assigna todos los valores que necesita el JFrame. Aqui dentro
     * se inizializan el fuego, el viewer y el control panel.
     */
    private void initComponents() {
        this.setSize(800,800);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.viewer=new Viewer(this);
        this.fireControlPanel=new FireControlPanel();
        this.fireControlPanel.setMyTask(this);
        this.convolutionControlPanel = new ConvolutionControlPanel();
        this.convolutionControlPanel.setMyTask(this);
        this.generalControlPanel=new GeneralControlPanel(this,this.fireControlPanel, this.convolutionControlPanel);
        this.convolutionFilter=new ConvolutionFilter(this.convolutionControlPanel.getInfoKernelValues());
        this.addPanels();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Da un tamaño minimo al TabbedPane del generalControlPanel
     */
    private void setTabedPaneSize(){
        this.generalControlPanel.getTabbedPane().setMinimumSize(new Dimension(this.fireControlPanel.getWidth()+5,this.fireControlPanel.getHeight()));
    }
}
