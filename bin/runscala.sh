#!/bin/bash
echo "" > classpath.txt
for file in `ls lib`;
        do echo -n 'lib/' >> classpath.txt;
        echo -n $file >> classpath.txt;
        echo -n ':' >> classpath.txt;
done
for file in `ls build`;
	do echo -n 'build/' >> classpath.txt;
	echo -n $file >> classpath.txt;
	echo -n ':' >> classpath.txt;
done

export CLASSPATH=$(cat classpath.txt)
export JAVA_OPTS="${JAVA_OPTS} -DrulePath=data -Xmx4096M -DloggerPath=$BUILD_COMMON/test-config/log4j.properties"
scala $WORDNIK_OPTS $JAVA_CONFIG_OPTIONS  -cp $CLASSPATH "$@"
