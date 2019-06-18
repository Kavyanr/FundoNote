package com.bridgelabz.app.service;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.app.model.Label;
import com.bridgelabz.app.model.Note;




public interface NoteService {

	Note updateNote(String header, Note note);
	//public Note noteCreate(Note note, HttpServletRequest request);

	public Note noteCreate(Note note, String token);

	//boolean deleteNote(String token, Note note);

	List<Note> getNotes(String header);
	
	Label updateLabel(String header, Label label);

    Label createLabel(String header, Label label);

    boolean deleteLabel(String token, Label label);

    Optional<Label> getLabels(String header);

	String deleteNote(int noteId);

}