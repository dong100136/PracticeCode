#! /bin/sh

#------------------------------------------
# 这个脚本用于配置集群，提供以下功能:
# - list:    查看当前配置的节点信息
# - ping:    测试当前主机和其他节点的连通性
# - copy:    复制文件到各个节点
# - execute: 在各个节点运行同一个指令
# - service: 启动，停止，查看服务状态
# 脚本写得比较少，这个边用边完善吧
# 使用的时候必修先配置节点信息
#------------------------------------------

# setting cluster
master="192.168.80.1"
nodes[0]="192.168.80.1"
nodes[1]="192.168.80.1"
nodes[2]="192.168.80.1"
nodes[3]="192.168.80.1"
nodes[4]="192.168.80.1"
nodes[5]="192.168.80.1"
nodes[6]="192.168.80.1"


function pingNode(){
    echo -e "\033[32m[Info]\033[0m ping all the node in the cluster:"
    echo -e "\033[32m[Info]\033[0m start ping..."

    ping -c 1 $master&>/dev/null
    res=$?
    if [ $res -eq 0 ];then
        echo -e "\033[32m[Ok]\033[0m ping master[$master]..."
    else
        echo -e "\a\033[32m[Fail]\031[0m ping master[$master]..."
    fi

    for node in ${nodes[@]};do
        ping -c 1 $node&>/dev/null
        res=$?
        if [ $res -eq 0 ];then
            echo -e "\033[32m[Ok]\033[0m ping node[$node]..."
        else
            echo -e "\a\033[31m[Fail]\033[0m ping node[$node]..."
        fi
    done

    echo -e "\033[32m[Info]\033[0m ping finish..."
}

function list(){
    echo -e "\033[32m[Info]\033[0m list all the node in the cluster:"
    echo "* master: $master"

    for node in ${nodes[@]};do
        echo "- node:   $node"
    done
}

function copy(){
    echo -e "\033[32m[Info]\033[0m copy $1 to $2 of all node in cluster "
    echo -e "\033[32m[Info]\033[0m start copying..."

    echo -e "\033[32m[Info]\033[0m copy to master[$master]: "
    scp -r $1 "root@$master:$2"
    for node in ${nodes[@]};do
        echo -e "\033[32m[Info]\033[0m copy to node[$node]: "
        scp -r $1 "root@$node:$2"
    done

    echo -e "\033[32m[Info]\033[0m copy finish...\033[0]"
}

function execute(){
    echo -e "\033[32m[Info]\033[0m execute command ** $1 ** in cluster "
    echo -e "\033[32m[Info]\033[0m start executing... "

    if [ $2 == "-master" ];then
        echo -e "\033[32m[Info]\033[0m execute ** $1 ** in master[$master] "
        ssh "root@$master" "$1"
    fi

    if [ $3 == "-node" ];then
        for node in ${nodes[@]};do
            echo -e "\033[32m[Info]\033[0m execute ** $1 ** in node[$node] "
            ssh "root@$node" "$1"
        done
    fi

    echo -e "\033[32m[Info]\033[0m execute finish... "
}

function service(){
    wait_time_master=10s
    wait_time=5s

    echo -e "\033[32m[Info]\033[0m  $2 sevice [$1] in the cluster "
    echo -e "\033[32m[Info]\033[0m  start $2 service [$1]..."

    ssh "root@$master" "service $1 $2"
    res=$?
    if [ $res -eq 0 ];then
        echo -e "\033[32m[$2]\033[0m service [$1] of master[$master] success"
    else
        echo -e "\033[31m[$2]\033[0m service [$1] of master[$master] fail"
    fi

    echo -e "\033[32m[Info]\033[0m wait for $wait_time_master"
    sleep $wait_time_master

    for node in ${nodes[@]};do
        ssh "root@$node" "service $1 $2"
        res=$?
        if [ $res -eq 0 ];then
            echo -e "\033[32m[$2]\033[0m service [$1] of node[$node] success"
        else
            echo -e "\033[31m[$2]\033[0m service [$1] of node[$node] fail"
        fi
        echo -e "\033[32m[Info]\033[0m wait for $wait_time"
        sleep $wait_time
    done

    echo -e "\033[32m[Info]\033[0m $2 service [$1] finish..."
}

case $1 in
    list)
        list
        ;;
    copy)
        # 检查输入是否正确
        if [ ! $2 -o ! $3 ];then
            echo -e "\033[31m[Error]\033[0m please input [copy pathA pathB]  "
        elif [ ! -e $2 ];then
            echo -e "\033[31m[Error]\033[0m the path $2 not exists  "
        else
            copy $2 $3
        fi
        ;;
    execute)
        if [ $# == 3 ];then
            if [ $2 == "--without-master" ];then
                execute $3 --no-master --node
            else
                echo -e "\033[31m[Error]\033[0m please input [execute --without-master command] "
            fi
        elif [ $# == 2 ];then
            execute "$2" -master -node
        else
            echo -e "\033[31m[Error]\033[0m please input [execute command or execute --without-master command] "
        fi
        ;;
    ping)
        pingNode
        ;;
    service)
        if [ $# == 3 ];then
            if [ $3 == "start" -o $3 == "stop" -o $3 == "restart" -o $3 == "status" ];then
                service $2 $3
            fi
        else
            echo -e "\033[31m[Error]\033[0m please input [service serviceName start|stop|restart|status]"
        fi
        ;;
    *)
        echo -e "\033[33m[Warning]\033[0m command \"$1\" is not support"
        echo ""
        echo "- list       show all the node in the cluster"
        echo "- copy       copy file to all node"
        echo "- execute    execute command in the cluster,can with args --without-master"
        echo "- ping       test the network of cluster"
        echo ""
esac
