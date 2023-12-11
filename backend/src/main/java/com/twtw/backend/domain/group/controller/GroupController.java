package com.twtw.backend.domain.group.controller;

import com.twtw.backend.domain.group.dto.request.InviteGroupRequest;
import com.twtw.backend.domain.group.dto.request.JoinGroupRequest;
import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.request.OutGroupRequest;
import com.twtw.backend.domain.group.dto.request.UpdateLocationRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.dto.response.SimpleGroupInfoResponse;
import com.twtw.backend.domain.group.service.GroupService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupInfoResponse> getGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<GroupInfoResponse> makeGroup(
            @RequestBody MakeGroupRequest makeGroupRequest) {
        return ResponseEntity.ok(groupService.makeGroup(makeGroupRequest));
    }

    @PostMapping("/join")
    public ResponseEntity<SimpleGroupInfoResponse> joinGroup(
            @RequestBody JoinGroupRequest joinGroupRequest) {
        return ResponseEntity.ok(groupService.joinGroup(joinGroupRequest));
    }

    @PostMapping("/invite")
    public ResponseEntity<GroupInfoResponse> inviteGroup(
            @RequestBody InviteGroupRequest inviteGroupRequest) {
        return ResponseEntity.ok(groupService.inviteGroup(inviteGroupRequest));
    }

    @PostMapping("/share/{id}")
    public ResponseEntity<Void> shareLocation(@PathVariable UUID id) {
        groupService.shareLocation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/unshare/{id}")
    public ResponseEntity<Void> unShareLocation(@PathVariable UUID id) {
        groupService.unShareLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/share/{id}")
    public ResponseEntity<ShareInfoResponse> getShare(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getShare(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupInfoResponse>> getMyGroups() {
        return ResponseEntity.ok(groupService.getMyGroups());
    }

    @PostMapping("location")
    public ResponseEntity<Void> updateLocation(
            @RequestBody final UpdateLocationRequest updateLocationRequest) {
        groupService.updateLocation(updateLocationRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("out")
    public ResponseEntity<Void> outGroup(@RequestBody final OutGroupRequest outGroupRequest) {
        groupService.outGroup(outGroupRequest);
        return ResponseEntity.noContent().build();
    }
}
