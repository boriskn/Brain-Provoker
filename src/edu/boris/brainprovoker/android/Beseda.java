package edu.boris.brainprovoker.android;

import android.graphics.Paint;

public class Beseda {

	public String ime;
	public Paint barva;
	public boolean enako;

	public Beseda(final String i, final Paint p,final boolean b) {
		this.ime = i;
		this.barva = p;
		this.enako = b;
	}

	public Beseda() {
	}

	public String getBeseda() {
		return ime;
	}

	public Paint getBarva() {
		return barva;
	}

}
