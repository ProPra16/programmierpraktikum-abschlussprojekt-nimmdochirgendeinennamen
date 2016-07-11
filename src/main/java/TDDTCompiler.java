/*
 * Copyright (c) 2016. Caro Jachmann, Dominik Kuhnen, Jule Pohlmann, Kai Brandt, Kai Holzinger
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

package main.java;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

import java.util.Collection;

public class TDDTCompiler {

	String info;

	public boolean compile(String code, boolean isTest, String classname) {
		if (isTest)
			return compileAndRunTests(code, classname);
		else
			return compileCode(code, classname);
	}
	
	public String getInfo() {
		return this.info;
	}

	private boolean compileCode(String code, String className) {
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

	private boolean compileAndRunTests(String code, String className) {
		//String className       = findClassName(code).trim();
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
