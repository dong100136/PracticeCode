abstract class DBQuery{
  def doQuery(name:String):String
}
class RealDBQuery extends DBQuery{
  @Override def doQuery(name:String):String={
    Thread.sleep(1000)
    Console.println("调用数据进行查询数据%s".format(name))
    "result"
  }
}


class DBQueryProxy extends DBQuery{
  val cache = new scala.collection.mutable.HashMap[String,String]
  val real = new RealDBQuery

  @Override def doQuery(name:String):String={
    Console.println("调用数据库代理类")
    if (!cache.contains(name)){
      val rs = real.doQuery(name)
      cache+=(name->rs)
      cache(name)
    }else{
      Console.println("使用缓存中的结果")
      cache(name)
    }
  }
}

object DBQueryFactory{
  def apply():DBQuery={
    return new DBQueryProxy
  }
}

object ProxyTest{
  def main(args:Array[String]):Unit={
    val dbQuery = DBQueryFactory()
    dbQuery.doQuery("stone")
    dbQuery.doQuery("stone")

    dbQuery.doQuery("stone2")
    dbQuery.doQuery("stone2")
  }
}
