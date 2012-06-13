import java.lang.reflect.Method

class TestCase(val name : String) {
  def assert(bool : Boolean) = {
    if (bool) {
      print(".")
    } else {
      print("X(" + name + ")")
    }
  }

  def setup() = {}

  def run() = {
    var opt : Option[Method] = this.getClass.getMethods.find(_.getName == name)
    opt match {
      case Some(m : Method) => 
        setup()
        m.invoke(this)
      case _ => null 
    }
  }
}

class MyTest(name : String) extends TestCase(name) {
  override def setup() = {
    // do nothing
  }

  def test1() = {
    assert(1 == 1)
  }

  def test2() = {
    assert(2 == 3)
  }

  def test3() = {
    assert(3 == 3)
  }
}

new MyTest("test1").run
new MyTest("test2").run
new MyTest("test3").run
