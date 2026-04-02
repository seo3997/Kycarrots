---
description: seo3997/Kycarrots 저장소의 Git Push 및 Jenkins 빌드 연동 워크플로우
---

### 🚀 Git Push 및 Jenkins 연동 프로세스

이 워크플로우는 사용자가 "git push 해줘"라고 요청할 때 실행되는 자동화 지침입니다.

#### 1. GitHub 작업 환경 고정
- **저장소**: `seo3997/Kycarrots`
- **대상 브랜치**: `feature/refactor-to-branch-mall`

#### 2. 실행 단계 (사용자 요청 시)
1. 사용자가 "**git push 해줘**"라고 입력하면 AI는 즉시 "**커밋 메시지를 무엇으로 할까요?**"라고 묻습니다.
2. 사용자가 커밋 메시지를 입력하면 다음 명령을 수행합니다:
   ```bash
   git add .
   git commit -m "[사용자 입력 메시지]"
   git push origin feature/refactor-to-branch-mall
   ```
3. 푸시가 성공하면 즉시 "**GitHub 푸시가 완료되었습니다. 젠킨스 'asagong-was' 빌드를 실행할까요?**"라고 묻습니다.
4. 사용자가 승인하면 젠킨스 API를 통해 `asagong-was` 빌드를 즉시 실행합니다.
5. **빌드 결과 확인**: 빌드가 시작되면 AI는 빌드 번호를 추적하고, 빌드가 끝날 때까지 모니터링하여 **성공/실패 여부와 주요 배송 로그 요약**을 사용자에게 보고합니다.

// turbo
#### 3. 젠킨스 빌드 실행 및 결과 보고 (승인 시)
- **Jenkins Job**: `asagong-was`
- **URL**: `http://152.70.255.251:8080/job/asagong-was/build`
- **모니터링**: 빌드 완료 시까지 상태 체크 후 `consoleText` 요역 보고.
