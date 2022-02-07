package proyectofuego;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 *
 * @author Jaume
 */
public class Flame extends BufferedImage implements Runnable{
    
    private MyTask myTask;
    private int [][] mapaTemperatura;
    private int [][] mapaTemperatura2;
    
    private double pondNorthWest;
    private double pondNorthCenter;
    private double pondNorthEast;
    private double pondCenterWest;
    private double pondCenterCenter;
    private double pondCenterEast;
    private double pondSouthWest;
    private double pondSouthCenter;
    private double pondSouthEast;
    
    private double pondDivisor;
    
    private int coolingTemp;
    private boolean running;
    private boolean sparks2Activated;
    private int sparksFromCol;
    private int sparksToCol;
    private int sparksPercentage;
    private int sparksFromCol2;
    private int sparksToCol2;
    private int sparksPercentage2;
    private int coolingFromCol;
    private int coolingToCol;
    private int coolingPercentage;
    private int fps=200;
    private int skip_ticks=1000/fps;
    private boolean convolateFire;
    
    public Flame(int width, int height, int imageType, MyTask myTask) {
        super(width, height, imageType);
        this.myTask=myTask;
        this.coolingTemp=12;
        
        this.pondNorthCenter=0.1;
        this.pondNorthEast=0;
        this.pondNorthWest=0;
        this.pondCenterCenter=0.5;
        this.pondCenterEast=0.8;
        this.pondCenterWest=0.2;
        this.pondSouthCenter=1.8;
        this.pondSouthEast=1.5;
        this.pondSouthWest=2.1;
        
        this.pondDivisor=695;
        this.convolateFire=false;
    }
    
    
    
    public int[][] getMapaTemperatura() {
        return mapaTemperatura;
    }

    public void setConvolateFire(boolean convolateFire) {
        this.convolateFire = convolateFire;
    }
    
    public void setCoolingFromCol(int coolingFromCol) {
        this.coolingFromCol = coolingFromCol;
    }

    public void setCoolingPercentage(int coolingPercentage) {
        this.coolingPercentage = coolingPercentage;
    }
    
    public void setCoolingTemp(int coolingTemp) {
        this.coolingTemp = coolingTemp;
    }
    
    public void setCoolingToCol(int coolingToCol) {
        this.coolingToCol = coolingToCol;
    }
    
    public void setMapaTemperatura(int[][] mapaTemperatura) {
        this.mapaTemperatura = mapaTemperatura;
    }

    public void setMapaTemperatura2(int[][] mapaTemperatura2) {
        this.mapaTemperatura2 = mapaTemperatura2;
    }
    
    public void setPondCenterCenter(double pondCenterCenter) {
        this.pondCenterCenter = pondCenterCenter;
    }

    public void setPondCenterEast(double pondCenterEast) {
        this.pondCenterEast = pondCenterEast;
    }
    
    public void setPondCenterWest(double pondCenterWest) {
        this.pondCenterWest = pondCenterWest;
    }

    public void setPondDivisor(double pondDivisor) {
        this.pondDivisor = pondDivisor;
    }
    
    public void setPondNorthCenter(double pondNorthCenter) {
        this.pondNorthCenter = pondNorthCenter;
    }

    public void setPondNorthEast(double pondNorthEast) {
        this.pondNorthEast = pondNorthEast;
    }

    public void setPondNorthWest(double pondNorthWest) {
        this.pondNorthWest = pondNorthWest;
    }
    
    public void setPondSouthCenter(double pondSouthCenter) {
        this.pondSouthCenter = pondSouthCenter;
    }

    public void setPondSouthEast(double pondSouthEast) {
        this.pondSouthEast = pondSouthEast;
    }

