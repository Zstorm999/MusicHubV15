package business.UI;

import business.dataStruct.DirectAccess;
import java.util.regex.PatternSyntaxException;
import utils.error.ForceQuitException;

/**
 * A command, part of a chain of responsability, looks at the arguments provided
 * by the user, and then evalute if the command is able or not to
 * fulfill the request.
 * if the command can handle it, then the command will do so, and otherwise, if
 * it does not, the command will ask the next command of the chain to perform
 * this whole process again.
 * @author CLEMENT Aimeric
 */
public abstract class Command {
    protected static final int INDENTWIDTH= 3;                                  /*shift width list of commands in help page*/
    protected static final int DESCRIPTIONPADDING= 12;                          /*max alignment offset after command tags in help page*/
    protected final Command nextCommand;
    
    /**
     * @param nextCommand is the next command to be tested in the chain
     */
    public Command(Command nextCommand){
        this.nextCommand= nextCommand;
    }

    /**
     * Test if the current command is the one supposed to process the request
     * then process it, or pass the request to the folowing command handler
     *
     * @param argsAll the full input submited by the user
     * @param access
     * @return the output String returned by the executed command
     * @throws utils.error.ForceQuitException
     */
    public String attempt(String argsAll, DirectAccess access) throws ForceQuitException{            
        String ret;
        String argsStrip;
        
        if (argsAll.split(" ")[0].equals(this.taskParam())){                    /*if the command handle the task*/
            /*strip parameters of the now unused part: */
            try{
                argsStrip= argsAll.replaceFirst(this.taskParam(), "").replaceFirst(" ", "");
            }
            catch(PatternSyntaxException e){                                    /*may fail if a character is wrongly interpreted as a regex special rule ("+" for exemple)*/
                argsStrip= argsAll.replaceFirst("\\"+this.taskParam(), "").replaceFirst(" ", "");
            }
            
            ret= this.doStuff(argsStrip, access);
        }
        else if (this.nextCommand!=null){                                       /*if the command as nothing to do with the task*/
            ret= this.nextCommand.attempt(argsAll, access);
        }
        else{
            ret= "Command not found\r\nUse \"h\" to see the list of all available commands\r\n";
        }
        return ret;
    }
    
    /**
     * Actually perform the task the command is meant to do
     * @param args striped of the first arg (which only define the action to perform)
     * @param access provide an access of the commands on the data
     * @return a string containing the command output
     * @throws utils.error.ForceQuitException
     */
    abstract protected String doStuff(String args, DirectAccess access) throws ForceQuitException;
    
    /**
     * @return the argument corresponding to the command 
     */
    abstract protected String taskParam();                                      /*is basicaly an atribute but in a more convenient form*/
    
    /**
     * @return the description and usage of the command 
     */
    abstract protected String description();                                       /*is basicaly an atribute but in a more convenient form*/
    
    /**
     * generate a space-composed basic indentation (width defined in <code>Command.INDENTWIDTH</code>)
     * @return return a space-composed basic indentation
     */
    protected static String shift(){
        String shift= "";
        for (int i= 0; i<Command.INDENTWIDTH; i++)
            shift+= " ";
        return shift;
    }
    
    @Override
    public String toString(){
        String shift= Command.shift();
        String alignFilling= "";
        String ret;
        
        for (int i= 0; i<Command.DESCRIPTIONPADDING-(this.taskParam().length()+1); i++)
            alignFilling+= " ";
        
        ret= shift+
                this.taskParam()+
                ":"+alignFilling+
                this.description()+
                "\r\n";
        if(this.nextCommand!=null)
            ret+= this.nextCommand.toString();
        return ret;
    }
}
