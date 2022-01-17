package proyectofuego;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Jaume
 */
public class FlamePalette {
    
    private Color [] colorPalette;
    private ArrayList <TargetColor> listaTargetColors;

    public FlamePalette() {
        this.listaTargetColors=new ArrayList <TargetColor>();
    }
    
    /**
     * Metodo que crea la paleta de colores. Primero instancia la colorPalette, 
     * luego ordena la ListaTargetColors, del targetColor con la posicion mas
     * pequeña al TargetColor con la posicion mas grande. Para terminar recorre
     * la listaTargetColors i va llamando al metodo interpolateColors en cada 
     * iteracion.
     */
    private void createColors(){
        
        colorPalette=new Color[256];
        
        boolean moving=true;
        while (moving){
            moving=false;
            for(int i=0;i<this.listaTargetColors.size()-1;i++){
                if(this.listaTargetColors.get(i).getPosition()>this.listaTargetColors.get(i+1).getPosition()){
                    TargetColor aux=this.listaTargetColors.get(i);
                    this.listaTargetColors.set(i, this.listaTargetColors.get(i+1));
                    this.listaTargetColors.set(i+1, aux);
                    moving=true;
                }
            }
        }
        
        for (int i=0; i<this.listaTargetColors.size()-1;i++){
            this.interpolateColors(this.listaTargetColors.get(i), this.listaTargetColors.get(i+1));
        }
    }
    
    /**
     * Metodo que hace la interpolacion entre dos colores desde una posicion inicial hasta una posicion final.
     * Se le pasan dos TragetColors, extrae el ARGB de los dos colores guardados en ellos, despues calcula los pasos
     * que hay entre la posicion guardada en uno y la posicion guardada en el otro y por cada paso que hay entre ellos
     * divide los valores ARGB para ir sumando el resultado de esta division al color ARGB del targetFromColor en cada paso
     * i guardarlo en la array colorPalette en la posicion del paso en el que se encuentra hasta llegar a la posicion del TargetEndColor.
     * 
     * @param TargetFromColor TargetColor en el que se encuentra la posicion desde la que comienza la interpolacion
     * @param TargetEndColor TargetColor en el que se encuentra la posicion en la que termina la interpolacion
     */
    private void interpolateColors(TargetColor TargetFromColor, TargetColor TargetEndColor){
        
        Color targetFrom=TargetFromColor.getColor();
        int aFrom=targetFrom.getAlpha();
        int rFrom=targetFrom.getRed();
        int gFrom=targetFrom.getGreen();
        int bFrom=targetFrom.getBlue();
        
        Color targetEnd=TargetEndColor.getColor();
        int aEnd=targetEnd.getAlpha();
        int rEnd=targetEnd.getRed();
        int gEnd=targetEnd.getGreen();
        int bEnd=targetEnd.getBlue();
        
        int steps=TargetEndColor.getPosition()-TargetFromColor.getPosition();
        
        int aSteps=(aEnd-aFrom)/steps;
        int rSteps=(rEnd-rFrom)/steps;
        int gSteps=(gEnd-gFrom)/steps;
        int bSteps=(bEnd-bFrom)/steps;
        
        this.colorPalette[TargetFromColor.getPosition()]=TargetFromColor.getColor();
        this.colorPalette[TargetEndColor.getPosition()]=TargetEndColor.getColor();
        for (int i=TargetFromColor.getPosition()+1; i<TargetEndColor.getPosition();i++){
            
            aFrom+=aSteps;
            rFrom+=rSteps;
            gFrom+=gSteps;
            bFrom+=bSteps;
            Color newColor=new Color((aFrom<<24) | (rFrom<<16) | (gFrom<<8) | bFrom, true);
            this.colorPalette[i]=newColor;
        }
    }
    
    /**
     * Metodo al que se le pasa un TargetColor y este lo asigna a la ArrayList
     * listaTargetColors
     * @param targetColor objeto de la clase TargetColor que guarda una posicion y un color
     */
    public void addTargetColor(TargetColor targetColor){ 
        this.listaTargetColors.add(targetColor);
    }
    
    /**
     * Metodo que retorna el color guardado en la Array colorPalette en la posicion 
     * que se pasa por parametro. Si la colorPalette es null, primero llama al metodo
     * createPalette.
     * @param temperature int posicion de la array de la que sacar el color a retornar
     * @return Color que se encontraba en la posicion pasada por parametro del array 
     */
    public Color getColor(int temperature){
        if (colorPalette==null){
            this.createColors();
        }
        return this.colorPalette[temperature];
    }
}
