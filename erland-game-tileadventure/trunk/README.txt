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
- *.war (The game classes)
- docs/* (The javadoc documentation for the library classes)

This source archive should contain the following files:
- README.txt (this file)
- LICENSE.txt (the software license)
- src/* (The game source code)
- web/* (The web application source code)
- build.xml (A build file for ant)
- maven.xml (A build file for maven)
- project.properties (A configuration file for maven)
- project.xml (A configuration file for maven)
- TileAdventure.bat (A bat file to start the game)

4. CONFIGURING JNLP
===================
The jnlp-file contains references to my local machie, so you will have to
change these to your machine

5. RUN
=================
To start the client extract the war-file and run the included bat-file, if you want 
to run a networked game you can start the server with the included
bat-file. 

6. RUN WITH WEBSTART
====================
To be able to run it with webstart you will either have to run it at my homepage
http://erland.homeip.net or deploy the war-file to your own web server.

7. BUILDING
============
This chapter is only interesting if you for some reason wants to recompile the web application from
the source archive. There is an ant build file delivered witht he application but the recommended and
tested way to build it is by using maven(http://maven.apache.org).

- Install maven
- Generate a new keystore with:
  maven jnlp:generate-keystore
- Build with:
  To just generate the war archive:
  maven all:build
  To generate a new distribution:
  maven dist
