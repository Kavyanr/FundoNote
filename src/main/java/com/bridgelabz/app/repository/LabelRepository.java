package com.bridgelabz.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.app.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    public Optional<Label> findByUserid(int varifiedUserid);

    public Optional<Label> findByUseridAndLabelid(int varifiedUserid, int userid);

    public void deleteByUseridAndLabelid(int varifiedUserid, int userid);

    //public Optional<Label> findAll();

	public Label findByLabelidAndUserid(int labelid, int varifiedUserId);

	public void deleteByLabelid(int labelid);




}