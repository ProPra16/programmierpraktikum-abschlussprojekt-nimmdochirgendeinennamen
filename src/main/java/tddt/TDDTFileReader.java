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
package tddt;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles file reading for some of the other TDD classes.
 * @author Dominik Kuhnen
 * @version unknown
 */
public class TDDTFileReader {

	/**
	 * Loads the content of a file into a String object.
	 * @param file The file that is loaded into the String
	 * @return The String that contains the file content.
     */
	public String readAll(File file) {
		StringBuilder content = new StringBuilder();
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (String line : lines)
				content.append(line).append("\n");

		} catch (Exception e) {
            e.printStackTrace();
		}
		return content.toString();
	}


	/**
	 * Loads each line of a file into a String array. Each line gets its own index in the array.
	 * @param file The file that is loaded into the String array.
	 * @return The String array that contains the file content.
     */
	public String[] readLines(File file) {
		ArrayList<String> list = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			list.addAll(lines);
		} catch (Exception e) {
            e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}

}
