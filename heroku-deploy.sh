#!/bin/bash

# will push the Server subdirectory to the heroku platform
# does local branch switching if needed. To run, simply
# execute the script like this: ./heroku-deploy.js

# author: Marius Avram 2014

# change here if the case
deployment_branch="develop"
server_file="Server/server/server.js"
server_file_tmp="$server_file"".tmp"

#automatically change server to debug false before push
is_debug=`cat $server_file | grep "app.set('debug', true)"`
echo $is_debug;
sed -e 's/app.set('\''debug.*/app.set('\''debug'\'', false);/' $server_file > $server_file_tmp
mv $server_file_tmp $server_file

# automatically add to git repo
git add $server_file
git commit --amend --no-edit

echo "=====================" 
git push heroku `git subtree split --prefix Server $deployment_branch`:master --force
echo "====================="

if [ "$is_debug" ]; then
    sed -e 's/app.set('\''debug.*/app.set('\''debug'\'', true);/' $server_file > $server_file_tmp
    mv $server_file_tmp $server_file
    git add $server_file
    git commit --amend --no-edit
fi
