export YARN_CONF_DIR=/opt/cloudera/parcels/CDH/lib/spark

function submitSpark(){
    spark-submit \
        --master yarn-client \
        --class SparkTest.SparkRun \
        --num-executors 5 \
        --driver-memory 5g \
        --executor-memory 5g \
        --executor-cores 10 \
        /home/haohan/elasticSearchTest/spark-learning-1.0-SNAPSHOT.jar \
        $1 \
        $2
}

# set fold
fileList=`hdfs dfs -ls /import_data/bupt/192.168.83.12/v6/201608* | cut -d "/" -f 6|sort -u |grep "^[0-9]*$"`

echo "======show all the file will run======"
for file in $fileList;do
    echo "$file"
done
echo "======show all the file will run======"

echo ""
echo ""
echo ""
echo ""

for file in $fileList;do
    from="/import_data/bupt/192.168.83.12/v6/$file/100/*"
    to="Analysis_Ma/$file"
    echo "=======running[$file]....================"
    echo "=======execute file :$from============="
    echo "=======save to :$to============="
    submitSpark "$from" "$to"
    echo "=======finish....================"
    echo ""
    echo ""
    echo ""
done
