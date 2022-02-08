package proyectofuego;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jaume
 */
public class FireControlPanel extends JPanel implements ActionListener, ChangeListener{
    
    private MyTask myTask;
    
    private ArrayList <Color> listaColoresPaleta;
    
    private JTable tablaColores;
    private JTable tablaPonderacion;
    private Map<String,FlamePalette> mapaPaletas;
    
    private JLabel titulo;
    private JLabel coloresPaleta;
    private JLabel perdidaCalorica;
    private JLabel targetColors;
    private JLabel labelFramesFire;
    private JLabel labelFramesCanvas;
    private JLabel labelSparks;
    private JLabel labelCoolingPoints;
    private JLabel labelPonderacio;
    private JLabel labelDivisorPonderacio;
    private JLabel labelDesde;
    private JLabel labelHasta;
    private JLabel labelPorcentage;
    
    private JSlider sliderRefresco;
    private JSlider sliderFramesFire;
    private JSlider sliderFramesCanvas;
    
    private JSpinner spinnerDivisorPonderacio;
    
    private JSpinner spinnerSparksFrom;
    private JSpinner spinnerSparksTo;
    private JSpinner spinnerSparksPercentage;
    
    private JCheckBox checkBoxSparks2;
    private JSpinner spinnerSparksFrom2;
    private JSpinner spinnerSparksTo2;
    private JSpinner spinnerSparksPercentage2;
    
    private JSpinner spinnerCoolingFrom;
    private JSpinner spinnerCoolingTo;
    private JSpinner spinnerCoolingPercentage;
    
    private JComboBox comboBoxPaletas;
    
    private JButton start;
    private JButton pause;
    private JButton stop;
    private JButton botonAnadirColor;
    private JButton botonGuardarPaleta;
    private JButton botonBorrarColores;
    private JButton botoActualitzar;
    private JButton botonSeleccionarPaleta;
    private JButton botonBorrarPaleta;

    public FireControlPanel() {
        this.initComponents();
    }

    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
    
