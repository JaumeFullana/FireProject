package proyectofuego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jaume
 */
public class GeneralControlPanel extends JPanel implements ActionListener, ChangeListener{
    
    private FireControlPanel fireControlPanel;
    private ConvolutionControlPanel convolutionControlPanel;
    private JTabbedPane tabbedPane;
    private MyTask myTask;
    
    private JLabel labelApp;
    private JLabel labelFondo;
    private JButton botoFondo;
    
    public GeneralControlPanel(MyTask myTask, FireControlPanel fireControlPanel, ConvolutionControlPanel convolutionControlPanel) {
        this.myTask = myTask;
        this.fireControlPanel = fireControlPanel;
        this.convolutionControlPanel = convolutionControlPanel;
        initComponents();
    }
    
    /**
     * Normal getter.
     * @return fireControlPanel
     */
    public FireControlPanel getFireControlPanel() {
        return fireControlPanel;
    }
    
    /**
     * Normal getter.
     * @return convolutionControlPanel
     */
    public ConvolutionControlPanel getConvolutionControlPanel() {
        return convolutionControlPanel;
    }
    
    /**
     * NormalGetter
     * @return tabbedPane
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
    
    /**
     * Metodo que añade el background del viewer. Si el archivo escogido no es un imagen
     * o su tamaño es 0 o menor y por tanto no se ve se asignara valor null para que se 
     * muestre la imagen por defecto, que es un fondo negro.
     */
    private void addBackground(){
        JFileChooser buscadorArchivo = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png", "bmp", "tif");
        buscadorArchivo.setFileFilter(filter);
        int respuesta=buscadorArchivo.showOpenDialog(this);
        if (respuesta==JFileChooser.APPROVE_OPTION){
            ImageIcon imageIcon=new ImageIcon(buscadorArchivo.getSelectedFile().getAbsolutePath());
            BufferedImage imagen=new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
            Graphics g=imagen.createGraphics();
            imageIcon.paintIcon(null, g, 0, 0);
            g.dispose();
            if (imagen.getHeight(null)<=0 || imagen.getWidth(null)<=0){
                myTask.getViewer().setChosenBackground(null);
            } else {
                myTask.getViewer().setChosenBackground(imagen);
                this.convolutionControlPanel.convolateImage();
                if(this.tabbedPane.getSelectedIndex()==1){
                    this.convolutionControlPanel.convolateChosenBackground();
                }
            }
        }
    }    
    
    /**
     * Inicializa los componentes de este JPanel y los posiciona.
     */
    private void initComponents(){
        this.tabbedPane=new JTabbedPane();
        JScrollPane scrollPane=new JScrollPane(this.fireControlPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.tabbedPane.addTab("Fire Control Panel", scrollPane);
        this.tabbedPane.addTab("Convolution Control Panel", this.convolutionControlPanel);     
        this.tabbedPane.addChangeListener(this);
        
        this.labelApp=new JLabel("FIRE IMAGE EDITOR");
        this.labelApp.setFont(new Font("Tahoma",Font.BOLD,24));
        this.labelApp.setForeground(Color.white);
        
        this.labelFondo=new JLabel("BACKGROUND:");
        this.labelFondo.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelFondo.setForeground(Color.white);
        
        this.botoFondo=new JButton("SELECT BACKGROUND");
        this.botoFondo.addActionListener(this);

        this.setLayout(new GridBagLayout());
        
        GridBagConstraints c=new GridBagConstraints();
        this.positionComponent(0, 0, 2, 0, 0, GridBagConstraints.WEST, 
            GridBagConstraints.NONE, new Insets(5,5,10,0), c, this.labelApp);
        this.positionComponent(0, 1, 1, 0, 0, GridBagConstraints.WEST, 
            GridBagConstraints.NONE, new Insets(5,5,10,20), c, this.labelFondo);
        c.ipadx=20;
        this.positionComponent(1, 1, 1, 0, 0.5, GridBagConstraints.WEST, 
            GridBagConstraints.BOTH, new Insets(5,0,10,0), c, this.botoFondo);
        this.positionComponent(0, 2, 2, 1, 1, GridBagConstraints.WEST, 
            GridBagConstraints.BOTH, new Insets(5,0,10,0), c, this.tabbedPane);
    }
    
    /**
     * Posiciona el componente recivido en este JPanel. La posicions y todos los 
     * paramteros son recibido a traves de los parametros del metodo.
     * @param gridx posicion horizontal del componente en el grid
     * @param gridy posicion vertical del componente en el grid
     * @param gridwidth celdas del grid que el componente va a ocupar
     * @param weightx peso horizontal del componente
     * @param weighty peos vertical del componente
     * @param anchor donde posicionar el componente en la celda
     * @param fill como el componente rellena la celda
     * @param insets margenes del componente
     * @param c GridBagConstraint donde los parametros son especificados
     * @param component componente a posicionar
     */
    private void positionComponent(int gridx, int gridy, int gridwidth, double weightx,
            double weighty, int anchor, int fill, Insets insets, GridBagConstraints c, 
            Component component){ 
        c.gridx=gridx;
        c.gridy=gridy;
        c.gridwidth=gridwidth;
        c.weightx=weightx;
        c.weighty=weighty;
        c.anchor=anchor;
        c.fill=fill;
        c.insets=insets;
        this.add(component,c);
    }
    
    /**
     * Metodo implementado de una interfaz.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SELECT BACKGROUND")){
            this.addBackground();
        }
    }
    
    /**
     * Metodo implementado de una interfaz.
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if(this.tabbedPane.getSelectedIndex()==1){
            this.convolutionControlPanel.convolateChosenBackground();
        }
    }
}
