package entregaut6.demo;
import entregaut6.modelo.CalendarioEventos;
import entregaut6.interfaz.IUConsola;

/**
 * Punto de entrad a la aplicación
 */
public class AppCalendarioEventos {

	public static void main(String[] args) {
		CalendarioEventos calendario = new CalendarioEventos();
		IUConsola interfaz = new IUConsola(calendario);
		interfaz.iniciar();

	}

}
