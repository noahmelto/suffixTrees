package Sufix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class Main {
	
	public static Set<String> getSubstrings(String str) {
        Set<String> ret = new HashSet<String>();
        // compute all substrings
        for (int len = 1; len <= str.length(); ++len) {
            for (int start = 0; start + len <= str.length(); ++start) {
                String itstr = str.substring(start, start + len);
                ret.add(itstr);
            }
        }

        return ret;
    }

	public static void main(String[] args) throws IOException {

		SuffixTree xMas = new SuffixTree();
		BufferedReader in = new BufferedReader(new FileReader("drae"));
		String str = in.readLine();
		ArrayList<String> str1 = new ArrayList<String>();
		String[] strResult = new String[(int)in.lines().count()];
		while (str != null) {

			String strAux[] = str.split(" ");
			int aLen = strResult.length;
			int bLen = strAux.length;
			String[] c= new String[aLen+bLen];
			System.arraycopy(strResult, 0, c, 0, aLen);
			System.arraycopy(strAux, 0, c, aLen, bLen);
			strResult = new String[c.length];
			System.arraycopy(c, 0, strResult, 0, c.length);


			str = in.readLine();
		}

		PrintWriter writer1 = new PrintWriter("construccion15.txt", "UTF-8");
		int maxCharact = 32768;
		int countChar =0;


		long tiempo_contruccion = 0;
		long contruccion_start = System.currentTimeMillis();



		for (int i = 0; i < strResult.length; i++) {
			if (countChar > maxCharact) {
				break;
			}
			if(strResult[i]!= null){
				//strResult[i]= Utils.normalize(strResult[i]);
				StringBuilder out = new StringBuilder();
				String l = strResult[i].toLowerCase();
				for (int j = 0; j < l.length(); ++j) {
					char c = l.charAt(j);
					if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9') {
						out.append(c);
					}
				}
				strResult[i] = out.toString();
				//str1.set(i, Utils.normalize(str1.get(i)));
				countChar+= strResult[i].length();
				xMas.addIndex(strResult[i], i);

			}

		}



		long contruccion_stop = System.currentTimeMillis();

		tiempo_contruccion = tiempo_contruccion + contruccion_stop - contruccion_start;
		writer1.println("tiempo de construccion: "+tiempo_contruccion);

		writer1.close();

		countChar =0;
		PrintWriter writer2 = new PrintWriter("busqueda15.txt", "UTF-8");
		for (int i = 0; i < strResult.length; i+=10) {
			if (countChar > maxCharact) {
				break;
			}
			if (strResult[i]!=null){
				countChar+= strResult[i].length();
				//int cantOccurr = 0;
				long tiempoBusqueda = 0;
				for (String s : getSubstrings(strResult[i])) {
					long busquedaStart = System.currentTimeMillis();
					//System.out.println("cadena "+ s + " i "+i);
					if (xMas.buscar(s).contains(i)) {
						//cantOccurr++;


					}

					long busquedaEnds = System.currentTimeMillis();
					tiempoBusqueda = tiempoBusqueda + busquedaEnds -busquedaStart;
					writer2.println("largo del patrón: "+s.length()+" tiempo de busqueda "+tiempoBusqueda);


				}
			}
		}
		writer2.close();
		in.close();

	}

}
