package proyectofuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author PC
 */
public class GeneralControlPanel extends JPanel implements ActionListener, ChangeListener{
    
    private FireControlPanel fireControlPanel;
    private ConvolutionControlPanel convolutionControlPanel;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;
    private MyTask myTask;
    
    private JLabel labelApp;
    private JLabel labelFondo;
    private JButton botoFondo;

    public FireControlPanel getFireControlPanel() {
        return fireControlPanel;
    }

    public ConvolutionControlPanel getConvolutionControlPanel() {
        return convolutionControlPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public GeneralControlPanel(MyTask myTask, FireControlPanel fireControlPanel, ConvolutionControlPanel convolutionControlPanel) {
        this.myTask = myTask;
        this.fireControlPanel = fireControlPanel;
        this.convolutionControlPanel = convolutionControlPanel;
        initComponents();
    }
    
    public void initComponents(){
        
        this.tabbedPane=new JTabbedPane();
        
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
        c.insets=new Insets(5,5,10,0);
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=2;
        c.anchor=GridBagConstraints.WEST;
        this.add(this.labelApp,c);
        c.insets=new Insets(5,5,10,20);
        c.gridx=0;
        c.gridy=1;
        c.gridwidth=1;
        this.add(this.labelFondo,c);
        c.insets=new Insets(5,0,10,0);
        c.ipadx=20;
        c.gridx=1;
        c.gridy=1;
        c.fill=GridBagConstraints.BOTH; 
        c.weighty=0.5;
        this.add(this.botoFondo,c);
        c.weightx=1;
        c.weighty=1;
        c.gridx=0;
        c.gridy=2;
        c.gridwidth=2;        
        JScrollPane scrollPane=new JScrollPane(this.fireControlPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.tabbedPane.addTab("Fire Control Panel", scrollPane);
        this.tabbedPane.addTab("Convolution Control Panel", this.convolutionControlPanel);
        this.add(this.tabbedPane,c);
        
        this.tabbedPane.addChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SELECT BACKGROUND")){
            this.addBackground();
        }
    }
    
        /**
     * Metodo que añade el background del viewer. Si el archivo escogido no es un imagen
     * o su tamaño es 0 o menor y por tanto no se ve se asignara valor null para que se 
     * muestre la imagen por defecto, que es un fondo negro.
     */
    public void addBackground(){
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
                myTask.getView().setChosenBackground(null);
            } else {
                myTask.getView().setChosenBackground(imagen);
                this.getConvolutionControlPanel().convolateImage();
                if(this.tabbedPane.getSelectedIndex()==1){
                    this.convolutionControlPanel.convulateChosenBackground();
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(this.tabbedPane.getSelectedIndex()==1){
            this.convolutionControlPanel.convulateChosenBackground();
        }
    }
}
