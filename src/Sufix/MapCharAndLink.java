package Sufix;

import java.util.Collection;
import java.util.Set;
import java.util.Map;

import java.util.Arrays;

public class MapCharAndLink implements Map<Character, Link>{
	
	private byte[] chars;
    private Link[] values;
    

    @Override
    public Link put(Character character, Link l) {
        char c = character.charValue();
        if (c != (char) (byte) c) {
            throw new IllegalArgumentException("Caracter  no valido " + c );
        }
        
        if (chars == null) {
            chars = new byte[0];
            values = new Link[0];
        }
        int index = buscar(c);
        Link pred = null;

        if (index < 0) {
            int tamanho = chars.length;
            //Idem que los links valorar ArrayList
            
            byte[] copy = new byte[tamanho + 1];
            System.arraycopy(chars, 0, copy, 0, tamanho);
            
            chars = copy;
            Link[] copy1 = new Link[tamanho + 1];
            System.arraycopy(values, 0, copy1, 0, tamanho);
            
            values = copy1;
            chars[tamanho] = (byte) c;
            values[tamanho] = l;
            tamanho++;

            sortArrays();

        } else {
            pred = values[index];
            values[index] = l;
        }
        return pred;
    }
    
   @Override
    public Link get(Object o) {
        return get(((Character) o).charValue());  
       //Tiene implicito la excepcion de Object si no es char
    }

    public Link get(char c) {
        if (c != (char) (byte) c) {
            throw new IllegalArgumentException("Caracter  no valido " + c);
        }
        
        int index = buscar(c);
        if (index < 0) {
            return null;
        }
        return values[index];
    }

    private int buscar(char c) {
        if (chars == null)
            return -1;
        
        
        //Para optimizar los tiempos lo podemos conversar luego
        
        
        

        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                return i;
            }
        }
        return -1;
    }

   @Override
    public Collection<Link> values() {
    	//Esto lo encontre por ahi y me parecio superbueno
        return Arrays.asList(values == null ? new Link[0] : values);
    }
    
    // Ordena el hash completo, esta bueno, igual lo encontre por ahi 
    
    private void sortArrays() {
        for (int i = 0; i < chars.length; i++) {
        	
         for (int j = i; j > 0; j--) {
             if (chars[j-1] > chars[j]) {
               byte temp = chars[j];
               chars[j] = chars[j-1];
               chars[j-1] = temp;

               Link lTemp = values[j];
               values[j] = values[j-1];
               values[j-1] = lTemp;
            }
         }
      }
    }
    
    @Override
    public boolean isEmpty() {
        return  (chars == null || chars.length == 0);
    }
    
    @Override
    public int size() {
        return chars == null ? 0 : chars.length;
    }
    
    
    //Esto es porque me daba problemas si no implementaba todos los metodos abstractos
    @Override
    public Set<Map.Entry<Character, Link>> entrySet() {
        throw new UnsupportedOperationException("No usado aqui");
    }
    
    @Override
    public Set<Character> keySet() {
        throw new UnsupportedOperationException("No usado aqui");
    }
    
    @Override
    public void clear() {
        throw new UnsupportedOperationException("No usado aqui");
    }
    
    @Override
    public void putAll(Map<? extends Character, ? extends Link> m) {
        throw new UnsupportedOperationException("No usado aqui");
    }
    
    @Override
    public Link remove(Object key) {
        throw new UnsupportedOperationException("No usado aqui");
    }
    
    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("No usado aqui");
    }
    
    @Override
    public boolean containsValue(Object key) {
        throw new UnsupportedOperationException("No usado aqui");
    }

}
