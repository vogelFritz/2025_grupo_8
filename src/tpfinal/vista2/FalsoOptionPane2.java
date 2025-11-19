package tpfinal.vista2;

import vista.IOptionPane;

public class FalsoOptionPane2 implements IOptionPane {
	private String mensaje = null;
	public FalsoOptionPane2() {
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
