package com.rt.tk.tims;

import java.beans.Customizer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Worker worker = new Worker();
		Thread threadWorker = new Thread(worker);
		threadWorker.start();
		try {
			threadWorker.join(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread workerThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Hello");
			}
			
		});

		workerThread2.start();
		WorkerThread objThread = new WorkerThread();
		objThread.start();
		*/
		MyHouse house  = new MyHouse();
		
		Thread customer1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				house.eatPizza();
			}
			
			
			
		});
		
		Thread customer2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				house.eatPizza();
			}
			
			
			
		});		
		
		Thread delivery = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				house.pizzaGuy();
			}
			
		});
		
		customer1.start();
		customer2.start();
	    delivery.start();	
			
	}

}
