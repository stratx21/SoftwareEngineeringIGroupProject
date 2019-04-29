package DysfunctionalDesigners.CompSciMerchStore;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GUIdo_OutputTools {
	/**
     * This function is used for the input of a long String that should be  
     *  condensed into several lines for the user interface, but each line 
     *  should not be longer than maxPixelWidth pixels long. 
     * 
     * ERR:: the function will not work if single words are longer than the
     *      maxPixelWidth
     * 
     * @param text the long String to be formatted into multiple lines
     * @param font the font that is being used to draw the String text
     * @param maxPixelWidth the max length of one line in pixels
     * @return an ArrayList of type String that contains each line that should
     *      be displayed based on how long each line is in pixels
     */
    public static ArrayList<String> formatStringForPrompt(String text,Font font,int maxPixelWidth){
        ArrayList<String> line_list=new ArrayList<String>();
        if(getPixelWidth(text,font)<=maxPixelWidth){ //default case just works
            line_list.add(text);
            return line_list;
        }
        //PRE [to this point in flow]:: the string is longer than what can be output in one line properly
        int lastLength=0;
        boolean totalDone=false;
        while(!totalDone
                &&text.length()>0
                &&lastLength!=text.length()){
            lastLength=text.length();
            boolean done=false,alreadyAdded=false;
            int indxr = text.indexOf(" ");
            String toAdd,temp="";
            if(indxr == -1)
            	toAdd = text;
            else
            	toAdd=text.substring(0,indxr);
            
            if(toAdd.length()>0) {
            	if(text.length()>toAdd.length())
            		text=text.substring(toAdd.length()+1);
            	else
            		text="";
            }
            
            while(!done){
                if(text.indexOf(" ")==-1){//out of spaces and is done
                    if(getPixelWidth(toAdd+" "+text,font)<maxPixelWidth){//can fit in the current line
                        toAdd+=" "+text;
                    }else{//cannot fit on the current line; must start on a new one
                        line_list.add(toAdd);//must call this here so that it is in the proper order
                        line_list.add(text);
                        alreadyAdded=true;
                    }
                    done=totalDone=true;
                }else if(text.indexOf(" ")==0){//there is a space on the first character of the String text; will be deleted
                    text=text.substring(1,text.length());
                }else if(getPixelWidth(toAdd+" "+(temp=text.substring(0,text.indexOf(" "))),font)<maxPixelWidth
                        /*&&temp.length()>0*/){
                    toAdd+=" "+temp;
                    text=text.substring(temp.length()+1);
                }else{
                    done=true;
                }
            }
            if(!alreadyAdded)
                line_list.add(toAdd);
        }
        
        return line_list;
    }
    
    /**
     * This function will return an integer value of how many pixels long a 
     *  String text will be by using Font font.
     * 
     * @param text the line of text that is being used with the given Font
     *      font to find the width of
     * @param font the font that is being used to draw the String text
     * @return the integer value of how long in pixels the String will be
     */
    public static int getPixelWidth(String text,Font font){
        AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        return (int)(font.getStringBounds(text,frc).getWidth());
    }
    
    /**
     * This function will return an integer value of how many pixels high a 
     *  String text will be by using Font font.
     * 
     * @param text the line of text that is being used with the given Font
     *      font to find the height of
     * @param font the font that is being used to draw the String text
     * @return the integer value of how high in pixels the String will be
     */
    public static int getPixelHeight(String text, Font font) {
    	AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        return (int)(font.getStringBounds(text,frc).getHeight());
    }
}
