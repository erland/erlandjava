1. LICENSE
==========
Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

2. LIBRARIES
============
A number of different libraries from http://jakarta.apache.org is also included

3. PREREQUISITES
================
- A web server supporting Java servlets and JSP, it has only been tested
  with Apache Tomcat 4.1.24 and Tomcat 5.5.4
- A SQL database, it has only been tested with MySQL 4.0.15

4. FILES
========
This binary archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- tvguide.war (The web application)
- database/*.sql (A number of database configuration scripts)
- docs/* (The javadoc documentation for the web application classes)

This source archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- database/*.sql (A number of database configuration scripts)
- src/* (The web application source code)
- web/* (The web application source code)
- build.xml (A build file for ant)
- maven.xml (A build file for maven)
- project.properties (A configuration file for maven)
- project.xml (A configuration file for maven)

5. CONFIGURING OF THE DATABASE
==============================
There are a number of database configuration scripts in the database directory.
I personally run the application in three different databases, but it is also
be possible to put all tables in the same database.
There are also upgrade scripts available to upgrade a database created in some
of the previous versions.
Anyway here are a short description of the scripts:

Table creation scripts:
- common.sql
  Creates the tables needed in the database which will be accessed as jdbc/common
- tvguide.sql
  Creates the tables needed in the database which will be accessed as jdbc/tvguide
- users.sql
  Creates the tables needed in the database which will be accessed as jdbc/users

Table upgrade scripts:
- common_upgrade_x_y.sql
  Upgrades the tables in the database which will be accessed as jdbc/common from a
  database created previously in version x.y
- tvguide_upgrade_x_y.sql
  Creates the tables in the database which will be accessed as jdbc/tvguide from a
  database created previously in version x.y
- users_upgrade_x_y.sql
  Creates the tables in the database which will be accessed as jdbc/users from a
  database created previously in version x.y

Data scripts:
- common_data.sql
  Setup text to show on first page (You might want to change this)
  Setup the native language code to sv (swedish)
  Setup the path to the online help
- users_data.sql
  Create a user with username "test" and password "test" and configure it with roles "user" and "manager"
- tvguide_data.sql
  Creates a user account for the "test" user.

6. CONFIGURATION OF JDBC DATASOURCES
====================================
You will need to configure tree JDBC datasources in your web-server
jdbc/common: Should have the tables defined in the file(database\common.sql)
jdbc/tvguid: Should have the tables defined in the file(database\tvguide.sql)
jdbc/user: Should have the tables defined in the file(database\users.sql)

The datasources in my installation points to tree different databases in the same
database server, but I you could also point them to the same database.

Also remember to install the JDBC drivers for your database if you haven't done
it already

7. DIRECTORIES
==============

8. THIRD PARTY LIBRARIES
========================
You will need to download the xalan library from http://xml.apache.org/xalan-j/index.html if
you want to use the XSL transformation features. There might be possible to use other similar
XSL transformation libraries also.
It is not delivered in the webapplication since it must be installed on the webserver itself.
On tomcat you have to copy it to the common/endorsed directory below the tomcat installation.

9. DEPLOY AND RUN
=================
You should now be able to deploy the tvguide.war file on you web-server and then access it
as for example:
http://mywebserver/tvguide

10. BUILDING
============
This chapter is only interesting if you for some reason wants to recompile the web application from
the source archive. There is an ant build file delivered witht he application but the recommended and
tested way to build it is by using maven(http://maven.apache.org).

- Install maven
- You will have to create a build.properties file with the following rows:
  maven.war.final.name=${maven.webapp.name}.war
  maven.war.build.dir=${maven.build.dir}
  maven.war.webapp.dir=${maven.war.build.dir}/${maven.webapp.name}
  maven.webserver.webapp.dir=D:/users/erland/webappstomcat
- The maven.webserver.webapp.dir parameter should point to the webapps expansion dir of your webserver
- Build with:
  To just generate the war archive:
  maven all:build
  To generate a new distribution:
  maven dist
