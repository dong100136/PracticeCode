#----------------------------------------------------------------
# 配置文件改写，用于自动化部署
#
#----------------------------------------------------------------

export es_config_file =/etc/elasticsearch/elasticsearch.yml
export es_jvm_file    =/etc/elasticsearch/jvm.options
export local_host     =`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
export master_host    =192.168.80.1
export cluster_name   =ElasticTest
export es_port        =9200

data_path             ='************************'
log_path              ='*************************'


# --------------------------ES-Config-----------------------------
# basic setting
#### cluster.name
sed -i 's/cluster.name.*$/cluster.name: '$cluster_name'/' $es_config_file
#### node.name
sed -i 's/node.name.*$/node.name: node-'${local_host##*.}'/' $es_config_file

# set master and node
# set single master
sed -i '/^node.master.*$/d' $es_config_file
sed -i '/node.data.*$/d' $es_config_file

if [ $local_host == $master_host ];then
	echo 'node.master: true' >>$es_config_file
	echo 'node.data: false' >>$es_config_file
else
	echo 'node.master: false' >>$es_config_file
	echo 'node.data: true' >>$es_config_file
fi

# host and ip
#### http.port
sed -i 's/http.port.*$/http.port: '$es_port'/' $es_config_file
#### network.host
sed -i 's/network.host.*$/network.host: '$local_host'/' $es_config_file
#### discovery.zen.ping.unicast.hosts
sed -i 's/discovery.zen.ping.unicast.hosts.*$/discovery.zen.ping.unicast.hosts: ["'$master_host'","'$local_host'"]/' $es_config_file

# disable swap memory
#### bootstrap.memory_lock: true
sed -i '/bootstrap.memory_lock.*$/d' $es_config_file
# echo 'bootstrap.memory_lock: true' >> $es_config_file

# set xpack
#### xpack.security.enabled: false
#### xpack.monitoring.collection.interval: 1s
sed -i '/xpack.*$/d' $es_config_file
echo 'xpack.security.enabled: false' >> $es_config_file
# echo 'xpack.monitoring.collection.interval: 1s' >> $es_config_file


# set thread pool
#### thread_pool.bulk.queue_size: 1000
sed -i '/thread_pool.bulk.queue_size.*$/d' $es_config_file
echo 'thread_pool.bulk.queue_size: 1000' >> $es_config_file

# set data and log path


# other
sed -i '/http.cors.enabled: true.*$/d' $es_config_file
echo 'http.cors.enabled: true' >> $es_config_file

sed -i '/http.cors.allow-origin.*$/d' $es_config_file
echo 'http.cors.allow-origin: "*"' >> $es_config_file

sed -i '/bootstrap.system_call_filter.*$/d' $es_config_file
echo 'bootstrap.system_call_filter: false' >> $es_config_file

# --------------------------ES-Config-----------------------------


# ----------------------------Other-------------------------------
# set jvm heap
sed -i 's/-Xmx2g/-Xmx4g/' $es_jvm_file
sed -i 's/-Xms2g/-Xms4g/' $es_jvm_file
# ----------------------------Other-------------------------------
