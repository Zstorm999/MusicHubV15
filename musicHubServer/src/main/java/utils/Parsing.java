package utils;

import java.util.ArrayList;

/**
 *
 * @author CLEMENT Aimeric
 */
public class Parsing{
    
    /**
     * Parse a string into a list of words, considering everything between quote
     * as one word. For exemple,
     * let say we have this input: hello "Robert Dubois" !
     * It would then return an array containing the word "hello", "Robert Dubois"
     * and finaly the word "!".
     * 
     * It is usefull in our case to interpret the user input, as a song name for
     * exemple, may contain blank spaces (such title would then be be mistaken
     * by the regular split of String as several arguments)
     *
     * @param str the full sentence
     * @return a list of words
     */
    public static ArrayList<String> split(String str){
        ArrayList<String> ret= new ArrayList<>();
        
        String buffer= "";
        Boolean flag= false;
        for (char c: str.toCharArray()){
            switch(c){
                case ' ':
                    if(!flag){
                        ret.add(buffer);
                        buffer= "";
                    }
                    else
                        buffer+= c;
                    break;
                
                case '\"':
                    flag= !flag;
                    break;
                
                default:
                    buffer+= c;
                    break;
            }
        }
        if (!str.isEmpty())
            ret.add(buffer);                                                    /*push final word without enccountering blank*/
        
        return ret;
    }
}