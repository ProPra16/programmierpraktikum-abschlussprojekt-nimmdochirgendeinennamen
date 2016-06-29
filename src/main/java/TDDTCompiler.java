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
		String className       = findClassName(code).trim();
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
		String className       = findClassName(code).trim();
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

/*

Problems with API of virtual-cata


Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
	at javafx.fxml.FXMLLoader$MethodHandler.invoke(FXMLLoader.java:1774)
	at javafx.fxml.FXMLLoader$ControllerMethodEventHandler.handle(FXMLLoader.java:1657)
	at com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
	at com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:238)
	at com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
	at com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
	at javafx.event.Event.fireEvent(Event.java:198)
	at javafx.scene.Node.fireEvent(Node.java:8411)
	at javafx.scene.control.Button.fire(Button.java:185)
	at com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:182)
	at com.sun.javafx.scene.control.skin.BehaviorSkinBase$1.handle(BehaviorSkinBase.java:96)
	at com.sun.javafx.scene.control.skin.BehaviorSkinBase$1.handle(BehaviorSkinBase.java:89)
	at com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:218)
	at com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
	at com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:238)
	at com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
	at com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
	at javafx.event.Event.fireEvent(Event.java:198)
	at javafx.scene.Scene$MouseHandler.process(Scene.java:3757)
	at javafx.scene.Scene$MouseHandler.access$1500(Scene.java:3485)
	at javafx.scene.Scene.impl_processMouseEvent(Scene.java:1762)
	at javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2494)
	at com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:352)
	at com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:275)
	at java.security.AccessController.doPrivileged(Native Method)
	at com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$355(GlassViewEventHandler.java:388)
	at com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:389)
	at com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:387)
	at com.sun.glass.ui.View.handleMouseEvent(View.java:555)
	at com.sun.glass.ui.View.notifyMouse(View.java:937)
	at com.sun.glass.ui.gtk.GtkApplication._runLoop(Native Method)
	at com.sun.glass.ui.gtk.GtkApplication.lambda$null$50(GtkApplication.java:139)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at sun.reflect.misc.Trampoline.invoke(MethodUtil.java:71)
	at sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at sun.reflect.misc.MethodUtil.invoke(MethodUtil.java:275)
	at javafx.fxml.FXMLLoader$MethodHandler.invoke(FXMLLoader.java:1771)
	... 48 more
Caused by: java.lang.NoClassDefFoundError: Task1Test (wrong name: test/java/Task1Test)
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:760)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
	at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at vk.core.internal.InternalCompiler.loadTests(InternalCompiler.java:122)
	at vk.core.internal.InternalCompiler.runAllTests(InternalCompiler.java:98)
	at vk.core.internal.InternalCompiler.compileAndRunTests(InternalCompiler.java:65)
	at main.java.TDDTCompiler.compileAndRunTests(TDDTCompiler.java:48)
	at main.java.TDDTCompiler.compile(TDDTCompiler.java:17)
	at main.java.Controller.checkTest(Controller.java:133)
	at main.java.Controller.nextPhase(Controller.java:44)
	... 58 more

*/
