# MineMaze

Author: Laubi   
Version: 2.0


**MineMaze** is a plugin for [Bukkit](http://bukkit.org).


####Commands:

**//maze**:  
Use this command to generate a maze in the selected area.  
Arguments:

  * -gen <Generator>  Use a special generator, to list them use "//maze list"  
  * -mat <Pattern>    Change the material of the walls. ([Block data syntax](http://wiki.sk89q.com/wiki/WorldEdit/Block_data_syntax))  
  * -bottom <Pattern> Change the material of the floor, if you don't one just don't use this command. ([Block data syntax](http://wiki.sk89q.com/wiki/WorldEdit/Block_data_syntax))  
  * -top <Pattern> Change the material of the ceiling. ([Block data syntax](http://wiki.sk89q.com/wiki/WorldEdit/Block_data_syntax))  
  * -nodoors Dont generate doors.  
  * -torches [-h <height>] [-p <percentage>] Generate some torches. With the "-h"-flag you can change the height of the torches, and with the "-p"-flag you can change the percentage of the torches.
  
**//maze help**:  
List all aviable subcommands and their description.

**//maze list**:  
List all aviable Mazegenerators and their aliases.

