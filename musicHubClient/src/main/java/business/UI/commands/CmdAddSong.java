package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.enums.Genre;
import java.util.ArrayList;
import java.util.Arrays;
import utils.Parsing;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdAddSong extends Command{
    
    public CmdAddSong(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret;
        
        ArrayList<String> argsSplit= Parsing.split(args);
        if(argsSplit.size()==5){
            String title= argsSplit.get(0);
            String content= argsSplit.get(1);
            String artist= argsSplit.get(2);
            String genre= argsSplit.get(3);
            try{
                int length= Integer.parseInt(argsSplit.get(4));                                                         /* may raise an error if the input length is not a number */
                try{
                    int newId= dataAccess.addSong(title, length, content, artist, genre);
                    ret= "A new song has successfully been added with the id "+newId;
                }
                catch(IllegalArgumentException e){
                    ret= "Error: The genre \""+genre+"\" is not available,\r\nPlease use instead: "+Arrays.toString(Genre.values());
                }
            }
            catch(NumberFormatException e){
                ret= "Error: Wrong type for the lenght; the lenght must be an integer";
            }
        }
        else{
            ret= "Error: Wrong number of arguments\r\n(usage: \"" + this.taskParam() + " <Title> <Content> <Artist> <Genre> <Length>\")";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "c";
    }
    
    @Override
    public String description(){
        return "add a new song (usage: \"" + this.taskParam() + " <Title> <Content> <Artist> <Genre> <Length>\")";
    }
}