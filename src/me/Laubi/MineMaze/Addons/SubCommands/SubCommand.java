package me.Laubi.MineMaze.Addons.SubCommands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Laubi
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
    String []alias();
    
    boolean console() default false;
    String permission() default "";
    String author() default "[unknown]";
    String description() default  "[No description]";
}
