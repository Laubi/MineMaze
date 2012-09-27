package me.Laubi.MineMaze;

import com.sk89q.worldedit.LocalPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;

/**
 *
 * @author Laubi
 */
public class CommandHandler {
    private final String [] originalArgs;
    private final char commandChar;
    private final char quote = '"';
    private final List<Argument> argumentList= new ArrayList<Argument>();
    private final String defaultSubCommand = "maze";
    

    public CommandHandler (String[] originalArgs, char commandChar) {
        this.originalArgs = originalArgs;
        this.commandChar = commandChar;
        
        this.argumentList.addAll(
                this.parseArguments(
                    this.parseQuotesAndStripCommand(originalArgs)
                ));
    }
    public CommandHandler (String [] oA){
        this(oA,'-');
    }
    
    protected final List<String> parseQuotesAndStripCommand(String [] args){
        final List<String> argList = new ArrayList<String>();
        
        for(int i = 0; i < args.length; i++){
            String arg = args[i];
            
            if(arg.charAt(0) == quote){ //We have quotes to strip
                final StringBuilder builder = new StringBuilder();
                
                int j;
                for(j = i; j < args.length; j++){
                    final String cur = args[j];
                    if(cur.charAt(cur.length() -1) == quote){
                        if(j != i)
                            builder.append(' ');
                        builder.append(cur.substring(j == i ? 1 : 0, cur.length() -1));
                        break;
                    }else if(j == i){
                        builder.append(cur.substring(1));
                    }else{
                        builder.append(' ').append(cur);
                    }
                }
                
                arg = builder.toString();
                i = j;
                
                if(arg.isEmpty())
                    continue;
            }
            argList.add(arg);
        }
        
        return argList;
    }
    protected final List<Argument> parseArguments(List<String> commands){
        List<Argument> arguments = new ArrayList<Argument>();
        Argument lastArgument = null;
        
        for(String cur : commands){
            if(cur.startsWith(String.valueOf(this.commandChar))){
                if(lastArgument != null)
                    arguments.add(lastArgument);
                
                lastArgument = new Argument(cur.substring(1));
            }else{
                if(lastArgument == null){
                    lastArgument = new Argument("", cur);
                }else{
                    if(lastArgument.getValue() == null)
                        lastArgument.appendValue(cur);
                    else
                        lastArgument.appendValue(" " + cur);
                }
            }
        }
        if(lastArgument != null)
            arguments.add(lastArgument);
        
        return arguments;
    }
    
    public Argument findArgument(String key){
        Iterator<Argument> it = this.argumentList.iterator();
        Argument result = null;
        
        while(it.hasNext() && result == null){
            Argument c = it.next();
            
            if(c.getKey().equalsIgnoreCase(key)){
                result = c;
            }
        }
        return result;
    }
    public String getSubCommand(){
        return this.findArgument("")==null?this.defaultSubCommand:this.findArgument("").getValue().split(" ")[0];
    }
    
    public String[] getUnordertArguments(){
        Argument subCommand = this.findArgument("");
        String [] result = new String[0];
        
        if(subCommand != null){
            String[] parts = subCommand.getValue().split(" ");
            result = new String [parts.length -1];
            System.arraycopy(parts, 1, result, 0, parts.length -1);
        }
        return result;
    }
    
    public boolean containsArgument(String key){
        return this.findArgument(key) != null;
    }
    public String getArgumentValue(String key){
        return this.findArgument(key)==null?null:this.findArgument(key).getValue();
    }
    public boolean hasValue(String key){
        Argument arg = this.findArgument(key);
        
        if(arg != null){
            return arg.getValue() != null;
        }
        return false;
    }
    
    
    public String [] getOriginalArgs(){
        return this.originalArgs;
    }
    
    public void dump(LocalPlayer p){
        p.print(ChatColor.RED+ "Debugging Arguments: ");
        p.print("    "+ChatColor.GREEN + "subcommand: "+ ChatColor.BLUE + this.getSubCommand());
        Iterator<Argument> it = this.argumentList.iterator();
        
        while(it.hasNext()){
            Argument c = it.next();
            p.print("    "+ChatColor.GREEN +c.getKey() + ": "+ ChatColor.BLUE + c.getValue());
        }
    }
}
