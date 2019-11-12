#!/bin/bash

VERSION=$(grep --max-count=1 '<version>' pom.xml | awk -F '>' '{ print $2  }' | awk -F '<' '{ print $1  }' | tr '\n' '-')
printf '%s' "$VERSION"
