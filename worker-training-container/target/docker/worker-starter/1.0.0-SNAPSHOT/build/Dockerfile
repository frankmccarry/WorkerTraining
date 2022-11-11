FROM dockerhub-public.svsartifactory.swinfra.net/cafapi/opensuse-jre11:3
ENV CAF_WORKER_FAILURE_QUEUE=worker-starter-err CAF_WORKER_INPUT_QUEUE=worker-starter-in CAF_WORKER_OUTPUT_QUEUE=worker-starter-out
COPY maven /
HEALTHCHECK CMD curl -f http://localhost:8081/healthcheck || exit 1
CMD ["/maven/worker.sh"]
