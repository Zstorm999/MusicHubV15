package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdHelp extends Command{

    /**
     * Need to be at the top of the chain of responsabilty. This command send
     * back a list of the descriptions of every available commands
     * @param cmnd 
     */
    public CmdHelp(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret= "General usage: <command> [args]\r\n"+
                "remarks: \r\n"+
                " -Titles that contain blanks must be surounded by quotation marks (the special char \"\\␣\" is not properly interpreted).\r\n"+
                " -When using commands to list any kind of element, the output will be first the IDs, and then the descriptions of the entries.\r\n"+
                " -To designate an element, we can either use its name or its ID.\r\n"+
                "available commands:\r\n"+this.toString();                      /*as defined in the Command class; to stringify a command recusively stringify every folowing commands*/
                ret+= "---";
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "h";
    }

    @Override
    public String description(){
        return "show this message";
    }
}