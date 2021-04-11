package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.elements.Album;
import business.dataStruct.elements.Song;
import java.util.ArrayList;
import java.util.Collections;
import utils.Parsing;
import utils.error.ForceQuitException;
import utils.error.NoElementFoundException;
import utils.error.TooManyMatchingElementsException;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdListOrderedSongs extends Command{

    public CmdListOrderedSongs(Command cmnd){
        super(cmnd);
    }

    @Override
    protected String doStuff(String args, DirectAccess dataAccess) throws ForceQuitException{
        String ret = "";
        String shift= Command.shift();
        
        ArrayList<Song> selectedSongs = new ArrayList<>();
        if(args.isEmpty()){
            selectedSongs= dataAccess.getSongs();
        }
        else{
            ArrayList<String> argsSplit= Parsing.split(args);
            if(argsSplit.size()==1){
                try{
                    selectedSongs= dataAccess.getAlbum(argsSplit.get(0)).getSongs();
                }
                catch(NoElementFoundException e){
                    ret= "Error: The album "+e.getMessage()+" canot be found";
                }
                catch(TooManyMatchingElementsException e){
                    ret= "Error: There is several albums with the name "+e.getMessage()+"\r\n"+
                            "please try to use the ID of the album instead\r\n"+
                            "Albums matching the Title: \r\n";
                    ArrayList<Album> matchingAlbums= dataAccess.getMatchingAlbum(argsSplit.get(0));
                    ret = matchingAlbums.stream().map(currMatching -> shift+currMatching.toString()+"\r\n").reduce(ret, String::concat);
                    ret+= "---";
                }
            }
            else{
                ret= "Error: Wrong number of arguments\r\n(usage: \"" + this.taskParam() + " [Album]\")";
            }
        }
        
        if(ret.isEmpty()){                                                      /*if not empty: an error occured*/
            selectedSongs= new ArrayList<>(selectedSongs);                      /*use a copie of the orginal to avoid the side effect of ordering the original*/
            Collections.sort(selectedSongs);
            
            /*print the list of songs: */
            ret= String.valueOf(selectedSongs.size())+((selectedSongs.size()>1)?" songs":" song")+" found: (ordered by genres)\r\n";
            for(Song currSong: selectedSongs){
                ret+= shift+currSong.toString()+"\r\n";
            }
            ret+= "---";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "lc";
    }

    @Override
    public String description(){
        return "list every songs of an album ordered by genre, and every songs of the database if none is defined (usage: \""+this.taskParam()+" [Album]\")";
    }
}
