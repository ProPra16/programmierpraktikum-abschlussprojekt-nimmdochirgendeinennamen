package main.java;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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

