# 자연어처리및정보검색 최종프로젝트
- 데이터 : https://imsdb.com
## 핵심 기능
- 대사로 검색해서 해당 대사랑 영화 정보까지 출력
- 동의어, 오탈자나 띄어쓰기 있어도 정상적으로 검색되게 하기

## 요구사항

### 필수기능

- 검색어는 텍스트로 띄어쓰기 포함해서 문장 단위로 입력받는다
- 검색 결과는 대사, 인물, 영화 정보를 출력한다
### 선택기능

- 사용자가 입력한 검색어의 오탈자를 보정한다 (ex. 사고ㅓ → 사과)
- 동의어 검색을 지원한다
## 설계

1. 영화 대사 데이터 DB 연동 (sqlLite) → 자바 객체로 바꾸기
2. 자바 객체 (영화 데이터)를 루씬 인덱스로 저장
3. 사용자 인터페이스에서 검색어 받아서 처리(파싱 필요할수도 있음)
4. 검색어로 서치해서 검색결과를 DTO로 변환
5. 검색어 오탈자 보정 (후순위)
### 주의사항
- 루신 인덱스파일(scripts.index) 갱신 시 **indexWriter/ScriptIndexWriter** 실행

### 참고 블로그

- 아파치 루신 튜토리얼

  https://tourspace.tistory.com/237

- 아파치 루신 동의어
  https://tourspace.tistory.com/249
