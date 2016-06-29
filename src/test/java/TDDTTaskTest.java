package test.java;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import main.java.TDDTTask;

public class TDDTTaskTest {

	//TODO find a way to split dependencies...

	@Test
	public void basicTask() {
		File file = new File("src/test/java/testResources/basicTask.txt");
		TDDTTask task = new TDDTTask(file);
		String actualCode = task.getCode();
		String actualTest = task.getTest();
		//inb4 StringBuilder/StringBuffer, its the same bytecode
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
		assertEquals(expectedCode, actualCode);
		assertEquals(expectedTest, actualTest);
	}

	@Test
	public void minimalisticTask() {
		File file = new File("src/test/java/testResources/minimalisticTask.txt");
		TDDTTask task = new TDDTTask(file);
		String actualCode = task.getCode();
		String actualTest = task.getTest();
		String expectedCode = "Dis is\n";
		String expectedTest = "task\n";
		assertEquals(expectedCode, actualCode);
		assertEquals(expectedTest, actualTest);
	}

	@Test
	public void noContentTask() {
		File file = new File("src/test/java/testResources/noContentTask.txt");
		TDDTTask task = new TDDTTask(file);
		String actualCode = task.getCode();
		String actualTest = task.getTest();
		String expectedCode = "";
		String expectedTest = "";
		assertEquals(expectedCode, actualCode);
		assertEquals(expectedTest, actualTest);
	}

	@Test
	public void notATask_missingTaskBorder() {
		File file = new File("src/test/java/testResources/notATask_missingTaskBorder.txt");
		TDDTTask task = new TDDTTask(file);
		String actualCode = task.getCode();
		String actualTest = task.getTest();
		String expectedCode = null;
		String expectedTest = null;
		assertEquals(expectedCode, actualCode);
		assertEquals(expectedTest, actualTest);
	}

	@Test
	public void notATask_havingTwoTaskBorders() {
		File file = new File("src/test/java/testResources/notATask_havingTwoTaskBorders.txt");
		TDDTTask task = new TDDTTask(file);
		String actualCode = task.getCode();
		String actualTest = task.getTest();
		String expectedCode = null;
		String expectedTest = null;
		assertEquals(expectedCode, actualCode);
		assertEquals(expectedTest, actualTest);
	}

}