package business.UI;

import java.util.Scanner;
import business.UI.commands.CmdAddAlbum;
import business.UI.commands.CmdAddAudioBook;
import business.UI.commands.CmdAddPlaylist;
import business.UI.commands.CmdAddSong;
import business.UI.commands.CmdAppendToAlbum;
import business.UI.commands.CmdDeletePlaylist;
import business.UI.commands.CmdHelp;
import business.UI.commands.CmdListAlbums;
import business.UI.commands.CmdListAudioBooks;
import business.UI.commands.CmdListOrderedSongs;
import business.UI.commands.CmdListPlaylists;
import business.UI.commands.CmdListSongs;
import business.UI.commands.CmdQuit;
import business.UI.commands.CmdSave;
import business.dataStruct.DirectAccess;
import utils.error.ForceQuitException;

/**
 * Is the command line interface for the user to use.
 * It somewhat acts like a bash command shell, where the command to perform is
 * called first and then the arguments follows, all within the same line.
 * when a command is used, the result is prompted to the console and the user is
 * requested to type an other request.
 * @author CLEMENT Aimeric
 */
public class CLI {
    private final Command commandArray;                                         /*use the "chain of responsability" design patern*/
    private final Scanner inputRead;
    private final DirectAccess dataAccess;
    
    /**
     * The CLI use a chaine of responsability, which mean that every commands
     * are materialized by their own separate object and that those are bound
     * as a linked list.
     * Since most of those commands are not just intended to prompt back a
     * result but must also have some kind of side effects on the
     * music database, those require to have access to the internal interface of
     * the database, modelized as "direct access".
     * @param directAccess is the internal interface that encapsulate somehow
     * the stored musical elements
     */
    public CLI(DirectAccess directAccess){
        this.inputRead= new Scanner(System.in);
        this.dataAccess= directAccess;
        this.commandArray=
                new CmdHelp(                                                    /*CmdHelp needs to be first has it simply recursively call the toString of the following commands*/
                new CmdAddSong(
                new CmdAddAlbum(
                new CmdAppendToAlbum(
                new CmdAddAudioBook(
                new CmdAddPlaylist(
                new CmdDeletePlaylist(
                new CmdListSongs(
                new CmdListOrderedSongs(
                new CmdListAlbums(
                new CmdListAudioBooks(
                new CmdListPlaylists(
                new CmdSave(
                new CmdQuit(
                null
                ))))))))))))));
    }
    
    /**
     * Trap the program in a loop, than can be escaped if the
     * exception <code>ForceQuitException</code> is raised.
     */
    public void run(){
        String usrInput;
        Boolean halt= false;
        
        System.out.print("Command line interface fully initialized, please submit your request: (type h for more information)\r\n\r\n");        
        while(!halt){
            try{
                usrInput= this.inputRead.nextLine();
                System.out.print(this.commandArray.attempt(usrInput, this.dataAccess)+"\r\n");
            }
            catch (ForceQuitException ex){
                System.out.print("Terminating the process...\r\n");
                halt= true;
            }
        }
        this.inputRead.close();
    }
}