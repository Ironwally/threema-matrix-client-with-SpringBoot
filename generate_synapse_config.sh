#!/bin/bash

docker run -it --rm \
    --mount type=bind,src="$(pwd)/synapse-data",target=/data \
    -e SYNAPSE_SERVER_NAME=matrix.local \
    -e SYNAPSE_REPORT_STATS=yes \
    matrixdotorg/synapse:latest generate

if ! grep -q "127.0.0.1 matrix.local" /etc/hosts; then
    echo "matrix.local missing in /etc/hosts, adding..."
    echo "127.0.0.1 matrix.local" | sudo tee -a /etc/hosts > /dev/null
    echo "matrix.local added to /etc/hosts"
fi

echo "DONE"
echo ""

echo "-------------------------------------"
echo "Host: matrix.local"
echo "Config file: ./synapse-data/homeserver.yaml"
echo ""
echo "Create admin user CMD example:"
echo "docker exec matrix-server register_new_matrix_user -u admin -p magentaerenfarve --admin -c /data/homeserver.yaml http://matrix.local:8008"
echo "-------------------------------------"
