package Sufix;

import java.util.Collection;

public  class ResultadosStruct {
	
	public int total;
    
    public Collection<Integer> resultados;

    public ResultadosStruct(Collection<Integer> r, int tr) {
        this.total = tr;
        this.resultados = r;
    }
}
