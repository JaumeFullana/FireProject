package proyectofuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author PC
 */
public class ConvolutionControlPanel extends JPanel implements ActionListener, ChangeListener{
    
   private MyTask myTask;
    
    private JLabel labelConvolutionPanel;
    private JLabel labelConvolutionKernel;
    private JLabel labelActivateConvolutionFire;
    
    private JCheckBox checkBoxConvolatePrincipalImage;
    
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

    public MyTask getMyTask() {
        return myTask;
    }

    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
    
    private void initComponents(){
        this.setLayout(new GridBagLayout());
        
        this.labelConvolutionPanel=new JLabel("CONVOLUTION CONTROL PANEL");
        this.labelConvolutionPanel.setFont(new Font("Tahoma",Font.BOLD,20));
        this.labelConvolutionPanel.setForeground(Color.white);
        
        this.labelConvolutionKernel=new JLabel("Convolution kernel: ");
        this.labelConvolutionKernel.setFont(new Font("Tahoma",Font.BOLD,20));
        this.labelConvolutionKernel.setForeground(Color.white);
        
        this.labelActivateConvolutionFire=new JLabel("Convolution Fire: ");
        this.labelActivateConvolutionFire.setFont(new Font("Tahoma",Font.BOLD,18));
        this.labelActivateConvolutionFire.setForeground(Color.white);
        
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
        
        
        this.checkBoxConvolatePrincipalImage=new JCheckBox("Use convolated image as principal image");
        this.checkBoxConvolatePrincipalImage.setFont(new Font("Tahoma",Font.BOLD,14));
        this.checkBoxConvolatePrincipalImage.setForeground(Color.white);
        this.checkBoxConvolatePrincipalImage.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkBoxConvolatePrincipalImage.isSelected()){
                    myTask.getView().setUsingConvulatedImage(true);
                } else{
                    myTask.getView().setUsingConvulatedImage(false);
                }
            }
        });

        
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
        
        GridBagConstraints c=new GridBagConstraints();
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=3;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        c.insets=new Insets(0,5,10,0);
        this.add(this.labelConvolutionPanel,c);
        c.gridx=0;
        c.gridy=1;
        c.gridwidth=1;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,10,0,0);
        this.add(this.buttonSobelVertical,c);
        c.gridx=1;
        c.gridy=1;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,0,0,0);
        this.add(this.buttonSobelHorizontal,c);
        c.gridx=2;
        c.gridy=1;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,0,0,10);
        this.add(this.buttonLaplacian,c);
        c.gridx=0;
        c.gridy=2;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,10,10,0);
        this.add(this.buttonSharpening,c);
        c.gridx=1;
        c.gridy=2;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,0,10,0);
        this.add(this.buttonRaised,c);
        c.gridx=2;
        c.gridy=2;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,0,10,10);
        this.add(this.buttonSmoothing,c);
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=3;
        c.insets=new Insets(0,5,10,5);
        this.add(this.labelConvolutionKernel,c);
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=4;
        c.insets=new Insets(0,10,0,0);
        this.add(this.tableConvolutionKernel,c);
        c.gridwidth=1;
        c.gridx=2;
        c.gridy=4;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(0,5,20,10);
        this.add(this.buttonSetConvolution,c);
        c.gridx=0;
        c.gridy=5;
        c.anchor=GridBagConstraints.WEST;
        c.insets=new Insets(0,10,5,0);
        this.add(this.labelActivateConvolutionFire,c);
        c.gridwidth=2;
        c.gridx=1;
        c.gridy=5;
        c.anchor=GridBagConstraints.CENTER;
        c.insets=new Insets(10,0,0,15);
        this.add(this.sliderActivateConvolutionFire,c);
        c.gridx=0;
        c.gridy=6;
        c.anchor=GridBagConstraints.WEST;
        c.insets=new Insets(10,10,5,0);
        this.add(this.checkBoxConvolatePrincipalImage,c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("APPLY")){
            convolateImage();
            if (this.sliderActivateConvolutionFire.getValue()==1){
                this.myTask.getFire().setConvolateFire(true);
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
    
    private void setKernelTableValues(String [][] kernelValues){
        for (int i=0; i<this.tableConvolutionKernel.getRowCount();i++){
            for (int j=0; j<this.tableConvolutionKernel.getColumnCount();j++){
                this.tableConvolutionKernel.setValueAt(kernelValues[i][j], i, j);
            }
        }
    }

    public void convolateImage() throws HeadlessException {
        if (this.tableConvolutionKernel.isEditing()){
            JOptionPane.showMessageDialog(myTask,"You have to deselect the cell of the Convolution Kernel table","Warning Message",JOptionPane.WARNING_MESSAGE);
            
        }
        else {
            this.myTask.getConvolutionFilter().setConvolutionKernel(this.getInfoKernelValues());
            if (myTask.getView().getChosenBackground()!=null){
                BufferedImage convultaedImage=myTask.getConvolutionFilter().applyConvolutionFilter(myTask.getView().getChosenBackground());
                BufferedImage newConvulatedImage=new BufferedImage(myTask.getView().getAmplada(), myTask.getView().getAltura(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g=newConvulatedImage.createGraphics();
                g.drawImage(convultaedImage, 0, 0, this.myTask.getView().getAmplada(), this.myTask.getView().getAltura(), null);
                g.dispose();
                myTask.getView().setConvolutedImage(newConvulatedImage);
            }
        }
    }
    
    public int [][] getKernelValues(){
        int [][] convolutionKernel=new int[3][3];
        convolutionKernel[0][0]=1;
        convolutionKernel[0][1]=0;
        convolutionKernel[0][2]=-1;
        convolutionKernel[1][0]=2;
        convolutionKernel[1][1]=0;
        convolutionKernel[1][2]=-2;
        convolutionKernel[2][0]=1;
        convolutionKernel[2][1]=0;
        convolutionKernel[2][2]=-1;
        
        return convolutionKernel;
    }
    
    public int [][] getInfoKernelValues(){
        int [][] convolutionKernel=new int[this.tableConvolutionKernel.getRowCount()][this.tableConvolutionKernel.getColumnCount()];
        for (int i=0; i<this.tableConvolutionKernel.getRowCount();i++){
            for (int j=0; j<this.tableConvolutionKernel.getColumnCount();j++){
                convolutionKernel[i][j]=Integer.parseInt(this.tableConvolutionKernel.getValueAt(i, j).toString());
            }
        }
        
        return convolutionKernel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider){
            JSlider slider=(JSlider)e.getSource();
            if(slider==this.sliderActivateConvolutionFire){
                if (!slider.getValueIsAdjusting()){
                    if (this.sliderActivateConvolutionFire.getValue()==0){
                        this.myTask.getFire().setConvolateFire(false);
                    }
                    else {
                        if (this.myTask.getView().getConvolutedImage()!=null){
                            this.myTask.getFire().setConvolateFire(true);
                        }
                    }
                }
            } 
        } 
    }
}
