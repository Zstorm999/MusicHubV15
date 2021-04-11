package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.elements.AudioBook;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdListAudioBooks extends Command{
    
    public CmdListAudioBooks(Command cmnd){
        super(cmnd);
    }

    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret= (args.isEmpty())?"":"Unused arguments\r\n";
        ArrayList<AudioBook> adBooks= dataAccess.getAudioBooks();
        String shift= Command.shift();
        
        adBooks= new ArrayList<>(adBooks);                                      /*use a copie of the orginal to avoid the side effect of ordering the original*/
        Collections.sort(adBooks);
        
        /*print the audio book list: */
        ret+= String.valueOf(adBooks.size())+((adBooks.size()>1)?" audio-books":" audio-book")+" found:\r\n";
        ret = adBooks.stream().map(currAdBook -> shift+currAdBook.toString()+"\r\n").reduce(ret, String::concat);
        ret+= "---";
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "ll";
    }

    @Override
    public String description(){
        return "list every audio books ordered by authors";
    }
}
