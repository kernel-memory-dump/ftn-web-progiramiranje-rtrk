package com.rt.rk.web;

public class Worker implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		final int N = 4;

		for (int i = 0; i < N; i++) {
			try {
				System.out.println("zdravo");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
