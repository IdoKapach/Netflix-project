# Run Android Front-End

## Steps
1. **Open Android Studio**.
2. **Open the project** located in the [`Android`](Android ) directory.
3. **Configure the server address**:  
    - identify your host machine ip address (`ipconfig` on windows\ `ip a` on linux)
    - modify `/Targil4/Android/app/src/main/res/xml/network_security_config.xml` to include your host IP
    - modify the field `BaseURL` under `/Targil4/Android/app/src/main/res/values/strings.xml` to have `http://{your-host-IP}:{API_PORT} instead of the default. 
4. **Build and run the project** on an emulator or physical device.  
### **make sure you're device is using the same network as the api server**

## Screenshots
1. Register new user: ![](./screenshots//android-register.jpg)
2. Login: ![](./screenshots/android-login.jpg)
3. Home Page: ![](./screenshots/android-homepage.jpg)
4. Movie Creation: ![](./screenshots/android-addmovie.jpg)  ![](./screenshots/android-movieadded.jpg)
5. Movie Modification: ![](./screenshots/android-editmovie.jpg)  ![](./screenshots/android-movieedited.jpg)  ![](./screenshots/android-movieedited2.jpg)
6. Movie Deletion: ![](./screenshots/android-deletemovie.jpg)  ![](./screenshots/android-moviedeleted.jpg)
7. Movie watching: ![](./screenshots/android-moviewatch1.jpg) ![](./screenshots/android-moviewatch2.jpg)
8. Movie Recommendation: ![](./screenshots/android-recommend-info.jpg)
9. Movie Search: ![](./screenshots/android-search1.jpg)  ![](./screenshots/android-search2.jpg)  ![](./screenshots/android-search3.jpg)
