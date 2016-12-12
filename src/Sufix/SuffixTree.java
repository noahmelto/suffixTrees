package Sufix;

import java.util.ArrayList;
import java.util.Collection;


import java.util.Collections;


public class SuffixTree {

    Nodo nodoGlobal;
    String stringGlobal;
    Boolean booleanGlobal;

	private  Nodo raiz = new Nodo();
	
	//Indice del ultimo anhadido
	
    private int ultAdded = 0;
    
    //Ultima hoja anhadida 
    private Nodo hoja = raiz;
    
    

    //Busca y devuelve los resultados
    
    public Collection<Integer> buscar(String w) {
        Nodo temp = buscarNodo(w);
        if (temp == null) {
            return Collections.EMPTY_LIST;
        }
        return temp.getInfo();
    }


    //para devolver el nodo que contiene la palabra si es que existe
    
    private Nodo buscarNodo(String w) {
        
        Nodo itN = raiz;
        Link itL;

        for (int i = 0; i < w.length(); ++i) {
            char ch = w.charAt(i);
            // se baja por el string en el arbol
            itL = itN.getLink(ch);
            
            //me daba problemas con la verificacion al reves por eso pongo null primero
            if ( itL == null) {
                // si entra aqui es que no hay ninguna empezando cone ste caracter
//            	System.out.println("No hay ninguna empezando con este caracter");
            	
                return null;
            } else {
                String tempValue = itL.getValue();
                int largo = Math.min(w.length() - i, tempValue.length());
                if (!w.regionMatches(i, tempValue, 0, largo)) {
                    // Si entra aqui es que no es igual a la que esta en esta parte del arbol
                //	System.out.println("No son iguales");
                    return null;
                }

                if (tempValue.length() >= w.length() - i) {
                    return itL.getDestino();
                    
                } else {
                    // seguimos para el proximo
                    itN = itL.getDestino();
                    i += largo - 1;
                	}
            }
        	}
        return null;
    }

    //Agrega el indice al arbol
    
    public void addIndex(String key, int index) throws IllegalStateException {
        if (index < ultAdded) {
            throw new IllegalStateException("El indice debe ser mayor que el ultimo agregado");
        } else {
            ultAdded = index;
        }

        // para empezar desde la raiz el procedimiento
        hoja = raiz;

        String valorInicial = key;
        Nodo n = raiz;

        // se empieza a construir como Ukkonen
        
        String texto = "";
        
        for (int i = 0; i < valorInicial.length(); i++) {
            
            texto += valorInicial.charAt(i);
            // use intern to make sure the resulting string is in the pool.
            //texto = texto.intern();

            actualizarSufijos(n, texto, valorInicial.substring(i), index);

            deepNValue(nodoGlobal, stringGlobal);

            texto = stringGlobal;
            n = nodoGlobal;
        }

        // comprueba si es necesario adicionar el link y lo adiciona
        if (null == hoja.getSuffix() && hoja != raiz && hoja != n) {
            hoja.setSuffix(n);
        }

    }

    // paso que se comprueba si es necesario dividir y divide
    
    private void checkDiv( Nodo in,  String segmento,  char ch,  String valorInicial,  int ind) {
        // descend the tree as far as possible
        deepNValue(in, segmento);

        Nodo n = nodoGlobal;

        String str = stringGlobal;

        if (!"".equals(str)) {
        	
        	
             Link g = n.getLink(str.charAt(0));

            String val = g.getValue();
            
            if (val.length() > str.length() && val.charAt(str.length()) == ch) {
                booleanGlobal = true;
                nodoGlobal = n;
                		return ;
            } else {
                // si entra aqui es que se necesita dividir el link
            //	System.out.println("Esta divideindo!");
                String newVal = val.substring(str.length());
                assert (val.startsWith(str));

                
                Nodo r = new Nodo();
                Link newL = new Link(str, r);

                g.setValue(newVal);

                // Creando el link de n con r
                r.addLink(newVal.charAt(0), g);
                n.addLink(str.charAt(0), newL);
                nodoGlobal = r;
                booleanGlobal = false;
                return ;
            }

        } else {
        	
            Link l = n.getLink(ch);
            if (null == l) {
                // Si entra aqui es que no hay transicion t
        //    	System.out.println("No hay transicion t");
            	booleanGlobal = false;
                nodoGlobal = n;
                return ;
                
                
            } else {
                if (valorInicial.equals(l.getValue())) {
                    // actualizacion
                    l.getDestino().addIndRef(ind);
                    nodoGlobal = n;
                    booleanGlobal = true;
                    return ;
                    
                } else if (valorInicial.startsWith(l.getValue())) {
                    nodoGlobal = n;
                    booleanGlobal = true;
                    return ;
                    
                	} else if (l.getValue().startsWith(valorInicial)) {
                		
                    // Si entra aqui es que se necesita divir igual que mas arriba
          //      		System.out.println("Esta dividiendo!");

                		Nodo nNodo = new Nodo();
                		nNodo.addIndRef(ind);

                		Link newL = new Link(valorInicial, nNodo);

                		l.setValue(l.getValue().substring(valorInicial.length()));
                		nNodo.addLink(l.getValue().charAt(0), l);

                		n.addLink(ch, newL);
                    nodoGlobal = n;
                    booleanGlobal = false;
                        return ;
                    
                } else {
                	
                	
                    // deben tener alguna substring igual
                	//System.out.println("subtring en comun");
                    nodoGlobal = n;
                    booleanGlobal = true;
                    return ;

               }
            	}
        	}

    }

