package chenillard;

public class DimmableSwitch {
	String name;
	String address;
	int vitesse;
	boolean etat;
	String ordre;



	public DimmableSwitch(boolean etat, int vitesse, String ordre) {
		super();
		this.ordre = ordre;
		this.vitesse = vitesse;
		this.etat = etat;
	}




	public String getOrdre() {
		return ordre;
	}

	public void setOrdre(String ordre) {
		this.ordre = ordre;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public boolean getEtat() {
		return etat;
	}
	
	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + vitesse;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DimmableSwitch other = (DimmableSwitch) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (vitesse != other.vitesse)
			return false;
		return true;
	}

}
