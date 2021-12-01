FROM openjdk:11.0.13-jdk-oraclelinux7
MAINTAINER martin2176@yahoo.com
WORKDIR /opt/app/
COPY jarfile /opt/app/
ENTRYPOINT ["sleep"]
CMD ["10000"]
