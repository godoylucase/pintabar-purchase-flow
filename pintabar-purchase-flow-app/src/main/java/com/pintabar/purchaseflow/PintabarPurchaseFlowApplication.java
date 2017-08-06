package com.pintabar.purchaseflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.pintabar")
public class PintabarPurchaseFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(PintabarPurchaseFlowApplication.class, args);
	}

}
