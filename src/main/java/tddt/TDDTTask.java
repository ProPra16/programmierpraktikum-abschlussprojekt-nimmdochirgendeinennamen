package main.java;
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

public class TDDTTask {

	private String codeCode;
	private String testCode;

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

	public String getCode() {
		return this.codeCode;
	}

	public String getTest() {
		return this.testCode;
	}

}
