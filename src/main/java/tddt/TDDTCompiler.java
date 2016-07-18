/*
 * Copyright (c) 2016. Caro Jachmann, Jule Pohlmann, Kai Brandt, Kai Holzinger
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

package tddt;

import java.util.Collection;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

/**
 * This class handles the interaction between the kata API and TDDT.
 * @author unknown
 * @version unknown
 */
public class TDDTCompiler {

	private String info;

	/**
	 * @return Compile errors.
     */
	public String getInfo() {
		return this.info;
	}

	/**
	 * Compiles the passed code and sets info to compile errors if there are any.
	 * @param code The code that is going to be compiled
	 * @param className The classname of the code
     * @return True if the code is compilable. Otherwise false.
     */
	public boolean compileCode(String code, String className) {
		//String className       = findClassName(code).trim();
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

	/**
	 * Compiles and runs the passed test and sets info to any error that may have occured.
	 * @param code The code that is going to be compiled
	 * @param className The classname of the code
     * @return True if the test is compile- and runnable. Otherwise false.
     */
	public boolean compileAndRunTests(String code, String codeClassName, String test, String testClassName) {
		CompilationUnit cuCode = new CompilationUnit(codeClassName, code, false);
		CompilationUnit cuTest = new CompilationUnit(testClassName, test, true);
		JavaStringCompiler jsc = CompilerFactory.getCompiler(cuCode, cuTest);
		jsc.compileAndRunTests();
		CompilerResult cr = jsc.getCompilerResult();

		if (cr.hasCompileErrors()) {
			info = formatCompileErrors(cr, cuCode);
			info += formatCompileErrors(cr, cuTest);
			return false;
		}

		TestResult tr = jsc.getTestResult();

		if (tr.getNumberOfFailedTests() != 0) {
			info = formatFailingTests(tr);
			return false;
		}

		return true;
	}

	/**
	 * This was used to determine the classname in a former version. Not used any longer.
	 * @param text The class code.
	 * @return The classname.
     */
	@SuppressWarnings("unused")
	@Deprecated
	private String findClassName(String text) {
		//offset 6 for the String "class "
		return text.substring(text.indexOf("class") + 6, text.indexOf(" {"));
	}

	/**
	 * This formats any compile errors.
	 * @param cr The CompilerResult of the uncompilable class.
	 * @param cu The CompilationUnit of the uncompilable class.
     * @return A String that contains all the errors.
     */
	private String formatCompileErrors(CompilerResult cr, CompilationUnit cu) {
		StringBuilder output = new StringBuilder();
		Collection<CompileError> errorList = cr.getCompilerErrorsForCompilationUnit(cu);
		for (CompileError error : errorList) {
			output.append(error.toString());
			output.append("\n");
		}
		return output.toString();
	}

	/**
	 * @see #formatCompileErrors(CompilerResult, CompilationUnit)
	 * @param tr The testresult of the failed test.
	 * @return A String that contains all the errors.
     */
	private String formatFailingTests(TestResult tr) {
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
