#!/bin/bash

# Simple script to reload/create a service in the server

# 1. Perform the creation, and if it fails, then update the service
# 2. Refresh the services in the server

# Params:

# 1 the name of the service, default ms-sancarlos
# 2 the url, default localhost:8080

# It's stores the result of the calls in files in /tmp, called:

# 1. $1_GET for the reload
# 2. $1_POST for the create
# 3. $1_PUT for the update

NAME="${1:-ms-cit}"
HOST="${2:-https://localhost}"
PRODUCT="${3:-cuotas}"
PAYLOAD="${4:-./utils/create_payload.json}"
TMP_FILE="/tmp/calls_$NAME"

# Do a curl post call and return the result
function _doCall() {
    echo `curl --write-out %{http_code} --output "$TMP_FILE"_$2 --silent -k -H 'Accept: application/json' -H 'Content-Type: application/json' --noproxy localhost -X $2 -d "$3" $1`
}

function create() {
    echo `_doCall $HOST/bancard/admin/services/$NAME/$PRODUCT POST @$PAYLOAD`
}

function update() {
    echo `_doCall $HOST/bancard/admin/services/$NAME/$PRODUCT PUT @$PAYLOAD`
}

function refresh() {
    echo `curl --write-out %{http_code} --output "$TMP_FILE"_REFRESH --silent -k -H 'Accept: application/json' -H "Content-Type: application/json" --noproxy localhost -X POST https://localhost/bancard/admin/services/$NAME/$PRODUCT/reload`
}

echo "Config:"
echo "    host: $HOST"
echo "    name: $NAME"
echo ""

CREATE_RESULT=`create`
echo "Result of create: $CREATE_RESULT"
cat "$TMP_FILE"_POST
if [[ "$CREATE_RESULT" -ne 200  ]] ; then
    echo "Service already created, updating"
    UPDATE_RESULT=`update`
    cat "$TMP_FILE"_PUT
    echo "Result of update $UPDATE_RESULT"
    if [[ "$UPDATE_RESULT" -ne 200  ]] ; then
        echo "Error updating, and error creating, aborting"
        exit 1
    fi
fi

echo "Reloading..."
REFRESH_RESULT=`refresh`
echo "Result of refresh `refresh`"
cat "$TMP_FILE"_REFRESH
if [[ "$REFRESH_RESULT" -ne 200  ]] ; then
    echo "Error reloading"
    exit 1
fi
