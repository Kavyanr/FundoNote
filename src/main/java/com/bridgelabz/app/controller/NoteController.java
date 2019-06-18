package com.bridgelabz.app.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.Note;
import  com.bridgelabz.app.service.NoteService;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoteController {

	@Autowired
	private NoteService noteService;
	  
//    @Autowired
//    private NoteRepository userRepository;
	
	@PostMapping(value = "/note/{token}")
	public Note createUser(@PathVariable String token, @RequestBody Note note, HttpServletRequest request) {
         return noteService.noteCreate(note, token);

	}
	
//	@PostMapping(value = "/createnote")
//	public Note createUser( @RequestBody Note note, HttpServletRequest request) {
//         return noteService.noteCreate(note, request);
//
//	}
	@PutMapping(value = "/note/{token}")
	// @RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public Note updateNote(@PathVariable String token, @RequestBody Note note, HttpServletRequest request) {
	System.out.println("updating"+   note.getNoteId());
	return noteService.updateNote(token, note);     //request.getHeader("token"), note);
	}

	// delete
	@DeleteMapping(value = "/note/{noteId}")
	// @RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE)
	public String deleteNote( @PathVariable int noteId, HttpServletRequest request) {
	System.out.println("I am token at delete method :" + request.getHeader("token"));
	// boolean deleteNote = noteService.deleteNote(request.getHeader("token"),
	return noteService.deleteNote( noteId);
	// note);

	}
	



	// fetch

	@GetMapping(value = "/retrievenote")
	public List<Note> getNotes(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.getNotes(request.getHeader("token"));
		// System.out.println("-->" + b);

	}
	@Cacheable(value = "users", key = "#userId")
	@GetMapping("/testRedis/{userId}")
	//@ApiResponse(response = String.class, message = "Test Redis	", code = 200)
	public String testRedis(@ApiParam("userId") @PathVariable String userId) {
	return "Success" + userId;
	}

	//@CachePut(key = "test")
	@Cacheable(value = "users", key = "#userId")
	@PostMapping("/testRedis/{userId}")
	@ApiResponse(response = String.class, message = "Test Redis post", code = 200)
	public String postRedis(@ApiParam("userId") @PathVariable String userId) {
	return "{}";
	}
	
}