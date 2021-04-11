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
public class CmdAddPlaylist extends Command{

    public CmdAddPlaylist(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret;
        
        ArrayList<String> argsSplit= Parsing.split(args);
        if(argsSplit.size()>=1){                                                /*do not prevent the creation of an empty playlist*/
            String title= argsSplit.get(0);
            ArrayList<String> addedElements= new ArrayList(argsSplit.subList(1, argsSplit.size()));
            try{
                int newId= dataAccess.addPlaylist(title, addedElements);
                ret= "A new playlist has successfully been added with the id "+newId;
            }
            catch(NoElementFoundException e){
                ret= "Error: One or more elements, including "+e.getMessage()+", canot be found";
            }
            catch(TooManyMatchingElementsException e){
                ret= "Error: There is several elements with the name "+e.getMessage()+"\r\n"+
                        "please try to use its ID instead";
            }
        }
        else{
            ret= "Error: Wrong number of arguments\r\n"+
                    "(usage: \""+this.taskParam()+" <Title> <ElementName_1...n>\")";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "p";
    }

    @Override
    public String description(){
        return "create a new playlist from existing songs and audio books (usage: \""+this.taskParam()+" <PlaylistName> [Element_1] [Element_2] [...]\")";
    }
}