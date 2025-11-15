package tpfinal.vista;

import vista.IOptionPane;

public class FalsoOptionPane implements IOptionPane {
	private String mensaje = null;
	public FalsoOptionPane() {
		super();
	}
	@Override
	public void ShowMessage(String arg0) {
		mensaje = arg0;
	}
	public String getMensaje() {
		return mensaje;
	}
}
