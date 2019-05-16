package com.bridgelabz.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.Note;
import com.bridgelabz.app.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public Note createUser(@RequestBody Note note, HttpServletRequest request) {

		return noteService.noteCreate(note, request);

	}
	// update

	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public Note updateNote(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("token");
		return noteService.updateNote(token, note);
	}

	@RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE)
	public void deleteNote(@RequestBody Note note, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
	    noteService.deleteNote(request.getHeader("token"), note);
		// System.out.println("-->" + b);

	}

	// fetch

	@RequestMapping(value = "/fetchNote", method = RequestMethod.GET)
	public List<Note> fetchNote(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.fetchNote(request.getHeader("token"));
		// System.out.println("-->" + b);

	}

}