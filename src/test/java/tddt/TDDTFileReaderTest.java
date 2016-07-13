package tddt;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class TDDTFileReaderTest {

	//NoSuchFileException is covered from FileChooser

	@Test
	public void readAll_basicFile() {
		String expected = "Dis is\nfile\n!\n\n";
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/resources/basicFile.txt");
		String actual = reader.readAll(file);
		assertEquals(expected, actual);
	}

	@Test
	public void readAll_emptyFile() {
		String expected = "";
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/resources/emptyFile.txt");
		String actual = reader.readAll(file);
		assertEquals(expected, actual);
	}

	@Test
	public void readLines_basicFile() {
		String[] expected = {"Dis is", "file", "!", ""};
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/resources/basicFile.txt");
		String[] actual = reader.readLines(file);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void readLines_emptyFile() {
		String[] expected = { /*nothing, length = 0 */ };
		TDDTFileReader reader = new TDDTFileReader();
		File file = new File("src/test/resources/emptyFile.txt");
		String[] actual = reader.readLines(file);
		assertArrayEquals(expected, actual);
	}

}
