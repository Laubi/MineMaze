package me.Laubi.MineMaze.Addons.MazeGenerators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Laubi
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MazeGenerator {
    String fullName();
    String []alias();
    
    String author() default "[unknown]";
    String permission() default "";
    
    int minHeight() default 1;
    int minLength() default 5;
    int minWidth () default 5;
    
    int maxHeight() default -1;
    int maxLength() default -1;
    int maxWidth () default -1;
}
