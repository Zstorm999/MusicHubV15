package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.elements.Playlist;
import java.util.ArrayList;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdListPlaylists extends Command{
    
    public CmdListPlaylists(Command cmnd){
        super(cmnd);
    }

    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret= (args.isEmpty())?"":"Unused arguments\r\n";
        ArrayList<Playlist> playlists= dataAccess.getPlaylists();
        String shift= Command.shift();
        
        /*print the list of playlist: */
        ret+= String.valueOf(playlists.size())+((playlists.size()>1)?" playlists":" playlist")+" found:\r\n";
        ret = playlists.stream().map(currPlaylist -> shift+currPlaylist.toString()+"\r\n").reduce(ret, String::concat);
        ret+= "---";
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "lp";
    }

    @Override
    public String description(){
        return "list every playlists";
    }
}
