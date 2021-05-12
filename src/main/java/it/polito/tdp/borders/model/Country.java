package it.polito.tdp.borders.model;

public class Country {
	
	private int cCode;
	private String stateAbb;
	private String stateNme;
	
	public Country(int cCode, String stateAbb, String stateNme) {
		this.cCode = cCode;
		this.stateAbb = stateAbb;
		this.stateNme = stateNme;
	}

	public int getcCode() {
		return cCode;
	}

	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public String getStateNme() {
		return stateNme;
	}

	public void setStateNme(String stateNme) {
		this.stateNme = stateNme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
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
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.stateNme;
	}

}
