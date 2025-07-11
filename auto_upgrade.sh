#!/bin/bash
# $1 模式，1后端 2前端 其他值为全部

appName="jvav-timetable"
user="root"
server="x.x.x.x"

bash package_upgrade.sh $1

ssh -t ${user}@${server} "\
bak_path=\"/home/bak/\$(date +%Y%m%d-%H%M%S)\";\
jvav_zip=\"/home/jvav.zip\"; jvav_package=\"/home/jvav_package\";\
if [ -e \"\$jvav_zip\" ] || [ -d \"\$jvav_package\" ]; \
then mkdir -p \$bak_path;\
fi;\
if [ -e \"\$jvav_zip\" ]; \
then mv \$jvav_zip \$bak_path;\
fi;\
if [ -d \"\$jvav_package\" ]; \
then mv \$jvav_package \$bak_path;\
fi\
"
echo "********************备份完成********************"

scp ${appName}.zip ${user}@${server}:/home
echo "********************上传完成********************"

ssh -t ${user}@${server} "cd /home && unzip ${appName}.zip && cd ${appName}_package && bash upgrade.sh"
echo "********************部署完成********************"
