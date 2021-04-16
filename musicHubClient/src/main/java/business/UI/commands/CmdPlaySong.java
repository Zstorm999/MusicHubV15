package business.UI.commands;

import business.UI.Command;
import business.dataStruct.elements.Song;
import business.dataStruct.enums.Genre;
import utils.Parsing;
import utils.error.ForceQuitException;
import business.dataStruct.DirectAccess;

import java.util.ArrayList;
import java.util.Arrays;

public class CmdPlaySong extends Command {
    public CmdPlaySong (Command cmnd) {super (cmnd);}
    @Override
    protected String doStuff(String args, DirectAccess access) throws ForceQuitException {
        String result;                                                                                                  // result send to the user

        ArrayList<String> argsSplit= Parsing.split(args);                                                               //transform the command into a table
        if(argsSplit.size()==2) {
            String title = argsSplit.get(0);
            Song song = access.getSong(title);
            if (song==null){
                result= "Error: The song doesn't exist";
            }
            else{
                result="Now playing: "+song.toString();
            }
        }
        else{
            result= "Error: Wrong number of arguments\r\n(usage: \"" + this.taskParam() +" [Song]\")";
        }

        return result+"\r\n";
    }

    @Override
    protected String taskParam() {
        return "ps";
    }

    @Override
    protected String description() {
        return   "Play the selected song (usage: \""+this.taskParam()+" [Song]\")";
    }

}