package com.twtw.backend.domain.plan.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.request.PlanMemberRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.repository.PlanRepository;
import com.twtw.backend.fixture.group.GroupEntityFixture;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.fixture.place.PlaceDetailsFixture;
import com.twtw.backend.fixture.place.PlaceEntityFixture;
import com.twtw.backend.fixture.plan.PlanEntityFixture;
import com.twtw.backend.support.service.LoginTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@DisplayName("PlanService의")
class PlanServiceTest extends LoginTest {

    @Autowired private PlanService planService;
    @Autowired private GroupRepository groupRepository;
    @Autowired private PlanRepository planRepository;

    @Test
    @DisplayName("목적지 검색이 수행되는가")
    void searchPlanDestination() {
        // given
        final SearchDestinationRequest given =
                new SearchDestinationRequest("스타벅스", 123.321, 123.123, 1, CategoryGroupCode.CE7);

        // when
        // final PlanDestinationResponse result = planService.searchPlanDestination(given);

        // then
        // assertThat(result.getResults()).isNotNull();
    }

    @Test
    @DisplayName("계획 저장이 수행되는가")
    void savePlan() {
        // given
        final UUID groupId = groupRepository.save(GroupEntityFixture.HDJ_GROUP.toEntity(loginUser)).getId();

        // when
        final PlanResponse planResponse =
                planService.savePlan(
                        new SavePlanRequest(
                                groupId, PlaceDetailsFixture.FIRST_PLACE.toPlaceDetails()));

        // then
        final Optional<Plan> result = planRepository.findById(planResponse.getPlanId());
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("계획 참여가 수행되는가")
    void joinPlan() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        final Plan plan =
                planRepository.save(
                        new Plan(
                                member,
                                PlaceEntityFixture.FIRST_PLACE.toEntity(),
                                GroupEntityFixture.BTS_GROUP.toEntity(loginUser)));
        final UUID planId = plan.getId();

        // when
        planService.joinPlan(new PlanMemberRequest(planId));

        // then
        final Plan result = planRepository.findById(planId).orElseThrow();
        assertThat(result.getPlanMembers()).hasSize(2);
    }

    @Test
    @DisplayName("계획 나가기가 수행되는가") // TODO: 계획에 1명 있는데 나가는 경우 생각해보기
    void outPlan() {
        // given
        final Plan plan = PlanEntityFixture.SECOND_PLACE.toEntity(loginUser, GroupEntityFixture.HDJ_GROUP.toEntity(loginUser));
        final UUID planId = planRepository.save(plan).getId();

        // when
        planService.outPlan(new PlanMemberRequest(planId));

        // then
        final Plan result = planRepository.findById(planId).orElseThrow();
        assertThat(result.getPlanMembers()).isEmpty();
    }

    @Test
    @DisplayName("PK로 계획 조회가 수행되는가") // TODO Fixture 사용하여 저장시 에러 확인
    void getPlanById() {
        // given
        final UUID planId =
                planRepository
                        .save(
                                new Plan(
                                        loginUser,
                                        PlaceEntityFixture.SECOND_PLACE.toEntity(),
                                        GroupEntityFixture.HDJ_GROUP.toEntity(loginUser)))
                        .getId();

        // when
        final PlanInfoResponse result = planService.getPlanById(planId);

        // then
        assertThat(result.getPlanId()).isEqualTo(planId);
    }

    @Test
    @DisplayName("삭제가 수행되는가") // TODO Fixture 사용하여 저장시 에러 확인
    void deletePlan() {
        // given
        final UUID planId =
                planRepository
                        .save(
                                new Plan(
                                        loginUser,
                                        PlaceEntityFixture.SECOND_PLACE.toEntity(),
                                        GroupEntityFixture.HDJ_GROUP.toEntity(loginUser)))
                        .getId();

        // when
        planService.deletePlan(planId);

        // then
        assertThat(planRepository.findById(planId)).isEmpty();
    }
}
