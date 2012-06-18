import java.lang.reflect.Method

abstract class Test {
  def run = {}
}

class TestSuite {
  var tests = List[Test]()
  def append(test : Test) = {
    tests = test :: tests
  }
  
  def run = {
    tests.foreach(_.run)
  }
}

class TestCase(val name : String) extends Test {
  def assert(bool : Boolean) = {
    if (bool) {
      print(".")
    } else {
      print("X(" + name + ")")
    }
  }

  def setup = {}

  override def run = {
    var opt : Option[Method] = this.getClass.getMethods.find(_.getName == name)
    opt match {
      case Some(m : Method) => 
        setup
        m.invoke(this)
      case _ => null 
    }
  }
}

object MyTest {
  def suite = {
    var suite = new TestSuite()
    suite.append(new MyTest("test1"))
    suite.append(new MyTest("test2"))
    suite.append(new MyTest("test3"))
    suite
  }
}

class MyTest(name : String) extends TestCase(name) {
  override def setup = {
    // do nothing
  }

  def test1 = {
    assert(1 == 1)
  }

  def test2 = {
    assert(2 == 3)
  }

  def test3 = {
    assert(3 == 3)
  }
}

MyTest.suite.run
