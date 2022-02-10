package proyectofuego;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jaume
 */
public class ConvolutionFilter {

    private int [][] convolutionKernel;

    public ConvolutionFilter(int[][] convolutionKernel) {
        this.convolutionKernel = convolutionKernel;
    }
    
    /**
     * Setter normal
     * @param convolutionKernel  
     */
    public void setConvolutionKernel(int[][] convolutionKernel) {
        this.convolutionKernel = convolutionKernel;
    }
    
    /**
     * Dependiendo de los valores del kernel comprobamos los valores de los canales
     * rojo, verde y azul de una manera diferente. Devuelve el color que es la 
     * combinacion de los valores del color rojo, verde y azul pasados por parametro.
     * @param kernelTotalValue La suma de los valores del kernel
     * @param totalRed valor del canal rojo
     * @param totalGreen valor del canal verde
     * @param totalBlue valor del canal azul
     * @return Color combinacion del rojo, verde y azul
     */
    private Color checkPosibleConvolution(double kernelTotalValue, double totalRed, double totalGreen, double totalBlue) {
        Color convulatedColor=null;
        if (Math.abs(this.convolutionKernel[1][1]-1)==kernelTotalValue-(this.convolutionKernel[1][1])){
            int intTotalRed=(int)Math.round(totalRed);
            int intTotalGreen=(int)Math.round(totalGreen);
            int intTotalBlue=(int)Math.round(totalBlue);

            int [] list=checkValues(intTotalRed, intTotalGreen, intTotalBlue);
            
            convulatedColor=new Color(list[0],list[1],list[2]);
        }
        else{
            convulatedColor=new Color(this.checkValue((int)Math.round(totalRed)),this.checkValue((int)Math.round(totalGreen)),
                    this.checkValue((int)Math.round(totalBlue)));
        }
        return convulatedColor;
    }
    
    /**
     * Comprueba si el valor del int pasado por parametro es menor que 0 o 
     * mayor que 255.
     * @param value int 
     * @return El valor recibido por parametro que puede haber sido cambiado
     */
    private int checkValue(int value){ 
        if (value<0){
            value=0;
        }
        
        if (value>255){
            value=255;
        }
        return value;
    }
    
    /**
     * Ajusta los valores de los tres canales de una manera relativa, asegurandose 
     * que la distancia entre los diferentes canales es respectada despues de que 
     * los valores hayan cambiado para estar entre el 0 y el 255.
     * @param red valor del canal rojo
     * @param green valor del canal verde
     * @param blue valor del canal azul
     * @return int [] donde estan los valores de red, green y blue
     */
    private int [] checkValues(int red, int green, int blue){        
        double min=Math.min(Math.min(red, blue),green);
        double max=Math.max(Math.max(red, blue),green);
        
        if (min<0 && max+(Math.abs(min))>255 || max>255 && min-(max-255)<0){
            double realMax=max;
            double realMin=min;
            double divisor=1.0;
            while (max-(min)>255){
                divisor+=0.1;
                max=realMax/divisor;
                min=realMin/divisor;
            }
            red/=divisor;
            blue/=divisor;
            green/=divisor;
        }
        
        if (min<0 && max+(Math.abs(min))<255){
            red+=(Math.abs(min));
            blue+=(Math.abs(min));
            green+=(Math.abs(min));
        } 
        else if (max>255 && min-(max-255)>=0){
            red-=(max-255);
            blue-=(max-255);
            green-=(max-255);
        } 
           
        int [] list = {checkValue(red), checkValue(green), checkValue(blue)};
        return list;
    }
    
    /**
     * Recive una matriz de pixeles y convoluciona el pixel con los valores del 
     * convolutionKernel. Retorna el rgb de un color que es el resultado de la 
     * convolucion.
     * @param pixels matriz de pixeles
     * @return int representa el rgb de un color
     */
    private int convolatePixel(int [][] pixels){ 
        double totalRed=0;
        double totalGreen=0;
        double totalBlue=0;
        double kernelTotalValue=0;
        
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                
                kernelTotalValue+=this.convolutionKernel[i][j];
                Color color = new Color(pixels [i][j]);
                
                double red=color.getRed();
                double green=color.getGreen();
                double blue=color.getBlue();
                
                totalRed+=red*this.convolutionKernel[i][j];
                totalGreen+=green*this.convolutionKernel[i][j];
                totalBlue+=blue*this.convolutionKernel[i][j];
            }
        }
        
        if (kernelTotalValue!=0){
            
            double multiplier=1.0/kernelTotalValue;
            
            totalRed*=multiplier;
            totalGreen*=multiplier;
            totalBlue*=multiplier;
        }
        
        Color convulatedColor=checkPosibleConvolution(kernelTotalValue, totalRed, totalGreen, totalBlue);
        int pixelConvulated=convulatedColor.getRGB();        
        return pixelConvulated;
    }
    
    /**
     * Recive una BufferedImage y dos ints que representan la posicion de un pixel 
     * en esa imagen, obtiene el rgb de ese pixel i de todos los pixels de alrededor, 
     * guardando esos pixels en una matriz de ints y convolucionando esa matriz 
     * dentro de un int, que es el rgb del resultado de la convolucion. Devuelve 
     * un int que es el valor rgb.
     * @param bufferedImage una imagen
     * @param x posicion horizontal del pixel
     * @param y posicion vertical del pixel
     * @return int que es el valor rgb resultante de la convolucion
     */
    private int getConvolution(BufferedImage bufferedImage ,int x, int y){
        
        int [][] pixels=new int [3][3];
        
        pixels[0][0]=bufferedImage.getRGB(x-1, y-1);
        pixels[0][1]=bufferedImage.getRGB(x, y-1);
        pixels[0][2]=bufferedImage.getRGB(x+1, y-1);
        pixels[1][0]=bufferedImage.getRGB(x-1, y);
        pixels[1][1]=bufferedImage.getRGB(x, y);
        pixels[1][2]=bufferedImage.getRGB(x+1, y);
        pixels[2][0]=bufferedImage.getRGB(x-1, y+1);
        pixels[2][1]=bufferedImage.getRGB(x, y+1);
        pixels[2][2]=bufferedImage.getRGB(x+1, y+1);
        
        int pixelConvulated=this.convolatePixel(pixels);
        
        return pixelConvulated;
    }
     
    /**
     * Recive una BufferedImage por parametro y recorre sus pixeles convolucionando 
     * cada uno de ellos y copiandolos e una nueva BufferedImage. Al final devulve
     * la nueva BufferedImage
     * @param bufferedImage una imagen
     * @return bufferedImage la convolucion de la BufferedImage recivida 
     */
    public BufferedImage applyConvolutionFilter(BufferedImage bufferedImage){
        BufferedImage convulatedImage=new BufferedImage(bufferedImage.getWidth(),
            bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        
        for (int i=1;i<bufferedImage.getHeight()-1;i++){
            for (int j=1;j<bufferedImage.getWidth()-1;j++){
                
                int pixelConvulated=this.getConvolution(bufferedImage, j, i);
                
                if (pixelConvulated==-1){
                    convulatedImage.setRGB(j, i, bufferedImage.getRGB(j, i));
                }
                else {
                    convulatedImage.setRGB(j, i, pixelConvulated);
                }    
            }
        }
        return convulatedImage;
    }
}
