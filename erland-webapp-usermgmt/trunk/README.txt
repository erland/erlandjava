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
A number of different libraries from http://jakarta.apache.org is also used.

3. PREREQUISITES
================
- A web server supporting Java servlets and JSP, it has only been tested
  with Apache Tomcat 4.1.24

4. FILES
========
This binary archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- *.jar (The library classes)
- docs/* (The javadoc documentation for the library classes)

This source archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- src/* (The library source code)
- build.xml (A build file for ant)
- maven.xml (A build file for maven)
- project.properties (A configuration file for maven)
- project.xml (A configuration file for maven)

5. THIRD PARTY LIBRARIES
========================
No third party libraries are needed besides those delivered with the application

6. RUN
=================
This is not an application, it is just a library that can be used
from other applications.

7. BUILDING
============
This chapter is only interesting if you for some reason wants to recompile the web application from
the source archive. There is an ant build file delivered witht he application but the recommended and
tested way to build it is by using maven(http://maven.apache.org).

- Install maven
- Build with:
  To just generate the war archive:
  maven all:build
  To generate a new distribution:
  maven dist
