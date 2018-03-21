list=[taobao]

function getCurrentTabUrl(callback) {
  var queryInfo = {
    active: true,
    currentWindow: true
  };

  chrome.tabs.query(queryInfo, function(tabs) {
    // exactly one tab.
    var tab = tabs[0];
    var url = tab.url;
    console.assert(typeof url == 'string', 'tab.url should be a string');

    callback(url);
  });
}

function render(){
    getCurrentTabUrl(function(url){
        document.getElementById('text').innerHTML=url;
    })
}

var taobao = {
    domain:["s.taobao.com"],
    getItemId:function(url){
        re=/id=(\d*)/;
        return re.exec(url)[1];
    },
    getCommentUrl:function(id,currentPage,pageSize){
        url = "https://rate.taobao.com/feedRateList.htm?auctionNumId="+
        id+"&currentPageNum="+currentPage+"&pageSize="+pageSize;
        return url;
    },
    getComment:function(url,callback){
        var id = getItemId(url);
        var commentUrl = getCommentUrl(id,1,20);
        
    },
    getJson:function(url){
        
    }

}

document.onload(function(){
    for (item in list){
        if (item.domain==url){
            item.getComment(function );
        }
    }
};