package com.bridgelabz.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.app.model.Label;
import com.bridgelabz.app.model.Note;
import com.bridgelabz.app.repository.LabelRepository;
import com.bridgelabz.app.repository.NoteRepository;
import com.bridgelabz.app.service.NoteService;
import com.bridgelabz.app.util.JWTUtil;




@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteRepository noteRep;

	@Autowired
	private LabelRepository labelRepository;

	@Override
	public Note noteCreate(Note note, String token) {
	//String token1 = request.getHeader("token");
	int id = JWTUtil.tokenVerification(token);
	Date date= new Date();
	LocalDateTime time=LocalDateTime.now();
	note.setCreadtedtime(time);
	note.setUserId(id);
	return noteRep.save(note);
}
//	@Override
//	public Note noteCreate(Note note, HttpServletRequest request) {
//		String token1 = request.getHeader("token");
//		int id = JWTUtil.tokenVerification(token1);
//		note.setUserId(id);
//		return noteRep.save(note);
//	}
	@Override
	public String deleteNote( int noteId) {
	// int verifiedUserId = JsonUtil.tokenVerification(token);
	//noteRep.deleteByUserIdAndNoteId(Util.tokenVerification(token), noteId);
	noteRep.deleteByNoteId(noteId);
	return "Deleted";
	}


	
	@Override
	public Note updateNote(String token, Note note) {
	// int verifiedUserId = JsonUtil.tokenVerification(token);
	System.out.println("varifiedUserId :" + JWTUtil.tokenVerification(token));
	Optional<Note> maybeNote = noteRep.findByUserIdAndNoteId(JWTUtil.tokenVerification(token), note.getNoteId());
	System.out.println("maybeNote :" + maybeNote);
	Note presentNote = maybeNote.map(existingNote -> {
	System.out.println("noteee here");
	existingNote.setDescription(
	note.getDescription() != null ? note.getDescription() : maybeNote.get().getDescription());
	existingNote.setTitle(note.getTitle() != null ? note.getTitle() : maybeNote.get().getTitle());
	existingNote.setIntrash(note.isIntrash());
	existingNote.setIsarchive(note.isIsarchive());
	existingNote.setIspinned(note.isIspinned());

	return existingNote;
	}).orElseThrow(() -> new RuntimeException("Note Not Found"));
	System.out.println(presentNote);

	return noteRep.save(presentNote);
	}

	@Override
	public List<Note> getNotes(String token) {
		int verifiedUserId = JWTUtil.tokenVerification(token);
		System.out.println("i m in fetch :"+verifiedUserId);
//		public List getAllNote() {
//			return (List) noteRep.findAll();
//		}
		List<Note> notes =  noteRep.findByUserId(verifiedUserId);
	
		return notes;
	}

	@Override
    public Label createLabel(String token, Label label) {
        int verifiedUserId = JWTUtil.tokenVerification(token);
        System.out.println("note creation :" + verifiedUserId);
        label.setUserid(verifiedUserId);
        return labelRepository.save(label);
    }

    @Override
    public Label updateLabel(String token, Label label) {
        int verifiedUserId = JWTUtil.tokenVerification(token);
        System.out.println("varifiedUserId :" + verifiedUserId);
        Optional<Label> maybeLabel = labelRepository.findByUseridAndLabelid(verifiedUserId, label.getLabelid());
        System.out.println("maybeLabel :" + maybeLabel);
        Label presentNote = maybeLabel.map(existingNote -> {
            System.out.println("noteee here");
            existingNote.setLabelname(
                    label.getLabelname() != null ? label.getLabelname() : maybeLabel.get().getLabelname());
            return existingNote;
        }).orElseThrow(() -> new RuntimeException("Note Not Found"));

        return labelRepository.save(presentNote);
    }
    
   ///
    @Override
	public boolean deleteLabel(String token, Label label) {
		int verifiedUserId = JWTUtil.tokenVerification(token);
	System.out.println("i m deletemethod :"+verifiedUserId);
		
    Label newlabel=  labelRepository.findByLabelidAndUserid(label.getLabelid(),verifiedUserId);
	
	labelRepository.deleteByLabelid(newlabel.getLabelid());
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
    public Optional<Label> getLabels(String token) {
        int verifiedUserId = JWTUtil.tokenVerification(token);
        System.out.println("i m in fetch :" + verifiedUserId);

        Optional<Label> label = (Optional<Label>) labelRepository.findByUserid(verifiedUserId);

        return label;
    }
   

}   