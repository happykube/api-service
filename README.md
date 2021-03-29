# API Service sample 

## How to import project 
- 작업PC에 디렉토리를 생성: 예) mkdir api && cd api
- clone git repository: git clone https://github.com/happynsmall/api-service.git 
- STS 실행 
- Workspace를 위에서 생성한 디렉토리(예:api)로 변경 : File > Switch Workspace
- Import project 
  - File > Import 
  - Projects from Folder or Archive 선택
  - api-service 디렉토리(예: ../api/api-service) 선택 후 Finish 클릭 

## How to test
- Find 'Boot Dashboard'
- 'local'을 펼치고, api-service 선택 
- 상단 맨 왼쪽 버튼 클릭 
- 웹브라우저에서 http://localhost:8881/swagger-ui/ 오픈 

## How to git push 
[STS에서 git commit & push하기](https://happycloud-lee.tistory.com/194?category=832250) 참조

## How to deploy on k8s cluster
- 사전준비 
  - cicd pipeline 설치
    - CI/CD Pipeline을 실행할 서버로 로그인 
    - 본인 OS ID로 변경: su - {OS userid}
    - git clone https://github.com/happyspringcloud/run-cicd.git
    - sudo ln -s ~/run-cicd/cmd/run-cicd /usr/local/bin/run-cicd
  - maven 설치: [maven설치](https://happycloud-lee.tistory.com/186?category=902419)참조하여 설치 

- cicd/cicd-common.properties 정의 후 git push 
- Pipeline을 실행할 서버에서 실행 
  - mkdir cicd && cd cicd 
  - git clone https://github.com/happynsmall/api-service.git
  - cd api-service
  - run-cicd {image registry userid} {image registry password} . dev . java 
    - ex) run-cicd happycloudpak passw0rd . dev . java 
    
     