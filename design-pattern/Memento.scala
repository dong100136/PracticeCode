class Book(val totalPage:Int){
  var currentPage=0;
  def read(page:Int){
    if (currentPage+page<totalPage){
      currentPage+=page
    }else{
      currentPage=totalPage
    }
    print
  }

  def save():Memento={
    new Memento(currentPage)
  }

  def undo(memento:Memento):Unit={
    currentPage = memento.currentPage
    print
  }

  def print:Unit={
    Console.println("现在读到%d页".format(currentPage))
  }
}

case class Memento(currentPage:Int)

class MementoCareTaker{
  val mementoList = new collection.mutable.ArrayBuffer[Memento]()

  def saveMemento(memento:Memento):Unit={
    mementoList+=memento
  }

  def getMemento():Memento={
    val memento = mementoList(mementoList.length-1)
    mementoList-=memento
    memento
  }
}

object MementoTest{
  def main(args:Array[String]):Unit={
    val book = new Book(100)
    val mementoCareTaker = new MementoCareTaker

    book.read(10)
    mementoCareTaker.saveMemento(book.save)
    book.read(10)
    mementoCareTaker.saveMemento(book.save)
    book.read(10)

    book.undo(mementoCareTaker.getMemento)
    book.undo(mementoCareTaker.getMemento)
  }
}
