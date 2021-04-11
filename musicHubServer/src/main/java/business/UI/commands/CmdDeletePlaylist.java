package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import java.util.ArrayList;
import utils.Parsing;
import utils.error.NoElementFoundException;
import utils.error.TooManyMatchingElementsException;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdDeletePlaylist extends Command{

    public CmdDeletePlaylist(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret;
        
        ArrayList<String> argsSplit= Parsing.split(args);
        if(argsSplit.size()==1){
            String playlistName= argsSplit.get(0);
            try{
                dataAccess.dellPlaylist(playlistName);
                ret= "The playlist "+playlistName+" has successfully been removed";
            }
            catch(NoElementFoundException e){
                ret= "Error: The playlist "+e.getMessage()+" canot be found in the database";
            }
            catch(TooManyMatchingElementsException e){
                ret= "Error: The playlist name \""+e.getMessage()+"\" appears more than once in the database\r\n"+
                        "please use its ID instead";
            }
        }
        else{
            ret= "Error: wrong number of parameters\r\n"+
                    "(usage: \"" + this.taskParam() + " <Playlist>\")";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "-";
    }

    @Override
    public String description(){
        return "remove the selected playlist (usage: \""+this.taskParam()+" <Playlist>\")";
    }
    
}