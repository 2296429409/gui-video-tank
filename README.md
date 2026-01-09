# 说明

## 修改端口说明

- 本地运行和 CI/CD 部署后的默认端口均为 8080 
- 如需修改修改服务运行端口与 CI/CD 端口，需要同步修改下述四处！！！
1. application.yml 中的 server.port 
2. Dockerfile 中的 EXPOSE 暴露的的端口
3. deployment.yaml 中的 containerPort 和 targetPort
4. Jenkinsfile 中的 `docker run -d --name $CONTAINER_NAME -p $CONTAINER_PORT:8080` 中的容器内服务运行端口