    /**
     * Instancia y añade los componentes al FireControlPanel
     */
    private void initComponents(){
        this.setLayout(new GridBagLayout());
        
        this.listaColoresPaleta=new ArrayList <Color>();
        
        this.coloresPaleta=new JLabel("PALETTE COLORS");
        this.coloresPaleta.setFont(new Font("Tahoma",Font.BOLD,20));
        this.coloresPaleta.setForeground(Color.white);
        this.titulo=new JLabel("FIRE CONTROL PANEL");
        this.titulo.setFont(new Font("Tahoma",Font.BOLD,20));
        this.titulo.setForeground(Color.white);
        this.perdidaCalorica=new JLabel("HEAT LOSS");
        this.perdidaCalorica.setFont(new Font("Tahoma",Font.BOLD,20));
        this.perdidaCalorica.setForeground(Color.white);
        this.targetColors=new JLabel("TARGET COLORS");
        this.targetColors.setFont(new Font("Tahoma",Font.BOLD,20));
        this.targetColors.setForeground(Color.white);
        this.labelFramesFire=new JLabel("Fire Frames:");
        this.labelFramesFire.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelFramesFire.setForeground(Color.white);
        this.labelFramesCanvas=new JLabel("Canvas Frames:");
        this.labelFramesCanvas.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelFramesCanvas.setForeground(Color.white);
        this.labelSparks=new JLabel("Sparks: ");
        this.labelSparks.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelSparks.setForeground(Color.white);
        this.labelCoolingPoints=new JLabel("Cooling Points: ");
        this.labelCoolingPoints.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelCoolingPoints.setForeground(Color.white);
        this.labelPonderacio=new JLabel("HEAT PONDERATION:");
        this.labelPonderacio.setFont(new Font("Tahoma",Font.BOLD,16));
        this.labelPonderacio.setForeground(Color.white);
        this.labelDivisorPonderacio=new JLabel("Ponderation divider:");
        this.labelDivisorPonderacio.setFont(new Font("Tahoma",Font.BOLD,14));
        this.labelDivisorPonderacio.setForeground(Color.white);
        this.labelDesde=new JLabel("  From");
        this.labelDesde.setFont(new Font("Tahoma",Font.BOLD,14));
        this.labelDesde.setForeground(Color.white);
        this.labelHasta=new JLabel("    To");
        this.labelHasta.setFont(new Font("Tahoma",Font.BOLD,14));
        this.labelHasta.setForeground(Color.white);
        this.labelPorcentage=new JLabel("%     ");
        this.labelPorcentage.setFont(new Font("Tahoma",Font.BOLD,14));
        this.labelPorcentage.setForeground(Color.white);
        
        this.sliderFramesFire=new JSlider(30,300,200);
        this.sliderFramesFire.setMinorTickSpacing(15);
        this.sliderFramesFire.setMajorTickSpacing(30);
        this.sliderFramesFire.setPaintTicks(true);
        this.sliderFramesFire.setPaintLabels(true);
        this.sliderFramesFire.setForeground(Color.white);
        this.sliderFramesFire.addChangeListener(this);
        
        this.sliderFramesCanvas=new JSlider(30,300,200);
        this.sliderFramesCanvas.setMinorTickSpacing(15);
        this.sliderFramesCanvas.setMajorTickSpacing(30);
        this.sliderFramesCanvas.setPaintTicks(true);
        this.sliderFramesCanvas.setPaintLabels(true);
        this.sliderFramesCanvas.setForeground(Color.white);
        this.sliderFramesCanvas.addChangeListener(this);
        
        this.sliderRefresco=new JSlider(10,30,12);
        this.sliderRefresco.setMinorTickSpacing(1);
        this.sliderRefresco.setMajorTickSpacing(5);
        this.sliderRefresco.setPaintTicks(true);
        this.sliderRefresco.setPaintLabels(true);
        this.sliderRefresco.setForeground(Color.white);
        this.sliderRefresco.addChangeListener(this);
        
        SpinnerModel modelDivisorPonderacio=new SpinnerNumberModel(695,0,1000,1);
        this.spinnerDivisorPonderacio=new JSpinner(modelDivisorPonderacio);
        
        SpinnerModel modelSparksFrom=new SpinnerNumberModel(0,0,800,1);
        SpinnerModel modelSparksTo=new SpinnerNumberModel(800,0,800,1);
        SpinnerModel modelSparksPercentage=new SpinnerNumberModel(1,0,10,1);
        
        this.spinnerSparksFrom=new JSpinner(modelSparksFrom);
        this.spinnerSparksTo=new JSpinner(modelSparksTo);
        this.spinnerSparksPercentage=new JSpinner(modelSparksPercentage);
        
        SpinnerModel modelSparksFrom2=new SpinnerNumberModel(0,0,800,1);
        SpinnerModel modelSparksTo2=new SpinnerNumberModel(0,0,800,1);
        SpinnerModel modelSparksPercentage2=new SpinnerNumberModel(0,0,10,1);
        
        this.checkBoxSparks2=new JCheckBox("Activate Sparks 2");
        this.checkBoxSparks2.setForeground(Color.white);
        this.checkBoxSparks2.setFont(new Font("Tahoma",Font.BOLD,12));
        this.checkBoxSparks2.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {                 
                if(checkBoxSparks2.isSelected()){
                    spinnerSparksFrom2.setEnabled(true);
                    spinnerSparksTo2.setEnabled(true);
                    spinnerSparksPercentage2.setEnabled(true);
                    myTask.getFire().setSparks2Activated(true);
                } else {
                    spinnerSparksFrom2.setEnabled(false);
                    spinnerSparksTo2.setEnabled(false);
                    spinnerSparksPercentage2.setEnabled(false);
                    myTask.getFire().setSparks2Activated(false);
                }
             }    
        });
        this.spinnerSparksFrom2=new JSpinner(modelSparksTo2);
        this.spinnerSparksTo2=new JSpinner(modelSparksFrom2);
        this.spinnerSparksPercentage2=new JSpinner(modelSparksPercentage2);
        spinnerSparksFrom2.setEnabled(false);
        spinnerSparksTo2.setEnabled(false);
        spinnerSparksPercentage2.setEnabled(false);
        
        SpinnerModel modelCoolingFrom=new SpinnerNumberModel(0,0,800,1);
        SpinnerModel modelCoolingTo=new SpinnerNumberModel(800,0,800,1);
        SpinnerModel modelCoolingPercentage=new SpinnerNumberModel(2,0,10,1);
        
        this.spinnerCoolingFrom=new JSpinner(modelCoolingFrom);
        this.spinnerCoolingTo=new JSpinner(modelCoolingTo);
        this.spinnerCoolingPercentage=new JSpinner(modelCoolingPercentage);
        
        String [] nombresPaletas=loadPalettes();
        this.comboBoxPaletas=new JComboBox(nombresPaletas);
        
        this.start=new JButton("PLAY", new ImageIcon(this.getClass().getResource("Images\\play.png")));
        this.pause=new JButton("PAUSE", new ImageIcon(this.getClass().getResource("Images\\pause.png")));
        this.stop=new JButton("STOP", new ImageIcon(this.getClass().getResource("Images\\stop.png")));
        this.botonAnadirColor=new JButton("ADD COLOR");
        this.botoActualitzar=new JButton("UPDATE VALUES");
        this.botonGuardarPaleta=new JButton("SAVE PALETTE");
        this.botonBorrarColores=new JButton("DELETE COLORS");
        this.botonSeleccionarPaleta=new JButton("SELECT PALETTE");
        this.botonBorrarPaleta=new JButton("DELETE PALETTE");
        
        this.start.addActionListener(this);
        this.pause.addActionListener(this);
        this.stop.addActionListener(this);
        this.botonAnadirColor.addActionListener(this);
        this.botoActualitzar.addActionListener(this);;
        this.botonGuardarPaleta.addActionListener(this);
        this.botonBorrarColores.addActionListener(this);
        this.botonSeleccionarPaleta.addActionListener(this);
        this.botonBorrarPaleta.addActionListener(this);
                  
        String [][] infoColores=new String[1][2];
        infoColores[0][0]="";
        infoColores[0][1]="";

        String [] columnNameColors={"COLOR", "TEMPERATURE"};
        this.tablaColores=new JTable(infoColores,columnNameColors); 
        
        String [][] infoPonderacio=new String[3][3];
        infoPonderacio[0][0]="0";
        infoPonderacio[0][1]="0.1";
        infoPonderacio[0][2]="0";
        infoPonderacio[1][0]="0.2";
        infoPonderacio[1][1]="0.5";
        infoPonderacio[1][2]="0.8";
        infoPonderacio[2][0]="2.1";
        infoPonderacio[2][1]="1.8";
        infoPonderacio[2][2]="1.5";
        String [] columnNamePonderacio={"","",""};
        this.tablaPonderacion=new JTable(infoPonderacio, columnNamePonderacio);
        
        GridBagConstraints c=new GridBagConstraints();
        c.weightx=0.5;
        c.weighty=0.8;
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=4;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        c.insets=new Insets(0,5,0,0);
        this.add(this.titulo,c);
        c.weighty=0.4;
        c.anchor=GridBagConstraints.CENTER;
        c.gridwidth=1;
        c.insets=new Insets(5,10,10,0);
        c.gridx=0;
        c.gridy=1;
        c.fill=GridBagConstraints.BOTH;
        this.add(this.start,c);
        c.insets=new Insets(5,0,10,0);
        c.gridx=1;
        c.gridy=1;
        this.add(this.pause,c);
        c.insets=new Insets(5,0,10,20);
        c.gridx=2;
        c.gridy=1;
        this.add(this.stop,c);
        c.gridwidth=1;
        c.weighty=0.6;
        c.gridx=0;
        c.gridy=3;
        c.insets=new Insets(0,5,0,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.labelFramesFire,c);
        c.gridx=1;
        c.gridy=3;
        c.insets=new Insets(0,0,0,20);
        c.gridwidth=3;
        c.anchor=GridBagConstraints.NORTH;
        c.fill=GridBagConstraints.BOTH;
        this.add(this.sliderFramesFire,c);
        c.gridx=0;
        c.gridy=4;
        c.insets=new Insets(0,5,0,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.labelFramesCanvas,c);
        c.gridx=1;
        c.gridy=4;
         c.insets=new Insets(0,0,0,20);
        c.gridwidth=3;
        c.anchor=GridBagConstraints.NORTH;
        c.fill=GridBagConstraints.BOTH;
        this.add(this.sliderFramesCanvas,c);
        c.weighty=0.8;
        c.gridx=0;
        c.gridy=5;
        c.gridwidth=4;
        c.insets=new Insets(0,5,0,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.coloresPaleta,c);
        c.weighty=0.4;
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=6;
        c.insets=new Insets(5,10,5,0);
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        this.add(this.botonAnadirColor,c);
        c.gridx=1;
        c.gridy=6;
        c.insets=new Insets(5,0,5,0);
        this.add(this.botonGuardarPaleta,c);
        c.gridx=2;
        c.gridy=6;
        c.insets=new Insets(5,0,5,20);
        this.add(this.botonBorrarColores,c);
        this.placeTablaColores();
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=9;
        c.insets=new Insets(5,10,10,0);
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        this.add(this.comboBoxPaletas,c);
        c.gridx=1;
        c.gridy=9;
        c.insets=new Insets(5,0,10,0);
        this.add(this.botonSeleccionarPaleta,c);
        c.gridx=2;
        c.gridy=9;
        c.insets=new Insets(5,0,10,20);
        this.add(this.botonBorrarPaleta,c);
        c.gridx=0;
        c.gridy=10;
        c.gridwidth=4;
        c.insets=new Insets(0,5,0,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.perdidaCalorica,c);
        c.weighty=0.6;
        c.gridx=0;
        c.gridy=11;
        c.gridwidth=4;
        c.insets=new Insets(0,10,0,20);
        c.anchor=GridBagConstraints.NORTH;
        c.fill=GridBagConstraints.BOTH;
        this.add(this.sliderRefresco,c);
        c.gridx=1;
        c.gridy=12;
        c.insets=new Insets(0,0,0,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(labelDesde,c);
        c.weightx=0.8;
        c.gridx=2;
        c.gridy=12;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(labelHasta,c);
        c.gridx=2;
        c.gridy=12;
        c.insets=new Insets(0,0,0,20);
        c.anchor=GridBagConstraints.EAST;
        c.fill=GridBagConstraints.NONE;
        this.add(labelPorcentage,c);
        c.weighty=0.2;
        c.gridx=0;
        c.gridy=13;
        c.insets=new Insets(0,5,10,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.labelSparks,c);
        c.gridx=1;
        c.gridy=13;
        c.insets=new Insets(0,0,10,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(spinnerSparksFrom,c);
        c.weightx=0.8;
        c.gridx=2;
        c.gridy=13;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(spinnerSparksTo,c);
        c.gridx=2;
        c.gridy=13;
        c.insets=new Insets(0,0,10,20);
        c.anchor=GridBagConstraints.EAST;
        c.fill=GridBagConstraints.NONE;
        this.add(spinnerSparksPercentage,c); 
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=14;
        c.insets=new Insets(0,5,10,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.checkBoxSparks2,c);
        c.gridx=1;
        c.gridy=14;
        c.insets=new Insets(0,0,10,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(spinnerSparksFrom2,c);
        c.gridx=2;
        c.gridy=14;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(spinnerSparksTo2,c);
        c.gridx=2;
        c.gridy=14;
        c.insets=new Insets(0,0,10,20);
        c.anchor=GridBagConstraints.EAST;
        c.fill=GridBagConstraints.NONE;
        this.add(spinnerSparksPercentage2,c); 
        c.weighty=0.2;
        c.gridx=0;
        c.gridy=15;
        c.insets=new Insets(0,5,10,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.labelCoolingPoints,c);
        c.gridx=1;
        c.gridy=15;
        c.insets=new Insets(0,0,10,0);
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.spinnerCoolingFrom,c);
        c.gridx=2;
        c.gridy=15;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.spinnerCoolingTo,c);
        c.gridx=2;
        c.gridy=15;
        c.insets=new Insets(0,0,10,20);
        c.anchor=GridBagConstraints.EAST;
        c.fill=GridBagConstraints.NONE;
        this.add(this.spinnerCoolingPercentage,c);
        c.weighty=0.4;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=16;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(0,5,0,20);
        this.add(this.labelPonderacio,c);
        c.weighty=0.4;
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=17;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.NONE;
        c.insets=new Insets(0,10,0,0);
        this.add(this.tablaPonderacion,c);
        c.weighty=0.4;
        c.gridwidth=1;
        c.gridx=1;
        c.gridy=17;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.NONE;
        c.insets=new Insets(0,10,0,0);
        this.add(this.labelDivisorPonderacio,c);
        c.weighty=0.4;
        c.gridwidth=1;
        c.gridx=2;
        c.gridy=17;
        c.anchor=GridBagConstraints.WEST;
        c.fill=GridBagConstraints.NONE;
        c.insets=new Insets(0,0,0,0);
        this.add(this.spinnerDivisorPonderacio,c);
        c.weighty=0.4;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=18;
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.BOTH;
        c.insets=new Insets(5,10,0,20);
        this.add(this.botoActualitzar,c);
    }
    
    /**
     * Metodo que es llamado cuando un ActionListener recibe un evento.
     * Dependiendo de cual sea el objeto que ha recibido el evento cambia unos valores
     * o otros.
     * @param e evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()){
            case ("PLAY"):
                this.start();
                break;
                
            case ("PAUSE"):
                this.pause();
                break;
                
            case ("STOP"):
                this.stop();
                break;

            case ("ADD COLOR"):
                this.addColorTableRow();
                break;
                
            case("SAVE PALETTE"):
                if (this.tablaColores.isEditing()){
                    JOptionPane.showMessageDialog(myTask,"You have to deselect the cell of the palette colors table","Warning Message",JOptionPane.WARNING_MESSAGE);
                } else {
                    this.savePalette();
                }
                break;

            case("DELETE COLORS"):
                this.deleteTableValues();
                break;
              
            case ("SELECT PALETTE"):
                this.myTask.setFlamePalette(this.mapaPaletas.get(this.comboBoxPaletas.getSelectedItem().toString()));
                break;
                
            case ("DELETE PALETTE"):
                this.deletePalette(this.comboBoxPaletas.getSelectedItem().toString());
                break;
                
            case ("UPDATE VALUES"):
                if (this.tablaPonderacion.isEditing()){
                    JOptionPane.showMessageDialog(myTask,"You have to deselect the cell of the ponderation table","Warning Message",JOptionPane.WARNING_MESSAGE);
                } else{
                    this.addSparksValues();
                    this.addCoolingPointsValues();
                    this.addPonderationValues();
                }
                break;
        }
    }
    
    public void addPonderationValues(){
        this.myTask.getFire().setPondNorthCenter(Double.parseDouble(this.tablaPonderacion.getValueAt(0, 1).toString()));
        this.myTask.getFire().setPondNorthWest(Double.parseDouble(this.tablaPonderacion.getValueAt(0, 0).toString()));
        this.myTask.getFire().setPondNorthEast(Double.parseDouble(this.tablaPonderacion.getValueAt(0, 2).toString()));
        
        this.myTask.getFire().setPondCenterCenter(Double.parseDouble(this.tablaPonderacion.getValueAt(1, 1).toString()));
        this.myTask.getFire().setPondCenterWest(Double.parseDouble(this.tablaPonderacion.getValueAt(1, 0).toString()));
        this.myTask.getFire().setPondCenterEast(Double.parseDouble(this.tablaPonderacion.getValueAt(1, 2).toString()));
        
        this.myTask.getFire().setPondSouthCenter(Double.parseDouble(this.tablaPonderacion.getValueAt(2, 1).toString()));
        this.myTask.getFire().setPondSouthWest(Double.parseDouble(this.tablaPonderacion.getValueAt(2, 0).toString()));
        this.myTask.getFire().setPondSouthEast(Double.parseDouble(this.tablaPonderacion.getValueAt(2, 2).toString()));
        
        this.myTask.getFire().setPondDivisor(Double.parseDouble(this.spinnerDivisorPonderacio.getValue().toString()));
    }
    
    /**
     * Metodo que abre un JColorChooser para que se elija un color y cuando se elija el color
     * se guarde este en la tablaColores. Se da una posicion por defecto dependiendo de la 
     * cantidad de filas que haya en la tabla.
     */
    public void addColorTableRow(){
        Color color=JColorChooser.showDialog(new JPanel(), "Elige un color", Color.BLACK);
        if (color!=null){
            this.listaColoresPaleta.add(color);
            
            int saltos;
            if(this.listaColoresPaleta.size()<=1){
                saltos=200/this.listaColoresPaleta.size();
            }
            else {
                saltos=200/(this.listaColoresPaleta.size()-1);
            }
            int saltoActual=55;
            
            String [][] info=new String[this.listaColoresPaleta.size()][2];
            
            for (int i=0; i<this.listaColoresPaleta.size();i++){
                info[i][0]="";
                info[i][1]=""+saltoActual;
                saltoActual+=saltos;
                if (i==this.listaColoresPaleta.size()-2){
                    saltoActual=255;
                }
            }
            
            
            this.remove(tablaColores.getTableHeader());
            this.remove(tablaColores);
            String [] columnName={"COLOR", "TEMPERATURE"};
            this.tablaColores=new JTable(info,columnName){
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component component=super.prepareRenderer(renderer, row, column);
                    if (column==0 && row<listaColoresPaleta.size()){
                        component.setBackground(listaColoresPaleta.get(row));
                    } else {
                        component.setBackground(Color.WHITE);
                    }
                    return component;
                }
            }; 
            placeTablaColores();
            this.revalidate();
            this.repaint();
        }
    }
    
    /**
     * Metodo que comprueva que los valores que se pasaran al metodo createCool 
     * no se salgan de los inidices de la matriz de mapaTemperatura para luego 
     * pasarselos correctamente.
     */
    public void addCoolingPointsValues(){
        if ((int)this.spinnerCoolingFrom.getValue()>this.myTask.getFire().getMapaTemperatura()[0].length){
            this.spinnerCoolingFrom.setValue(this.myTask.getFire().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerCoolingFrom.getValue()<0){
            this.spinnerCoolingFrom.setValue(0);
        }
        if ((int)this.spinnerCoolingTo.getValue()>this.myTask.getFire().getMapaTemperatura()[0].length){
            this.spinnerCoolingTo.setValue(this.myTask.getFire().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerCoolingTo.getValue()<0){
            this.spinnerCoolingTo.setValue(0);
        }
        this.myTask.getView().getFire().setCoolingFromCol((int)this.spinnerCoolingFrom.getValue());
        this.myTask.getView().getFire().setCoolingToCol((int)this.spinnerCoolingTo.getValue());
        this.myTask.getView().getFire().setCoolingPercentage((int)this.spinnerCoolingPercentage.getValue());        
    }
    
    /**
     * Metodo que comprueva que los valores que se pasaran al metodo createSparks 
     * no se salgan de los inidices de la matriz de mapaTemperatura para luego 
     * pasarselos correctamente.
     */
    public void addSparksValues(){
        
        if ((int)this.spinnerSparksFrom.getValue()>this.myTask.getFire().getMapaTemperatura()[0].length){
            this.spinnerSparksFrom.setValue(this.myTask.getFire().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksFrom.getValue()<0){
            this.spinnerSparksFrom.setValue(0);
        }
        if ((int)this.spinnerSparksTo.getValue()>this.myTask.getFire().getMapaTemperatura()[0].length){
            this.spinnerSparksTo.setValue(this.myTask.getFire().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksTo.getValue()<0){
            this.spinnerSparksTo.setValue(0);
        }
        if ((int)this.spinnerSparksFrom2.getValue()>this.myTask.getFire().getMapaTemperatura()[0].length){
            this.spinnerSparksFrom2.setValue(this.myTask.getFire().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksFrom2.getValue()<0){
            this.spinnerSparksFrom2.setValue(0);
        }
        if ((int)this.spinnerSparksTo2.getValue()>this.myTask.getFire().getMapaTemperatura()[0].length){
            this.spinnerSparksTo2.setValue(this.myTask.getFire().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksTo2.getValue()<0){
            this.spinnerSparksTo2.setValue(0);
        }
        this.myTask.getView().getFire().setSparksFromCol((int)this.spinnerSparksFrom.getValue());
        this.myTask.getView().getFire().setSparksToCol((int)this.spinnerSparksTo.getValue());
        this.myTask.getView().getFire().setSparksPercentage((int)this.spinnerSparksPercentage.getValue());
        this.myTask.getView().getFire().setSparksFromCol2((int)this.spinnerSparksFrom2.getValue());
        this.myTask.getView().getFire().setSparksToCol2((int)this.spinnerSparksTo2.getValue());
        this.myTask.getView().getFire().setSparksPercentage2((int)this.spinnerSparksPercentage2.getValue());
    }
    
    /**
     * Metodo que elimina una de las paletas guardadas en el archivo paleta, tanto
     * del archivo como del programa. Para hacerlo leemos el fichero y lo volvemos
     * a escribir sin esa paletta. Despues los valores de la aplicacion se actualizan.
     * @param palette Nombre de la paleta a borrar.
     */
    public void deletePalette(String palette){
        try {
            File file=new File("paletas.txt");
            String paletasAntiguas=readFile(file);
            String[] paletas=paletasAntiguas.split("#");
            String paletasNuevas="";
            for (int i=1;i<paletas.length;i++){
                if (!palette.equals(paletas[i].split("/")[0])){
                    paletasNuevas+="#"+paletas[i];
                }
            }
            BufferedWriter escritor = new BufferedWriter(new FileWriter(file));
            escritor.write(paletasNuevas);
            escritor.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.updatePalettes();
    }
    
    /**
     * Metodo que borra todos los valores de la tablaColores. Remueve la tabla del 
     * JPanel, para luego instanciar una nueva tabla vacia en la misma variable y 
     * volverla a colocar en el sitio que estaba la otra antes.
     */
    public void deleteTableValues() {
        this.listaColoresPaleta.clear();
        this.remove(tablaColores);
        this.remove(tablaColores.getTableHeader());
        String [][] info=new String[1][2];
        info[0][0]="";
        info[0][1]="";
        String [] columnName={"COLOR", "TEMPERATURE"};
        this.tablaColores=new JTable(info,columnName);
        
        placeTablaColores();
        
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Metodo que coloca la tablaColores en su sitio.
     */
    public void placeTablaColores() {
        GridBagConstraints c=new GridBagConstraints();
        
        c.weightx=0.5;
        c.weighty=0.2;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=7;
        c.anchor=GridBagConstraints.SOUTH;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(5,10,0,20);
        this.add(this.tablaColores.getTableHeader(),c);
        c.gridx=0;
        c.gridy=8;
        c.anchor=GridBagConstraints.NORTH;
        c.insets=new Insets(0,10,0,20);
        this.add(this.tablaColores,c);
    }
    
    /**
     * Metodo que accede al fichero de las paletas guardadas, para coger los valores
     * de estas. Coge el nombre de la paleta (que despues devuelve para ponerlos en
     * el comboBox de la paletas) y todos sus colores y posiciones de esos colores.
     * Guarda los colores y sus posiciones en una flamePalette y despues guarda el nombre
     * de esa paleta y la flamePalette en el Map mapaPaletas.
     * @return Array de strings donde se encuentran los nombres de las paletas
     */
    public String[] loadPalettes(){
        this.mapaPaletas=new HashMap <String,FlamePalette>();
        String [] nombresPaletas=null;
        try {
            File file=new File("paletas.txt");
            String texto=readFile(file);
            String[] paletas=texto.split("#");
            nombresPaletas=new String [paletas.length-1];
            for (int i=1; i<paletas.length;i++){
                String[] paleta=paletas[i].split("/");
                String nombre=paleta[0];
                nombresPaletas[i-1]=nombre;
                FlamePalette flamePalette=new FlamePalette();
                for (int j=1;j<paleta.length;j++){
                    //Canvia quan posi transparenci
                    if (j==1){
                        flamePalette.addTargetColor(new TargetColor(0,new Color(0,0,0,0)));
                    }
                    else {
                        String[] stringTargetColor=paleta[j].split(",");
                        flamePalette.addTargetColor(new TargetColor(Integer.parseInt
                                    (stringTargetColor[0]),new Color(Integer.parseInt(stringTargetColor[1]),true)));
                    }
                }
                this.mapaPaletas.put(nombre, flamePalette);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return nombresPaletas;
    }
    
    /**
     * Metodo con el que se pausa la ejecucion de la aplicacion.
     */
    public void pause(){
        this.start.setEnabled(true);
        this.pause.setEnabled(false);
        this.stop.setEnabled(true);
        this.myTask.getFire().setRunning(false);
    }
    
    /**
     * Metodo que accede a un fichero, lee su contenido y lo copia en una String 
     * que luego devuelve. La ruta al fichero la recibe por parametro.
     * @param file ruta al archivo que queremos leer
     * @return String una copia del contenido del archivo
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    public String readFile(File file) throws FileNotFoundException, IOException {
        BufferedReader lector=new BufferedReader(new FileReader(file));
        boolean eof=false;
        String texto="";
        while(!eof){
            String linea=lector.readLine();
            if (linea==null){
                eof=true;
            }
            else {
                texto+=linea;
            }
        }
        lector.close();
        return texto;
    }
    
    /**
     * Metodo que coge los valores de la tablaColores y los guarda en el archivo 
     * de texto de las paletas con el nombre que se inserta en el Dialog. Tambien
     * se se añaden los nuevos valores guardados al mapaPaletas y a la comboBox de 
     * las paletas.
     */
    public void savePalette(){
        File file=new File("paletas.txt");
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(file,true))) {
            if (this.tablaColores.getRowCount()==1 && this.tablaColores.getValueAt(0, 1).toString().isBlank()){
                throw new Exception("The palette can't be created.\nThere isn't any color specified");
            }
            boolean maxTemperatureSeted=false;
            String nombrePaleta = (String)JOptionPane.showInputDialog(
                    this.myTask,"Write the plaette name:");        
            escritor.write("#"+nombrePaleta);
            escritor.write("/"+0+","+new Color(0,0,0,0).getRGB());
            for (int i=0; i<this.tablaColores.getRowCount();i++){
                Component component=this.tablaColores.prepareRenderer(this.tablaColores.getCellRenderer(i, 0),i,0);
                int temperature=Integer.parseInt(this.tablaColores.getValueAt(i, 1).toString());
                if (temperature<1){
                    temperature=1;
                }
                if (temperature==255){
                    maxTemperatureSeted=true;
                }
                escritor.write("/"+temperature+","+component.getBackground().getRGB());
            }
            if (!maxTemperatureSeted){
                escritor.write("/"+255+","+new Color(255,255,255,255).getRGB());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            if (ex.getMessage().equals("The palette can't be created.\nThere isn't any color specified")){
                JOptionPane.showMessageDialog(this.myTask, ex.getMessage());
            }
        }
        
        updatePalettes();
    }
    
    /**
     * Metodo que carga las paletas del archivo de texto de la paletas en la aplicacion.
     * Actualiza el ComboBox de las paletas y el map mapaPaletas
     */
    public void updatePalettes() {
        String [] nombresPaletas=loadPalettes();
        this.comboBoxPaletas.removeAllItems();
        for (int i=0; i<nombresPaletas.length;i++){
            this.comboBoxPaletas.addItem(nombresPaletas[i]);
        }
    }
    
    /**
     * Metodo con el que comienza la ejecucion de la aplicacion.
     */
    public void start(){
        this.start.setEnabled(false);
        this.pause.setEnabled(true);
        this.stop.setEnabled(true);
        this.myTask.getView().setStoped(false);
        this.addSparksValues();
        this.addCoolingPointsValues();
        this.myTask.getFire().setRate(sliderFramesFire.getValue());
        this.myTask.getFire().setRunning(true);
        Thread hiloFire=new Thread(this.myTask.getFire());
        hiloFire.start();
    }
    
    /**
     * Metodo que es llamado cuando un stateChangedListener recibe un evento.
     * Dependiendo de cual sea el objeto que que ha recibido el evento cambia unos valores
     * o otros.
     * @param e evento
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider){
            JSlider slider=(JSlider)e.getSource();
            if(slider==sliderRefresco){
                if (!slider.getValueIsAdjusting()){
                    this.myTask.getFire().setCoolingTemp(slider.getValue()); 
                }
            } else if (slider==sliderFramesFire){
                if (!slider.getValueIsAdjusting()){
                    this.myTask.getFire().setRate(slider.getValue());
                }
            } else if (slider==sliderFramesCanvas){
                if (!slider.getValueIsAdjusting()){
                    this.myTask.getView().setRate(slider.getValue());
                }
            }
        } 
    }
    
    /**
     * Metodo con el que se para la ejecucion de la aplicacion.
     */
    public void stop(){
        this.start.setEnabled(true);
        this.pause.setEnabled(true);
        this.stop.setEnabled(false);
        this.myTask.getFire().setTemperatureTo0();
        this.myTask.getFire().setRunning(false);
        this.myTask.getView().setStoped(true);
    }
    
    public void initViewThread(){
        this.myTask.getView().setRate(sliderFramesCanvas.getValue());
        this.myTask.getView().setRunning(true);
        this.myTask.getView().setStoped(false);
        Thread hiloView=new Thread(this.myTask.getView());
        hiloView.start();
    }
}   
