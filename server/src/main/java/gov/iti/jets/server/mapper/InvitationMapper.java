package gov.iti.jets.server.mapper;


import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.dto.UserCardDto;
import gov.iti.jets.server.entity.Invitation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class InvitationMapper implements BaseMapper<Invitation, InvitationDto>{

    @Override
    public InvitationDto toDTO(Invitation invitation) {
        UserCardDto userCardDto = new UserCardDto();
        userCardDto.setId(invitation.getSenderId());
        InvitationDto dto = InvitationDto.builder()
                .id(invitation.getId())
                .userCardDto(userCardDto)
                .receiverId(invitation.getReceiverId())
                .createdOn(invitation.getCreatedOn())
                .status(invitation.getStatus())
                .isSeen(invitation.isSeen())
                .build();
        return dto;
    }

    @Override
    public Invitation toEntity(InvitationDto invitationDto) {
        Invitation entity = Invitation.builder()
                .id(invitationDto.getId())
                .senderId(invitationDto.getUserCardDto().getId())
                .receiverId(invitationDto.getReceiverId())
                .createdOn(invitationDto.getCreatedOn())
                .status(invitationDto.getStatus())
                .isSeen(invitationDto.isSeen())
                .build();
        return entity;
    }

    @Override
    public ArrayList<InvitationDto> toDTOs(Collection<Invitation> invitations) {
        return (ArrayList<InvitationDto>) invitations.
                stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArrayList<Invitation> toEntities(Collection<InvitationDto> invitationDtos) {
        return (ArrayList<Invitation>) invitationDtos.
                stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}