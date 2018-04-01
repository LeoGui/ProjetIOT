package chenillard;

import java.net.InetSocketAddress;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.KNXAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.cemi.CEMILData;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.NetworkLinkListener;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

public class Chenillard implements Chenillardif{

	public static boolean etat;
	public static boolean ordre = false;
	public static KNXNetworkLinkIP netLinkIp;
	public static Chenillard chen;
	public ProcessCommunicator pc; 
	public MyThread t;
	public String str;
	public boolean boucle = false;
	public int speed = 5;
	public Scanner sc;
	public int j;
	public String [] testordre;
	public int demarrer = 0;
	public String address2;
	public KNXAddress address;


	public static void main (String...  args) {
		chen = new Chenillard();
		etat = false;
		chen.connection();

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessage() {
		chen.allumer();
		return "chenillard allumé";
	}
	
	public void connection (){

		final InetSocketAddress loc = new InetSocketAddress("192.168.0.105",0);
		final InetSocketAddress dest = new InetSocketAddress("192.168.0.21",KNXnetIPConnection.DEFAULT_PORT);

		try {
			netLinkIp = KNXNetworkLinkIP.newTunnelingLink(loc, dest, false, TPSettings.TP1); // ligne torsadée
			System.out.println("Connection established to server : " + netLinkIp);
			netLinkIp.addLinkListener(new NetworkLinkListener(){
				public void confirmation(FrameEvent arg0) {
				}

				public void indication(FrameEvent arg0) {
					address = ((CEMILData) arg0.getFrame()).getDestination();
					address2 = address.toString();
					if (address2.equals("0/2/0")) {
						allumer();
					}
					if (address2.equals("0/2/1")) {
						eteindre();
					}
				}

				public void linkClosed(CloseEvent arg0) {
				}
			});
			pc = new ProcessCommunicatorImpl(netLinkIp);

			System.out.println("start : demarre le chenillard / stop : arrete le chenillard / vitesse : gere la durée du chenillard / ordre : changer ordre du chenillard:");

			while(boucle == false) {
				sc = new Scanner(System.in);
				str = sc.nextLine();			
				if (str.equals("start")) {
					allumer();
				}
				if (str.equals("stop")) {
					eteindre();
				}
				if (str.equals("vitesse")) {
					changeSpeed();
				}
				if (str.equals("ordre")) {
					changeOrder();
				}
			}


		}



		catch (KNXException | InterruptedException e) {
			System.out.println("Error reading KNX datapoint: " + e.getMessage());
		}
	}



	public ProcessCommunicator GetPc() {
		return pc;
	}

	public  boolean GetEtat() {
		return etat;
	}

	public  boolean getOrdre() {
		return ordre;
	}

	public int getSpeed() {
		return speed;
	}

	public String[] getTabOrdre() {
		return testordre;	
	} 

	public void allumer() {
		t = new MyThread (chen, pc);
		etat = true;		
		t.start();	
		demarrer = 1;
	}

	public void changeSpeed() {
		System.out.println("tapez la vitesse désirée en seconde, superieur à 5 : ");
		speed = sc.nextInt();
	}

	public void changeOrder() {
		if (demarrer ==0) {
			allumer();
		}
		System.out.println("tapez lordre souhaité (par exemple : 2,3,1,4) : ");
		String order = sc.nextLine();
		testordre = order.split(",");
		ordre = true;
	}

	@Override
	public void eteindre() {
		etat = false;
	}

	@Override
	public void monter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void descendre() {
		// TODO Auto-generated method stub

	}

}
