import scala.collection.mutable.ArrayBuffer
trait Observable{
  val observers = new ArrayBuffer[Observer]()

  def attach(observer:Observer):Unit={
    observers+=observer
  }

  def detach(observer:Observer):Unit={
    observers-=observer
  }

  def notify2:Unit={
    for (observer<-observers){
      observer.update(this)
    }
  }
}

trait Observer{
  def update(subject:Observable):Unit
}

class Reader(val name:String) extends Observer{
  def update(bookstore:Observable){
    if (bookstore.isInstanceOf[BookStore]){
      Console.println("%s去书店%s逛了一趟".format(name,bookstore.asInstanceOf[BookStore].name))
    }
  }
}

class BookStore(val name:String) extends Observable{
  def addBook(bookname:String){
    Console.println("%s进了新书%s".format(name,bookname))
    notify2
  }
}


object ObserverTest{
  def main(args:Array[String]):Unit={
    val bookstore1 = new BookStore("百味书店")
    val bookstore2 = new BookStore("新华书店")
    val reader1 = new Reader("小明")
    val reader2 = new Reader("小新")
    val reader3 = new Reader("小红")

    bookstore1.attach(reader1)
    bookstore1.attach(reader2)
    bookstore2.attach(reader2)
    bookstore2.attach(reader3)

    bookstore1.addBook("《程序员的自我修养》")
    bookstore2.addBook("《通信原理》")
  }
}
