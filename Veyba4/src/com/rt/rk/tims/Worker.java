package com.rt.rk.tims;

public class Worker implements Runnable {

	@Override
	public void run() {
		for(int i=0; i<5 ;i++){
			System.out.println("Politicka korektnost");
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
