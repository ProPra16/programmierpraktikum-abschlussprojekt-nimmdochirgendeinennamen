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

	public boolean compile(String code, boolean isTest) {
		if (isTest)
			return compileAndRunTests(code);
		else
			return compileCode(code);
	}

	private boolean compileCode(String code) {
		//checking if className can be determined
		if (!code.contains("class") & !code.contains("{")) return false;
		String className       = findClassName(code);
		CompilationUnit cu     = new CompilationUnit(className, code, false);
		JavaStringCompiler jsc = CompilerFactory.getCompiler(cu);

		jsc.compileAndRunTests();

		CompilerResult cr = jsc.getCompilerResult();

		if (cr.hasCompileErrors()) {
			displayCompileErrors(cr, cu);
			return false;
		}

		return true;
	}

	private boolean compileAndRunTests(String code) {
		//checking if className can be determined
		if (!code.contains("class") & !code.contains("{")) return false;
		String className       = findClassName(code);
		CompilationUnit cu     = new CompilationUnit(className, code, true);
		JavaStringCompiler jsc = CompilerFactory.getCompiler(cu);

		jsc.compileAndRunTests();

		CompilerResult cr = jsc.getCompilerResult();

		if (cr.hasCompileErrors()) {
			displayCompileErrors(cr, cu);
			return false;
		}

		TestResult tr = jsc.getTestResult();

		if (tr.getNumberOfSuccessfulTests() != 0) {
			displayFailingTests(tr, cu);
			return false;
		}

		return true;
	}

	private String findClassName(String text) {
		//offset 6 for the String "class "
		return text.substring(text.indexOf("class") + 6, text.indexOf(" {"));
	}

	private void displayCompileErrors(CompilerResult cr, CompilationUnit cu) {
		Collection<CompileError> errorList = cr.getCompilerErrorsForCompilationUnit(cu);
		CompileError error = errorList.iterator().next();
		//TODO implement and use Dialog for Exceptions
		new TDDTDialog("alert", error.toString());
	}
	
	private void displayFailingTests(TestResult tr, CompilationUnit cu) {
		Collection<TestFailure> failureList = tr.getTestFailures();
		TestFailure failure = failureList.iterator().next();
		//TODO implement and use Dialog for Exceptions
		new TDDTDialog("alert", failure.toString());
	}

}
