package com.chenmeng.project.spring.springmvc;

/**
 * 多种 Controller 实现
 *
 * @author chenmeng
 */
public interface Controller {

}

//
class HttpController implements Controller {
	public void doHttpHandler() {
		System.out.println("http...");
	}
}

//
class SimpleController implements Controller {
	public void doSimplerHandler() {
		System.out.println("simple...");
	}
}

//
class AnnotationController implements Controller {
	public void doAnnotationHandler() {
		System.out.println("annotation...");
	}
}
