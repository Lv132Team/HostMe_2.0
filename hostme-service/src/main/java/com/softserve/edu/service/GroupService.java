package com.softserve.edu.service;

import java.util.Set;

import com.softserve.edu.model.Group;

public interface GroupService {

    public Set<Group> findAll();

    public Group findOne(Long id);

    public void delete(Group group);

    public void create(Group group);

    public void update(Group group);

}
