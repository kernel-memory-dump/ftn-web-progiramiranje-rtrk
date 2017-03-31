package com.rt.rk.tims;

public class Main {

	public static void main(String[] args) {
		Worker worker = new Worker();
		Thread threadWorker = new Thread(worker);
		threadWorker.start();
		try{
			threadWorker.join(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		Thread workerThread2 = new Thread(new Runnable(){
			public void run(){
				System.out.println("Zdravo2");
			}

		});
		workerThread2.start();
		WorkerThread klasaThread = new WorkerThread();
		klasaThread.start();
		
		
		MyHouse kuca = new MyHouse();
		
		Thread musterija1 = new Thread(new Runnable(){

			@Override
			public void run() {
				kuca.eatPizza();
				
			}
			
			
		
	});
		
		Thread musterija2 = new Thread(new Runnable(){

			@Override
			public void run() {
				kuca.eatPizza();
				
			}
			
			
		
	});
		
		
		Thread dostavljac = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				kuca.pizzaGuy();
				
			}
			
			
		
	});
		
		musterija1.start();
		musterija2.start();
		dostavljac.start();
		
		
	}
}