package proyectofuego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author Jaume
 */
public class ConvolutionControlPanel extends JPanel implements ActionListener, ChangeListener{
    
    private MyTask myTask;
    
    private BufferedImage convolutedImagePreview;
    
    private Canvas convulationPreview;
    
    private JLabel labelConvolutionPanel;
    private JLabel labelConvolutionKernel;
    private JLabel labelActivateConvolutionFire;
    private JLabel labelConvolutionPreview;
    
    private JTable tableConvolutionKernel;
    
    private JSlider sliderActivateConvolutionFire;
    
    private JButton buttonSetConvolution;
    private JButton buttonSobelVertical;
    private JButton buttonSobelHorizontal;
    private JButton buttonLaplacian;
    private JButton buttonSharpening;
    private JButton buttonSmoothing;
    private JButton buttonRaised;

    public ConvolutionControlPanel() {
        this.initComponents();
    }
    
    /**
     * Setter normal
     * @param myTask 
     */
    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
    
    /**
     * Inicializa todos los botones y añade un listener a cada uno.
     */
    private void initButtons() {
        this.buttonSetConvolution=new JButton("APPLY");
        this.buttonSobelVertical=new JButton("Sobel Y");
        this.buttonSobelHorizontal=new JButton("Sobel X");
        this.buttonLaplacian=new JButton("Laplacian");
        this.buttonSharpening=new JButton("Sharpening");
        this.buttonSmoothing=new JButton("Smoothing");
        this.buttonRaised=new JButton("Raised");
        
        
        this.buttonSetConvolution.addActionListener(this);
        this.buttonSobelVertical.addActionListener(this);
        this.buttonSobelHorizontal.addActionListener(this);
        this.buttonLaplacian.addActionListener(this);
        this.buttonSharpening.addActionListener(this);
        this.buttonSmoothing.addActionListener(this);
        this.buttonRaised.addActionListener(this);
    }
    
