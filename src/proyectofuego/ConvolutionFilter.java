/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofuego;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author PC
 */
public class ConvolutionFilter {
    
    /*Pensa que s'ha de intentar emplear una arquitectura mvc (model vista controlador), 
    per tant les clases creades ara son temporals, s'ha de pensar quines clases necesiterem
    i com ho farem perque quedi be*/
    
    /*Calcular valors de un pixel depenguent del rgb dels pixels de debora(cada un amb el seu pes
    o amb el mateix, es sumarien tots i es dividirien per el nombres de pixels empleats), aixo es
    sol fe amb es pixels exactament de debora per tant serien 9 pixels en forma de una matriu de 3x3,
    aquesta matriu 3x3 s'anomena kernel de convolucio, el cual tendrem i emplearem per tots els filtres 
    simplement cambiant el seu valors, s'aplicacio del filtre no es fa damunt la imtage original sino
    damunt una copia, ja que necesitam tot es temps els pixels de la imatge original(apart de que despres
    per posar el foc haurem de tenir aquesta imatge original tambe)*/
    
    
    private int [][] convolutionKernel;

    public ConvolutionFilter(int[][] convolutionKernel) {
        this.convolutionKernel = convolutionKernel;
    }

    public int[][] getConvolutionKernel() {
        return convolutionKernel;
    }

    public void setConvolutionKernel(int[][] convolutionKernel) {
        this.convolutionKernel = convolutionKernel;
    }
    
    public BufferedImage applyConvolutionFilter(BufferedImage bufferedImage){
        BufferedImage convulatedImage=new BufferedImage(bufferedImage.getWidth(),
            bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        
        for (int i=1;i<bufferedImage.getHeight()-1;i++){
            for (int j=1;j<bufferedImage.getWidth()-1;j++){
                
                int pixelConvulated=this.getConvulation(bufferedImage, j, i);
                
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
    
    private int convulatePixel(int [][] pixels){
        
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
        
        Color convulatedColor=null;

        if (Math.abs(this.convolutionKernel[1][1]-1)==kernelTotalValue-(this.convolutionKernel[1][1])){
            int intTotalRed=(int)Math.round(totalRed);
            int intTotalGreen=(int)Math.round(totalGreen);
            int intTotalBlue=(int)Math.round(totalBlue);

            int [] list=checkValues(intTotalRed, intTotalGreen, intTotalBlue);

            if (list[0]>255 || list[0]<0 || list[1]>255 || list[1]<0 || list[2]>255 || list[2]<0){
                System.out.println("shit");
            }

            convulatedColor=new Color(list[0],list[1],list[2]);
        }
        else{
            convulatedColor=new Color(this.checkValue((int)Math.round(totalRed)),this.checkValue((int)Math.round(totalGreen)),
                    this.checkValue((int)Math.round(totalBlue)));
        }
        int pixelConvulated=convulatedColor.getRGB();
                
        return pixelConvulated;
    }
    
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
    
    private int checkValue(int value){
        
        
        if (value<0){
            value=0;
        }
        
        if (value>255){
            value=255;
        }
        
        
        return value;
    }
    
    private int getConvulation(BufferedImage bufferedImage ,int x, int y){
        
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
        
        int pixelConvulated=this.convulatePixel(pixels);
        
        return pixelConvulated;
    }
}
