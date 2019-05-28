#INSTALL

- install environment: 
	1. install node and npm: 
	  + download install file at https://nodejs.org/en/download/
	  + run install file (node-version.msi)
	- 1.1 test node and npm: run command in terminal: 
	  + node -v
	  + npm -v 
	2.  install angular-cli:	
	  + run command in terminal: npm install -g @angular/cli										
- install dependencies: 
	+ open teminal 
	+ cd to client folder (UI folder)
	+ run command: npm install


#START

- edit server name at UI\src\environments\environment.ts:
	+ at line: API: '{server name}' (ex: API: 'http://192.168.191.232:9000')
- open teminal
- cd to client folder (UI folder)
- run command: ng s -o  
- open google chrome type localhost:4200 


#BUILD
- run command: npm build
	

