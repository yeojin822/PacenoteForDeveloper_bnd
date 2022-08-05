package com.example.portfoliopagebuilder_bnd.service.builder;

import com.example.portfoliopagebuilder_bnd.repository.UserRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.CareerRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.PortfolioRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.ProfileRepository;
import com.example.portfoliopagebuilder_bnd.repository.builder.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BuilderServiceImplTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    PortfolioRepository portfolioRepository;

    @Mock
    ProfileRepository profileRepository;

    @Mock
    CareerRepository careerRepository;

    @Mock
    UserRepository userRepository;

    private BuilderService builderService;
    String profile = "{\n" +
            "  \"id\": \"kakao_test\",\n" +
        "      \"blockType\": \"Profile\",\n" +
        "      \"fieldValues\": {\n" +
        "        \"profileImage\": \"https://image.shutterstock.com/image-photo/osaka-japan-jun e-24-2017-600w-669537982.jpg\",\n" +
        "        \"profileMainText\": \"Front End Developer\",\n" +
        "        \"profileSubText\": \"안녕하세요 :) 서핏 팀의 \\n디자이너 박소연입니다.\",\n" +
        "        \"profileAdditionalInfo\": \"apply\",\n" +
        "        \"profileApplyCompany\": \"당근마켓\",\n" +
        "        \"profileApplyPosition\": \"Front End\",\n" +
        "        \"profilePhoneNumber\": \"010-3734-1715\",\n" +
        "        \"profileEmail\": \"ket8780@gmail.com\",\n" +
        "        \"profileGitHubURL\": \"https://github.com/choiseunghyeon\",\n" +
        "        \"profileKeyword1\": \"근명\",\n" +
        "        \"profileKeyword2\": \"성실\",\n" +
        "        \"profileKeyword3\": \"책임\",\n" +
        "        \"profileKeyword4\": \"개발\",\n" +
        "        \"profileKeyword5\": \"혁신\"\n" +
        "      }\n" +
            "}";

    String json = "{\n" +
            "  \"id\": 1,\n" +
            "  \"blocks\": [\n" +
            "    {\n" +
            "      \"blockType\": \"Profile\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"profileImage\": \"https://image.shutterstock.com/image-photo/osaka-japan-jun e-24-2017-600w-669537982.jpg\",\n" +
            "        \"profileMainText\": \"Front End Developer\",\n" +
            "        \"profileSubText\": \"안녕하세요 :) 서핏 팀의 \\n디자이너 박소연입니다.\",\n" +
            "        \"profileAdditionalInfo\": \"apply\",\n" +
            "        \"profileApplyCompany\": \"당근마켓\",\n" +
            "        \"profileApplyPosition\": \"Front End\",\n" +
            "        \"profilePhoneNumber\": \"010-3734-1715\",\n" +
            "        \"profileEmail\": \"ket8780@gmail.com\",\n" +
            "        \"profileGitHubURL\": \"https://github.com/choiseunghyeon\",\n" +
            "        \"profileKeyword1\": \"근명\",\n" +
            "        \"profileKeyword2\": \"성실\",\n" +
            "        \"profileKeyword3\": \"책임\",\n" +
            "        \"profileKeyword4\": \"개발\",\n" +
            "        \"profileKeyword5\": \"혁신\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Project\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"projectName\": \"대출 추천 재개발\",\n" +
            "        \"projectOrganigation\": \"현대 자동차\",\n" +
            "        \"projectDescription\": \"도시·개발계획 분석 전문가인 엄재웅(서경파파)씨가 신간 ‘강남 되는 강북 부동산은 정해져 있다’(위즈덤하우스)를 펴냈다. 엄씨는 부동산에서 가장 중요한 것은 입지가 아닌 정책이 부동산 시장에서 교통\\n 호재는 언제나 많은 관심을 부르는 키워드입니다. 특히 교통 호재는 계획 발표\",\n" +
            "        \"projectTerm\": { \"from\": \"2022-04-01\", \"to\": \"2022-05-30\" },\n" +
            "        \"projectSkills\": \"View와 Data를 분리하고 모든 비즈니스 로직을 redux middleware에서 처리\\nredux, redux-saga 적용 및 가이드 공유\",\n" +
            "        \"projectSkillSet\": [\"React\"]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Project\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"projectName\": \"대출 추천 재개발\",\n" +
            "        \"projectOrganigation\": \"현대 자동차\",\n" +
            "        \"projectDescription\": \"도시·개발계획 분석 전문가인 엄재웅(서경파파)씨가 신간 ‘강남 되는 강북 부동산은 정해져 있다’(위즈덤하우스)를 펴냈다. 엄씨는 부동산에서 가장 중요한 것은 입지가 아닌 정책이 부동산 시장에서 교통\\n 호재는 언제나 많은 관심을 부르는 키워드입니다. 특히 교통 호재는 계획 발표\",\n" +
            "        \"projectTerm\": { \"from\": \"2022-04-01\", \"to\": \"2022-05-30\" },\n" +
            "        \"projectSkills\": \"View와 Data를 분리하고 모든 비즈니스 로직을 redux middleware에서 처리\\nredux, redux-saga 적용 및 가이드 공유\",\n" +
            "        \"projectSkillSet\": [\"React\"]\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Career\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"careerMainText\": \"현대 자동차\",\n" +
            "        \"careerSubText\": \"Front-End\",\n" +
            "        \"careerDescription\": \"View와 Data를 분리하고 모든 비즈니스 로직을 redux middleware에서 처리\\n redux, redux-saga 적용 및 가이드 공유\",\n" +
            "        \"careerTerm\": { \"from\": \"2022-04-01\", \"to\": \"2022-05-30\" }\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Career\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"careerMainText\": \"현대 자동차\",\n" +
            "        \"careerSubText\": \"Front-End\",\n" +
            "        \"careerDescription\": \"View와 Data를 분리하고 모든 비즈니스 로직을 redux middleware에서 처리\\n redux, redux-saga 적용 및 가이드 공유\",\n" +
            "        \"careerTerm\": { \"from\": \"2022-04-01\", \"to\": \"2022-05-30\" }\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Career\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"careerMainText\": \"현대 자동차\",\n" +
            "        \"careerSubText\": \"Front-End\",\n" +
            "        \"careerDescription\": \"View와 Data를 분리하고 모든 비즈니스 로직을 redux middleware에서 처리\\n redux, redux-saga 적용 및 가이드 공유\",\n" +
            "        \"careerTerm\": { \"from\": \"2022-04-01\", \"to\": \"2022-05-30\" }\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Portfolio\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"portfolioThumbnail\": \"https://image.shutterstock.com/image-photo/osaka-japan-june-24-2017-600w-669537982.jpg\",\n" +
            "        \"portfolioName\": \"MZ세대 언어\",\n" +
            "        \"portfolioDescription\": \"어남선생 류수영, 레시피 여왕 박복순 박솔미, 국민아들 찬또배기 이찬원이 치열한 경쟁을 예고\",\n" +
            "        \"portfolioURL\": \"http://sports.hankooki.com/news/articleView.html?idxno=6798068\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Portfolio\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"portfolioThumbnail\": \"https://image.shutterstock.com/image-photo/osaka-japan-june-24-2017-600w-669537982.jpg\",\n" +
            "        \"portfolioName\": \"MZ세대 언어\",\n" +
            "        \"portfolioDescription\": \"어남선생 류수영, 레시피 여왕 박복순 박솔미, 국민아들 찬또배기 이찬원이 치열한 경쟁을 예고\",\n" +
            "        \"portfolioURL\": \"http://sports.hankooki.com/news/articleView.html?idxno=6798068\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Portfolio\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"portfolioThumbnail\": \"https://image.shutterstock.com/image-photo/osaka-japan-june-24-2017-600w-669537982.jpg\",\n" +
            "        \"portfolioName\": \"MZ세대 언어\",\n" +
            "        \"portfolioDescription\": \"어남선생 류수영, 레시피 여왕 박복순 박솔미, 국민아들 찬또배기 이찬원이 치열한 경쟁을 예고\",\n" +
            "        \"portfolioURL\": \"http://sports.hankooki.com/news/articleView.html?idxno=6798068\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"Portfolio\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"portfolioThumbnail\": \"https://image.shutterstock.com/image-photo/osaka-japan-june-24-2017-600w-669537982.jpg\",\n" +
            "        \"portfolioName\": \"MZ세대 언어\",\n" +
            "        \"portfolioDescription\": \"어남선생 류수영, 레시피 여왕 박복순 박솔미, 국민아들 찬또배기 이찬원이 치열한 경쟁을 예고\",\n" +
            "        \"portfolioURL\": \"http://sports.hankooki.com/news/articleView.html?idxno=6798068\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"blockType\": \"MarkDown\",\n" +
            "      \"fieldValues\": {\n" +
            "        \"markdownText\": \"# Multi Project Extension 제작 회고 \\n ## 제작 동기 \\n 현재 다니고 있는 ECount 회사는 Client/Server Framework를 자체 제작해서 사용한다.  \\n신규 Framework를 제작 중인데 핵심 개념은 모듈화다.  \"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    @BeforeEach
    public void setup(){
        builderService = new BuilderServiceImpl(projectRepository, portfolioRepository, profileRepository, careerRepository, userRepository);
    }

    @Test
    void updateBuilder() throws Exception {
         ObjectMapper mapper = new ObjectMapper();
        assertThat(builderService.updateBuilder(mapper.readValue(json, Map.class))).isEqualTo(true);
    }

    @Test
    void testSave() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        assertThat(builderService.testSave(mapper.readValue(profile, Map.class))).isEqualTo(true);
    }
}
