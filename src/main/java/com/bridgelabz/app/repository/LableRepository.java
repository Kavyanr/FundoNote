package com.bridgelabz.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.app.model.Lable;
import com.bridgelabz.app.model.Note;

@Repository
public interface LableRepository extends JpaRepository<Lable, Integer> {

    public List<Lable> findByUserid(int varifiedUserid);

    public Optional<Lable> findByUseridAndLableid(int varifiedUserid, int userid);

    public void deleteByUseridAndLableid(int varifiedUserid, int userid);

    public List<Lable> findAll();

	public Lable findByLableidAndUserid(int lableid, int varifiedUserId);




}