    /**
     * Canvia el layout a GridBagLayout, inicializa todos los componentes, 
     * añade listeners a algunos componentes i posiciona todos los componentes.
     */
    private void initComponents(){
        this.setLayout(new GridBagLayout());
        
        convulationPreview=new Canvas();
        convulationPreview.setBackground(Color.black); 
        initLabels();
        initButtons();
        initSliderActivateConvolutionFire();
        initTableConvolutionKernel();    
        
        GridBagConstraints c=new GridBagConstraints();
        this.positionComponent(0, 0, 3, 0.5, 0, GridBagConstraints.WEST, 
            GridBagConstraints.NONE, new Insets(10,5,10,0), c, this.labelConvolutionPanel);
        this.positionComponent(0, 1, 1, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,10,0,0), c, this.buttonSobelVertical);
        this.positionComponent(1, 1, 1, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,0,0,0), c, this.buttonSobelHorizontal);
        this.positionComponent(2, 1, 1, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,0,0,10), c, this.buttonLaplacian);
        this.positionComponent(0, 2, 1, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,10,10,0), c, this.buttonSharpening);
        this.positionComponent(1, 2, 1, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,0,10,0), c, this.buttonRaised);
        this.positionComponent(2, 2, 1, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,0,10,10), c, this.buttonSmoothing);
        this.positionComponent(0, 3, 2, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,5,10,5), c, this.labelConvolutionKernel);
        this.positionComponent(0, 4, 2, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(0,10,0,0), c, this.tableConvolutionKernel);
        this.positionComponent(2, 4, 1, 0.5, 0, GridBagConstraints.WEST, 
            GridBagConstraints.BOTH, new Insets(0,5,20,10), c, this.buttonSetConvolution);
        this.positionComponent(0, 5, 1, 0.5, 0, GridBagConstraints.WEST, 
            GridBagConstraints.BOTH, new Insets(0,10,5,0), c, this.labelActivateConvolutionFire);
        this.positionComponent(1, 5, 2, 0.5, 0, GridBagConstraints.CENTER, 
            GridBagConstraints.BOTH, new Insets(10,0,0,15), c, this.sliderActivateConvolutionFire);
        this.positionComponent(0, 6, 2, 0.5, 0, GridBagConstraints.WEST, 
            GridBagConstraints.BOTH, new Insets(10,10,5,0), c, this.labelConvolutionPreview);
        this.positionComponent(0, 7, 3, 0.5, 1, GridBagConstraints.WEST, 
            GridBagConstraints.BOTH, new Insets(10,10,10,10), c, this.convulationPreview);
    }
    
    /**
     * Inicializa todos los Labels.
     */
    private void initLabels() {
        this.labelConvolutionPanel=new JLabel("CONVOLUTION CONTROL PANEL");
        this.labelConvolutionPanel.setFont(new Font("Tahoma",Font.BOLD,20));
        this.labelConvolutionPanel.setForeground(Color.white);
        
        this.labelConvolutionKernel=new JLabel("CONVOLUTION KERNEL: ");
        this.labelConvolutionKernel.setFont(new Font("Tahoma",Font.BOLD,20));
        this.labelConvolutionKernel.setForeground(Color.white);
        
        this.labelActivateConvolutionFire=new JLabel("Convolution Fire: ");
        this.labelActivateConvolutionFire.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelActivateConvolutionFire.setForeground(Color.white);
        
        this.labelConvolutionPreview=new JLabel("CONVOLUTION IMAGE PREVIEW: ");
        this.labelConvolutionPreview.setFont(new Font("Tahoma",Font.BOLD,20));
        this.labelConvolutionPreview.setForeground(Color.white);
    }
    
    /**
     * Inicializa el sliderActivateConvolutionFire
     */
    private void initSliderActivateConvolutionFire() {
        this.sliderActivateConvolutionFire=new JSlider(0,1,0);
        this.sliderActivateConvolutionFire.setPaintLabels(true);
        this.sliderActivateConvolutionFire.addChangeListener(this);
        Hashtable labelTable = new Hashtable();
        JLabel labelDisabled=new JLabel("Disabled");
        JLabel labelActivated=new JLabel("Activated");
        labelDisabled.setForeground(Color.white);
        labelActivated.setForeground(Color.white);
        labelTable.put(0, labelDisabled);
        labelTable.put(1, labelActivated);
        this.sliderActivateConvolutionFire.setLabelTable(labelTable);
        this.sliderActivateConvolutionFire.setForeground(Color.white);
    }
    
    /**
     * Inicializa todos la tableConvolutionKernel y le añade un listener.
     */
    private void initTableConvolutionKernel() {
        String [][] infoConvolutionKernel=new String[3][3];
        infoConvolutionKernel[0][0]="1";
        infoConvolutionKernel[0][1]="2";
        infoConvolutionKernel[0][2]="1";
        infoConvolutionKernel[1][0]="0";
        infoConvolutionKernel[1][1]="0";
        infoConvolutionKernel[1][2]="0";
        infoConvolutionKernel[2][0]="-1";
        infoConvolutionKernel[2][1]="-2";
        infoConvolutionKernel[2][2]="-1";
        String [] columnNamePonderacio={"","",""};
        this.tableConvolutionKernel=new JTable(infoConvolutionKernel, columnNamePonderacio);
        
        this.tableConvolutionKernel.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                convolateChosenBackground();
            }
        });
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
     * Recive una matriz de Strings y canvia los valores de la tableConvolutionKernel 
     * a los valores recividos en la matriz.
     * @param kernelValues Matriz de strings donde estan los nuevos valores 
     */
    private void setKernelTableValues(String [][] kernelValues){
        for (int i=0; i<this.tableConvolutionKernel.getRowCount();i++){
            for (int j=0; j<this.tableConvolutionKernel.getColumnCount();j++){
                this.tableConvolutionKernel.setValueAt(kernelValues[i][j], i, j);
            }
        }
    }
    
    /**
     * Metodo implementado de una interfaz.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("APPLY")){
            convolateImage();
            if (this.sliderActivateConvolutionFire.getValue()==1){
                this.myTask.getFlame().setConvolateFire(true);
            }
        } 
        else if (e.getActionCommand().equals("Sobel Y")){
            String [][] values={{"1","0","-1"},{"2","0","-2"},{"1","0","-1"}};
            this.setKernelTableValues(values);
        }
        else if (e.getActionCommand().equals("Sobel X")){
            String [][] values={{"1","2","1"},{"0","0","0"},{"-1","-2","-1"}};
            this.setKernelTableValues(values);
        }
        else if (e.getActionCommand().equals("Laplacian")){
            String [][] values={{"-1","-1","-1"},{"-1","8","-1"},{"-1","-1","-1"}};
            this.setKernelTableValues(values);
        }
        else if (e.getActionCommand().equals("Sharpening")){
            String [][] values={{"-1","-1","-1"},{"-1","9","-1"},{"-1","-1","-1"}};
            this.setKernelTableValues(values);
        }
        else if (e.getActionCommand().equals("Smoothing")){
            String [][] values={{"1","1","1"},{"1","1","1"},{"1","1","1"}};
            this.setKernelTableValues(values);
        }
        else if (e.getActionCommand().equals("Raised")){
            String [][] values={{"0","0","-2"},{"0","2","0"},{"1","0","0"}};
            this.setKernelTableValues(values);
        }
    }
    
    /**
     * Convoluciona el chosenBackground, guardandolo en el atributo convulatedImagePreview 
     * y llama al metodo paintPreviewConvolution
     */
    public void convolateChosenBackground(){
        myTask.getConvolutionFilter().setConvolutionKernel(getInfoKernelValues());
        if (myTask.getViewer().getChosenBackground()!=null){
            convolutedImagePreview=myTask.getConvolutionFilter().applyConvolutionFilter(myTask.getViewer().getChosenBackground());
            paintPreviewConvolution();
        }
    }
    
    /**
     * Si hay una imagen guardada en el ChosenBackground el metodo convoluciona 
     * esa imagen y coloca el resultado de la convolution en la ConvulatedImage 
     * del objeto Viewer.
     */
    public void convolateImage() {
        if (this.tableConvolutionKernel.isEditing()){
            JOptionPane.showMessageDialog(myTask,"You have to deselect the cell of the Convolution Kernel table","Warning Message",JOptionPane.WARNING_MESSAGE);
        }
        else {
            this.myTask.getConvolutionFilter().setConvolutionKernel(this.getInfoKernelValues());
            if (myTask.getViewer().getChosenBackground()!=null){
                BufferedImage convultaedImage=myTask.getConvolutionFilter().applyConvolutionFilter(myTask.getViewer().getChosenBackground());
                BufferedImage newConvulatedImage=new BufferedImage(myTask.getViewer().getAnchura(), myTask.getViewer().getAltura(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g=newConvulatedImage.createGraphics();
                g.drawImage(convultaedImage, 0, 0, this.myTask.getViewer().getAnchura(), this.myTask.getViewer().getAltura(), null);
                g.dispose();
                myTask.getViewer().setConvolutedImage(newConvulatedImage);
            }
        }
    }
    
    /**
     * Recoge los valores guardados en la tableConvolutionKernel y los devuelve 
     * como una matriz de int del mismo tamaño que la tabla.
     * @return int matriz donde los valores son guardados
     */
    public int [][] getInfoKernelValues(){
        int [][] convolutionKernel=new int[this.tableConvolutionKernel.getRowCount()][this.tableConvolutionKernel.getColumnCount()];
        for (int i=0; i<this.tableConvolutionKernel.getRowCount();i++){
            for (int j=0; j<this.tableConvolutionKernel.getColumnCount();j++){
                convolutionKernel[i][j]=Integer.parseInt(this.tableConvolutionKernel.getValueAt(i, j).toString());
            }
        }
        
        return convolutionKernel;
    }
    
    /**
     * Pinta la convulatedImagePreview en el canvas convultaionPreview
     */
    public void paintPreviewConvolution() {
        Graphics g=convulationPreview.getGraphics();
        g.drawImage(convolutedImagePreview, 0, 0, convulationPreview.getWidth(), convulationPreview.getHeight(), null);
        g.dispose();
    }
    
    /**
     * Metodo implementado de una interfaz.
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider){
            JSlider slider=(JSlider)e.getSource();
            if(slider==this.sliderActivateConvolutionFire){
                if (!slider.getValueIsAdjusting()){
                    if (this.sliderActivateConvolutionFire.getValue()==0){
                        this.myTask.getFlame().setConvolateFire(false);
                    }
                    else {
                        if (this.myTask.getViewer().getConvolutedImage()!=null){
                            this.myTask.getFlame().setConvolateFire(true);
                        }
                    }
                }
            } 
        } 
    }
}
