1. LICENSE
==========
Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)

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
This softaware also includes a library "metadata extraction in java" for
extracting exif data from the images, see http://www.drewnoakes.com for
more information

3. PREREQUISITES
================
- A web server supporting Java servlets and JSP, it has only been tested
  with Apache Tomcat 4.1.24
- A SQL database, it has only been tested with MySQL 4.0.13

4. FILES
========
This archive should contain the following files:
- readme.txt (this file)
- dirgallery.war (The web application code)
- database/*.sql (A number of database configuration scripts)

5. CONFIGURING OF THE DATABASE
==============================
There are a number of database configuration scripts in the database directory.
I personally run the application in three different databases, but it might also
be possible to put all tables in the same database.
Anyway here are a short description of the scripts:

Table creation scripts:
- dirgallery.sql:
  Creates the tables needed in the datbase which will be accessed as jdbc/dirgallery
- gallery.sql
  Creates the tables needed in the database which will be accessed as jdbc/gallery
- users.sql
  Creates the tables needed in the database which will be accessed as jdbc/users

Data scripts:
- users_data.sql
  Create a user with username "test" and password "test" and configure it with roles "user" and "manager"
- dirgallery_data.sql
  Creates a user account for the "test" user.
  Setup thumbnail cache dir to "D:\users\erland\thumbnail2" (You probably want to change this)
- gallery_data.sql
  Creates a row for all exif parameters that should be shown when showing picture information

6. CONFIGURATION OF JDBC DATASOURCES
====================================
You will need to configure tree JDBC datasources in your web-server
jdbc/gallery: Should have the tables defined in the file(database\gallery.sql)
jdbc/dirgallery: Should have the tables defined in the file(database\dirgallery.sql)
jdbc/user: Should have the tables defined in the file(database\users.sql)

The datasources in my installation points to three different databases in the same
database server, but I think you also could point them to the same database.

Also remember to install the JDBC drivers for your database if you haven't done
it already

7. DIRECTORIES
==============
The application will store thumbnails in a directory specified in the resources table in
the database accessed as jdbc/dirgallery. You have to make sure that this directory really
exists.

8. THIRD PARTY LIBRARIES
========================
You will have to make sure that you have installed the BeanUtils library(commons-beanutils.jar)
on your webserver, you can download it from:
http://jakarta.apache.org/commons/beanutils.html

To make it possible to generate thumbnails for mpeg files you also need to install
Java Media Framework on the web server, you can download it from:
http://java.sun.com/products/java-media/jmf/

9. DEPLOY AND RUN
=================
You should now be able to deploy the dirgallery.war file on you web-server and then access it
as for example:
http://mywebserver/dirgallery

