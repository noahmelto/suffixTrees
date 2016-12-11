package Sufix;

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] words = new String[] {"libertypike",
	            "franklintn",
	            "carothersjohnhenryhouse",
	            "carothersezealhouse",
	            "acrossthetauntonriverfromdightonindightonrockstatepark",
	            "dightonma",
	            "dightonrock",
	            "6mineoflowgaponlowgapfork",
	            "lowgapky",
	            "lemasterjohnjandellenhouse",
	            "lemasterhouse",
	            "70wilburblvd",
	            "poughkeepsieny",
	            "freerhouse",
	            "701laurelst",
	            "conwaysc",
	            "hollidayjwjrhouse",
	            "mainandappletonsts",
	            "menomoneefallswi",
	            "mainstreethistoricdistrict",
	            "addressrestricted",
	            "brownsmillsnj",
	            "hanoverfurnace",
	            "hanoverbogironfurnace",
	            "sofsavannahatfergusonaveandbethesdard",
	            "savannahga",
	            "bethesdahomeforboys",
	            "bethesda"};
		
		
		SuffixTree XMas = new SuffixTree();
		
		for (int i = 0; i < words.length; ++i) {
			XMas.addIndex(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = XMas.buscar(s);
                //assertNotNull("result null for string " + s + " after adding " + words[i], result);
                //assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }


        }
		
		

	}

}
