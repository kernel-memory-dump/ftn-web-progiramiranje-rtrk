package isidor.rimski.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GlavnaKlasaServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server = new ServerSocket(8080);
		
		while(true) {
			Socket vezaKaKlientu = server.accept();
			
			new Thread(new Runnable() {
				
				InplementriraIntr impl = new InplementriraIntr();
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
	}

}
