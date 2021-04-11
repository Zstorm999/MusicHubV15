package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.elements.Album;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import utils.Parsing;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdAddAlbum extends Command{
    
    public CmdAddAlbum(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret;
        
        ArrayList<String> argsSplit= Parsing.split(args);
        if(argsSplit.size()==3){
            String title= argsSplit.get(0);
            String artist= argsSplit.get(1);
            String launchDate= argsSplit.get(2);
            SimpleDateFormat format= new SimpleDateFormat(Album.DATEFORMAT);
            try{
                Date launchDateParsed= format.parse(launchDate);
                int newId= dataAccess.addAlbum(title, artist, launchDateParsed);
                ret= "A new album has successfully been added with the id "+newId;
            }
            catch (ParseException e){
                ret= "Error: the lauch date is not properly reconized\r\n"+
                        "The correct format is "+Album.DATEFORMAT+
                        " (exemple: \"19/05/1949\")";
            }
        }
        else{
            ret= "Error: Wrong number of arguments\r\n(usage: \""+
                    this.taskParam()+
                    " <Title> <Artist> <LaunchDate>\")";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "a";
    }

    @Override
    public String description(){
        return "add a new album (usage: \"" + this.taskParam() + " <Title> <Artist> <LaunchDate>\")";
    }
}