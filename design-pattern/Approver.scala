abstract class Approver {
  var successer: Option[Approver] = None

  def process(req: Request)
}

case class Request(name: String, price: Int, count: Int)

class Director extends Approver {
  override def process(req: Request): Unit = {
    if (req.price < 100) {
      Console.println("主任处理了一个价值%d元的%s订单".format(req.count * req.price, req.name))
    } else {
      if (successer != None) {
        successer.get.process(req)
      }
    }
  }
}

class VicePersisdent extends Approver {
  override def process(req: Request): Unit = {
    if (req.price < 1000) {
      Console.println("副主席处理了一个价值%d元的%s订单".format(req.count * req.price, req.name))
    } else {
      if (successer != None) {
        successer.get.process(req)
      }
    }
  }
}

class Persisdent extends Approver {
  override def process(req: Request): Unit = {
    if (req.price < 10000) {
      Console.println("主席处理了一个价值%d元的%s订单".format(req.count * req.price, req.name))
    } else {
      if (successer != None) {
        successer.get.process(req)
      }else{
        Console.println("主席表示没办法处理")
      }
    }
  }
}

object ApproverTest {
  def main(args: Array[String]): Unit = {
    val director = new Director()
    val vicePersisdent = new VicePersisdent()
    val persisdent = new Persisdent()

    director.successer = Some(vicePersisdent)
    vicePersisdent.successer = Some(persisdent)

    director.process(new Request("文具",90,10))
    director.process(new Request("电脑",900,10))
    director.process(new Request("设备",9000,10))
    director.process(new Request("设备",90000,10))
  }
}
