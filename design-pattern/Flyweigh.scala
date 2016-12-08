class Point(val x:Int,val y:Int,colorString:String){
  val color = ColorFactory(colorString)

  def print():Unit={
    Console.print("在点(%d，%d)画了一个%s的点\n".format(x,y,color.color))
  }
}

object Point{
  def apply(x:Int,y:Int,colorString:String):Point={
    new Point(x,y,colorString)
  }
}

class Color(val color:String) 

object ColorFactory{
  val hash = new java.util.HashMap[String,Color]

  def apply(colorString:String):Color={
    var color = hash.get(colorString)
    if (color==null){
      hash.put(colorString,new Color(colorString))
      color=hash.get(colorString)
    }
    color
  }
}


object FlyweighTest{
  def main(args:Array[String]):Unit={
    val point1 = Point(1,1,"红色")
    val point2 = Point(2,2,"蓝色")
    val point3 = Point(3,3,"蓝色")
    val point4 = Point(4,4,"蓝色")

    point1.print()
    point2.print()
    point3.print()
    point4.print()
  }
}
