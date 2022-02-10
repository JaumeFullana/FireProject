package proyectofuego;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JSpinner spinnerSparksFrom2;
    private JSpinner spinnerSparksTo2;
    private JSpinner spinnerSparksPercentage2;
    private JSpinner spinnerCoolingFrom;
    private JSpinner spinnerCoolingTo;
    private JSpinner spinnerCoolingPercentage;
    
    private JComboBox comboBoxPaletas;
    
    private JCheckBox checkBoxSparks2;
    
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
    
    /**
     * Setter normal
     * @param myTask  
     */
    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
    
    /**
     * Metodo que abre un JColorChooser para que se elija un color y cuando se elija el color
     * se guarde este en la tablaColores. Se da una posicion por defecto dependiendo de la 
     * cantidad de filas que haya en la tabla.
     */
    private void addColorTableRow(){
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
    private void addCoolingPointsValues(){
        if ((int)this.spinnerCoolingFrom.getValue()>this.myTask.getFlame().getMapaTemperatura()[0].length){
            this.spinnerCoolingFrom.setValue(this.myTask.getFlame().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerCoolingFrom.getValue()<0){
            this.spinnerCoolingFrom.setValue(0);
        }
        if ((int)this.spinnerCoolingTo.getValue()>this.myTask.getFlame().getMapaTemperatura()[0].length){
            this.spinnerCoolingTo.setValue(this.myTask.getFlame().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerCoolingTo.getValue()<0){
            this.spinnerCoolingTo.setValue(0);
        }
        this.myTask.getViewer().getFlame().setCoolingFromCol((int)this.spinnerCoolingFrom.getValue());
        this.myTask.getViewer().getFlame().setCoolingToCol((int)this.spinnerCoolingTo.getValue());
        this.myTask.getViewer().getFlame().setCoolingPercentage((int)this.spinnerCoolingPercentage.getValue());        
    }
    
    /**
     * Obtiene los valores que estan en la tablaPonderacion, los guarda en una 
     * matriz de doubles y la manda a la clase Flame. Tambien manda a esa clase 
     * el valor del spinnerDivisorPonderacio
     */
    private void addPonderationValues(){
        double [][] ponderationMatrix=new double[this.tablaPonderacion.getRowCount()][this.tablaPonderacion.getColumnCount()];
        for (int i=0; i<this.tablaPonderacion.getRowCount();i++){
            for (int j=0; j<this.tablaPonderacion.getColumnCount();j++){
                ponderationMatrix[i][j]=Double.parseDouble(this.tablaPonderacion.getValueAt(i, j).toString());
            }
        }
        this.myTask.getFlame().setPonderationMatrix(ponderationMatrix);
        
        this.myTask.getFlame().setPondDivisor(Double.parseDouble(this.spinnerDivisorPonderacio.getValue().toString()));
    }
    
    /**
     * Metodo que comprueva que los valores que se pasaran al metodo createSparks 
     * no se salgan de los inidices de la matriz de mapaTemperatura para luego 
     * pasarselos correctamente.
     */
    private void addSparksValues(){
        
        if ((int)this.spinnerSparksFrom.getValue()>this.myTask.getFlame().getMapaTemperatura()[0].length){
            this.spinnerSparksFrom.setValue(this.myTask.getFlame().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksFrom.getValue()<0){
            this.spinnerSparksFrom.setValue(0);
        }
        if ((int)this.spinnerSparksTo.getValue()>this.myTask.getFlame().getMapaTemperatura()[0].length){
            this.spinnerSparksTo.setValue(this.myTask.getFlame().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksTo.getValue()<0){
            this.spinnerSparksTo.setValue(0);
        }
        if ((int)this.spinnerSparksFrom2.getValue()>this.myTask.getFlame().getMapaTemperatura()[0].length){
            this.spinnerSparksFrom2.setValue(this.myTask.getFlame().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksFrom2.getValue()<0){
            this.spinnerSparksFrom2.setValue(0);
        }
        if ((int)this.spinnerSparksTo2.getValue()>this.myTask.getFlame().getMapaTemperatura()[0].length){
            this.spinnerSparksTo2.setValue(this.myTask.getFlame().getMapaTemperatura()[0].length);
        }
        if ((int)this.spinnerSparksTo2.getValue()<0){
            this.spinnerSparksTo2.setValue(0);
        }
        this.myTask.getViewer().getFlame().setSparksFromCol((int)this.spinnerSparksFrom.getValue());
        this.myTask.getViewer().getFlame().setSparksToCol((int)this.spinnerSparksTo.getValue());
        this.myTask.getViewer().getFlame().setSparksPercentage((int)this.spinnerSparksPercentage.getValue());
        this.myTask.getViewer().getFlame().setSparksFromCol2((int)this.spinnerSparksFrom2.getValue());
        this.myTask.getViewer().getFlame().setSparksToCol2((int)this.spinnerSparksTo2.getValue());
        this.myTask.getViewer().getFlame().setSparksPercentage2((int)this.spinnerSparksPercentage2.getValue());
    }
    
    /**
     * Metodo que elimina una de las paletas guardadas en el archivo paleta, tanto
     * del archivo como del programa. Para hacerlo leemos el fichero y lo volvemos
     * a escribir sin esa paletta. Despues los valores de la aplicacion se actualizan.
     * @param palette Nombre de la paleta a borrar.
     */
    private void deletePalette(String palette){
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
    private void deleteTableValues() {
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
    * Inicializa los botones de este JPanel y les añade listeners.
    */
    private void initButtons() {
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
    }

    /**
     * Inicializa el checkBoxSparks2 de este JPanel y le añade un listener.
     */
    private void initCheckBox() {
        this.checkBoxSparks2=new JCheckBox("Activate Sparks 2");
        this.checkBoxSparks2.setForeground(Color.white);
        this.checkBoxSparks2.setFont(new Font("Tahoma",Font.BOLD,12));
        this.checkBoxSparks2.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                if(checkBoxSparks2.isSelected()){
                    spinnerSparksFrom2.setEnabled(true);
                    spinnerSparksTo2.setEnabled(true);
                    spinnerSparksPercentage2.setEnabled(true);
                    myTask.getFlame().setSparks2Activated(true);
                } else {
                    spinnerSparksFrom2.setEnabled(false);
                    spinnerSparksTo2.setEnabled(false);
                    spinnerSparksPercentage2.setEnabled(false);
                    myTask.getFlame().setSparks2Activated(false);
                }
            }
        });
    }
    
    /**
     * Canvia el layout a GridBagLayout, inicializa todos los componentes, 
     * añade listeners a algunos componentes i posiciona todos los componentes.
     */
    private void initComponents(){
        this.setLayout(new GridBagLayout());
        
        this.listaColoresPaleta=new ArrayList <Color>();
        initLabels();
        initSliders();
        initSpinners();        
        initCheckBox();
        initButtons();
        initTables(); 
        
        String [] nombresPaletas=loadPalettes();
        this.comboBoxPaletas=new JComboBox(nombresPaletas);
               
        GridBagConstraints c=new GridBagConstraints();
        
        positionFirstHalf(c);
        positionSecondHalf(c);
    }
    
    /**
     * Inicializa los labels de este JPanel.
     */
    private void initLabels() {
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
    }
    
    /**
     * Inicializa los sliders de este JPanel.
     */
    private void initSliders() {
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
    }

    /**
     * Inicializa los Spinners de este JPanel.
     */
    private void initSpinners() {
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
    }
    
    /**
     * Inicializa las tablas de este JPanel.
     */
    private void initTables() {
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
    }
    
    /**
     * Metodo que accede al fichero de las paletas guardadas, para coger los valores
     * de estas. Coge el nombre de la paleta (que despues devuelve para ponerlos en
     * el comboBox de la paletas) y todos sus colores y posiciones de esos colores.
     * Guarda los colores y sus posiciones en una flamePalette y despues guarda el nombre
     * de esa paleta y la flamePalette en el Map mapaPaletas.
     * @return Array de strings donde se encuentran los nombres de las paletas
     */
    private String[] loadPalettes(){
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
    private void pause(){
        this.start.setEnabled(true);
        this.pause.setEnabled(false);
        this.stop.setEnabled(true);
        this.myTask.getFlame().setRunning(false);
    }
    
    /**
     * Metodo que coloca la tablaColores en su sitio.
     */
    private void placeTablaColores() {
        GridBagConstraints c=new GridBagConstraints();
        
        c.weightx=0.5;
        c.weighty=0.2;
        c.gridwidth=3;
        c.gridx=0;
        c.gridy=6;
        c.anchor=GridBagConstraints.SOUTH;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(5,10,0,20);
        this.add(this.tablaColores.getTableHeader(),c);
        c.gridx=0;
        c.gridy=7;
        c.anchor=GridBagConstraints.NORTH;
        c.insets=new Insets(0,10,0,20);
        this.add(this.tablaColores,c);
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
     * Posiciona la primera mitad de los componentes de este JPanel
     * @param c GridBagConstraint
     */
    private void positionFirstHalf(GridBagConstraints c) {
        this.positionComponent(0, 0, 4, 0.5, 0.8, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,0,0), c, this.titulo);
        this.positionComponent(0, 1, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,10,10,0), c, this.start);
        this.positionComponent(1, 1, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,0,10,0), c, this.pause);
        this.positionComponent(2, 1, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,0,10,20), c, this.stop);
        this.positionComponent(0, 2, 1, 0.5, 0.6, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,0,0), c, this.labelFramesFire);
        this.positionComponent(1, 2, 3, 0.5, 0.6, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(0,0,0,20), c, this.sliderFramesFire);
        this.positionComponent(0, 3, 1, 0.5, 0.6, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,0,0), c, this.labelFramesCanvas);
        this.positionComponent(1, 3, 3, 0.5, 0.6, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(0,0,0,20), c, this.sliderFramesCanvas);
        this.positionComponent(0, 4, 4, 0.5, 0.8, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,0,0), c, this.coloresPaleta);
        this.positionComponent(0, 5, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,10,5,0), c, this.botonAnadirColor);
        this.positionComponent(1, 5, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,0,5,0), c, this.botonGuardarPaleta);
        this.positionComponent(2, 5, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,0,5,20), c, this.botonBorrarColores);
        this.placeTablaColores();
        this.positionComponent(0, 8, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,10,10,0), c, this.comboBoxPaletas);
        this.positionComponent(1, 8, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,0,10,0), c, this.botonSeleccionarPaleta);
        this.positionComponent(2, 8, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,0,10,20), c, this.botonBorrarPaleta);
        this.positionComponent(0, 9, 4, 0.5, 0.4, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,0,0), c, this.perdidaCalorica);
        this.positionComponent(0, 10, 4, 0.5, 0.6, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(0,10,0,20), c, this.sliderRefresco);
    }

    /**
     * Posiciona la segunda mitad de los componentes de este JPanel
     * @param c GridBagConstraint
     */
    private void positionSecondHalf(GridBagConstraints c) {
        this.positionComponent(1, 11, 4, 0.5, 0.6, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,0,0), c, this.labelDesde);
        this.positionComponent(2, 11, 4, 0.8, 0.6, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,0,0), c, this.labelHasta);
        this.positionComponent(2, 11, 4, 0.8, 0.6, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0,0,0,20), c, this.labelPorcentage);
        this.positionComponent(0, 12, 4, 0.8, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,10,0), c, this.labelSparks);
        this.positionComponent(1, 12, 4, 0.8, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,10,0), c, this.spinnerSparksFrom);
        this.positionComponent(2, 12, 4, 0.8, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,10,0), c, this.spinnerSparksTo);
        this.positionComponent(2, 12, 4, 0.8, 0.2, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0,0,10,20), c, this.spinnerSparksPercentage);
        this.positionComponent(0, 13, 4, 0.5, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,10,0), c, this.checkBoxSparks2);
        this.positionComponent(1, 13, 4, 0.5, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,10,0), c, this.spinnerSparksFrom2);
        this.positionComponent(2, 13, 4, 0.5, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,10,0), c, this.spinnerSparksTo2);
        this.positionComponent(2, 13, 4, 0.5, 0.2, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0,0,10,20), c, this.spinnerSparksPercentage2);
        this.positionComponent(0, 14, 4, 0.5, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,5,10,0), c, this.labelCoolingPoints);
        this.positionComponent(1, 14, 4, 0.5, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,10,0), c, this.spinnerCoolingFrom);
        this.positionComponent(2, 14, 4, 0.5, 0.2, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,10,0), c, this.spinnerCoolingTo);
        this.positionComponent(2, 14, 4, 0.5, 0.2, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0,0,10,20), c, this.spinnerCoolingPercentage);
        this.positionComponent(0, 15, 3, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0,5,0,20), c, this.labelPonderacio);
        this.positionComponent(0, 16, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0,10,0,0), c, this.tablaPonderacion);
        this.positionComponent(1, 16, 1, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0,10,0,0), c, this.labelDivisorPonderacio); 
        this.positionComponent(2, 16, 1, 0.5, 0.4, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0,0,0,0), c, this.spinnerDivisorPonderacio);
        this.positionComponent(0, 17, 3, 0.5, 0.4, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(5,10,0,20), c, this.botoActualitzar);
    }
    
    /**
     * Metodo que accede a un fichero, lee su contenido y lo copia en una String 
     * que luego devuelve. La ruta al fichero la recibe por parametro.
     * @param file ruta al archivo que queremos leer
     * @return String una copia del contenido del archivo
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    private String readFile(File file) throws FileNotFoundException, IOException {
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
    private void savePalette(){
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
     * Metodo con el que comienza la ejecucion de la aplicacion.
     */
    private void start(){
        this.start.setEnabled(false);
        this.pause.setEnabled(true);
        this.stop.setEnabled(true);
        this.myTask.getViewer().setStoped(false);
        this.addSparksValues();
        this.addCoolingPointsValues();
        this.myTask.getFlame().setRate(sliderFramesFire.getValue());
        this.myTask.getFlame().setRunning(true);
        Thread hiloFire=new Thread(this.myTask.getFlame());
        hiloFire.start();
    }
    
    /**
     * Metodo con el que se para la ejecucion de la aplicacion.
     */
    private void stop(){
        this.start.setEnabled(true);
        this.pause.setEnabled(true);
        this.stop.setEnabled(false);
        this.myTask.getFlame().setTemperatureTo0();
        this.myTask.getFlame().setRunning(false);
        this.myTask.getViewer().setStoped(true);
    }
    
    /**
     * Metodo que carga las paletas del archivo de texto de la paletas en la aplicacion.
     * Actualiza el ComboBox de las paletas y el map mapaPaletas
     */
    private void updatePalettes() {
        String [] nombresPaletas=loadPalettes();
        this.comboBoxPaletas.removeAllItems();
        for (int i=0; i<nombresPaletas.length;i++){
            this.comboBoxPaletas.addItem(nombresPaletas[i]);
        }
    }
    
    /**
     * Metodo implementado de una interfaz.
     * @param e 
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
    
    /**
     * Metodo que inicializa en thread del Viewer
     */
    public void initViewThread(){
        this.myTask.getViewer().setRate(sliderFramesCanvas.getValue());
        this.myTask.getViewer().setRunning(true);
        this.myTask.getViewer().setStoped(false);
        Thread hiloView=new Thread(this.myTask.getViewer());
        hiloView.start();
    }
    /**
     * Metodo implementado de una interfaz.
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider){
            JSlider slider=(JSlider)e.getSource();
            if(slider==sliderRefresco){
                if (!slider.getValueIsAdjusting()){
                    this.myTask.getFlame().setCoolingTemp(slider.getValue()); 
                }
            } else if (slider==sliderFramesFire){
                if (!slider.getValueIsAdjusting()){
                    this.myTask.getFlame().setRate(slider.getValue());
                }
            } else if (slider==sliderFramesCanvas){
                if (!slider.getValueIsAdjusting()){
                    this.myTask.getViewer().setRate(slider.getValue());
                }
            }
        } 
    }
}   
