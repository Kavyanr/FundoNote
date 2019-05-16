package com.bridgelabz.app.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bridgelabz.app.model.Lable;
import com.bridgelabz.app.model.Note;
import com.bridgelabz.app.repository.LableRepository;
import com.bridgelabz.app.repository.NoteRepository;
import com.bridgelabz.app.service.NoteService;
import com.bridgelabz.app.util.JsonToken;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	public NoteRepository noteRep;

	@Autowired
	public LableRepository lableRepository;

	@Autowired
	private JsonToken jsonToken;
	

	@Override
	public Note noteCreate(Note note, HttpServletRequest request) {
	String token1 = request.getHeader("token");
	int id = jsonToken.tokenVerification(token1);
	note.setUserId(id);
	return noteRep.save(note);
}
	
	@Override
	public Note updateNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("varifiedUserId :" + varifiedUserId);
		Optional<Note> maybeNote = noteRep.findByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		
		System.out.println("maybeNote :" + maybeNote);
		Note presentNote = maybeNote.map(existingNote -> {
			System.out.println("noteee here");
			existingNote.setDescription(
					note.getDescription() != null ? note.getDescription() : maybeNote.get().getDescription());
			existingNote.setTitle(note.getTitle() != null ? note.getTitle() : maybeNote.get().getTitle());
			return existingNote;
		}).orElseThrow(() -> new RuntimeException("Note Not Found"));

		return noteRep.save(presentNote);
	}

	@Override
	public boolean deleteNote(String token, Note note) {
		int varifiedUserId = jsonToken.tokenVerification(token);
	System.out.println("i m deletemethod :"+varifiedUserId);
		
	Note n= noteRep.findByNoteIdAndUserId(note.getNoteId(),varifiedUserId);
	
	noteRep.deleteById(n.getNoteId());
		return true;
	}

	@Override
	public List<Note> fetchNote(String token) {
		int varifiedUserId = jsonToken.tokenVerification(token);
		System.out.println("i m in fetch :"+varifiedUserId);
//		public List getAllNote() {
//			return (List) noteRep.findAll();
//		}
		List<Note> notes = (List<Note>) noteRep.findByUserId(varifiedUserId);
	
		return notes;
	}

	@Override
    public Lable createLable(String token, Lable lable) {
        int varifiedUserId = jsonToken.tokenVerification(token);
        System.out.println("note creation :" + varifiedUserId);
        lable.setUserid(varifiedUserId);
        return lableRepository.save(lable);
    }

    @Override
    public Lable updateLable(String token, Lable lable) {
        int varifiedUserId = jsonToken.tokenVerification(token);
        System.out.println("varifiedUserId :" + varifiedUserId);
        Optional<Lable> maybeLable = lableRepository.findByUseridAndLableid(varifiedUserId, lable.getLableid());
        System.out.println("maybeLable :" + maybeLable);
        Lable presentNote = maybeLable.map(existingNote -> {
            System.out.println("noteee here");
            existingNote.setLablename(
                    lable.getLablename() != null ? lable.getLablename() : maybeLable.get().getLablename());
            return existingNote;
        }).orElseThrow(() -> new RuntimeException("Note Not Found"));

        return lableRepository.save(presentNote);
    }
    
   ///
    @Override
	public boolean deleteLable(String token, Lable lable) {
		int varifiedUserId = jsonToken.tokenVerification(token);
	System.out.println("i m deletemethod :"+varifiedUserId);
		
    Lable lable1=  lableRepository.findByLableidAndUserid(lable.getLableid(),varifiedUserId);
	
	lableRepository.deleteById(lable.getLableid());
		return true;
	}
    
    
    
//
//    @Override
//    public boolean deleteLable(String token, Lable lable) {
//        int varifiedUserId = jsonToken.tokenVerification(token);
//        lableRepository.deleteByUseridAndLableid(varifiedUserId, lable.getLableid());
//        return true;
//    }

    @Override
    public List<Lable> fetchLable(String token) {
        int varifiedUserId = jsonToken.tokenVerification(token);
        System.out.println("i m in fetch :" + varifiedUserId);

        List<Lable> lable = (List<Lable>) lableRepository.findByUserid(varifiedUserId);

        return lable;
    }


}   