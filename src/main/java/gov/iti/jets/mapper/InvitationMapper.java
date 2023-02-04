package gov.iti.jets.mapper;

import gov.iti.jets.dto.InvitationDto;
import gov.iti.jets.entity.Invitation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class InvitationMapper implements BaseMapper<Invitation, InvitationDto>{

    @Override
    public InvitationDto toDTO(Invitation invitation) {
        InvitationDto dto = InvitationDto.builder()
                .id(invitation.getId())
                .senderId(invitation.getSenderId())
                .receiverId(invitation.getReceiverId())
                .createdOn(invitation.getCreatedOn().toString())
                .status(invitation.getStatus())
                .isSeen(invitation.isSeen())
                .build();
        return dto;
}

    @Override
    public Invitation toEntity(InvitationDto invitationDto) {
        Invitation entity = Invitation.builder()
                .id(invitationDto.getId())
                .senderId(invitationDto.getSenderId())
                .receiverId(invitationDto.getReceiverId())
                .createdOn(Date.valueOf(invitationDto.getCreatedOn()))
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