    public void setPondSouthWest(double pondSouthWest) {
        this.pondSouthWest = pondSouthWest;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public void setSparksFromCol(int sparksFromCol) {
        this.sparksFromCol = sparksFromCol;
    }
    
    public void setSparksFromCol2(int sparksFromCol2) {
        this.sparksFromCol2 = sparksFromCol2;
    }
    
    public void setSparksPercentage(int sparksPercentage) {
        this.sparksPercentage = sparksPercentage;
    }
    
    public void setSparksPercentage2(int sparksPercentage2) {
        this.sparksPercentage2 = sparksPercentage2;
    }
    
    public void setSparksToCol(int sparksToCol) {
        this.sparksToCol = sparksToCol;
    }
    
    public void setSparksToCol2(int sparksToCol2) {
        this.sparksToCol2 = sparksToCol2;
    }

    public void setSparks2Activated(boolean sparks2Activated) {
        this.sparks2Activated = sparks2Activated;
    }
    
    public void createCoolingPoint(int percentage, Random r, int i, int j) {
        if (percentage>=r.nextInt(10)+1){
            this.mapaTemperatura2[i][j]=0;
        }
    }
    
    /**
     * Metodo que asigna puntos frios, el valor 0, a algunos indices de la
     * matriz MapaTemperatura2. Estos indices pueden ser aleatorios i dependen 
     * de las variables que se pasan por parametro.
     * @param FromCol int columna desde la que se empezara a recorres el mapaTemperatura
     * @param ToCol int columna hasta la que se recorre el mapaTemperatura, una vez
     * llegada a esta se parara
     * @param fromRow desde la fila que se recorre el mapaTemperatura
     * @param percentage porcentage de que se asigne el valor 0 en cada indice
     */
    private void createCoolingPoints(double fromCol, double toCol, double fromRow,int percentage){
        Random r=new Random();
        for (int i=(int)fromRow; i<this.mapaTemperatura2.length;i++){
            for (int j=(int)fromCol; j<toCol;j++){
                createCoolingPoint(percentage, r, i, j);
            }
        }
    }
    
    /**
     * Metodo que asigna los colores a cada pixel de la bufferedImage. Recorre 
     * la matriz mapaTemperatura y pasa el valor de cada indice al metodo getColor 
     * de la Clase FlamePalette para que este devuelva un color que es asignado 
     * a la posicion en la que se encuentra el mapaTemperatura a la BufferedImage del fuego.
     */
    private void createFlameImage(){
        for (int i=0; i<mapaTemperatura.length;i++){
            for (int j=0; j<mapaTemperatura[i].length;j++){
                int temperatura=mapaTemperatura[i][j];
                this.setRGB(j, i,  this.myTask.getFlamePalette().getColor(temperatura).getRGB());
            }
        }
    }
    
    
    public void CreateSpark(int percentage, Random r, int i, int j) {
        if (percentage>=r.nextInt(10)+1){
            this.mapaTemperatura2[i][j]=255;
        }
    }
    
    /**
     * Metodo que asigna puntos calientes, el valor 255, a algunos indices de la
     * matriz MapaTemperatura2. Estos indices pueden ser aleatorios i dependen 
     * de las variables que se pasan por parametro.
     * @param sparksFromCol int columna desde la que se empezara a recorres el mapaTemperatura
     * @param sparksToCol int columna hasta la que se recorre el mapaTemperatura, una vez
     * llegada a esta se parara
     * @param fromRow desde la fila que se recorre el mapaTemperatura
     * @param percentage porcentage de que se asigne el valor 255 en cada indice
     */
    private void createSparks(int sparksFromCol, int sparksToCol, float fromRow, int percentage){
        Random r=new Random();
        for (int i=(int)fromRow; i<this.mapaTemperatura2.length;i++){
            for (int j=sparksFromCol; j<sparksToCol;j++){
                CreateSpark(percentage, r, i, j);
            }
        }
    }
    
    /**
     * Metodo que hace una media de la temperatura de un indice concreto del 
     * mapaTemperatura con su temperatura y la temperatura de sus indices vecinos.
     * En la media hay algunas modificaciones.
     * @param y int indice de la fila de la matriz mapaTemperatura
     * @param x int indice de la columna de la matriz mapaTemperatura
     * @return int temperatura media resultante del calculo
     */
    private int temperatureEvolve(int y, int x){
        /*int temperaturaMedia=(int) (((this.getMapaTemperatura()[y-1][x]*0.1
                +this.getMapaTemperatura()[y][x]*0.5
                +this.getMapaTemperatura()[y][x-1]*0.5
                +this.getMapaTemperatura()[y][x+1]*0.5
                +this.getMapaTemperatura()[y+1][x+1]*1.8
                +this.getMapaTemperatura()[y+1][x-1]*1.8
                +this.getMapaTemperatura()[y+1][x]*1.8)/6.95)-coolingTemp/10);
        */
        
        int temperaturaMedia=(int) (((this.getMapaTemperatura()[y-1][x]*pondNorthCenter
                +this.getMapaTemperatura()[y-1][x+1]*pondNorthEast
                +this.getMapaTemperatura()[y-1][x-1]*pondNorthWest
                +this.getMapaTemperatura()[y][x]*pondCenterCenter
                +this.getMapaTemperatura()[y][x+1]*pondCenterEast
                +this.getMapaTemperatura()[y][x-1]*pondCenterWest
                +this.getMapaTemperatura()[y+1][x]*pondSouthCenter
                +this.getMapaTemperatura()[y+1][x+1]*pondSouthEast
                +this.getMapaTemperatura()[y+1][x-1]*pondSouthWest)/(pondDivisor/100))-coolingTemp/10);
        
        return temperaturaMedia;
    }
    
    /**
     * Metodo en el que se guarda la logica de la evolucion de la llama. Se van 
     * creando puntos calientes y frios, llamando a los metodos createSparks y 
     * createCool, segun los valores asignados por el panel de control. Luego se
     * recorre el mapaTemperatura haciendo una media de la temperatura de un indice
     * y sus indices de al lado mas alguna modificacion llamando al metodo 
     * temperatureEvolve. Luego el valor es asignado a un indice de una fila menor
     * en el mapaTemperatura2, para que el fuego vaya subiendo. Una vez terminado
     * el bucle llama al metodo createFlameImage y asigna los valores del 
     * mapaTemperatura2 al mapaTemperatura.
     */
    public void flameEvolve(){
        if (convolateFire){
            this.searchSparkPositions();
            //this.searchCoolingPositions();
        }
        else {
            this.createSparks(this.sparksFromCol,this.sparksToCol,this.mapaTemperatura2.length-1,this.sparksPercentage);
            if (sparks2Activated){
                this.createSparks(this.sparksFromCol2,this.sparksToCol2,this.mapaTemperatura2.length-1,this.sparksPercentage2);
            }
        }
        this.createCoolingPoints(this.coolingFromCol,this.coolingToCol,this.mapaTemperatura2.length-1,this.coolingPercentage);
        //for (int i=1; i<this.getMapaTemperatura().length-1;i++){
            //for (int j=1; j<this.getMapaTemperatura()[i].length-1;j++){
        for (int i=this.getMapaTemperatura().length-2; i>0;i--){
            for (int j=this.getMapaTemperatura()[i].length-2; j>0;j--){
                int temperatura=this.temperatureEvolve(i, j);
                if (temperatura>255){
                    temperatura=255;
                } 
                else if (temperatura<0){
                    temperatura=0;
                }
                //Aqui esta es problema
                //this.mapaTemperatura2[i-1][j]=temperatura;
                this.mapaTemperatura2[i][j]=temperatura;
            }
        }
        this.createFlameImage();
        this.setMapaTemperatura(this.mapaTemperatura2);
    }
    
    /**
     * Metodo que va llamando al metodo flameEvolve mientras el atributo running
     * sea true. Este metodo es heredado de la interfaz runnable y empieza a correr cuando 
     * llamamos al metodo start en un Thread en el cual hemos metido un objeto de esta clase.
     * Tiene una thread sleep para que la ejecucion se vaya pausando dependiendo de los frames
     * por segundo que se hayan asignado.
     */
    @Override
    public void run() {
        while(running){
            if(this.getMapaTemperatura()!=null){ 
                this.flameEvolve();
            }
            try {
                Thread.sleep(skip_ticks);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void searchSparkPositions(){
        Random r=new Random();
        for (int i=this.myTask.getView().getConvolutedImage().getHeight()-2; i>0;i--){
            for (int j=this.myTask.getView().getConvolutedImage().getWidth()-2; j>0;j--){
                int pixelColor=this.myTask.getView().getConvolutedImage().getRGB(j, i);
                int lum = (77*((pixelColor>>16)&255) + 150*((pixelColor>>8)&255) + 29*((pixelColor)&255))>>8;
                if (lum>20){
                    this.CreateSpark(sparksPercentage, r, i, j);
                    this.createCoolingPoint(coolingPercentage, r, i, j);
                }
            }
        }
    }
    
    /**
     * Metodo que asigna una paleta de colores con colores parecidos a los de un
     * fuego real
     */
    public void setPalette(){
        //fe if que cuigui valors, sino aixo
        FlamePalette palette=new FlamePalette();
        palette.addTargetColor(new TargetColor(0,new Color(0,0,0,0)));
        palette.addTargetColor(new TargetColor(30,new Color(255,0,0,100)));
        palette.addTargetColor(new TargetColor(45,new Color(255,90,0,200)));
        palette.addTargetColor(new TargetColor(80,new Color(255,160,0,200)));
        palette.addTargetColor(new TargetColor(100,new Color(255,220,0,200)));
        palette.addTargetColor(new TargetColor(130,new Color(255,250,0,200)));
        palette.addTargetColor(new TargetColor(230,new Color(255,250,255,200)));
        palette.addTargetColor(new TargetColor(254,new Color(255,250,255,200)));
        palette.addTargetColor(new TargetColor(255,new Color(255,255,255,255)));
        this.myTask.setFlamePalette(palette);
    }
    
    /**
     * Metodo que asigna los fps, frames por segundo, i los skip_ticks, que es 
     * el tiempo de pausa que tendra el bucle principal de la clase que ira evolucinando el fuego.
     * @param rate int, frames por segundo que se veran en el programa.
     */
    public void setRate(int rate){
        this.fps=rate;
        this.skip_ticks=1000/rate;
    }
    
    /**
     * Metodo que asigna a cada indice de las dos matrizes de mapaTemperatura el valor 0.
     */
    public void setTemperatureTo0(){
        for (int i=0; i<mapaTemperatura.length;i++){
            for (int j=0; j<mapaTemperatura[i].length;j++){
                mapaTemperatura[i][j]=0;
                mapaTemperatura2[i][j]=0;
            }
        }
    } 
}
