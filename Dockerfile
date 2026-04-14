FROM public.ecr.aws/lambda/java:17

COPY build/classes/java/main/ ${LAMBDA_TASK_ROOT}/
COPY build/resources/main/ ${LAMBDA_TASK_ROOT}/

COPY build/dependency/* ${LAMBDA_TASK_ROOT}/lib/

CMD ["com.gonghak98.v2.StreamLambdaHandler::handleRequest"]
