package tddt;
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

import java.io.File;

/**
 * This class was used as a task loader at the beginning of this project. However, we decided to switch
 * to the XML Reader {@link main.java.xmlHandler.XMLLoader}.
 * @author Dominik Kuhnen
 * @version unknown
 */
@Deprecated //used XMLLoader instead
public class TDDTTask {

	private String codeCode;
	private String testCode;

	/**
	 * Reads a file and splits the contained class code and test code into 2 Strings.
	 * @param file The file that contains the code.
     */
	@Deprecated
	public TDDTTask(File file) {
		TDDTFileReader reader = new TDDTFileReader();
		String content = reader.readAll(file);
		String[] splitContent = content.split("<Code-Test-Border, do not remove>\n");

		//check if selected file is a Task
		if (splitContent.length != 2) {
			codeCode = null;
			testCode = null;
			return;
		}

		this.codeCode = splitContent[0];
		this.testCode = splitContent[1];
	}

	/**
	 * @return The code that's been saved to String {@link #codeCode}
     */
	@Deprecated
	public String getCode() {
		return this.codeCode;
	}

	/**
	 * @return The code that's been saved to String {@link #testCode}
     */
	@Deprecated
	public String getTest() {
		return this.testCode;
	}

}