    //Devuelve el nodo mas lejano al que se puede llegar con la cadena que se le pasa y emparejado con el valor que se le debe anhadir al link
    private void deepNValue( Nodo n,  String walkingStr) {

        if ("".equals(walkingStr)) {
            nodoGlobal = n;
            stringGlobal = walkingStr;
            return ;
            
        } else {
            Nodo itN = n;
            String str = walkingStr;
            Link g = n.getLink(str.charAt(0));
            
            // baja por el arbol mientras pueda 
            while (g != null && str.startsWith(g.getValue())) 
            {
                str = str.substring(g.getValue().length());
                itN = g.getDestino();
                
                if (str.length() > 0) {
                    g = itN.getLink(str.charAt(0));
                	}
            }

            nodoGlobal = itN;
            stringGlobal = str;
            	return ;
        }
    }

    //Se actualiza el arbol a partir de los parametros dados
    
    private void actualizarSufijos( Nodo innode,  String segmento,  String restante,  int ind) {
    	
    	String tempStr = segmento;
    	
        Nodo n = innode;
        
        char nChar = segmento.charAt(segmento.length() - 1);

        Nodo raizOriginal = raiz;

        checkDiv(n, tempStr.substring(0, tempStr.length() - 1), nChar, restante, ind);


        Nodo r = nodoGlobal;
        
        boolean meta = booleanGlobal;

        Nodo nhoja;
       
        while (!meta) {
            
            Link tempL = r.getLink(nChar);
            if (null != tempL) {
                // Esto lo agregue porque estuve leyendo que puede tener nodos mas profundos en esta etapa y por eso puse el parche este 
                nhoja = tempL.getDestino();
            } else {
                // Si entra aqui es que debe construir una hoja nueva
            	
            	//System.out.println("hoja nueva");
                nhoja = new Nodo();
                nhoja.addIndRef(ind);
                Link newL = new Link(restante, nhoja);
                r.addLink(nChar, newL);
            }

            // actualiza el link con la hoja nueva
            if (hoja != raiz) {
                hoja.setSuffix(nhoja);
            }
            hoja = nhoja;

            
            if (raizOriginal != raiz) {
                raizOriginal.setSuffix(r);
            }

            
            raizOriginal = r;

            
            if (null == n.getSuffix()) { // para el nodo raiz
                assert (raiz == n);
                // caso especial
                tempStr = tempStr.substring(1);
            } else {
                deepNValue(n.getSuffix(), safeCutLastChar(tempStr));
                n = nodoGlobal;
                
                
                // con intern para asegurarnos que tempstr es una referencia de string pool
                tempStr = (stringGlobal + tempStr.charAt(tempStr.length() - 1)).intern();
            }

            
            checkDiv(n, safeCutLastChar(tempStr), nChar, restante, ind);
            r = nodoGlobal;
            meta = booleanGlobal;

        }

        
        if (raizOriginal != raiz) {
        	
            raizOriginal.setSuffix(r);
        
        }

        nodoGlobal = n;
        stringGlobal = tempStr;
        return ;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    private String safeCutLastChar(String seq) {
        if (seq.length() == 0) {
            return "";
        }
        return seq.substring(0, seq.length() - 1);
    }

    public int contar() {
        return raiz.contarResultados();
    }

   

    
    

}
