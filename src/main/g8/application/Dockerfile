FROM openjdk:11-jre-slim

RUN \
      addgroup --system $name;format="norm"$ \
      && adduser --system --disabled-login --disabled-password --home /var/lib/$name;format="norm"$ --shell /sbin/nologin --ingroup $name;format="norm"$ $name;format="norm"$

COPY ./target/scala-2.12/application-assembly.jar /var/lib/$name;format="norm"$/

USER $name;format="norm"$
WORKDIR /var/lib/$name;format="norm"$

ENTRYPOINT ["java", "-jar", "application-assembly.jar"]