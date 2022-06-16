package br.usp.icmc.labes.jstatemodeltest.testgen.fsm;

import br.usp.icmc.labes.jstatemodeltest.pcomplete.Pair;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class TestSequenceTest {
  @Test
  public void getAllPrefixesFromTests() {
    ArrayList<String> prefixes = TestSequence.getAllPrefixesFrom("EPSILON");
    System.out.println(prefixes);
    Assert.assertTrue((prefixes.size() == 1));
    prefixes = TestSequence.getAllPrefixesFrom("a,bb,ccc");
    System.out.println(prefixes);
    Assert.assertTrue((prefixes.size() == 4));
    prefixes = TestSequence.getAllPrefixesFrom("alpha,beta,alpha,beta");
    System.out.println(prefixes);
    Assert.assertTrue((prefixes.size() == 5));
    prefixes = TestSequence.getAllPrefixesFrom("alpha");
    System.out.println(prefixes);
    Assert.assertTrue((prefixes.size() == 2));
    System.out.println(TestSequence.getAllSuffixesFrom("alpha,beta,gamma"));
    System.out.println(TestSequence.getCommonSuffixesFrom("alpha,beta,gamma", "beta,gamma"));
    Assert.assertTrue((TestSequence.lenght("EPSILON") == 0));
    Assert.assertEquals(1L, TestSequence.lenght("a"));
    Assert.assertEquals(2L, TestSequence.lenght("a,b"));
    Assert.assertEquals(4L, TestSequence.lenght("a,b,gamma,delta"));
    Pair p = new Pair("a,b", "b,c,d");
    System.out.println(p.getShorter());
    System.out.println(p.getlonger());
  }
  
  @Test
  public void testTestSet() {
    ArrayList<String> s1 = new ArrayList<String>();
    s1.add("a");
    s1.add("b");
    s1.add("c");
    ArrayList<String> s2 = new ArrayList<String>();
    s2.add("c");
    s2.add("a");
    System.out.println(TestSet.minus(s1, s2));
    Assert.assertEquals(1L, TestSet.minus(s1, s2).size());
    System.out.println(TestSet.minus(s1, "c"));
    Assert.assertEquals(2L, TestSet.minus(s1, "c").size());
  }
  
  @Test
  public void testIsPrefix() {
    Assert.assertTrue(TestSequence.isPrefixOf("aa", "aa,bb"));
    Assert.assertTrue(TestSequence.isPrefixOf("a,b,c", "a,b,c"));
    Assert.assertTrue(TestSequence.isPrefixOf(TestSequence.EPSILON, "a,b,c"));
    Assert.assertFalse(TestSequence.isPrefixOf("a", TestSequence.EPSILON));
    Assert.assertTrue(TestSequence.isPrefixOf(TestSequence.EPSILON, TestSequence.EPSILON));
    Assert.assertFalse(TestSequence.isPrefixOf("a,b,c,d", "a,b,c"));
    Assert.assertFalse(TestSequence.isPrefixOf("aa", "aaa,bb"));
    Assert.assertTrue(TestSequence.isPrefixOf("aaa", "aaa,bb"));
    Assert.assertFalse(TestSequence.isPrefixOf("aaa,b", "aaa,bb"));
    Assert.assertTrue(TestSequence.isPrefixOf("aaa,bb", "aaa,bb"));
  }
  
  @Test
  public void testGetSuffixFrom() {
    Assert.assertEquals("a,a", TestSequence.getSuffixFrom("a,a", TestSequence.EPSILON));
    Assert.assertEquals(TestSequence.EPSILON, TestSequence.getSuffixFrom(TestSequence.EPSILON, TestSequence.EPSILON));
    Assert.assertEquals("a", TestSequence.getSuffixFrom("aaa,a", "aaa"));
    Assert.assertEquals(TestSequence.EPSILON, TestSequence.getSuffixFrom("aaa,bb", "aaa,bb"));
  }
  
  @Test
  public void testGetPrefixFrom() {
    Assert.assertEquals("a,a", TestSequence.getPrefixFrom("a,a", TestSequence.EPSILON));
    Assert.assertEquals(TestSequence.EPSILON, TestSequence.getPrefixFrom(TestSequence.EPSILON, TestSequence.EPSILON));
    Assert.assertEquals("aaa", TestSequence.getPrefixFrom("aaa,a", "a"));
    Assert.assertEquals(TestSequence.EPSILON, TestSequence.getPrefixFrom("aaa,bb", "aaa,bb"));
    Assert.assertEquals("a,b,c,d", TestSequence.getPrefixFrom("a,b,c,d,e", "e"));
    Assert.assertEquals("a,b,c", TestSequence.getPrefixFrom("a,b,c,d,e", "d,e"));
    Assert.assertEquals("a,b", TestSequence.getPrefixFrom("a,b,c,d,e", "c,d,e"));
    Assert.assertEquals("a", TestSequence.getPrefixFrom("a,b,c,d,e", "b,c,d,e"));
    Assert.assertEquals("a", TestSequence.getPrefixFrom("a,bb", "bb"));
    Assert.assertEquals("a,bb,ccc", TestSequence.getPrefixFrom("a,bb,ccc,ddd", "ddd"));
    Assert.assertEquals("a,bb", TestSequence.getPrefixFrom("a,bb,ccc,ddd", "ccc,ddd"));
    Assert.assertEquals("a", TestSequence.getPrefixFrom("a,bb,ccc,ddd", "bb,ccc,ddd"));
    Assert.assertEquals(TestSequence.EPSILON, TestSequence.getPrefixFrom("a,bb,ccc,ddd", "a,bb,ccc,ddd"));
  }
  
  @Test
  public void testIsSufix() {
    Assert.assertTrue(TestSequence.isSufixOf("bb", "aa,bb"));
    Assert.assertTrue(TestSequence.isSufixOf("a,b,c", "a,b,c"));
    Assert.assertTrue(TestSequence.isSufixOf(TestSequence.EPSILON, "a,b,c"));
    Assert.assertFalse(TestSequence.isSufixOf("a", TestSequence.EPSILON));
    Assert.assertTrue(TestSequence.isSufixOf(TestSequence.EPSILON, TestSequence.EPSILON));
    Assert.assertFalse(TestSequence.isSufixOf("a,b,c,d", "a,b,c"));
    Assert.assertFalse(TestSequence.isSufixOf("aa", "aaa,bb"));
    Assert.assertTrue(TestSequence.isSufixOf("bb", "aaa,bb"));
    Assert.assertFalse(TestSequence.isSufixOf("aaa,b", "aaa,bb"));
    Assert.assertTrue(TestSequence.isSufixOf("aaa,bb", "aaa,bb"));
    Assert.assertTrue(TestSequence.isSufixOf("eeeee", "a,bb,ccc,dddd,eeeee"));
    Assert.assertTrue(TestSequence.isSufixOf("dddd,eeeee", "a,bb,ccc,dddd,eeeee"));
    Assert.assertTrue(TestSequence.isSufixOf("ccc,dddd,eeeee", "a,bb,ccc,dddd,eeeee"));
    Assert.assertTrue(TestSequence.isSufixOf("bb,ccc,dddd,eeeee", "a,bb,ccc,dddd,eeeee"));
    Assert.assertTrue(TestSequence.isSufixOf("a,bb,ccc,dddd,eeeee", "a,bb,ccc,dddd,eeeee"));
    Assert.assertFalse(TestSequence.isSufixOf("eeee", "a,bb,ccc,dddd,eeeee"));
  }
  
  @Test
  public void testCalcCost() {
    ArrayList<String> T = new ArrayList<String>();
    TestSet.addAllPrefsOf(T, "x,x,y");
    TestSet.addAllPrefsOf(T, "x,y,y,y");
    TestSet.addAllPrefsOf(T, "y,x,y");
    TestSet.addAllPrefsOf(T, "y,y,x,x");
    TestSet.addAllPrefsOf(T, "y,y,y,y,y,y");
    TestSet.addAllPrefsOf(T, "x,x,x,y,x,y,y,y");
    System.out.println(TestSet.calcCost("x,x,y", "y", T, 0));
    System.out.println(TestSet.calcCost("y,x", "x", T, 0));
  }
}
