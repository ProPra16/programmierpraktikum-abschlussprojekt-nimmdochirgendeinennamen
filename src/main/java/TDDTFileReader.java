package main.java;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class TDDTFileReader {

	public String read(File file) {
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

}
