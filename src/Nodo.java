import java.util.Collection;
import java.util.Set;
import java.util.Map;

import java.util.HashSet;

import java.util.Arrays;



public class Nodo {

	private int[] info;
   
    private int ultimo = 0;
   
    private static final int INIT = 0;
   
    private static final int INC = 1;
    
    
    
    private final Map<Character, Link> links;
    
    
    
    private Nodo sufijo;
    
    private int count = -1;

 
    Nodo() {
        links = new MapCharAndLink();
        sufijo = null;
        info = new int[INIT];
    }

    
    public  Collection<Integer> getInfo() {
        return getInfo(-1);
    }

    
    public Collection<Integer> getInfo(int cantElementos) {
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
        	
            int medio = (menor + mayor) >>> 1;
             
            int valorMedio = info[medio];

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
    	if (java.util.Arrays.binarySearch(info, 0, ultimo, index)>= 0) return;
        

        addIndex(index);

        // actualiza agregando en todos los sufijos
        
        
         Nodo it = this.sufijo;
        while (it != null) {
            if (java.util.Arrays.binarySearch(it.info, 0, ultimo, index)>=0) {
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

    
    
    
    private void addIndex(int index) {
        if (ultimo == info.length) {
        	
        	//Pablo analiza si es mejor poner un array list aqui para quitarnos la copiadera 
            int[] copy = new int[info.length + INC];
            
            System.arraycopy(info, 0, copy, 0, info.length);
            info = copy;
            
            
        }
        info[ultimo++] = index;
    }
}
