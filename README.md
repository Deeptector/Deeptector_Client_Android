<div>

# Deeptector_Client_Android

<img width="100" src="https://user-images.githubusercontent.com/30898520/46004540-22006200-c0ee-11e8-82fe-8022210de98e.jpg">

</div>

Deeptector_Android_Client는 실시간 행동 탐지기(딥 러닝)을 이용한 행동 감지 시스템 딥 러닝의 어플리케이션입니다.    
본 클라이언트는 크게 3가지의 서버와 모두 켜져있을 시 기능들을 이용하실 수 있습니다.  
* Rtsp_Server
* Spring_Server
* React  




해당 탐지기에 특정 행동이나 상황이 감지 되면 어플리케이션을 통해 알람(notification)을 실시간으로 확인 할 수 있습니다.   
또한 감지와 동시에 이루어지는 영상을 실시간으로 지켜 볼 수 있으며, 지난 영상들을 서버를 통해 다운받아 다시 확인 해보실수 있습니다.     
  
현재 이 repository는 Android_Client 부분입니다.

===

## 사용법

### 1. 권한
<div>
  <img width="200" src="https://user-images.githubusercontent.com/30898520/46005628-a6ec7b00-c0f0-11e8-897e-8e2a45037760.jpg">  
  <img width="200" src="https://user-images.githubusercontent.com/30898520/46005896-3db93780-c0f1-11e8-9b2c-6574096ef0ae.png">  
</div>

먼저, Deeptector_Android_Client 최초 실행 시 외부저장소에 대한 권한 요청을 하게됩니다.   
이 권한을 허가 해주셔야 사용하실 수 있으시며, 이 권한은 후에 감지된 영상을 저장 및 열람 할 수 있게 해주는 권한이므로     
안심하셔도 됩니다.
권한이 확인 된 후에는 우측의 화면과 같은 홈화면을 만나실 수 있습니다.


### 2. 지난 영상 리스트 및 다운로드

