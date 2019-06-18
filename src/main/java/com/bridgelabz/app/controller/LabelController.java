package com.bridgelabz.app.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.Label;
import com.bridgelabz.app.service.NoteService;

@RestController
public class LabelController {
	@Autowired
	private NoteService noteService;

	// Create
	@PostMapping(value = "/createlable")
	public Label createLabel(@RequestBody Label label, HttpServletRequest request) {

		return noteService.createLabel(request.getHeader("token"), label);
	}

	// update

	@PutMapping(value = "/updatelable")
	public Label updateLabel(@RequestBody Label label, HttpServletRequest request) {

		return noteService.updateLabel(request.getHeader("token"), label);
	}

	// delete

	@DeleteMapping(value = "/deletelable")
	public void deleteLabel(@RequestBody Label label, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
		noteService.deleteLabel(request.getHeader("token"), label);

	}

	// fetch

	@GetMapping(value = "/fetchlable")
	public Optional<Label> getlabels(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.getLabels(request.getHeader("token"));

	}

}
