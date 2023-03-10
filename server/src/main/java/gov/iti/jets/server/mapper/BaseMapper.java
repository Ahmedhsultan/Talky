package gov.iti.jets.server.mapper;


import gov.iti.jets.common.dto.BaseDto;
import gov.iti.jets.server.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;

public interface BaseMapper <E extends BaseEntity, D extends BaseDto>{
    D toDTO(final E e);

    E toEntity(final D d);

    ArrayList<D> toDTOs(final Collection<E> e);

    ArrayList<E> toEntities(final Collection<D> ds);

}
