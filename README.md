# Streambot
This bot is written in Java as a Maven-Projet. The IDE used is Eclipse.  
# Information
Other users are free to use this bot as well. It might not be perfect and the code is not as clean as possible but it works and should be easy to understand.
# Installation & Usage
To install and use this bot you have to follow these steps:
1. Download the code
2. Add a "resources" folder and add a "credentials.property" file
3. Add the following key/value pairs: BOT\__NAME, BOT\__TOKEN, CHANNEL, RCON, DB\__NAME, DB\__PASS
4. You need a Database using mariadb (for others you need to change the connector). The DB is for userpoints and some statistics
5. Follow the Twitch Bot Development Tutorial and adapt your keys and values to your own need
6. Install the Bot using maven with dependencies and run the bot
# Credits
I want to give credits to these projects:  
IRC-Code 	https://gist.github.com/kaecy/286f8ad334aec3fcb588516feb727772#file-simpleircclient-java  
RCON-Code   https://github.com/jobfeikens/rcon  
