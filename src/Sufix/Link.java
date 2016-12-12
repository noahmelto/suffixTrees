package Sufix;

public class Link {
	
	private String value;
	
    private Nodo destino;

    public String getValue() {
        return value;
    }

    
    public void setValue(String v) {
        this.value = v;
    }

    public Nodo getDestino() {
        return destino;
    }

    public Link(String v, Nodo dest) {
        this.value = v;
        this.destino = dest;
    }

}
