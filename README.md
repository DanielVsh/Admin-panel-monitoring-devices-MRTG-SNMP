# Administrator panel to manage the devices monitored by MRTG SNMP

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Acknowledgements](#acknowledgements)
* [Contact](#contact)
<!-- * [License](#license) -->


## General Information
This is a complete web application with an admin panel that uses Multi Router Traffic Grapher (MRTG) technology to monitor the network of devices.  


## Technologies Used
- Java - version 19.0.2
- Spring Boot - version 2.7.4
- Javers - version 6.10.0
- Java JWT - version 4.0.0
- Maven - version 3.6.3
- Node.JS - version 16.15.1
- npm - version 8.11.0
- React - version 18.2.0
- React Redux - version 8.0.5
- RTK Query - version 1.8.6
- Docker - version 20.10.17




## Features
List the ready features here:
- MRTG generation of network traffic statistics via SNMP community
- Audit logs
- Token system


## Screenshots
![main_page_open_user](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/1b3bab34-4821-45dc-b11c-14a38b03d63c)
![mrtg_main_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/5c0994d7-433d-46e6-ac92-1d52fac9cf21)
![mrtg_stat_1](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/53f0a027-cabb-4297-9dc0-c4a36c86d33a)
![mrtg_stat_2](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/ad7f5692-b2b4-40f6-a556-46912a106398)
![login_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/9be964f0-cc5d-4ac6-9d19-7943288c68ae)
![dashboard_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/c430199e-bf5c-4349-8551-b2d21ed3cde1)
![main_page_open_admin](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/e2c35d71-7414-4e39-a88c-4937573e5277)
![building_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/555a6cfc-1ed3-4957-ae34-983a5468e0b8)
![building_create_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/8982cc69-cb27-49f7-9bc8-6f6d2223996a)
![building_update_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/1a457580-8b88-4d6a-aafc-37619ddc48e0)
![device_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/bf7bb550-c324-4e3a-8148-958545ec7eb9)
![device_create_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/6f1ff830-8644-4438-a44f-a8482e68ad0f)
![device_update_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/c823e41a-722e-4b53-8086-5613c08e8e12)
![location_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/d95f9a01-45df-44eb-9333-788a653ecf28)
![location_create_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/d5e3cbdd-4f3a-4331-8cc1-2d53c7667f09)
![location_update_page](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/b61a21dd-0da2-4040-9bb2-07b09c920662)
![logs_page_calendar_1](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/52fe0c2b-13a0-44b9-a0c2-8988de692378)
![logs_page_calendar_2](https://github.com/DanielVsh/Admin-panel-monitoring-devices-MRTG-SNMP/assets/103316975/13c409ad-f594-406d-be97-8923c20db016)


## Setup
Installing an application using Docker
Steps to run an application using a Docker container:
   1. The application can be started in the root folder of the project using the `docker compose up` command.


## Usage
You need to add the device with its IP and SNMP community, then regularly and automatically at regular intervals it will generate statistics about the traffic which you can find on the main page of the application.


## Project Status
Project is: _in progress_


## Room for Improvement
Room for improvement:
- Improve the design of the app.


To do:
- Make it possible for the main administrator to manage the other administrators. 



## Contact
Created by Daniel Vishnievskyi. 
[LinkedIn](https://linkedin.com/in/daniel-vishnievskyi) - feel free to contact me!


<!-- Optional -->
<!-- ## License -->
<!-- This project is open source and available under the [... License](). -->

<!-- You don't have to include all sections - just the one's relevant to your project -->
