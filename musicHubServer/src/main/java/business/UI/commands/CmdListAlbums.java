package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.elements.Album;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdListAlbums extends Command{

    public CmdListAlbums(Command cmnd){ super(cmnd); }

    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret= (args.isEmpty())?"":"Unused arguments\r\n";
        ArrayList<Album> albums= dataAccess.getAlbums();
        String shift= Command.shift();
        
        albums= new ArrayList<>(albums);                                        /*use a copie of the orginal to avoid the side effect of ordering the original*/
        Collections.sort(albums);                                               /*sort using the natural order of album: by date of launch*/
        
        /*print the album list: */
        ret+= String.valueOf(albums.size())+((albums.size()>1)?" albums":" album")+" found:\r\n";
        ret = albums.stream().map(currAlbum -> shift+currAlbum.toString()+"\r\n").reduce(ret, String::concat);
        ret+= "---";
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "la";
    }

    @Override
    public String description(){
        return "list every albums ordered by date of launch";
    }
}
