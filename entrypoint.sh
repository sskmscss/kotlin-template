#!/usr/bin/env sh

DEFAULT_JAVA_OPTS="-XX:+UnlockDiagnosticVMOptions -noverify -XX:+AlwaysPreTouch"

#
# Set up some easily accessible MIN/MAX params for JVM mem usage
#
if [ -n "${JAVA_MAX_MEM}" ]; then
    DEFAULT_JAVA_OPTS="-Xmx${JAVA_MAX_MEM} ${DEFAULT_JAVA_OPTS}"
fi
if [ -n "${JAVA_MIN_MEM}" ]; then
    DEFAULT_JAVA_OPTS="-Xms${JAVA_MIN_MEM} ${DEFAULT_JAVA_OPTS}"
fi

JAVA_OPTS="${JAVA_OPTS} ${DEFAULT_JAVA_OPTS}"

exec java ${JAVA_OPTS} -jar lib/app.jar