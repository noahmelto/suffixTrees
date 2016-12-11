package Sufix;

import java.util.*;


public class Nodo {

	private ArrayList<Integer> info;
   
    private int ultimo = 0;

    
    
    
    private final Map<Character, Link> links;
    
    
    
    private Nodo sufijo;
    
    private int count = -1;

 
    Nodo() {
        links = new MapCharAndLink();
        sufijo = null;
        info = new ArrayList<>();
    }

    
    public Collection<Integer> getInfo() {
        int cantElementos = -1;
        Set<Integer> colector = new HashSet<Integer>();
        
        
        for (int num : info) {
        	
            colector.add(num);
             if (colector.size() == cantElementos) {
            	 
              return colector;
            }
        }
        // falta hacerlo para los hijos
        
        for (Link l : links.values()) {
        	
             if (-1 == cantElementos || colector.size() < cantElementos) {
                for (int num : l.getDestino().getInfo()) {
                	
                    colector.add(num);
                    if (colector.size() == cantElementos) {
                        return colector;  ///Idem al caso base
                    }
                  }
                
          }
        }
        
        return colector;
    }
    
    
    private boolean contains(int index) {
        int menor = 0;
        int mayor = ultimo - 1;

        while (menor <= mayor) {
        	
            int medio = (menor + mayor)/2;
             
            int valorMedio = info.get(medio);

            if (valorMedio < index){   menor = valorMedio + 1;}
            else if (valorMedio > index)  { mayor = medio - 1;}
                 else
                   return true;
        }
        return false;
        // Java 5 equivalent to
        // return java.util.Arrays.binarySearch(data, 0, lastIdx, index) >= 0;
    }

   ///agrega la referencia a un indice
    public void addIndRef(int index) {
        for(int i = 0; i<info.size();i++){
            if(info.get(i)==index) return;
        }

        info.add(index);

        // actualiza agregando en todos los sufijos
        
        
         Nodo it = this.sufijo;
        while (it != null) {
            if (it.contains(index)) {
                break;
            }
            
            it.addIndRef(index);
            it = it.sufijo;
        }

    }

    
    

    ///Cantidad de resultados en el nodo y sus hijos
    
    protected int contarResultados() {
        contarResultadosAux();
        return count;
    }

    private Set<Integer> contarResultadosAux() {

        Set<Integer> collect = new HashSet<Integer>();
        
        for (int num : info) {
            collect.add(num);
        }


         for (Link l : links.values()) {
            for (int num : l.getDestino().contarResultadosAux()) {
                collect.add(num);
            }
        }

        count = collect.size();
        return collect;
        
    }

    //Para devolver los resultados agregue una excepcion para que solo se ejecute si contarResultados se ejecuto, porque -1 no sirve como valor para lo que se utiliza
    public int getCount() throws IllegalStateException {
    	
        if (count < 0) {
            throw new IllegalStateException("Se debe contar primero!!");
        }

        return count;
    }

    public void addLink(char ch, Link l) {
    	//como es hash
        links.put(ch, l);
    }

    public Link getLink(char ch) {
        return links.get(ch);
     }
    
    

    public Map<Character, Link> getLinks() {
        return links;
      }

    public Nodo getSuffix() {
        return sufijo;
    }
    
    

    public void setSuffix(Nodo suf) {
        	this.sufijo = suf;
    	}

}
