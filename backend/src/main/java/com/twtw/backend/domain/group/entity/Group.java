package com.twtw.backend.domain.group.entity;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.*;

import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String groupImage;
    private UUID leaderId;

    @OneToMany(
            mappedBy = "group",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(
            mappedBy = "group",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Plan> groupPlans = new ArrayList<>();

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public Group(final String name, final String groupImage, final Member leader) {
        this.name = name;
        this.groupImage = groupImage;
        this.leaderId = leader.getId();

        final GroupMember groupMember = new GroupMember(this, leader);
        this.groupMembers.add(groupMember);
        groupMember.changeGroupCode();
    }

    public void addPlan(final Plan plan) {
        this.groupPlans.add(plan);
    }

    public void inviteAll(final List<Member> friends) {
        friends.forEach(friend -> addGroupMember(new GroupMember(this, friend)));
    }

    private boolean addGroupMember(final GroupMember groupMember) {
        return this.groupMembers.add(groupMember);
    }
}
