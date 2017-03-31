package com.rt.tk.tims;

public class Worker implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5 ; i++) {
         System.out.println("Vucicu Srederu.");
	      try {
			Thread.sleep(1000);
	     	 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }		
		}
		
	}

	
	
}
