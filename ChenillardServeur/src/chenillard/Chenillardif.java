package chenillard;

import tuwien.auto.calimero.process.ProcessCommunicator;

public interface Chenillardif {
	public void allumer();
	public void eteindre();
	public void monter();
	public void descendre();
	public ProcessCommunicator GetPc();
	public  boolean GetEtat();
}
