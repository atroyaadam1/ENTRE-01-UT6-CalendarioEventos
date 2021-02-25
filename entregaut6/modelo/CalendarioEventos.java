package entregaut6.modelo;   
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.*;
import entregaut6.io.CalendarioIO;
/**
 * Esta clase modela un sencillo calendario de eventos.
 * 
 * Por simplicidad consideraremos que los eventos no se solapan
 * y no se repiten
 * 
 * El calendario guarda en un map los eventos de una serie de meses
 * Cada mes (la clave en el map, un enumerado Mes) tiene asociados 
 * en una colección ArrayList los eventos de ese mes
 * 
 * Solo aparecen los meses que incluyen algún evento
 * 
 * Las claves se recuperan en orden alfabético
 * 
 * @Author : Anthonny Troya
 */
public class CalendarioEventos {
    private TreeMap<Mes, ArrayList<Evento>> calendario;

    /**
     * el constructor
     */
    public CalendarioEventos() {
        this.calendario = new TreeMap<>();
    }

    /**
     * añade un nuevo evento al calendario
     * Si la clave (el mes del nuevo evento) no existe en el calendario
     * se creará una nueva entrada con dicha clave y la colección formada
     * por ese único evento
     * Si la clave (el mes) ya existe se añade el nuevo evento insertándolo de forma
     * que quede ordenado por fecha y hora de inicio
     * 
     * Pista! Observa que en la clase Evento hay un método antesDe() que vendrá
     * muy bien usar aquí
     */
    public void addEvento(Evento nuevo) {
        Mes mes = nuevo.getMes();
        if(calendario.containsKey(mes)){
            ArrayList<Evento> evento = calendario.get(mes);
            for(Evento eventos : evento){
                if(eventos.antesDe(nuevo)){
                    evento.add(evento.indexOf(eventos),nuevo);
                }
            }
        } else {
            ArrayList<Evento> evento = new ArrayList<>();
            evento.add(nuevo);
            calendario.put(mes,evento);
        }

    }

    /**
     * Representación textual del calendario
     * Hacer de forma eficiente 
     * Usar el conjunto de entradas  
     */
    public String toString() {
        String str = "";
        Set<Map.Entry<Mes,ArrayList<Evento>>> nuevo = calendario.entrySet();
        for( Map.Entry<Mes, ArrayList<Evento>> entrada : nuevo){
            str = "\n" + str + entrada.getKey() + "\t" + entrada.getValue() +"\n";
        }   
        return str;
    }

    /**
     * Dado un mes devolver la cantidad de eventos que hay en ese mes
     * Si el mes no existe se devuelve 0
     */
    public int totalEventosEnMes(Mes mes) {
        if(calendario.containsKey(mes)){
            return calendario.get(mes).size();
        }
        return 0;
    }

    /**
     * Devuelve un conjunto (importa el orden) 
     * con los meses que tienen mayor nº de eventos
     * Hacer un solo recorrido del map con el conjunto de claves
     *  
     */
    public TreeSet<Mes> mesesConMasEventos() {
        TreeSet<Mes> resultado = new TreeSet<>();
        Set<Mes> nuevo = calendario.keySet();
        for(Mes mes : nuevo){
            int eventos = 0;
            if(eventos == calendario.get(mes).size()){
                resultado.add(mes);
            } else if(eventos < calendario.get(mes).size()){
                eventos = calendario.get(mes).size();
                resultado.add(mes);
            }
        }
        return resultado;
    }

    /**
     * Devuelve el nombre del evento de mayor duración en todo el calendario
     * Se devuelve uno solo (el primero encontrado) aunque haya varios
     */
    public String eventoMasLargo() {
        String nom = "";
        Set<Map.Entry<Mes, ArrayList<Evento>>> nuevo = calendario.entrySet();
        for(Map.Entry<Mes, ArrayList<Evento>> entrada: nuevo){
            ArrayList<Evento> eventos = entrada.getValue();
            for(Evento ev1: eventos){
                int duracion = 0;
                if(ev1.getDuracion() > duracion){
                    duracion = ev1.getDuracion();
                    nom = ev1.getNombre();
                }
            }
        }
        return nom;
    }

    /**
     * Borrar del calendario todos los eventos de los meses indicados en el array
     * y que tengan lugar el día de la semana proporcionado (se entiende día de la
     * semana como 1 - Lunes, 2 - Martes ..  6 - Sábado, 7 - Domingo)
     * 
     * Si alguno de los meses del array no existe el el calendario no se hace nada
     * Si al borrar de un mes los eventos el mes queda con 0 eventos se borra la entrada
     * completa del map
     */
    public int cancelarEventos(Mes[] meses, int dia) {
        int borrados=0;

        for(Mes mes : meses){
            ArrayList<Evento> eventos = calendario.get(mes);
            Iterator<Evento> it = eventos.iterator();
            if(eventos.size()==0){
                calendario.remove(meses);
            } else {
                calendario.put(mes,eventos);
            }
            if(calendario.containsKey(mes)){
                while(it.hasNext()){
                    Evento nuevo = it.next();
                    if(nuevo.getDia()== dia){
                        it.remove();
                        borrados++;
                    }
                }
            }
        }
        return borrados;
    }

    /**
     * Código para testear la clase CalendarioEventos
     */
    public static void main(String[] args) {
        CalendarioEventos calendario = new CalendarioEventos();
        CalendarioIO.cargarEventos(calendario);
        System.out.println(calendario);

        System.out.println();

        Mes mes = Mes.FEBRERO;
        System.out.println("Eventos en " + mes + " = "
            + calendario.totalEventosEnMes(mes));
        mes = Mes.MARZO;
        System.out.println("Eventos en " + mes + " = "
            + calendario.totalEventosEnMes(mes));
        System.out.println("Mes/es con más eventos "
            + calendario.mesesConMasEventos());

        System.out.println();
        System.out.println("Evento de mayor duración: "
            + calendario.eventoMasLargo());

        System.out.println();
        Mes[] meses = {Mes.FEBRERO, Mes.MARZO, Mes.MAYO, Mes.JUNIO};
        int dia = 6;
        System.out.println("Cancelar eventos de " + Arrays.toString(meses));
        int cancelados = calendario.cancelarEventos(meses, dia);
        System.out.println("Se han cancelado " + cancelados +
            " eventos");
        System.out.println();
        System.out.println("Después de cancelar eventos ...");
        System.out.println(calendario);
    }

}
