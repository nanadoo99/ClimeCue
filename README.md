# ⛅ ClimeCue
사용자가 선택한 위치의 **날씨 데이터를 바탕으로, AI를 통해 실생활에 유용한 조언을 제공**하는 위치 기반 날씨 생활 서비스입니다. 단순한 기상 정보 제공을 넘어, 건강 정보를 반영한 개인 맞춤형 조언으로 날씨에 따른 더 나은 하루를 준비할 수 있도록 돕습니다.
지도 기반 인터페이스와 실시간 분석 기능을 통해 직관적이고 실용적인 사용자 경험을 제공합니다.

## 🛠️ 사용 기술
- Backend: Java 17, Spring Boot, Spring Security, JPA
- Infra: AWS Elastic Beanstalk, RDS(MySQL)
- 외부 API: 기상청, Kakao Map, OpenAI GPT
- Frontend: Thymeleaf, JavaScript
- 오픈 소스: Bootstrap, jQuery

## 🚀 기능
- 위치 기반 날씨 정보 제공: 지도에서 위치를 선택하면, 해당 지역의 실시간 기상 정보 표시
- 생활 맞춤형 조언 출력: 날씨 상태에 따라 외출/운동/건강 관리에 대한 조언 제공
- 개인화 기능: 로그인한 사용자는 건강 정보 기반의 맞춤형 날씨 조언 이용 가능

## 🧩 기술 & 시스템 아키텍쳐
![ClimeCue_system.png](https://github.com/nanadoo99/ClimeCue/blob/master/ClimeCue_system.png)

## 🔍 문제해결 사례
> 잘못된 기상 데이터 차단을 위한 유효 좌표 검증 시스템 구축
  ### 문제 원인
  - 기상청 API는 지원하지 않는 좌표에도 200 OK를 응답하고, -999°C와 같은 무의미한 값이 포함되어 사용자에게 잘못된 날씨 정보가 노출되는 문제.
  - 이는 OpenAI 기반의 생활 조언 생성 결과에도 영향을 주는 중대한 정확도 이슈의 원인임 됨.

  ### 해결안
  #### 1. 기상청 좌표 Excel을 사용한 싱글톤 기반 유효 좌표 관리 유틸리티 (ValidVilageFcstPoints) 구현
- @PostConstruct를 통해 애플리케이션 시작 시 유효한 좌표 Excel을 읽고, Set<GridPoint> 형태로 메모리에 저장
  
  #### 2. Bean Validation 기반 유효성 검사 어노테이션 설계
- `@VilageFcstPointCheck` 라는 커스텀 어노테이션을 정의하고, `ConstraintValidator` 인터페이스를 통해 유효성 로직을 분리
- Spring의 `LocalValidatorFactoryBean` 설정을 통해 의존성 주입이 가능한 구조로 설계
  
  #### 3. Controller 요청 차단 로직 적용
- /weather API에서 @Valid VilageFcstNxNyDto를 바인딩하고, Bean Validation에서 좌표가 유효하지 않으면 즉시 요청을 차단

##### ValidVilageFcstPoints

  ```java
@Component
public class ValidVilageFcstPoints {
		...

    @Setter
    @Value("${resources.excel.vilageFcst}")
    private Resource vilageFcstFile;

    private final Set<GridPoint> validPoints = new HashSet<>();
    
    // 애플리케이션 시작 시 유효 좌표 데이터를 메모리에 로딩
    @PostConstruct 
    public void init() { readExcelData(vilageFcstFile);}
	
		// 엑셀 파싱 및 유효 좌표 Set에 추가 
    private void readExcelData(Resource file) { ... } 
		
		// API 요청 좌표가 유효한지 검사
    public boolean isValidPoint(VilageFcstNxNyDto dto) { 
        return validPoints.contains(new GridPoint(dto.getNx(), dto.getNy()));
    }

    // 좌표 중복을 방지하기 위한 equals, hashCode 재정의
    // 내부 클래스 사용: 해당 좌표 객체는 유효성 검사 용도로만 사용되며, 외부에 노출될 필요가 없기 때문
    private static class GridPoint {
        private final int nx;
        private final int ny;

        public GridPoint(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }

        @Override
        public boolean equals(Object o) { ... }
        
         @Override
        public int hashCode() { ... }
				...
    }
}

  ```
