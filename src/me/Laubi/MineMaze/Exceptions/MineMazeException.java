package me.Laubi.MineMaze.Exceptions;

/**
 *
 * @author Laubi
 */
public class MineMazeException extends Exception{
    private static final long serialVersionUID = -4244502648129665164L;

    /**
     * Creates a new instance of
     * <code>MineMazeException</code> without detail message.
     */
    public MineMazeException() {
    }

    /**
     * Constructs an instance of
     * <code>MineMazeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MineMazeException(String msg) {
        super(msg);
    }
}
