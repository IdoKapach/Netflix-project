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
1. Register new user:  
    <img src="./screenshots/android-register.jpg" width="200px" style="margin-right: 10px;">
2. Login:  
    <img src="./screenshots/android-login.jpg" width="200px" style="margin-right: 10px;">
3. Home Page:  
    <img src="./screenshots/android-homepage.jpg" width="200px" style="margin-right: 10px;">
4. Movie Creation:  
    <div style="display: flex;">
        <img src="./screenshots/android-addmovie.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-movieadded.jpg" width="200px" style="margin-right: 10px;">
    </div>
5. Movie Modification:  
    <div style="display: flex;">
        <img src="./screenshots/android-editmovie.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-movieedited.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-movieedited2.jpg" width="200px" style="margin-right: 10px;">
    </div>
6. Movie Deletion:  
    <div style="display: flex;">
        <img src="./screenshots/android-deletemovie.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-moviedeleted.jpg" width="200px" style="margin-right: 10px;">
    </div>
7. Movie watching:  
    <div style="display: flex;">
        <img src="./screenshots/android-moviewatch1.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-moviewatch2.jpg" width="400px" style="margin-right: 10px;">
    </div>
8. Movie Recommendation:  
    <img src="./screenshots/android-recommend-info.jpg" width="200px" style="margin-right: 10px;">
9. Movie Search:  
    <div style="display: flex;">
        <img src="./screenshots/android-search1.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-search2.jpg" width="200px" style="margin-right: 10px;">
        <img src="./screenshots/android-search3.jpg" width="200px" style="margin-right: 10px;">
    </div>
