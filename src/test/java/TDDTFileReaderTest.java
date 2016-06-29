package test.java;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import main.java.TDDTFileReader;

public class TDDTFileReaderTest {

	//NoSuchFile

	@Test
	public void readAll_basicFile() {
		String expected = "Dis is\nfile\n!\n\n";
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/java/testResources/basicFile.txt");
		String actual = reader.readAll(file);
		assertEquals(expected, actual);
	}

	@Test
	public void readAll_emptyFile() {
		String expected = "";
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/java/testResources/emptyFile.txt");
		String actual = reader.readAll(file);
		assertEquals(expected, actual);
	}

	@Test
	public void readLines_basicFile() {
		String[] expected = {"Dis is", "file", "!", ""};
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/java/testResources/basicFile.txt");
		String[] actual = reader.readLines(file);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void readLines_emptyFile() {
		String[] expected = { /*nothing, length = 0 */ };
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/java/testResources/emptyFile.txt");
		String[] actual = reader.readLines(file);
		assertArrayEquals(expected, actual);
	}

}
