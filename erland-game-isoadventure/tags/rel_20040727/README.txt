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

2. PREREQUISITES
================
- Java 1.4 or later must be installed

3. FILES
========
This binary archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- *.jar (The game classes)
- docs/* (The javadoc documentation for the library classes)
- IsoAdventure.bat (A bat file to start the game)

This source archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- src/* (The library source code)
- build.xml (A build file for ant)
- maven.xml (A build file for maven)
- project.properties (A configuration file for maven)
- project.xml (A configuration file for maven)
- IsoAdventure.bat (A bat file to start the game)

4. CONFIGURING JNLP
===================
The jnlp-file contains references to my local machie, so you will have to
change these to your machine

5. RUN
=================
If you want to run a networked game you can start the server with the included
bat-file. To start the client as standalone or networked just lanunch the bat-file 
to start the client.

6. RUN WITH WEBSTART
====================
To be able to run it with webstart you will either have to run it at my homepage
http://erland.homeip.net or you need to download the source package and build a jnlp
file as follows:

- Modify the project.xml file and the <url> tag to point to your website where you 
  will put the jnlp files
- See the BUILDING chapter below for information about how to generate the jnlp package


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
  To generate a jnlp webstart package(Se also previous chapter):
  maven jnlp
