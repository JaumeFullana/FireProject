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
    private double [][] ponderationMatrix;
    
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
        
        double [][] matrix={{0,0.1,0},{0.2,0.5,0.8},{2.1,1.8,1.5}};
        this.ponderationMatrix=matrix;
        
        this.pondDivisor=695;
        this.convolateFire=false;
    }
    
    /**
     * Getter normal
     * @return mapaTemperatura
     */
    public int[][] getMapaTemperatura() {
        return mapaTemperatura;
    }
    
    /**
     * Setter normal
     * @param convolateFire  
     */
    public void setConvolateFire(boolean convolateFire) {
        this.convolateFire = convolateFire;
    }
    
    /**
     * Setter normal
     * @param coolingFromCol 
     */
    public void setCoolingFromCol(int coolingFromCol) {
        this.coolingFromCol = coolingFromCol;
    }
    
    /**
     * Setter normal
     * @param coolingPercentage 
     */
    public void setCoolingPercentage(int coolingPercentage) {
        this.coolingPercentage = coolingPercentage;
    }
    
    /**
     * Setter normal
     * @param coolingTemp 
     */
    public void setCoolingTemp(int coolingTemp) {
        this.coolingTemp = coolingTemp;
    }
    
    /**
     * Setter normal
     * @param coolingToCol 
     */
    public void setCoolingToCol(int coolingToCol) {
        this.coolingToCol = coolingToCol;
    }
    
    /**
     * Setter normal
     * @param mapaTemperatura 
     */
    public void setMapaTemperatura(int[][] mapaTemperatura) {
        this.mapaTemperatura = mapaTemperatura;
    }
    
    /**
     * Setter normal
     * @param mapaTemperatura2 
     */
    public void setMapaTemperatura2(int[][] mapaTemperatura2) {
        this.mapaTemperatura2 = mapaTemperatura2;
    }
    
    /**
     * Setter normal
     * @param pondDivisor 
     */
    public void setPondDivisor(double pondDivisor) {
        this.pondDivisor = pondDivisor;
    }
    
    /**
     * Setter normal
     * @param ponderationMatrix 
     */
    public void setPonderationMatrix(double[][] ponderationMatrix) {
        this.ponderationMatrix = ponderationMatrix;
    }
    
    /**
     * Setter normal
     * @param running 
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    /**
     * Setter normal
     * @param sparksFromCol 
     */
    public void setSparksFromCol(int sparksFromCol) {
        this.sparksFromCol = sparksFromCol;
    }
    
    /**
     * Setter normal
     * @param sparksFromCol2 
     */
    public void setSparksFromCol2(int sparksFromCol2) {
        this.sparksFromCol2 = sparksFromCol2;
    }
    
    /**
     * Setter normal
     * @param sparksPercentage 
     */
    public void setSparksPercentage(int sparksPercentage) {
        this.sparksPercentage = sparksPercentage;
    }
    
    /**
     * Setter normal
     * @param sparksPercentage2 
     */
    public void setSparksPercentage2(int sparksPercentage2) {
        this.sparksPercentage2 = sparksPercentage2;
    }
    
    /**
     * Setter normal
     * @param sparksToCol 
     */
    public void setSparksToCol(int sparksToCol) {
        this.sparksToCol = sparksToCol;
    }
    
    /**
     * Setter normal
     * @param sparksToCol2 
     */
    public void setSparksToCol2(int sparksToCol2) {
        this.sparksToCol2 = sparksToCol2;
    }
    
    /**
     * Setter normal
     * @param sparks2Activated 
     */
    public void setSparks2Activated(boolean sparks2Activated) {
        this.sparks2Activated = sparks2Activated;
    }
    
    /**
     * Hay posibilidades de que añada un coolingPoint, pone temperatura a 0, en 
     * un punto del mapa de temperatura.
     * @param percentage porcentage de que se ponga el cooling point
     * @param r Random
     * @param i posicion vertical donde se pondra el cooling point
     * @param j posicion horizontal donde se pondra el cooling point
     */
    private void createCoolingPoint(int percentage, Random r, int i, int j) {
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
    
    /**
     * Hay posibilidades de que añada un spark, pone temperatura a 255, en 
     * un punto del mapa de temperatura.
     * @param percentage porcentage de que se ponga la spark
     * @param r Random
     * @param i posicion vertical donde se pondra la spark
     * @param j posicion horizontal donde se pondra la spark
     */
    private void CreateSpark(int percentage, Random r, int i, int j) {
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
    private void flameEvolve(){
        if (convolateFire){
            this.searchSparksPositions();
        }
        else {
            this.createSparks(this.sparksFromCol,this.sparksToCol,this.mapaTemperatura2.length-1,this.sparksPercentage);
            if (sparks2Activated){
                this.createSparks(this.sparksFromCol2,this.sparksToCol2,this.mapaTemperatura2.length-1,this.sparksPercentage2);
            }
        }
        this.createCoolingPoints(this.coolingFromCol,this.coolingToCol,this.mapaTemperatura2.length-1,this.coolingPercentage);
        for (int i=this.getMapaTemperatura().length-2; i>0;i--){
            for (int j=this.getMapaTemperatura()[i].length-2; j>0;j--){
                int temperatura=this.temperatureEvolve(i, j);
                if (temperatura>255){
                    temperatura=255;
                } 
                else if (temperatura<0){
                    temperatura=0;
                }
                this.mapaTemperatura2[i][j]=temperatura;
            }
        }
        this.createFlameImage();
        this.setMapaTemperatura(this.mapaTemperatura2);
    }
    
    /**
     * Busca la posicion en la que colocar las sparks dependiendo de la luminosidad 
     * que hay en ese pixel.
     */
    private void searchSparksPositions(){
        Random r=new Random();
        for (int i=this.myTask.getViewer().getConvolutedImage().getHeight()-2; i>0;i--){
            for (int j=this.myTask.getViewer().getConvolutedImage().getWidth()-2; j>0;j--){
                int pixelColor=this.myTask.getViewer().getConvolutedImage().getRGB(j, i);
                int lum = (77*((pixelColor>>16)&255) + 150*((pixelColor>>8)&255) + 29*((pixelColor)&255))>>8;
                if (lum>20){
                    this.CreateSpark(sparksPercentage, r, i, j);
                    this.createCoolingPoint(coolingPercentage, r, i, j);
                }
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
        double sumTemperatura=0;
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                sumTemperatura+=this.getMapaTemperatura()[y+(i)][x+(j)]*this.ponderationMatrix[i+1][j+1];
            }
        }
        return (int)(((sumTemperatura)/(pondDivisor/100))-coolingTemp/10);
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
    
    /**
     * Metodo que asigna una paleta de colores con colores parecidos a los de un
     * fuego real
     */
    public void setPalette(){
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
