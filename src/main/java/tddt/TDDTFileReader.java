package main.java.tddt;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
@Deprecated
public class TDDTFileReader {

	public String readAll(File file) {
		StringBuilder content = new StringBuilder();
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (String line : lines)
				content.append(line + "\n");

		} catch (Exception e) {
            e.printStackTrace();
		}
		return content.toString();
	}

	public String[] readLines(File file) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (String line : lines)
				list.add(line);

		} catch (Exception e) {
            e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}

}
