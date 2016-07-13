package tddt;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
@SuppressWarnings("deprecation")
public class TDDTTaskTest {

	//TODO find a way to split dependencies...
	//TDDTFileReaderTest is basically just a wrapper...is necessary?

	@Test
	public void basicTask() {
		File file = new File("src/test/java/testResources/basicTask.txt");
		TDDTTask task = new TDDTTask(file);
		String expectedCode = (
			  "public class Task1 {\n"
			+ "\t//Task: Print out Hello World!\n"
			+ "\tpublic static void main(String[] args) {\n"
			+ "\t\t\n"
			+ "\t}\n"
			+ "}\n"
		);
		String expectedTest = (
			  "package test.java;\n"
			+ "import static org.junit.Assert.*;\n"
			+ "import org.junit.Test;\n"
			+ "\n"
			+ "public class Task1Test {\n"
			+ "\t@Test\n"
			+ "\tpublic void test() {\n"
			+ "\t\t\n"
			+ "\t}\n"
			+ "}\n"
		);
		assertEquals(expectedCode, task.getCode());
		assertEquals(expectedTest, task.getTest());
	}

	@Test
	public void minimalisticTask() {
		File file = new File("src/test/java/testResources/minimalisticTask.txt");
		TDDTTask task = new TDDTTask(file);
		assertEquals("Dis is\n", task.getCode());
		assertEquals("task\n", task.getTest());
	}

	@Test
	public void noContentTask() {
		File file = new File("src/test/java/testResources/noContentTask.txt");
		TDDTTask task = new TDDTTask(file);
		assertEquals(null, task.getCode());
		assertEquals(null, task.getTest());
	}

	@Test
	public void notATask_missingTaskBorder() {
		File file = new File("src/test/java/testResources/notATask_missingTaskBorder.txt");
		TDDTTask task = new TDDTTask(file);
		assertEquals(null, task.getCode());
		assertEquals(null, task.getTest());
	}

	@Test
	public void notATask_havingTwoTaskBorders() {
		File file = new File("src/test/java/testResources/notATask_havingTwoTaskBorders.txt");
		TDDTTask task = new TDDTTask(file);
		assertEquals(null, task.getCode());
		assertEquals(null, task.getCode());
	}

}