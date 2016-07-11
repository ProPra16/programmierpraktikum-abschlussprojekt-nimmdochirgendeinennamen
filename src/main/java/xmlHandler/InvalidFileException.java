/*
 * Copyright (c) 2016. Caro Jachmann, Dominik Kuhnen, Jule Pohlmann, Kai Brandt, Kai Holzinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package main.java.xmlHandler;

/** This is a subclass of RuntimeException. It's used for Exceptions that occur while using the xmlHandler.
 * @author Kai Holzinger
 * @version 1.0
 */
public class InvalidFileException extends RuntimeException {

    /** This constructor method creates a new Exception object.
     * @param message   The message that is shown whenever the Exception is thrown.
     * @param cause     The Exception that is handled.
     */
    protected InvalidFileException(String message, Exception cause){
        super(message,cause);
    }
}
