/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.Laubi.MineMaze.Exceptions;

/**
 *
 * @author Laubi
 */
public class PermissionException extends MineMazeException {
    private String permission;
    
    public PermissionException() {
    }

    public PermissionException(String permission, String msg) {
        super(msg);
        this.permission = permission;
    }

    public PermissionException(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
