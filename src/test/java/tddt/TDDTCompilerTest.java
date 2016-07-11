package test.java.tddt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.java.tddt.TDDTCompiler;

public class TDDTCompilerTest {

    TDDTCompiler compiler = new TDDTCompiler();
    boolean passed;

    @Before
    public void initializeBooleanForCompilerReturn() {
        passed = false;
    }

    @Test
    public void simpleCompilePass() {
        String code = (
                "public class simpleCompile {\n"
                        + ""
                        + "}"
        );
        passed = compiler.compile(code, false, "simpleCompile");
        assertEquals(true, passed);
    }

    @Test
    public void compilePass() {
        //My solution to ProjectEuler Problem12
        String code = (
                "public class Problem12 {\n"
                        +"	public static void main(String[] args) {\n"
                        +"		//generate triangle number array\n"
                        +"		long[] array = new long[20000000];\n"
                        +"		array[0] = 0;\n"
                        +"		for (long i = 1; i < array.length; i++) {\n"
                        +"			array[(int) i] = array[(int)i-1] + i;\n"
                        +"		}\n"
                        +"		//calculate first triangle number div > 500\n"
                        +"		for (long i = 1; i < array.length; i++) {\n"
                        +"			if (divisors(array[(int) i]) > 500) {\n"
                        +"				System.out.println(array[(int) i]);\n"
                        +"				break;\n"
                        +"			}\n"
                        +"		}\n"
                        +"	}\n"

                        +"	public static int divisors(long k) {\n"
                        +"	    long bound = k;\n"
                        +"	    int amount = 0;\n"
                        +"	    if (k == 1) return 1;\n"
                        +"	    for (int i = 1; i < bound; ++i) {\n"
                        +"	        if (k % i == 0) {\n"
                        +"	            bound = k / i;\n"
                        +"	            if (bound != i) {\n"
                        +"	                amount++;\n"
                        +"	            }\n"
                        +"	            amount++;\n"
                        +"	        }\n"
                        +"	    }\n"
                        +"	    return amount;\n"
                        +"	}\n"
                        +"}"
        );
        passed = compiler.compile(code, false, "Problem12");
        assertEquals(true, passed);
    }

    @Test
    public void simpleCompileFail_notAClass() {
        String code = (
                "public simpleCompile {\n"
                        + ""
                        + "}"
        );
        passed = compiler.compile(code, false,"simpleCompile");
        assertEquals(false, passed);
    }

    @Test
    public void simpleCompileFail_Syntax_WrongOperation() {
        String code = (
                "public simpleCompile {\n"
                        + "int i == 1;"
                        + "}"
        );
        passed = compiler.compile(code, false, "simpleCompile");
        assertEquals(false, passed);
    }

    @Test
    public void simpleCompileFail_Syntax_MissingBracket() {
        String code = (
                "public simpleCompile {\n"
                        + "int i = 1;"
        );
        passed = compiler.compile(code, false, "simpleCompile");
        assertEquals(false, passed);
    }

    @Test
    public void simpleCompileFail_missingClassesInPath() {
        String code = (
                "public class simpleCompile {\n"
                        + "   public static void main(String[] args) {\n"
                        + "      NotExistant k = new NotExistant();\n"
                        + "   }\n"
                        + "}"
        );
        passed = compiler.compile(code, false, "simpleCompile");
        assertEquals(false, passed);
    }

}