package main.java;

import java.util.Collection;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

public class TDDTCompiler {

	String info;

	public boolean compile(String code, boolean isTest) {
		if (isTest)
			return compileAndRunTests(code);
		else
			return compileCode(code);
	}
	
	public String getInfo() {
		return this.info;
	}

	private boolean compileCode(String code) {
		String className       = findClassName(code).trim();
		CompilationUnit cu     = new CompilationUnit(className, code, false);
		JavaStringCompiler jsc = CompilerFactory.getCompiler(cu);

		jsc.compileAndRunTests();
		CompilerResult cr = jsc.getCompilerResult();

		if (cr.hasCompileErrors()) {
			info = formatCompileErrors(cr, cu);
			return false;
		}

		return true;
	}

	private boolean compileAndRunTests(String code) {
		String className       = findClassName(code).trim();
		CompilationUnit cu     = new CompilationUnit(className, code, true);
		JavaStringCompiler jsc = CompilerFactory.getCompiler(cu);

		jsc.compileAndRunTests();
		CompilerResult cr = jsc.getCompilerResult();

		if (cr.hasCompileErrors()) {
			info = formatCompileErrors(cr, cu);
			return false;
		}

		TestResult tr = jsc.getTestResult();

		if (tr.getNumberOfFailedTests() != 0) {
			info = formatFailingTests(tr, cu);
			return false;
		}

		return true;
	}

	private String findClassName(String text) {
		//offset 6 for the String "class "
		return text.substring(text.indexOf("class") + 6, text.indexOf(" {"));
	}

	private String formatCompileErrors(CompilerResult cr, CompilationUnit cu) {
		StringBuilder output = new StringBuilder();
		Collection<CompileError> errorList = cr.getCompilerErrorsForCompilationUnit(cu);
		for (CompileError error : errorList) {
			output.append(error.toString());
			output.append("\n");
		}
		return output.toString();
	}
	
	private String formatFailingTests(TestResult tr, CompilationUnit cu) {
		StringBuilder output = new StringBuilder();
		Collection<TestFailure> failureList = tr.getTestFailures();
		for (TestFailure failure : failureList) {
			output.append(failure.getMethodName());
			output.append(": ");
			output.append(failure.getMessage());
			output.append("\n");
		}
		return output.toString();
	}

}
