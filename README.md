WeatherApp (was GetYourGear)
This is the week 15 final release.

Workspace
- Github
  
    -Repository: https://github.com/bq0463/alvaroAppDeliver
  
    -Release: https://github.com/bq0463/alvaroAppDeliver/releases/tag/FinalVersion2

Description

 -Tracks your locationby long/alt and gives you relevant information about the weather with the purpose of knowing the weather before you leave home or when you are in places like Subways (Secluded areas).
 
Screenshots and Navigation 
-ThirdActivity(Dress)

![ThirdActivity](https://github.com/bq0463/alvaroAppDeliver/assets/158185157/2200108e-93a5-4082-abba-97fdd00d725c)
-SecondActivity(Time)

![SecondActivity](https://github.com/bq0463/alvaroAppDeliver/assets/158185157/f02e0dd2-ba40-4c8f-ab86-592fb6562f7b)
-MainActivity(with firebase AUTH)

![MainActivity](https://github.com/bq0463/alvaroAppDeliver/assets/158185157/3bcf0358-92f8-4afb-92d5-fd1d5ed6d195)
-Before Firebase Authentication

![beforeAuthFirebase](https://github.com/bq0463/alvaroAppDeliver/assets/158185157/93c993d7-4577-4e97-82d3-44223b61509b)
-After Firebase Authentication

![afterAuthFirebase_Home](https://github.com/bq0463/alvaroAppDeliver/assets/158185157/b6215d02-6bb5-4e55-974c-6ed34111c1ca)

Demo Video

Video demonstrating how the app works

link: https://upm365-my.sharepoint.com/:v:/g/personal/alvaro_hidalgo_lopez_alumnos_upm_es/EcISfJHFr_JAuQ4T-DGbYiUBSgh9_Z2aNFvwMXhCEKQF0A?e=UaVYrc&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D

Features

List the functional features of the app.

    -Now you can know how is the weather on the fly.
    -Interactive Drawer Menu.
    -logout quick button.
    -user saving to save time when you run it.
    -Real-time traffic information by openWeatherMap API

List the technical features of the app.
      --XML are configs of the activities
      
    -Home: MainActivity.kt and activity_main.XML (Uses firebase by default to log with google account or email,
    tracks your latest location(var),notified with Toast),also has a Toast display of the user saved before and displays google user from screen in that case. 
   
!!!!IMPORTANT !!!!:EXECUTE THIS ONE FIRST AFTER LOADING THE APP AND BEFORE PRESSING THE Dress BUTTON.ALSO, IF IT DOESNT LOAD THE COORDINATES,JUST PRESS HOME AGAIN AND WAIT FOR THEM,AND THEN YOU´LL BE PROVIDED WITH THE API INFO TOO.

    -Time: SecondActivity.kt and activity_second.xml (makes the call and saves the response of the weatehrMapAPI and shows relevant data)
    -Dress: ThridActivity.kt and activity_third.xml (recomends you what to wear in case you are in home or if the weather is dangerous,all thanks to the data from Time activity)
    -logout Button: if you want to disconnect your account from the app.This is a function that every activity has.
    -Firebase authentication.
    
    retrofit Package:
        -Resful APIs used (Openwheathermaps (https://api.openweathermap.org/data/2.5/) .Gives you a lot of information about the weather by passing latitude,longitude and an APIKEY(gathered by loging in the website) along with the baseURL).
            All this API data is gathered by the var result: remoteResult? which is returned from the openWeatherMapAPI.kt  with an object(like a function).
        -openWeatherMapAPI.kt: makes the call and stores all the traffic data inside the retrofit Data classes (Coord etc..)
    
    UI package: Just icons for the buttons in the drawer menu.
    
    Layout (almost all xml are there)
    -toolbar integrated in the app_var_main.xml along with the header of the drawer menu and everything else(weatherItem etc..)
    
    Menu
      -activity_main_drawer.xml : Buttons and sections of the drawer menu.
      
    colors.xml : -colors.xml defines primary color and everything else
    
    drawable: Has all the icons and png  files used in this app.

    strings.xml : SignedIN and all the other messages for the Toast Feature along app name,..etc


  All made with gradle version java home 17.0.9 JDK  and android studio SDK version 34.

Additional section:
    -ROOM package is implemented but not used after considering the goal of the app.It also has a favLocationEntity made.
How to Use:
    1. Start the app and sign up by google account or email.
    2. After that you can press logout in the drawer menu .
    3.Press the time button in the drawer menu.
    3.1!!!!IMPORTANT !!!!:EXECUTE THIS ONE FIRST AFTER LOADING THE APP AND BEFORE PRESSING THE Dress BUTTON.
    4. After this you can check anything you want on dress or anywhere else

Participant
    -Álvaro Hidalgo López (alvaro.hidalgo.lopez@alumnos.upm.es) only.
    100%


     
    
