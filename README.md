<p align="center">
  <img src="https://raw.githubusercontent.com/timostrating/parkingsimulator/master/blender_files/logo/logo.png" alt="rug" width="700" height="400">
</p>
<p align="center">
  This is an AWESOME parkingsimulator inspired by Rollercoaster Tycoon 2.
</p>

![gif](https://i.imgur.com/rIjKCu7.gif)

[![Build Status](https://travis-ci.org/timostrating/parkingsimulator.svg?branch=master)](https://travis-ci.org/timostrating/parkingsimulator)
[![BCH compliance](https://bettercodehub.com/edge/badge/timostrating/parkingsimulator?branch=master)](https://bettercodehub.com/)

## Team
* [Hilko Janssen](https://github.com/hilkojj)
* [CodeCasper](https://github.com/codecasper)
* [Timo Strating](https://github.com/timostrating) 
<br/><br/>


## Dependencies
* Gradle
* Java 8
* JRE: 1.8+ 64bit
* JVM: OpenJDK 64-Bit Server VM by JetBrains
<br/><br/>


## Documentation
[Javadoc documentation can be found here](https://timostrating.github.io/parkingsimulator/)
<br/>


## Installation

gradle.properties
```gradle.properties
org.gradle.java.home=__URL_TO_YOUR_JAVA_HOME__
```

Replace this with the url of your jdk installation. For example: if you have jdk version 1.8.0_73 installed on Windows.
WARNING: Java 9 not supported!

```gradle.properties
org.gradle.java.home=C:\\Program Files\\Java\\jdk1.8.0_73
```

Now go to the platform / tool that you would like to use.
<br/>


### Intellij idea

Press ALT + SHIFT + F9 add select edit configuration. Now add the following gradle configuration to Intellij and hit apply.

![Intellij Gradle run](https://i.imgur.com/scTH9Jw.png)

Now we are going to refresh the dependencies. Go to View -> Tool Windows -> Gradle. And now click on the first icon with the 2 spinning arrows.

![Intellij Gradle run](https://i.imgur.com/gRYdssI.png)

Now hit SHIFT + F9 and enjoy.
<br/>

### Windows
There is a special .bat file that automates the windows pipeline.
```
.\gradlew.dat desktop:run
```
In the case this does not work take a look at the Windows Powershell installation or the intellij development installation.
<br/>

### Windows Powershell

Open powershell as an administrator. And run this command below to install chocolatey.

```
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
```

Now we are going to install gradle by using the chocolatey package manager.
```
choco install gradle
```

With gradle we can run the application.
```
gradle desktop:run
```
<br/>


### Linux (Ubuntu)
Java 8 or newer should be installed. You can install it by running the following commands. You may need to replace "apt install" with your package managers install command like "yaourt -S" in the case of arch.
``` 
apt install openjdk-8-jre
apt install openjdk-8-jrk
```

On linux your java.home should look like something like this. Depending on what kind of hardware you have, something like "-amd64" could be added in your case. So check if this path exist. Change the gradle.properties file to reflect this.
```gradle.properties
org.gradle.java.home=/usr/lib/jvm/java-8-openjdk/jre
```

Next up we can install gradle. You may need to replace "apt install" with your package managers install command.
``` 
apt install gradle
```

With gradle we can run the application.
```
gradle desktop:run
```
<br/>


### MAC OS
On MAC OS your java.home should look like something like this. This needs to be changed in the gradle.properties file.
```gradle.properties
org.gradle.java.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home
```

We are going to install gradle using brew.
``` 
brew install gradle
```

With gradle we can run the application.
```
gradle desktop:run
```
