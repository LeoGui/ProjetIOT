package chenillard;

import java.net.InetSocketAddress;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

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

@Path("Chenillard")

public class Chenillard {

	public static boolean etat = false;
	public static boolean ordre = false;
	public static KNXNetworkLinkIP netLinkIp;
	public static Chenillard chen = new Chenillard();
	public static ProcessCommunicator pc;
	public static MyThread t;
	public String str;
	public boolean boucle = false;
	public static int speed = 5;
	public Scanner sc;
	public int j;
	public static String[] testordre;
	public static String initordre = "1,2,3,4";
	public static int demarrer = 0;
	public String address2;
	public KNXAddress address;
	// DimmableSwitch ds = new DimmableSwitch(etat, speed,testordre);
	static DimmableSwitch ds = new DimmableSwitch(etat, speed, "1,2,3,4");
	private final static String STATE = "state";
	private final static String VITESSE = "vitesse";
	private final static String ORDRE = "ordre";

	

	// @Path("json")
	// @GET
	// @Produces(MediaType.APPLICATION_JSON)
	// public DimmableSwitch getSW() {
	// //System.out.println("test");
	// System.out.println("Switch info: " + ds.getEtat() + " " + ds.getOrdre() + " "
	// + ds.getVitesse());
	// return ds;
	// }

	@Path("connect")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String getConnect() {
		System.out.println("test");
		chen.connection();
		String connect = "Vous etes connectés";
		return connect;
	}
	
	@Path("json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DimmableSwitch getSW() {
		// System.out.println("test");
		System.out.println("Switch info: " + ds.getEtat() + " " + ds.getVitesse());
		return ds;
	}

	@Path("post")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DimmableSwitch postDimmableSwitch(MultivaluedMap<String, String> switchParams) {
		
		
		String state = switchParams.getFirst(STATE);
		String vitesse = switchParams.getFirst(VITESSE);
		String ordre = switchParams.getFirst(ORDRE);
				

		System.out.println("Storing posted " + ds.getEtat() + " " + ds.getVitesse() + " " + ds.getOrdre());

		// Selection de l'état
		if (state == null) {
			ds.setEtat(ds.getEtat());
		}
		else{
			ds.setEtat(Boolean.parseBoolean(state));
		}

		// Selection de la vitesse
		if (vitesse == null) {
			ds.setVitesse(ds.getVitesse());
		}
		else {
			ds.setVitesse(Integer.parseInt(vitesse));
			chen.changeSpeed(ds.getVitesse());
		}

		// Selection de l'ordre
		if (ordre == null) {
			ds.setOrdre(ds.getOrdre());
		}
		else{
			ds.setOrdre(ordre);
			chen.changeOrder(ds.getOrdre());
		}

		// En fonction de l'état, allumer ou éteindre
		if (ds.getEtat() == true) {
			System.out.println("chenillard allumé");
			chen.allumer();
		}
		if (ds.getEtat() == false) {
			System.out.println("chenillard eteint");
			chen.eteindre();
		}

		return ds;
	}

	// @POST
	// @Produces(MediaType.APPLICATION_JSON)
	// public DimmableSwitch postDimmableSwitch(DimmableSwitch ds) {
	// // sw.setValue(Integer.parseInt(value));
	// System.out.println("Switch info: " + ds.getEtat() + " " + ds.getOrdre() + " "
	// + ds.getVitesse());
	// if(ds.getEtat() == true && chen.GetEtat() == false) {
	// chen.allumer();
	// }
	// if (ds.getEtat() == false && chen.GetEtat() == true) {
	// chen.eteindre();
	// }
	// return ds;
	//
	// }

	public void connection() {

		final InetSocketAddress loc = new InetSocketAddress("192.168.0.105", 0);
		final InetSocketAddress dest = new InetSocketAddress("192.168.0.21", KNXnetIPConnection.DEFAULT_PORT);

		try {
			netLinkIp = KNXNetworkLinkIP.newTunnelingLink(loc, dest, false, TPSettings.TP1); // ligne torsadée
			System.out.println("Connection established to server : " + netLinkIp);
			netLinkIp.addLinkListener(new NetworkLinkListener() {
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

			System.out.println(
					"start : demarre le chenillard / stop : arrete le chenillard / vitesse : gere la durée du chenillard / ordre : changer ordre du chenillard: \n");

			// while (boucle == false) {
			// sc = new Scanner(System.in);
			// str = sc.nextLine();
			// if (str.equals("start")) {
			// allumer();
			// }
			// if (str.equals("stop")) {
			// eteindre();
			// }
			// if (str.equals("vitesse")) {
			// changeSpeed();
			// }
			// if (str.equals("ordre")) {
			// changeOrder();
			// }
			// }

		}

		catch (KNXException | InterruptedException e) {
			System.out.println("Error reading KNX datapoint: " + e.getMessage());
		}
	}

	public ProcessCommunicator GetPc() {
		return pc;
	}

	public boolean GetEtat() {
		return etat;
	}

	public boolean getOrdre() {
		return ordre;
	}

	public int getSpeed() {
		return speed;
	}

	public String[] getTabOrdre() {
		return testordre;
	}

	public void allumer() {
		if (demarrer == 0) {
			System.out.println("test");
			etat = true;
			t = new MyThread(chen, pc);
			t.start();
			demarrer = 1;
		}
	}

	public void changeSpeed(int vitesse) {
//		if (demarrer == 0) {
//			allumer();
//		}
		// System.out.println("tapez la vitesse désirée en seconde, superieur à 5 : ");
		speed = vitesse;
	}

	public void changeOrder(String test) {
		if (demarrer == 0) {
			allumer();
		}
		testordre = test.split(",");
		ordre = true;
	}

	public void eteindre() {
		etat = false;
		demarrer = 0;
	}

	public Chenillard getChenillard() {
		return chen;
	}
	// @Path("start")
	// @GET
	// @Produces(MediaType.TEXT_PLAIN)
	// public String getMessage() {
	//
	// chen.allumer();
	// return "Le chenillard est allume";
	// }
	//
	// @Path("stop")
	// @GET
	// @Produces(MediaType.TEXT_PLAIN)
	// public String getMessage2() {
	// chen.eteindre();
	// return "Le chenillard est eteint";
	// }

	//
	// @Override
	// public void monter() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void descendre() {
	// // TODO Auto-generated method stub
	//
	// }

}
