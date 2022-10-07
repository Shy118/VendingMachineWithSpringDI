package com.vendingmachine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vendingmachine.controller.VendingMachineController;

public class App {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
		controller.run();
	} //main

} //class
