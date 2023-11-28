kubectl port-forward service/kibana 5601 --namespace kube-logging &
curl -XPOST -D- 'http://localhost:5601/api/saved_objects/index-pattern' \
    -H 'kbn-xsrf: true' \
    -H 'Content-Type: application/json' \
    -d '{"attributes":{"title":"logstash-*","timeFieldName":"@timestamp"}}'
kill %1