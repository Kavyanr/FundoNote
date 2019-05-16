package com.bridgelabz.app.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.bridgelabz.app.model.Lable;
import com.bridgelabz.app.model.Note;

public interface NoteService {

	Note updateNote(String header, Note note);

	public Note noteCreate(Note note, HttpServletRequest request);

	boolean deleteNote(String token, Note note);

	List<Note> fetchNote(String header);
	
	Lable updateLable(String header, Lable lable);

    Lable createLable(String header, Lable lable);

    boolean deleteLable(String token, Lable lable);

    List<Lable> fetchLable(String header);

}