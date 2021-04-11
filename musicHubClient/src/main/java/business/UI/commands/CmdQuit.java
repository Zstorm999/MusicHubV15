package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import utils.error.ForceQuitException;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdQuit extends Command{

    public CmdQuit(Command cmnd) {
        super(cmnd);
    }

    @Override
    protected String doStuff(String args, DirectAccess dataAccess) throws ForceQuitException{
        throw new ForceQuitException();
    }

    @Override
    protected String taskParam(){
        return "quit";
    }

    @Override
    public String description(){
        return "quit the application";
    }
}
