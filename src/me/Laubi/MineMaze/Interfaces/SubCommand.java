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

package me.Laubi.MineMaze.Interfaces;

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
    String permission();
    String author() default "[unknown]";
    String description() default  "[No description]";
}
