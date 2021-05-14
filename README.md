# API Service sample 

## How to import project 
- 작업PC에 디렉토리를 생성: 
  예) mkdir -p \~/study/src && cd ~/study/src
- clone git repository: git clone https://github.com/happykbfg/api-service.git 
- STS 실행 
- Workspace를 위에서 생성한 디렉토리(예:study)로 변경 : File > Switch Workspace
- Import project 
  - File > Import 
  - Projects from Folder or Archive 선택
  - api-service 디렉토리(예: ../study/src/api-service) 선택 후 Finish 클릭 

## How to test
- Find 'Boot Dashboard' 
- 'local'을 펼치고, api-service 선택 
- 상단 맨 왼쪽 버튼 클릭 
- 웹브라우저에서 http://localhost:8881/swagger-ui/ 오픈 

\* Boot dashboard 안 보이면 프로젝트 선택 > 우클릭 메뉴에서 Run As .. > Spring Boot App 클릭 

## How to git push 
- github.com에 본인의 repository를 만듭니다.   
- git clone한 디렉토리로 이동하여, 원격 git repository를 본인걸로 바꿉니다. 

```
$ cd ~/study/src/api-service
$ git remote remove origin
$ git init 
$ git remote add origin {your git repository url}
```

- 본인 git repository로 푸시 합니다.  

```
$ git add . && git commit -m "copy my repo" && git push -u origin main
```

STS에서 편하게 push할 수도 있습니다. 아래를 참조하세요.   
[STS에서 git commit & push하기](https://happycloud-lee.tistory.com/194?category=832250) 참조

## How to deploy on k8s cluster
- 사전준비: pipeline실행할 서버에서 수행 
  - cicd pipeline 설치
    - CI/CD Pipeline을 실행할 서버로 로그인 
    - 본인 OS ID로 변경: su - {OS userid}
    - download
    
```
    $ git clone https://github.com/happykube/run-cicd.git
    $ mkdir ~/bin
    $ ln -s ~/run-cicd/cmd/run-cicd ~/bin/run-cicd
```
    
  - maven 설치: [maven설치](https://happycloud-lee.tistory.com/186?category=902419) 참조하여 설치 

- CI/CD변수 설정: 작업PC/VM에서 수행
  - cicd/cicd-common.properties 적절히 수정 (아래 예제 참조)

```
# Container Image info
image_registry=docker.io
image_project=happykbfg
image_repository=api-service
image_tag=0.0.1

# resources
req_cpu=128m
req_mem=128Mi
limit_cpu=1024m
limit_mem=1024Mi

# namespace, sa
namespace=kb05
serviceaccount=sa-kb05

# Service info
service_target_port=8881
service_port=8881
service_host=hklee.api-service.169.56.113.40.nip.io
service_replicas=1

image_pull_policy=Always
```

  - git push   

```  
    $ git add . && git commit -m "set cicd variables" && git push -u origin main   
```
    

- CI/CD: Pipeline을 실행할 서버에서 실행 

```
  $ mkdir cicd && cd cicd 
  $ git clone {your git repository url}
  $ cd api-service 
  $ run-cicd {image registry userid} {image registry password} . dev . java 
    ex) run-cicd happycloudpak passw0rd . dev . java 
```
