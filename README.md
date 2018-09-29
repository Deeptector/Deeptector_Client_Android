<div>

# Deeptector_Client_Android

<img width="100" src="https://user-images.githubusercontent.com/30898520/46004540-22006200-c0ee-11e8-82fe-8022210de98e.jpg">

</div>

#### Deeptector_Android_Client는 실시간 행동 탐지기(딥 러닝)을 이용한 행동 감지 시스템 딥 러닝의 어플리케이션입니다.    
본 클라이언트는 크게 3가지의 서버 모두 켜져있을 시 제공되는 기능들을 이용하실 수 있습니다.  
* Rtsp_Server
* Spring_Server
* React  

해당 탐지기에 특정 행동이나 상황이 감지 되면 어플리케이션을 통해 알림(notification)을 실시간으로 확인 할 수 있습니다.   
또한 감지와 동시에 이루어지는 영상을 실시간으로 지켜 볼 수 있으며, 지난 영상들을 서버를 통해 다운받아 다시 확인 해보실수 있습니다.     
  
웹 형태로 제공되는 클라이언트는 [**Deeptector_React**](https://github.com/Deeptector/Deeptector_React)를 참고 해주시면 되겠습니다.   
    
현재 이 Repository는 **안드로이드 클라이언트** 부분입니다.  

<    
## 사용법
   
### 0. Android-studio에서 실행 시 (NDK 설치)  
##### apk파일이 아닌 Studio에서 실행시 NDK 툴을 설치해주셔야 합니다   
Tools -> Android -> SDK Manager 로 들어가셔서 다음 화면과 같이 NDK 설치 여부를 확인해주십시오.
<div>
 <img width="600" height="400" src="https://user-images.githubusercontent.com/30898520/46242881-c6ddb080-c408-11e8-8d8c-50810e4b0774.png">
</div>

   
### 1. 권한
<div>
  <img width="200" src="https://user-images.githubusercontent.com/30898520/46005628-a6ec7b00-c0f0-11e8-897e-8e2a45037760.jpg">  
  <img width="200" src="https://user-images.githubusercontent.com/30898520/46005896-3db93780-c0f1-11e8-9b2c-6574096ef0ae.png">  
</div>

먼저, Deeptector_Android_Client 최초 실행 시 외부저장소에 대한 권한 요청을 하게됩니다.   
   
이 권한을 허가 해주셔야 사용하실 수 있으시며, 이 권한은 후에 감지된 영상을 저장 및 열람 할 수 있게 해주는 권한이므로     
안심하셔도 됩니다.
권한이 확인 된 후에는 우측의 화면과 같은 홈화면을 만나실 수 있습니다.
   
<br/><br/>
### 2. 지난 영상 확인 및 다운로드
<div>
  <img width="200" src="https://user-images.githubusercontent.com/30898520/46005889-3abe4700-c0f1-11e8-97f9-d843bd1e6a94.png">
  <img width="200" src="https://user-images.githubusercontent.com/30898520/46006570-cedcde00-c0f2-11e8-80ef-4ac09cdcc73e.jpg">
</div>

#### 사용자는 서버에 녹화 저장된 동영상 목록들을 현재 이 화면에서 확인해보실 수 있습니다. 
#### (영상은 특정행동이 감지되었을 시 서버에 저장됩니다.)
>* 목록들 이름 옆에 표시된 폴더 모양을 클릭하시면 영상을 다운받으실 수 있습니다.  
>* 우측 하단 파란색원 버튼을 클릭 하셔서 다운받은 영상들을 확인하여 재생하실 수 있습니다.  
   
<br/><br/>
### 3. 실시간 영상 수신
<div>
   <img width="200" src="https://user-images.githubusercontent.com/30898520/46006795-51fe3400-c0f3-11e8-9736-7a1b86038b64.png">
   <img width="200" src="https://user-images.githubusercontent.com/30898520/46009264-fedbaf80-c0f9-11e8-97f7-561511c61795.png">
</div>

#### 사용자는 현재 촬영 되고있는 영상을 실시간으로 확인 해보실 수 있습니다.  
>* 현재 이 CCTV 화면에서는 카메라에 담기고 있는 영상을 Rtsp서버를 통해 실시간으로 확인할 수 있습니다.
   
<br/><br/>
### 4. 알림 (Notification)
<div>
   <img width="200" src="https://user-images.githubusercontent.com/30898520/46010596-3cdad280-c0fe-11e8-9f78-6a38f9355b67.jpg">
   <img width="200" src="https://user-images.githubusercontent.com/30898520/46010597-3f3d2c80-c0fe-11e8-8546-4500e87d2a4f.jpg">
</div>

* 감지
	* 특정 행동이 감지되었을 시, 알림 메세지와 알림소리를 통하여 사용자에게 알려줍니다.
* 영상 다운
	* 원하는 영상이 다운로드가 완료되었을 시, 사용자에게 알려줍니다.

<br/><br/>
## 프로젝트 관련 링크
* [Deeptector](https://github.com/Deeptector/Deeptector)

* [Deeptector_React](https://github.com/Deeptector/Deeptector_React)

* [Deeptector_Spring](https://github.com/Deeptector/Deeptector_Spring)

* [DarkNet](https://github.com/Deeptector/Darknet)

<br/><br/>
## 빌드 실패시
Edit configuration - Error : please select android sdk 가 뜬다면   
프로젝트 Gradle Scripts -> build.gradle(Module:app)에서 compileSdkVersion과 targetSdkVersion의 숫자를 바꿔주신 뒤    
다시 원래 27로 고쳐주고 sync now를 해주시면 해결 됩니다.  
<br/><br/>
혹은 build.gradle(Project: Deeptector_Client_Android) 에서
```sh
buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
        

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
``` 
classpath에 있는 3.0.1버전을 귀하의 안드로이드 스튜디오에 맞게 고쳐주시면 됩니다.
