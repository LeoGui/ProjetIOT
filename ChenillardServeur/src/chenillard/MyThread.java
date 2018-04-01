package chenillard;

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.process.ProcessCommunicator;

public class MyThread extends Thread{
	public Chenillard chen;
	public ProcessCommunicator pc;
	public boolean etat;
	public boolean ordre;
	public int speed;
	public int j = 0;
	public String [] testordre = {"1","2","3","4"};
	public String lampe;

	public MyThread(Chenillard chen,ProcessCommunicator pc) {
		this.chen = chen;
		this.pc = pc;
	}

	public void run() {
		etat = chen.GetEtat();
		speed = chen.getSpeed();
		ordre = chen.getOrdre();
		try {
			while(etat !=false) {
				for (int i =0; i<4;i++) {
					if (i == 0 && etat == true) {
						if(ordre != true) {
							lampe = testordre[i];
						}
						Lampe();
						pc.write(new GroupAddress("0/0/" + lampe),true);
						Thread.sleep(speed * 100);
						pc.write(new GroupAddress("0/0/" + lampe),false);
						etat = chen.GetEtat();
						ordre = chen.getOrdre();
						speed = chen.getSpeed();
					}

					if (i == 1 && etat == true) {
						if(ordre != true) {
							lampe = testordre[i];
						}
						Lampe();
						pc.write(new GroupAddress("0/0/" + lampe),true);
						Thread.sleep(speed * 100);
						pc.write(new GroupAddress("0/0/" + lampe),false);
						etat = chen.GetEtat();
						ordre = chen.getOrdre();
						speed = chen.getSpeed();
					}


					if (i == 2 && etat == true) {
						if(ordre != true) {
							lampe = testordre[i];
						}
						Lampe();
						pc.write(new GroupAddress("0/0/" + lampe),true);
						Thread.sleep(speed * 100);
						pc.write(new GroupAddress("0/0/" + lampe),false);
						etat = chen.GetEtat();
						ordre = chen.getOrdre();
						speed = chen.getSpeed();
					}

					if (i == 3 && etat == true) {
						if(ordre != true) {
							lampe = testordre[i];
						}
						Lampe();
						pc.write(new GroupAddress("0/0/" + lampe),true);
						Thread.sleep(speed * 100);
						pc.write(new GroupAddress("0/0/" + lampe),false);
						etat = chen.GetEtat();
						ordre = chen.getOrdre();
						speed = chen.getSpeed();
					}
				}

				//				while (etat != false && ordre == false) {
				//					for (int i =0; i<4;i++) {
				//
				//						if (i == 0) {
				//							pc.write(new GroupAddress("0/0/1"),true);
				//							Thread.sleep(speed * 100);
				//							pc.write(new GroupAddress("0/0/1"),false);
				//							etat = chen.GetEtat();
				//							speed = chen.getSpeed();
				//						}
				//
				//						if (i == 1 && etat == true) {
				//							pc.write(new GroupAddress("0/0/2"),true);
				//							Thread.sleep(speed * 100);
				//							pc.write(new GroupAddress("0/0/2"),false);
				//							etat = chen.GetEtat();
				//							speed = chen.getSpeed();
				//						}
				//
				//
				//						if (i == 2 && etat == true) {
				//							pc.write(new GroupAddress("0/0/3"),true);
				//							Thread.sleep(speed * 100);
				//							pc.write(new GroupAddress("0/0/3"),false);
				//							etat = chen.GetEtat();
				//							speed = chen.getSpeed();
				//						}
				//
				//						if (i == 3 && etat == true) {
				//							pc.write(new GroupAddress("0/0/4"),true);
				//							Thread.sleep(speed * 100);
				//							pc.write(new GroupAddress("0/0/4"),false);
				//							etat = chen.GetEtat();
				//							speed = chen.getSpeed();
				//						}
				//
				//					}
				//
				//				}

				if(etat == false) {
					pc.write(new GroupAddress("0/0/1"),false);
					pc.write(new GroupAddress("0/0/2"),false);
					pc.write(new GroupAddress("0/0/3"),false);
					pc.write(new GroupAddress("0/0/4"),false);
					etat = chen.GetEtat();
					speed = chen.getSpeed();
				}
			}
		}
		catch (KNXException | InterruptedException e) {
			System.out.println("Error reading KNX datapoint: " + e.getMessage());
		}
	}

	public void Lampe() {
		if (ordre == true) {
			testordre = chen.getTabOrdre();
			lampe = testordre[j];
			j++;
			if (j == 4) {
				j =0;
			}
		}
	}
	
	
}
