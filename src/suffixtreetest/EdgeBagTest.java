/**
 * Copyright 2012 Alessandro Bahgat Shehata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package suffixtreetest;

import Sufix.Link;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import Sufix.MapCharAndLink;

public class EdgeBagTest {



     @Test
     public void testPut() {
        MapCharAndLink bag = new MapCharAndLink();
        Link e1 = new Link("asd", null);
         Link e2 = new Link("errimo", null);
         Link e3 = new Link("foo", null);
         Link e4 = new Link("bar", null);
        bag.put('a', e1);
        bag.put('e', e2);
        bag.put('f', e3);
        bag.put('b', e4);
        assertTrue("Bag contains " + bag.values().size() + " elements", bag.values().size() == 4);
        assertTrue(bag.get('a').equals(e1));
        assertTrue(bag.get('e').equals(e2));
        assertTrue(bag.get('f').equals(e3));
        assertTrue(bag.get('b').equals(e4));
     }

     @Test
     public void testCast() {
         for (char c = '0'; c <= '9'; ++c) {
             assertEquals(c, (char)(byte)c);
         }

         for (char c = 'a'; c <= 'z'; ++c) {
             assertEquals(c, (char)(byte)c);
         }
     }
     
     public void testSort() {
         
     }

}