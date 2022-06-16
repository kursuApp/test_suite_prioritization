package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RulerTest {
  ArrayList<Pair> C;
  
  ArrayList<Pair> D;
  
  ArrayList<String> T;
  
  Ruler ruler;
  
  ArrayList<Trigger> queue;
  
  @Before
  public void configure() {
    this.C = new ArrayList<Pair>();
    this.D = new ArrayList<Pair>();
    this.T = new ArrayList<String>();
    this.ruler = new Ruler(this.C, this.D, this.T);
    this.queue = new ArrayList<Trigger>();
    this.ruler.setQueue(this.queue);
  }
  
  @Test
  public void testRule01() {
    this.C.add(new Pair("a,a", "a,b"));
    System.out.println("before: " + this.C);
    this.ruler.rule01(new Pair("a,a", "a,b"));
    System.out.println("after:  " + this.C);
    this.C.clear();
    this.D.clear();
    this.queue.clear();
    this.C.add(new Pair("chi", "alpha"));
    this.ruler.rule01(new Pair("alpha", "beta"));
    System.out.println(this.queue.get(0));
    this.C.clear();
    this.D.clear();
    this.queue.clear();
    this.C.add(new Pair("chi", "alpha"));
    this.ruler.rule01(new Pair("beta", "alpha"));
    System.out.println(this.queue.get(0));
  }
  
  @Test
  public void testRule02() {
    this.T.add("EPSILON");
    this.T.add("a");
    this.T.add("a,b");
    System.out.println("before: " + this.C);
    this.ruler.rule02(new Pair("a", "a,b"));
    System.out.println("after:  " + this.C);
    Assert.assertTrue((this.C.size() == 0));
    this.T.add("a,c");
    this.T.add("a,b,c");
    System.out.println("before: " + this.C);
    this.ruler.rule02(new Pair("a", "a,b"));
    System.out.println("after:  " + this.C);
    Assert.assertTrue((this.C.size() == 1));
    this.T.clear();
    this.C.clear();
    this.queue.clear();
    this.T.add("EPSILON");
    this.T.add("a");
    this.T.add("a,a");
    System.out.println("before: " + this.C);
    this.ruler.rule02(new Pair("EPSILON", "a"));
    System.out.println("after:  " + this.C);
    Assert.assertTrue((this.C.size() == 1));
    System.out.println(this.queue.get(0));
  }
  
  @Test
  public void testRule03() {
    this.ruler.rule03(new Pair("a,g", "b,g"));
    System.out.println(this.queue.get(0));
    this.queue.clear();
    this.D.clear();
    this.C.clear();
    this.ruler.rule03(new Pair("a,g,g", "b,g,g"));
    System.out.println(this.queue.get(0));
    System.out.println(this.queue.get(1));
  }
  
  @Test
  public void testRule04() {
    this.T.add("EPSILON");
    this.T.add("a");
    this.T.add("a,a");
    this.T.add("b");
    System.out.println("before: " + this.C);
    this.ruler.rule04(new Pair("a", "a,a"));
    System.out.println("after:  " + this.C);
    Assert.assertTrue((this.C.size() == 0));
    this.D.add(new Pair("a", "b"));
    System.out.println("before: " + this.D);
    this.ruler.rule04(new Pair("a", "a,a"));
    System.out.println("after:  " + this.D);
    Assert.assertTrue((this.D.size() == 2));
    this.T.clear();
    this.D.clear();
    this.C.clear();
    this.queue.clear();
    this.T.add("chi1");
    this.T.add("chi2");
    this.D.add(new Pair("alpha", "chi1"));
    this.D.add(new Pair("chi2", "beta"));
    this.ruler.rule04(new Pair("alpha", "beta"));
    System.out.println(":" + this.queue);
  }
  
  @Test
  public void testRule05() {
    this.T.add("EPSILON");
    this.T.add("a");
    this.T.add("a,a");
    this.T.add("b");
    System.out.println("before: " + this.D);
    this.ruler.rule05(new Pair("a", "a,a"));
    System.out.println("after:  " + this.D);
    Assert.assertTrue((this.D.size() == 0));
    this.C.add(new Pair("a", "b"));
    System.out.println("before: " + this.D);
    this.ruler.rule05(new Pair("a", "a,a"));
    System.out.println("after:  " + this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.T.clear();
    this.D.clear();
    this.C.clear();
    this.queue.clear();
    this.T.add("chi1");
    this.T.add("chi2");
    this.C.add(new Pair("alpha", "chi1"));
    this.C.add(new Pair("chi2", "beta"));
    this.ruler.rule05(new Pair("alpha", "beta"));
    System.out.println(":" + this.queue);
  }
  
  @Test
  public void testRule06() {
    Assert.assertEquals(1L, this.ruler.getPotency("a,b,a,b,a,b").size());
    Assert.assertEquals(0L, this.ruler.getPotency("a,b,c,d,a,b,d,e").size());
    Assert.assertEquals("a,b,c,d", this.ruler.getPotency("a,b,c,d,a,b,c,d").get(0));
    Assert.assertEquals("a", this.ruler.getPotency("a,a").get(0));
    Assert.assertEquals("a", this.ruler.getPotency("a,a,a").get(0));
    Assert.assertEquals(2L, this.ruler.getPotency("a,a,a,a").size());
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,a"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,a,a"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,a,a,a,a"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,b,a,b,a"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 0));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,b,a,b"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,b,a,b,a,b"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,b,a,a,b,a"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 1));
    this.D.clear();
    this.ruler.rule06(new Pair("c", "c,a,b,a,b,a,b,a,b"));
    System.out.println(this.D);
    Assert.assertTrue((this.D.size() == 2));
    this.D.clear();
    this.queue.clear();
    this.ruler.rule06(new Pair("alpha", "alpha,x,x,x,x"));
    System.out.println(this.queue);
    Assert.assertTrue((this.queue.size() == 2));
  }
  
  @Test
  public void testRule07() {
    Assert.assertEquals("a,b,c", TestSequence.getPrefixFrom("a,b,c", "EPSILON"));
    Assert.assertEquals("a,b", TestSequence.getPrefixFrom("a,b,c", "c"));
    Assert.assertEquals("aa", TestSequence.getPrefixFrom("aa,bbb,cccc", "bbb,cccc"));
    this.D.add(new Pair("alpha", "alpha,gamma"));
    System.out.println("before: " + this.D);
    this.ruler.rule07(new Pair("alpha", "alpha,beta,gamma"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(2L, this.D.size());
    this.D.clear();
    this.D.add(new Pair("alpha,gamma", "alpha"));
    System.out.println("before: " + this.D);
    this.ruler.rule07(new Pair("alpha", "alpha,beta,gamma"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(2L, this.D.size());
    this.D.clear();
    System.out.println("before: " + this.D);
    this.ruler.rule07(new Pair("alpha", "alpha,beta,gamma"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(0L, this.D.size());
    this.D.clear();
    this.queue.clear();
    this.D.add(new Pair("a,b", "a,b,g"));
    this.ruler.rule07(new Pair("a,b,e,g", "a,b"));
    System.out.println(this.queue);
  }
  
  @Test
  public void testRule08() {
    this.C.add(new Pair("alpha", "alpha,beta,gamma"));
    System.out.println("before: " + this.D);
    this.ruler.rule08(new Pair("alpha", "alpha,gamma"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(1L, this.D.size());
    this.C.clear();
    this.D.clear();
    this.queue.clear();
    this.C.add(new Pair("alpha", "alpha,beta,gamma"));
    this.ruler.rule08(new Pair("alpha", "alpha,gamma"));
    System.out.println(": " + this.D);
    System.out.println(": " + this.queue);
    Assert.assertEquals(1L, this.queue.size());
  }
  
  @Test
  public void testRule09() {
    this.D.add(new Pair("beta", "beta,gamma"));
    System.out.println("before: " + this.D);
    this.ruler.rule09(new Pair("alpha", "alpha,gamma"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(2L, this.D.size());
    this.D.clear();
    this.C.clear();
    this.queue.clear();
    this.D.add(new Pair("beta,gamma", "beta"));
    System.out.println("before: " + this.D);
    this.ruler.rule09(new Pair("alpha,gamma", "alpha"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(2L, this.D.size());
    System.out.println(": " + this.queue);
    Assert.assertEquals(1L, this.queue.size());
  }
  
  @Test
  public void testRule10() {
    this.C.add(new Pair("alpha", "alpha,gamma"));
    System.out.println("before: " + this.D);
    this.ruler.rule10(new Pair("beta", "beta,gamma"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(1L, this.D.size());
    this.C.clear();
    this.D.clear();
    this.queue.clear();
    this.C.add(new Pair("alpha,gamma", "alpha"));
    System.out.println("before: " + this.D);
    this.ruler.rule10(new Pair("beta,gamma", "beta"));
    System.out.println("after: " + this.D);
    Assert.assertEquals(1L, this.D.size());
    System.out.println(this.queue);
    Assert.assertEquals(1L, this.queue.size());
  }
}
