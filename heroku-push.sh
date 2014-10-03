#!/bin/bash

# will push the Server subdirectory to the heroku platform
# does local branch switching if needed

# author: Marius Avram 2014

previous_branch="$(git rev-parse --abbrev-ref HEAD)"
deployment_branch="develop" # change here if the case

if [ "$previous_branch" != "$deployment_branch" ] ; then
    echo "=============================="
    echo "Switching to $deployment_branch brach ..."
    echo "=============================="
    git checkout develop
fi
git push heroku `git subtree split --prefix Server $deployment_branch`:master --force
if [ "$previous_branch" != "$deployment_branch" ] ; then
    echo "=============================="
    echo "Switching to $previous_branch branch ..."
    echo "=============================="
    git checkout $previous_branch
fi
