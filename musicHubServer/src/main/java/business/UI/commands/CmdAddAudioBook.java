package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import business.dataStruct.enums.Category;
import business.dataStruct.enums.Language;
import java.util.ArrayList;
import java.util.Arrays;
import utils.Parsing;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdAddAudioBook extends Command{
    
    public CmdAddAudioBook(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret;
        ArrayList<String> argsSplit= Parsing.split(args);
        
        if(argsSplit.size()==6){
            String title= argsSplit.get(0);
            try{
                int lenght= Integer.parseInt(argsSplit.get(1));
                String content= argsSplit.get(2);
                String author= argsSplit.get(3);
                String category= argsSplit.get(4);
                String language= argsSplit.get(5);
                try{
                    int newId= dataAccess.addAudioBook(title, lenght, content, author, category, language);
                    ret= "A new audio book has successfully been added with the id "+newId;
                }
                catch(IllegalArgumentException e){
                    ret= "Error: The selected category or language is not available,\r\nPlease use instead:\r\n"+
                            Command.shift()+"categories: "+Arrays.toString(Category.values())+"\r\n"+
                            Command.shift()+"languages: "+Arrays.toString(Language.values());
                }
            }
            catch(NumberFormatException e){
                ret= "Error: Wrong type for the lenght; the lenght must be an integer";
            }
        }
        else{
            ret= "Error: Wrong number of arguments\r\n"+
                    "(usage: \""+this.taskParam()+" <Title> <Lenght> <Content> <Author> <Category> <Language>\")";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "l";
    }

    @Override
    public String description(){
        return "add a new audio book (usage: \""+this.taskParam()+" <Title> <Lenght> <Content> <Author> <Category> <Language>\")";
    }
}