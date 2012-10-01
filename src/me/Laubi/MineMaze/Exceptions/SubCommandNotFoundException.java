/**
 * Copyright 2012 Laubi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package me.Laubi.MineMaze.Exceptions;

/**
 *
 * @author Laubi
 */


public class SubCommandNotFoundException extends Exception {
    private static final long serialVersionUID = 830114005966197735L;
    protected String subCommand;
    /**
     * Creates a new instance of
     * <code>SubCommandNotFoundException</code> without detail message.
     */
    public SubCommandNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>SubCommandNotFoundException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public SubCommandNotFoundException(String subCommand) {
        this.subCommand = subCommand;
    }

    public String getSubCommand() {
        return subCommand;
    }
    
    
}
