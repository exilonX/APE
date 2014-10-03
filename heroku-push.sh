#!/bin/bash

# will push the Server subdirectory to the heroku platform
# does local branch switching if needed

# author: Marius Avram 2014

deployment_branch="develop" # change here if the case
git push heroku `git subtree split --prefix Server $deployment_branch`:master --force
